package com.devops.javaprojet.client;

import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public void setGameController(GameController gameController) {
        connection.setGameController(gameController);
        this.gameController = gameController;
    }
    public void setLoginController(LoginController loginController) {
        connection.setLoginController(loginController);
        this.loginController = loginController;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public Stage getStage() { return stage; }

    private String address;
    private int port;
    private Socket socket;
    public Connection getConnection() {
        return connection;
    }

    private Connection connection;

    private GameController gameController;
    private LoginController loginController;

    private Stage stage;
    public Client(String address, int port, GameController gameController, LoginController loginController, Stage stage) {
        this.address = address;
        this.port = port;
        this.gameController = gameController;
        this.loginController = loginController;
        this.stage = stage;

        try {
            socket = new Socket(address, port);
            connection = new Connection(this, socket, gameController, loginController);
            Thread connectionThread = new Thread(connection);
            connectionThread.start();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
