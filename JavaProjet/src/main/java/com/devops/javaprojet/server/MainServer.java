package com.devops.javaprojet.server;
import com.devops.javaprojet.server.database.Database;
import com.devops.javaprojet.server.database.DatabaseDAO;


import java.net.*;
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
        Integer port = 1234;
        Server server = new Server(port);
        if (server != null) {
            System.out.println("Server connected.");
        }
    }
}
