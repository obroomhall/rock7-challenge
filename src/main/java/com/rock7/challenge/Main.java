package com.rock7.challenge;

import com.rock7.challenge.model.SqlObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Main {

    private static final double FEET_ABOVE_SEA_LEVEL = 10;
    private static final double VIEW_DISTANCE_CONSTANT = 0.5736;
    private static final double MILES_TO_KILOMETER_CONSTANT = 1.60934;
    private static final double MAX_VIEW_DISTANCE_KILOMETERS =
            Math.sqrt(FEET_ABOVE_SEA_LEVEL/VIEW_DISTANCE_CONSTANT) * MILES_TO_KILOMETER_CONSTANT;

    public static void main(String[] args) throws SQLException, IOException {

        System.out.println(MAX_VIEW_DISTANCE_KILOMETERS + "km viewing distance");



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
