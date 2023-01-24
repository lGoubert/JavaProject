package com.devops.javaprojet.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

public class GameController {
    public TextField getChatText() {
        return chatText;
    }

    @FXML
    private TextField chatText;

    public void setChatFlow(TextFlow chatFlow) {
        this.chatFlow = chatFlow;
    }

    public TextFlow getChatFlow() {
        return chatFlow;
    }

    @FXML
    private TextFlow chatFlow;

}
