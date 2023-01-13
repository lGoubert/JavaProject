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
        databaseDAO.InsertNewUser(username.toLowerCase(), password);
    }

    public String Login(String username, String password) throws SQLException {
        switch (databaseDAO.GetUserInfo(username.toLowerCase(), password)){
            case 0:
                return "L'ensemble username/password est incorrect";
            case 1:
                return "Connexion valide bienvenue ! " + username;
            default:
                return "Erreur lors de la connexion";
        }
    }

    public void GetGameResponse("")
}
