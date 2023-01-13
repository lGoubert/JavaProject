package com.devops.javaprojet.client;
import java.net.*;
import java.io.*;

public class MainClient {
    public static void main(String[] args) throws IOException {
        String serverHostname = "localhost"; // Adresse IP ou nom d'hôte du serveur
        int port = 5000; // Numéro de port sur lequel le serveur écoute

        Socket clientSocket = new Socket(serverHostname, port);
        System.out.println("Connexion établie avec " + clientSocket.getRemoteSocketAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Réponse du serveur : " + in.readLine());
            if (userInput.equals("Bye."))
                break;
        }

        clientSocket.close();
    }
}
