package com.devops.javaprojet.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GameController {
    @FXML
    public static TextField chatText;

    public static String GetChatText() {
        return chatText.getText();
    }



}
