package com.devops.javaprojet.server.api;

import com.devops.javaprojet.server.database.DatabaseDAO;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Api {
    DatabaseDAO databaseDAO;

    public Api(DatabaseDAO databaseDAO) {
        this.databaseDAO = databaseDAO;
    }

    public String Login(String username, String password) throws SQLException {
        switch (databaseDAO.GetUserInfo(username.toLowerCase(), password)){
            case 0:
                return "L'association username/password est incorrect !";
            case 1:
                return "Connexion valide bienvenue " + username + " !";
            default:
                return "Erreur lors de la connexion";
        }
    }

    public String Register(String username, String password) throws SQLException, NoSuchAlgorithmException {

        switch (databaseDAO.GetUser(username.toLowerCase())){
            case 0:
                return "Le pseudo saisie existe deja";
            case 1:
                databaseDAO.InsertNewUser(username.toLowerCase(), password);
                return "Bienvenue " + username + " !";
            default:
                return "Erreur lors de la connexion";
        }
    }

    public void LogMessage(int idUser, String message) throws SQLException { databaseDAO.InsertMessage(idUser, message); }

    public Map<String, String> GetInfoCountryRandom() throws SQLException {
        Map<String, String> map = new HashMap<>();
        ResultSet result = databaseDAO.GetAllCountries();
        result.last();
        Random randNum = new Random();
        int randInt = randNum.nextInt(result.getRow()) + 1;
        result = databaseDAO.GetCountryFromID(randInt);
        ResultSetMetaData data = result.getMetaData();
        for (int i = 1; i <= data.getColumnCount(); i++) {
            map.put(data.getColumnName(i), result.getString(i));
        }
        return map;
    }

    public Map<String, String> GetInfoCountry(int idCountry) throws SQLException {
        Map<String, String> map = new HashMap<>();
        ResultSet result = databaseDAO.GetCountryFromID(idCountry);
        ResultSetMetaData data = result.getMetaData();
        for (int i = 1; i <= data.getColumnCount(); i++) { map.put(data.getColumnName(i), result.getString(i)); }
        return map;
    }

    public void AddScore(int idUser, int score) throws SQLException {
        databaseDAO.updateScore(idUser, score);
    }
}
