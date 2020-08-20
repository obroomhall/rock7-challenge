package com.rock7.challenge;

import com.rock7.challenge.math.Distance;
import com.rock7.challenge.model.SqlObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        System.out.println(Distance.haversine(36.03, -86.53, 35.98, -86.48));
        System.out.println(Distance.pythagorean(36.03, -86.53, 35.98, -86.48));

//        Connection connection = MySqlConfig.getDefaultConnection();
//        ObjectMapper objectMapper = new ObjectMapper();
//        Race race = objectMapper.readValue(new File("src/main/resources/positions.json"), Race.class);
//
//        insertObjectToDatabase(race, connection);
//        for (Team team : race.getTeams()) {
//            insertObjectToDatabase(team, connection);
//            for (Position position : team.getPositions()) {
//                position.setTeamSerial(team.getSerial());
//                insertObjectToDatabase(position, connection);
//            }
//        }

    }

    private static void insertObjectToDatabase(SqlObject sqlObject, Connection connection) throws SQLException {
        List<PreparedStatement> preparedStatements = sqlObject.toSqlInsertStatements(connection);
        for (PreparedStatement preparedStatement : preparedStatements) {
            preparedStatement.executeUpdate();
        }
    }
}
