package com.devops.javaprojet.client;

import com.devops.javaprojet.common.Message;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;

public class Connection implements Runnable {
    private Client client;
    private Socket socket;
    private ObjectInputStream in;

    public ObjectOutputStream getOut() {
        return out;
    }

    private GameController gameController;

    private ObjectOutputStream out;

    public Connection(Client client, Socket socket, GameController gameController) {
        this.client = client;
        this.socket = socket;
        this.gameController = gameController;
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
                if(userInput.getType() == 1){
                    Platform.runLater(() -> {
                        Text receivedMessage = new Text(userInput.getSender() + " :" + userInput.getContent() + "\n" );
                        gameController.getChatFlow().getChildren().add(receivedMessage);
                    });

                }
                else if (userInput.getType() == 2){

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
