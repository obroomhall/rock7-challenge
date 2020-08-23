package com.rock7.challenge.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MySqlWorker {

    private final Connection connection;

    public MySqlWorker() throws SQLException {
        this.connection = MySqlConfig.getDefaultConnection();
    }

    private String readFileToString(String filename) throws FileNotFoundException {
        File createMomentsTableQueryFile = new File(filename);
        return new Scanner(createMomentsTableQueryFile).useDelimiter("\\Z").next();
    }

    public int executeFileUpdate(String filename) throws FileNotFoundException, SQLException {
        return executeUpdate(readFileToString(filename));
    }

    public ResultSet executeFileQuery(String filename) throws FileNotFoundException, SQLException {
        return executeQuery(readFileToString(filename));
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return connection.createStatement().executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        return connection.createStatement().executeUpdate(query);
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public PreparedStatement prepareInsertStatement(String table, int columns) throws SQLException {

        Character[] chars = new Character[columns];
        Arrays.fill(chars, '?');
        String placeholders = Arrays.stream(chars)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String query = String.format(
                "INSERT INTO %s VALUES (%s)",
                table,
                placeholders
        );

        return prepareStatement(query);
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
