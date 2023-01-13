package com.devops.javaprojet.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;

public class Connection implements Runnable  {
    private Server server;
    private ServerSocket serverSocket;

    public Connection(Server server) {
        this.server = server;
        try {
            this.serverSocket = new ServerSocket(server.getPort());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Le serveur est en écoute sur le port " + server.getPort());

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept(); // Attente de la connexion d'un client
                System.out.println("Connexion établie avec " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine, outputLine;
                while ((inputLine = in.readLine()) != null) {
                    // Traitement de la requête du client
                    outputLine = "Réponse du serveur : " + inputLine;
                    System.out.println(outputLine);
                    out.println(outputLine);
                    if (inputLine.equals("Bye."))
                        break;
                }

                clientSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
