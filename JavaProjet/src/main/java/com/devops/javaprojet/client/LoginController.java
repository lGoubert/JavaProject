package com.devops.javaprojet.client;

import com.devops.javaprojet.common.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    TextField statusMessage;

    @FXML
    TextField loginUsername;
    @FXML
    PasswordField loginPassword;
    @FXML
    Button loginButton;
    @FXML
    TextField registerUsername;
    @FXML
    PasswordField registerPassword;
    @FXML
    Button registerButton;
    @FXML
    Button buttonScore;

    public void initialize(){
        statusMessage.setText("");
        statusMessage.setVisible(false);
        loginUsername.requestFocus();
    }

    //Button login
    @FXML public void loginButtonHandle() throws IOException {
        statusMessage.setText("");
        statusMessage.setVisible(false);
        String username = loginUsername.getText().replace("|", "");
        String password = loginPassword.getText().replace("|", "");
        System.out.println(username + "|" + password);
        GameApplication.Login(username, password);
    }

    //Button register
    @FXML public void registerButtonHandle() throws IOException {
            statusMessage.setText("");
            statusMessage.setVisible(false);
            String username = registerUsername.getText().replace("|", "");
            String password = registerPassword.getText().replace("|", "");
            System.out.println(username + "|" + password);
            GameApplication.Register(username, password);
    }

    @FXML public void scoreButtonHandle() throws IOException {
        GameApplication.LoadScoreScene();
    }

    public void showStatus(String status){
        statusMessage.setText(status);
        statusMessage.setVisible(true);
    }
}
