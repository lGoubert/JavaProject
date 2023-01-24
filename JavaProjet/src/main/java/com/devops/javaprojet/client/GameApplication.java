package com.devops.javaprojet.client;

import com.devops.javaprojet.common.Message;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {

    private static Client client;
    private static GameController gameController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Flagame");
        stage.setScene(scene);
        stage.show();

        gameController = fxmlLoader.getController();
        client = new Client("localhost", 1234, gameController);


        //Event lorsque l'utilisateur appuie sur Entry
        if (fxmlLoader.getController() instanceof GameController gameController) {
            gameController.getChatText().setOnKeyPressed(event -> {
                try {
                    SendingMessage(event, gameController);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void SendingMessage(KeyEvent event, GameController gameController) throws IOException {
        if (KeyCode.ENTER == event.getCode()) {
            Text messageToChat = new Text("Vous: " + gameController.getChatText().getText() + "\n" );
            gameController.getChatFlow().getChildren().add(messageToChat);

            Message messageToServer = new Message("",gameController.getChatText().getText(),1);
            client.getConnection().getOut().writeObject(messageToServer);
            client.getConnection().getOut().flush();

            gameController.getChatText().setText("");
        }
    }
}