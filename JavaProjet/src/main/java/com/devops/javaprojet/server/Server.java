package com.devops.javaprojet.server;

public class Server {
    private int port;

    public int getPort() {
        return port;
    }

    public Server(int port) {
        this.port = port;
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }
}
