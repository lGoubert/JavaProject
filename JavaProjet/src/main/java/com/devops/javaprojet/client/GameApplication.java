package com.devops.javaprojet.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Flagame");
        stage.setScene(scene);
        stage.show();
        if (fxmlLoader.getController() instanceof GameController gameController) {
            gameController.getChatText().setOnKeyPressed(event -> envoyerMessage(event, gameController.getChatText()));
        }
    }

    public static void main(String[] args) {
        launch();
        String address = "localhost";
        Integer port = 5000;
        Client c = new Client(address, port);
    }

    public void envoyerMessage(KeyEvent event, TextField textField) {
        if (KeyCode.ENTER == event.getCode()) {

        }
    }
}