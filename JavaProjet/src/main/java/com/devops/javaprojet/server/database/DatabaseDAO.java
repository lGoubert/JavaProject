package com.devops.javaprojet.server.database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseDAO {
    private Connection connection;

    public DatabaseDAO(java.sql.Connection connection) {
        this.connection = connection;
    }

    public ResultSet GetAllCountries() throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM countries;");
        ResultSet result = prepareStatement.executeQuery();
        result.first();
        return result;
    }

    public ResultSet GetCountryFromName(String countryName) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM countries WHERE country = ?;");
        prepareStatement.setString(1, countryName);
        ResultSet result = prepareStatement.executeQuery();
        result.first();
        return result;
    }

    public ResultSet GetCountryFromID(int id) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM countries WHERE id = ?;");
        prepareStatement.setInt(1, id);
        ResultSet result = prepareStatement.executeQuery();
        result.first();
        return result;
    }

    public ResultSet GetScoreboard(int scoreboardSize) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT id_user, SUM(score) AS Scoreboard FROM games_scores GROUP BY id_user ORDER BY scoreboard DESC LIMIT " + scoreboardSize + ";");
        ResultSet result = prepareStatement.executeQuery();
        result.first();
        return result;
    }

    public ResultSet GetScoreboard() throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT id_user, SUM(score) AS Scoreboard FROM games_scores GROUP BY id_user ORDER BY scoreboard DESC LIMIT 10;");
        ResultSet result = prepareStatement.executeQuery();
        result.first();
        return result;
    }

    public void InsertMessage(int idSender, String message) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO messages (`id`, `id_user`, `message`)VALUES (NULL,?,?);");
        prepareStatement.setInt(1, idSender);
        prepareStatement.setString(2, message);
        ResultSet result = prepareStatement.executeQuery();
    }

    public ResultSet GetAllMessageByUserID(int idUser) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT message WHERE id_user = ?;");
        prepareStatement.setInt(1, idUser);
        ResultSet result = prepareStatement.executeQuery();
        result.first();
        return  result;
    }

    public void InsertNewUser(String username, String password) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO users (`id`, `username`, `password`) VALUES (NULL,?,?);");
        prepareStatement.setString(1, username);
        prepareStatement.setString(2, password);
        ResultSet result = prepareStatement.executeQuery();
    }

    public String GetUsernameWithID(int idUser) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT username WHERE id = ?;");
        prepareStatement.setInt(1, idUser);
        ResultSet result = prepareStatement.executeQuery();
        result.first();
        return result.getString("username");
    }

    public int GetUserInfo(String username, String password) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT id, username WHERE username = ? AND password= ?");
        prepareStatement.setString(1, username);
        prepareStatement.setString(1, password);
        ResultSet result = prepareStatement.executeQuery();
        if (result.next() == false) {
            return 0;
        }
        return 1;
    }
}
