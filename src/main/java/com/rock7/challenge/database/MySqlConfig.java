package com.rock7.challenge.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConfig {

    private static final String url = "jdbc:mysql://localhost/%s?serverTimezone=%s";
    private static final String username = System.getenv("MYSQL_USERNAME");
    private static final String password = System.getenv("MYSQL_PASSWORD");
    private static final String databaseName = "rock7";
    private static final String timezone = "UTC";

    public static Connection getDefaultConnection() throws SQLException {
        Connection connection = getConnection(databaseName, timezone);
        connection.setAutoCommit(true);
        return connection;
    }

    public static Connection getConnection(String databaseName, String timezone) throws SQLException {
        return DriverManager.getConnection(String.format(url, databaseName, timezone), username, password);
    }
}
