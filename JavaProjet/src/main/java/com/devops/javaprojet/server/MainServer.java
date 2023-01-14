package com.devops.javaprojet.server;
import com.devops.javaprojet.server.database.Database;
import com.devops.javaprojet.server.database.DatabaseDAO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainServer {
    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {
        /* Connection a la base de données */
        Database database = new Database("jdbc:mariadb://45.155.169.116:6006/javaprojet","javaprojet","devops");
        DatabaseDAO dataDAO = new DatabaseDAO(database.getMariadbConnection());
        ResultSet result = dataDAO.GetAllCountries();
        while (result.next()) {
            var country = result.getString("country");
            System.out.println(country);
        }

        int port = 1234; // Le numéro de port sur lequel le serveur écoutera

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Le serveur est en écoute sur le port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept(); // Attente de la connexion d'un client
            System.out.println("Connexion établie avec " + clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                // Traitement de la requête du client
                outputLine = "Réponse du serveur : " + inputLine;
                out.println(outputLine);
                if (inputLine.equals("Bye."))
                    break;
            }

            clientSocket.close();
        }
    }
}
