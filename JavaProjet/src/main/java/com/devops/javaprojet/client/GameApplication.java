package com.devops.javaprojet.client;

import com.devops.javaprojet.common.Message;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class GameApplication extends Application {

    private static Client client;
    private static GameController gameController;
    private static LoginController loginController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Flagame");
        stage.setScene(scene);
        stage.show();

        /*
        gameController = fxmlLoader.getController();
        client = new Client("localhost", 1234, gameController, null);
        */

        loginController = fxmlLoader.getController();
        client = new Client("localhost", 1234, null, loginController, stage);

        //Ferme tout les thread ouvert a la fermeture du programme
       stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public static void SendingMessage(KeyEvent event, GameController gameController) throws IOException {
        if (KeyCode.ENTER == event.getCode()) {
            Text messageToChat = new Text("Vous: " + gameController.getChatText().getText() + "\n" );
            gameController.getChatFlow().getChildren().add(messageToChat);

            Message messageToServer = new Message("",gameController.getChatText().getText(),101);
            client.getConnection().getOut().writeObject(messageToServer);
            client.getConnection().getOut().flush();

            gameController.getChatText().setText("");
        }
    }

    public static void LoadGameScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        client.getStage().setTitle("Flagame");
        client.getStage().setScene(scene);
        client.getStage().show();

        client.setGameController(fxmlLoader.getController());
        client.setLoginController(null);

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

    public static void Register(String username, String password) throws IOException {
        Message messageToServer = new Message("",username + "|" + password,103);
        client.getConnection().getOut().writeObject(messageToServer);
        client.getConnection().getOut().flush();
    }

    public static void Login(String username, String password) throws IOException {
        Message messageToServer = new Message("",username + "|" + password,102);
        client.getConnection().getOut().writeObject(messageToServer);
        client.getConnection().getOut().flush();
    }
}