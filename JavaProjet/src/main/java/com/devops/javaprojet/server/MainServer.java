package com.devops.javaprojet.server;
import com.devops.javaprojet.server.api.Api;
import com.devops.javaprojet.server.database.Database;
import com.devops.javaprojet.server.database.DatabaseDAO;


import java.net.*;
import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MainServer {
    public static Api api;
    public static Map<String,String> flagMap;
    ;
    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {
        /* Connection a la base de données */
        Database database = new Database("jdbc:mariadb://45.155.169.116:6006/javaprojet","javaprojet","devops");
        DatabaseDAO dataDAO = new DatabaseDAO(database.getMariadbConnection());
        api = new Api(dataDAO);
        
        ResultSet result = dataDAO.GetAllCountries();
        while (result.next()) {
            var country = result.getString("country");
            System.out.println(country);
        }

        try {
            flagMap = api.GetInfoCountryRandom();
            System.out.println("Nouvelle réponse: " + flagMap.get("country"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Integer port = 1234;
        Server server = new Server(port);
        if (server != null) {
            System.out.println("Server connected.");
        }
    }
}
