package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SignUpController extends Yay{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private TextField lastName;

    @FXML
    private TextField city;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private Button signUpButton;

    @FXML
    private RadioButton maleCheckBox;

    @FXML
    private RadioButton femaleCheckBox;

    @FXML
    void initialize() {

        maleCheckBox.setOnMouseClicked(e->{
            femaleCheckBox.setSelected(false);
        });
        femaleCheckBox.setOnMouseClicked(e->{
            maleCheckBox.setSelected(false);
        });

        signUpButton.setOnAction(e -> {
            signUpNewUser();
        });

    }

    private void signUpNewUser() {
        DataBaseHandler dbHandler = new DataBaseHandler();
        String firstName =this.name.getText();
        String lastName = this.lastName.getText();
        String userName = this.login.getText();
        String password = this.password.getText();
        String city = this.city.getText();
        String gender = "";
        if(maleCheckBox.isSelected()){
            gender = "Male";
        }else{
            gender = "Female";}
        User user = new User(firstName,lastName,userName,password,city,gender);
        dbHandler.signUpUser(user);
        int idk = dbHandler.getIdUser(userName);
        ForeignKeyID fk = new ForeignKeyID();
        fk.putInfo(idk);
        System.out.println(idk);
        this.name.setText("");
        this.lastName.setText("");
        this.login.setText("");
        this.password.setText("");
        this.city.setText("");
        Note first = new Note(" "," ", idk);
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.addNoteToDataBase(first);
    }



    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Stage stagee = new Stage();
        Pane layout = FXMLLoader.load(getClass().getResource("signInSample.fxml"));
        stagee.setScene(new Scene(layout));
        stagee.show();
        Stage stage = (Stage) city.getScene().getWindow();
        stage.close();
    }
}
