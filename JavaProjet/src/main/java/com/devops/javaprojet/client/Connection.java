package com.devops.javaprojet.client;

import com.devops.javaprojet.common.Message;
import com.devops.javaprojet.server.MainServer;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Connection implements Runnable {
    public void setGameController(GameController gameController) { this.gameController = gameController; }

    public void setLoginController(LoginController loginController) { this.loginController = loginController; }

    private Client client;
    private Socket socket;
    private ObjectInputStream in;

    public ObjectOutputStream getOut() {
        return out;
    }

    private GameController gameController;

    private LoginController loginController;

    private ObjectOutputStream out;

    public Connection(Client client, Socket socket, GameController gameController, LoginController loginController) {
        this.client = client;
        this.socket = socket;
        this.gameController = gameController;
        this.loginController = loginController;
    }

    public void run() {
        try {
            System.out.println("Connexion établie avec " + socket.getRemoteSocketAddress());

            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            boolean isActive = true;

            while (isActive) {
                Message userInput = (Message)in.readObject();
                System.out.println(userInput.getSender() + " a ecrit " + userInput.getContent());
                switch (userInput.getType()) {
                    case 201: //Message public
                        Platform.runLater(() -> {
                            Text receivedMessage = new Text(userInput.getSender() + ": " + userInput.getContent() + "\n");
                            gameController.getChatFlow().getChildren().add(receivedMessage);
                        });
                        break;
                    case 202://Login Error
                    case 204: //Register Error
                        loginController.showStatus(userInput.getContent());
                        break;
                    case 203://Login
                    case 205: //Register
                        Platform.runLater(() -> {
                            try {
                                GameApplication.LoadGameScene();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    case 206:
                        break;
                    default:
                        break;
                }
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
