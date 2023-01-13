package com.devops.javaprojet.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.devops.javaprojet.client.GameController.*;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Flagame");
        stage.setScene(scene);
        stage.show();

        chatText = new TextField();
        chatText.setOnAction(event -> envoyerMessage());
    }

    public static void main(String[] args) {
        launch();
        String address = "localhost";
        Integer port = 5000;
        Client c = new Client(address, port);
    }

    public void envoyerMessage(){

    }
}