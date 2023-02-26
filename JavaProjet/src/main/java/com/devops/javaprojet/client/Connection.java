package com.devops.javaprojet.client;

import com.devops.javaprojet.common.Message;
import com.devops.javaprojet.server.MainServer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Pattern;

public class Connection implements Runnable {
    public void setGameController(GameController gameController) { this.gameController = gameController; }

    public void setLoginController(LoginController loginController) { this.loginController = loginController; }

    public void setScoreboardController(ScoreboardController scoreboardController) { this.scoreboardController = scoreboardController; }

    public Socket getSocket() { return socket; }

    private Client client;
    private Socket socket;
    private ObjectInputStream in;

    public ObjectOutputStream getOut() {
        return out;
    }

    private GameController gameController;

    private LoginController loginController;

    private ScoreboardController scoreboardController;

    private ObjectOutputStream out;

    public Connection(Client client, Socket socket, GameController gameController, LoginController loginController, ScoreboardController scoreboardController) {
        this.client = client;
        this.socket = socket;
        this.gameController = gameController;
        this.loginController = loginController;
        this.scoreboardController = scoreboardController;
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
                        if(gameController == null){ continue; }
                        Platform.runLater(() -> {
                            Text receivedMessage = new Text(userInput.getSender() + ": " + userInput.getContent() + "\n");
                            gameController.getChatFlow().getChildren().add(receivedMessage);
                        });
                        break;
                    case 202://Login Error
                    case 204: //Register Error
                        if(scoreboardController == null){ continue; }
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
                        if(gameController == null){ continue; }
                        Platform.runLater(() -> {
                            try {
                                String imageData = "data:image/png;base64," + userInput.getContent();
                                String base64Data = imageData.split(",")[1];

                                byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
                                ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
                                BufferedImage Bufferedimage = ImageIO.read(bis);

                                File outputFile = new File("output.png");
                                ImageIO.write(Bufferedimage, "png", outputFile);

                                File file = new File("output.png");
                                Image image = new Image(file.toURI().toString());

                                gameController.getImageView().setImage(image);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    case 207:
                        if(scoreboardController == null){ continue; }
                        String input = userInput.getContent();
                        System.out.println(input);
                        int index = input.indexOf("|");
                        String name = input.substring(0, index);
                        String value = input.replace(name + "|", "");
                        Score score = new Score(name, value);
                        Platform.runLater(() -> {
                            scoreboardController.addScore(score);
                        });
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
