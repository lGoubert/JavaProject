package com.devops.javaprojet.server;

import com.devops.javaprojet.common.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;

public class Connection implements Runnable {
    private Server server;
    private ServerSocket serverSocket;

    public Connection(Server server) {
        this.server = server;
        try {
            this.serverSocket = new ServerSocket(server.getPort());
            serverSocket.setSoTimeout(0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket sockNewClient = serverSocket.accept();

                ConnectedClient newClient = new ConnectedClient(server, sockNewClient);
                newClient.setId(server.getNumClients());
                server.addClient(newClient);

                Thread threadNewClient = new Thread(newClient);
                threadNewClient.start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
