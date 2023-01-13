package com.devops.javaprojet.server.api;

import com.devops.javaprojet.server.database.DatabaseDAO;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Api {
    DatabaseDAO databaseDAO;

    public Api(DatabaseDAO databaseDAO) {
        this.databaseDAO = databaseDAO;
    }

    public void CreateUser(String username, String password) throws SQLException, NoSuchAlgorithmException {
        databaseDAO.InsertNewUser(username, password);
    }
}
