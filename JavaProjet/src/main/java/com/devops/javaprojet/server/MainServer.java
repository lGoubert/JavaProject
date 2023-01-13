package com.devops.javaprojet.server;

import java.net.*;
import com.devops.javaprojet.server.api.Api;
import com.devops.javaprojet.server.database.Database;
import com.devops.javaprojet.server.database.DatabaseDAO;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainServer {
    public static void main(String[] args) throws IOException {
        Integer port = 5000;
        Server server = new Server(port);
        if (server != null) {
            System.out.println("Server connected.");
        /* Connection a la base de données */
        Database database = new Database("jdbc:mariadb://45.155.169.116:6006/javaprojet","javaprojet","devops");
        DatabaseDAO dataDAO = new DatabaseDAO(database.getMariadbConnection());
        Api api = new Api(dataDAO);
        
        dataDAO.InsertNewUser("username", "password");
        ResultSet result = dataDAO.GetAllCountries();
        while (result.next()) {
            var country = result.getString("country");
            System.out.println(country);
        }

        }
    }

    private static void printUsage() {
        System.out.println("java server.Server <port>");
        System.out.println("\t<port>: server's port");
    }
}
