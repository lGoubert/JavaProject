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
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        try {
            socket = new Socket(address, port);
            Connection clientReceive = new Connection(this, socket);
            Thread receive = new Thread(clientReceive);
            receive.start();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
