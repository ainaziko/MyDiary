package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.animations.Shake;

public class SignInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton1;

    @FXML
    protected TextField loginToSignIn;

    @FXML
    protected PasswordField passwordToSignIn;

    @FXML
    private Button signInButton;

    @FXML
    private Label existence;


    public int id = 0;

    User user = new User();

    @FXML
    void initialize() {
        existence.setVisible(false);
        signInButton.setOnAction(e -> {
            String loginText = loginToSignIn.getText().trim();
            String loginPassword = passwordToSignIn.getText().trim();
            if(!loginText.isEmpty() && !loginPassword.isEmpty()){
                loginUser(loginText,loginPassword);
            }else{
                existence.setVisible(true);
                Shake userLoginShake = new Shake(loginToSignIn);
                Shake userLoginShake1 = new Shake(passwordToSignIn);
                userLoginShake.playAnim();
                userLoginShake1.playAnim();

            }
        });

    }

    private void loginUser(String loginText, String loginPassword) {
        DataBaseHandler dbHandler = new DataBaseHandler();

        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);


        int counter = 0;
        while(true){
            try {
                if (!result.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            counter++;
            if(counter >= 1){
                ForeignKeyID fk = new ForeignKeyID();
                fk.putInfo(dbHandler.getIdUser(loginText));
                openNewScene("/sample/yaySample.fxml");
            }
        }
        if(counter == 0){
            System.out.println("User doesn't exist in db");
            Shake userLoginShake = new Shake(loginToSignIn);
            Shake userLoginShake1 = new Shake(passwordToSignIn);
            userLoginShake.playAnim();
            userLoginShake1.playAnim();
        }
    }

    public void goToSignUp(ActionEvent actionEvent) throws IOException {
        Stage stagee = (Stage) signInButton.getScene().getWindow();
        stagee.close();
        Stage stage = new Stage();
        Pane layout = FXMLLoader.load(getClass().getResource("signUpSample.fxml"));
        stage.setScene(new Scene(layout));
        stage.show();
    }

    public void openNewScene(String window){
        signUpButton1.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}