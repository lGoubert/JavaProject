package com.devops.javaprojet.client;

import com.devops.javaprojet.common.Message;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class GameApplication extends Application {

    private static Client client;
    private static GameController gameController;
    private static LoginController loginController;
    private static ScoreboardController scoreboardController;
    private static String LastProposition;
    private static String actualUsername;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setTitle("Flagame");
        Image icon = new Image(getClass().getResourceAsStream("/com/devops/javaprojet/client/icon.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        loginController = fxmlLoader.getController();
        client = new Client("localhost", 1234, null, loginController, null, stage);

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

            keepLastMessages(gameController.getChatFlow());
        }
    }

    public static void SendingResponse(KeyEvent event, GameController gameController) throws IOException {
        if (KeyCode.ENTER == event.getCode()) {
            Message messageToServer = new Message("", gameController.getImageText().getText(), 104);
            client.getConnection().getOut().writeObject(messageToServer);
            client.getConnection().getOut().flush();

            LastProposition = gameController.getImageText().getText();
            gameController.getImageText().setText("");
            keepLastMessages(gameController.getChatFlow());
        }
        else if (KeyCode.UP == event.getCode()){
            gameController.getImageText().setText(LastProposition);
        }
        else if (KeyCode.DOWN == event.getCode()){
            gameController.getImageText().setText("");
        }
    }

    public static void LoadScoreScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("scoreboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        client.getStage().setTitle("Flagame");
        Image icon = new Image(client.getClass().getResourceAsStream("/com/devops/javaprojet/client/icon.png"));
        client.getStage().getIcons().add(icon);
        client.getStage().setScene(scene);
        client.getStage().show();

        client.setGameController(null);
        client.setLoginController(null);
        client.setScoreboardController(fxmlLoader.getController());

        Message messageToServer = new Message("","",105);
        client.getConnection().getOut().writeObject(messageToServer);
        client.getConnection().getOut().flush();
    }

    public static void LoadMainScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        client.getStage().setTitle("Flagame");
        Image icon = new Image(client.getClass().getResourceAsStream("/com/devops/javaprojet/client/icon.png"));
        client.getStage().getIcons().add(icon);
        client.getStage().setScene(scene);
        client.getStage().show();

        client.setGameController(null);
        client.setLoginController(fxmlLoader.getController());
        client.setScoreboardController(null);
    }

    public static void LoadGameScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        client.getStage().setTitle("Flagame — " + actualUsername);
        Image icon = new Image(client.getClass().getResourceAsStream("/com/devops/javaprojet/client/icon.png"));
        client.getStage().getIcons().add(icon);
        client.getStage().setScene(scene);
        client.getStage().show();

        client.setGameController(fxmlLoader.getController());
        client.setLoginController(null);
        client.setScoreboardController(null);

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

        if (fxmlLoader.getController() instanceof GameController gameController) {
            gameController.getImageText().setOnKeyPressed(event -> {
                try {
                    SendingResponse(event, gameController);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public static void Register(String username, String password) throws IOException {
        actualUsername = username;
        Message messageToServer = new Message("",username + "|" + password,103);
        client.getConnection().getOut().writeObject(messageToServer);
        client.getConnection().getOut().flush();
    }

    public static void Login(String username, String password) throws IOException {
        actualUsername = username;
        Message messageToServer = new Message("",username + "|" + password,102);
        client.getConnection().getOut().writeObject(messageToServer);
        client.getConnection().getOut().flush();
    }

    public static void keepLastMessages(TextFlow textFlow) {
        // Compter le nombre de lignes dans le TextFlow
        int numLines = textFlow.getChildren().size();

        // Supprimer les lignes les plus anciennes si nécessaire
        if (numLines > 25) {
            textFlow.getChildren().remove(0, numLines - 25);
        }
    }
}