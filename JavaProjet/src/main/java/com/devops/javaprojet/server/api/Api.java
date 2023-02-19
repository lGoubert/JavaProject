package com.devops.javaprojet.server.api;

import com.devops.javaprojet.common.Message;
import com.devops.javaprojet.server.MainServer;
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

    public Message Login(String username, String password) throws SQLException {
        if(username.equals("")){ return new Message("", "Le pseudo ne peut pas être vide", 202); }

        if(password.equals("")){ return new Message("", "Le mot de passe ne peut pas être vide", 202); }

        switch (databaseDAO.GetUserInfo(username.toLowerCase(), password)){
            case 0:
                return new Message("", "L'association username/password est incorrect !", 202);
            case 1:
                return new Message("", "Connexion valide bienvenue " + username + " !", 203);
            default:
                return new Message("", "Erreur lors de la connexion", 202);
        }
    }

    public Message Register(String username, String password) throws SQLException, NoSuchAlgorithmException {
        if(username.equals("")){ return new Message("", "Le pseudo ne peut pas être vide", 204); }

        if(password.equals("")){ return new Message("", "Le mot de passe ne peut pas être vide", 204); }

        switch (databaseDAO.GetUser(username.toLowerCase())){
            case 0:
                return new Message("", "Le pseudo saisie existe deja", 204);
            case 1:
                databaseDAO.InsertNewUser(username.toLowerCase(), password);
                return new Message("", "Bienvenue " + username + " !", 205);
            default:
                return new Message("", "Erreur lors de la connexion", 204);
        }
    }

    public void LogMessage(int idUser, String message) throws SQLException { databaseDAO.InsertMessage(idUser, message); }

    public void LogMessage(String username, String message) throws SQLException {
        int id = getIdUser(username);
        databaseDAO.InsertMessage(id, message);
    }

    public int getIdUser(String username) throws SQLException {
        return databaseDAO.GetUser(username);
    }

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
        /*
          map.get("flag")
         */
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

    public void AddScore(String username, int score) throws SQLException {
        int id = getIdUser(username);
        databaseDAO.updateScore(id, score);
    }

    public ArrayList<String> GetScoreboard() throws SQLException {
        ArrayList<String> scores = new ArrayList<String>();
        ResultSet result = databaseDAO.GetScoreboard();
        while (result.next()) {
            int id = result.getInt("id_user");
            String user = databaseDAO.GetUserWithID(id);
            int score = result.getInt("Scoreboard");
            scores.add(user + "|" + score);
        }
        return scores;
    }
}

