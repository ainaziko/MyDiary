package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static java.util.Collections.reverse;

public class Yay extends SignInController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    protected TableView<List> listOfRecords;

    @FXML
    private TableColumn<List,String> noteTitlesColumn;

    @FXML
    private TableColumn<List, Integer> idCol;

    @FXML
    private TableColumn<List, String> noteCol;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField title;


    @FXML
    protected Button deleteButton;


    @FXML
    private Button newButton;

    @FXML
    private Button logOutButton;




    @FXML
    void initialize() {
        textArea.getStylesheets().add("sample/stylesheets/styles.css");
        title.getStylesheets().add("sample/stylesheets/styles.css");
        listOfRecords.getStylesheets().add("sample/stylesheets/styles.css");
        showNotes();
        setSelectedFirstRow();

        autoUpdate();

        listOfRecords.setOnMouseClicked(e->{ deleteButton.setDisable(false);openNote();});
        logOutButton.setOnAction(e-> { try { goBack(); } catch (IOException ioException) { ioException.printStackTrace(); }});

        newButton.setOnAction(e-> {
            clear();
            addNote();
            showNotes();
            setSelectedFirstRow();
            autoUpdate();
        });
        deleteButton.setOnAction(e ->{ deleteRecord(); setSelectedFirstRow();});
    }

    public void autoUpdate(){
        textArea.setOnKeyReleased(e->{
            updateRecord();
            textArea.requestFocus();
            textArea.end();
            System.out.println("key is pressed");
        });
        title.setOnKeyReleased(e->{
            updateRecord();
            title.requestFocus();
            title.end();
        });
    }

    public void setSelectedFirstRow(){
        listOfRecords.requestFocus();
        listOfRecords.getSelectionModel().selectFirst();
        listOfRecords.getSelectionModel().select(0);
        listOfRecords.getFocusModel().focus(0);
        openNote();
        title.requestFocus();
        title.end();
        deleteButton.setDisable(false);
    }



    protected void openNote() {
        List selectedItem = listOfRecords.getSelectionModel().getSelectedItem();
        String title = selectedItem.getTitle();
        String text = selectedItem.getNote();
        this.title.setText(title);
        textArea.setText(text);
    }

    private void clear() {
        title.setText("");
        textArea.setText("");
    }


    private void goBack() throws IOException {
        ForeignKeyID fk = new ForeignKeyID();
        fk.clearList();
        Stage stagee = (Stage) logOutButton.getScene().getWindow();
        stagee.close();
        Stage stage = new Stage();
        Pane layout = FXMLLoader.load(getClass().getResource("signInSample.fxml"));
        stage.setScene(new Scene(layout));
        stage.show();
    }



    protected void addNote(){
        int selectedRow = 0;
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        String title = this.title.getText();
        String note = this.textArea.getText();
        int id = this.id;
        Note currNote = new Note(title, note, id);
        dataBaseHandler.addNoteToDataBase(currNote);
        listOfRecords.requestFocus();
        listOfRecords.getSelectionModel().select(selectedRow);
        listOfRecords.getFocusModel().focus(selectedRow);
        openNote();
    }

    public Connection getConnection(){
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root","");
            return conn;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public ObservableList<List> getNotesList(){
        ForeignKeyID fk = new ForeignKeyID();
        ObservableList<List> notesList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM notes WHERE idFK = " + fk.getID();
        System.out.println(fk.getID());
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            List titles;
            while (rs.next()){
                titles = new List(rs.getString("title"), rs.getInt("id"),rs.getString("note"));
                notesList.add(titles);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return notesList;
    }
    public void showNotes(){
        ObservableList<List> list = getNotesList(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        reverse(list);
        noteTitlesColumn.setCellValueFactory(new PropertyValueFactory<List, String>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<List, Integer>("id"));
        noteCol.setCellValueFactory(new PropertyValueFactory<List,String>("note"));
        listOfRecords.setItems(list);
    }
    public int accessToId(){
        List selectedTitle = listOfRecords.getSelectionModel().getSelectedItem();
        System.out.println("it is an id: " + idCol.getId() + " text " +  idCol.getText());
        return selectedTitle.getId();
    }


    private void deleteRecord() {
        String query = "DELETE FROM notes WHERE id = " + accessToId();
        executeQuery(query);
        showNotes();
    }
    private void updateRecord(){
        int selectedRow = listOfRecords.getSelectionModel().getSelectedIndex();
        String query = "UPDATE notes SET title = '" + title.getText() + "', note = '" + textArea.getText() + "' WHERE id = " + accessToId() + "";
        executeQuery(query);
        showNotes();
        System.out.println("updated");
        listOfRecords.requestFocus();
        listOfRecords.getSelectionModel().select(selectedRow);
        listOfRecords.getFocusModel().focus(selectedRow);
        openNote();
        System.out.println("looooook " + accessToId());
    }


    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
