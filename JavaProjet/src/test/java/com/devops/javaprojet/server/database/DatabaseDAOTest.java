package com.devops.javaprojet.server.database;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseDAOTest {
    Database database = new Database("jdbc:mariadb://45.155.169.116:6006/javaprojet_test","javaprojet","devops");
    DatabaseDAO dataDAO = new DatabaseDAO(database.getMariadbConnection());

    DatabaseDAOTest() throws SQLException {}

    @Test
    void getAllCountries() throws SQLException {
        ResultSet result = dataDAO.GetAllCountries();
        while (result.next()) {
            var country = result.getString("country");
            assertEquals(country, country);
        }
    }

    @Test
    void getCountryFromName() throws SQLException {
        ResultSet result = dataDAO.GetCountryFromName("france");
        assertEquals("france", result.getString("country"));
    }

    @Test
    void getCountryFromID() throws SQLException {
        ResultSet result = dataDAO.GetCountryFromID(65);
        assertEquals("france", result.getString("country"));
    }

    @Test
    void getScoreboard() {
    }

    @Test
    void testGetScoreboard() throws SQLException {
        ResultSet result = dataDAO.GetScoreboard(2);
        assertEquals(result.getInt("scoreboard"), 15);
        result.next();
        assertEquals(result.getInt("scoreboard"), 13);

        result = dataDAO.GetScoreboard();
        assertEquals(result.getInt("scoreboard"), 15);
        result.next();
        assertEquals(result.getInt("scoreboard"), 13);
        result.next();
        assertEquals(result.getInt("scoreboard"), 9);
        result.next();
        assertEquals(result.getInt("scoreboard"), 8);
        result.next();
        assertEquals(result.getInt("scoreboard"), 7);
        result.next();
        assertEquals(result.getInt("scoreboard"), 6);
        result.next();
        assertEquals(result.getInt("scoreboard"), 5);
        result.next();
        assertEquals(result.getInt("scoreboard"), 4);
        result.next();
        assertEquals(result.getInt("scoreboard"), 3);
        result.next();
        assertEquals(result.getInt("scoreboard"), 2);
    }

    @Test
    void insertMessage() {
        
    }

    @Test
    void getAllMessageByUserID() {
    }

    @Test
    void insertNewUser() {
    }

    @Test
    void getUsernameWithID() {
    }
}