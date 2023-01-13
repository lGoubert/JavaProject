package com.devops.javaprojet.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    Connection connection;
    String url;
    String user;
    String password;

    public Database(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        connection = DriverManager.getConnection(url, user, password);
    }

    public java.sql.Connection getMariadbConnection() { return connection; }
}
