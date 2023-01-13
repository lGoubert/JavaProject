package com.devops.javaprojet.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    private String address;
    private int port;
    private Socket socket;

    public Connection getConnection() {
        return connection;
    }

    private Connection connection;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        try {
            socket = new Socket(address, port);
            connection = new Connection(this, socket);
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
