package com.rock7.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rock7.challenge.config.MySqlConfig;
import com.rock7.challenge.model.Position;
import com.rock7.challenge.model.Race;
import com.rock7.challenge.model.SqlObject;
import com.rock7.challenge.model.Team;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = MySqlConfig.getDefaultConnection();
        ObjectMapper objectMapper = new ObjectMapper();
        Race race = objectMapper.readValue(new File("src/main/resources/positions.json"), Race.class);

        insertObjectToDatabase(race, connection);
        for (Team team : race.getTeams()) {
            insertObjectToDatabase(team, connection);
            for (Position position : team.getPositions()) {
                position.setTeamSerial(team.getSerial());
                insertObjectToDatabase(position, connection);
            }
        }

    }

    private static void insertObjectToDatabase(SqlObject sqlObject, Connection connection) throws SQLException {
        List<PreparedStatement> preparedStatements = sqlObject.toSqlInsertStatements(connection);
        for (PreparedStatement preparedStatement : preparedStatements) {
            preparedStatement.executeUpdate();
        }
    }
}
