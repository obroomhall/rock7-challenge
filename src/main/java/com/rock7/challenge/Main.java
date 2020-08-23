package com.rock7.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rock7.challenge.database.MySqlWorker;
import com.rock7.challenge.math.Distance;
import com.rock7.challenge.model.*;
import com.rock7.challenge.model.parse.Position;
import com.rock7.challenge.model.parse.Race;
import com.rock7.challenge.model.parse.Team;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {

    private static final String RESOURCES_DIR = "src/main/resources/";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final int LAST_SEEN_THRESHOLD_MINUTES = 15;

    private static final Distance.TwoLocationsConsumer distanceAlgorithm = Distance::pythagorean; // or Distance::haversine

    public static void main(String[] args) throws SQLException, IOException {
        new Main().run();
    }

    private void run() throws SQLException, IOException {
        MySqlWorker mySqlWorker = new MySqlWorker();
        List<Moment> moments = createMomentsFromJson(RESOURCES_DIR + "positions.json");
        insertMomentsIntoDatabase(mySqlWorker, RESOURCES_DIR + "create_moments.sql", moments);
        correctSightings(mySqlWorker, RESOURCES_DIR + "all_sightings_by_millis.sql");
        mySqlWorker.closeConnection();
    }

    private List<Moment> createMomentsFromJson(String filename) throws IOException {

        Race race = new ObjectMapper().readValue(new File(filename), Race.class);

        List<Moment> moments = new ArrayList<>();
        for (Team team : race.getTeams()) {
            for (Position position : team.getPositions()) {
                moments.add(Moment.fromPosition(position, team.getSerial()));
            }
        }

        return moments;
    }

    private void insertMomentsIntoDatabase(MySqlWorker mySqlWorker, String tableCreationFilename, List<Moment> moments)
            throws SQLException, FileNotFoundException
    {
        mySqlWorker.executeFileUpdate(tableCreationFilename);

        PreparedStatement preparedStatement = mySqlWorker.prepareInsertStatement(
                Moment.getDatabaseTableName(),
                Moment.getDatabaseTableColumns()
        );

        for (Moment moment : moments) {
            moment.addSqlInsertStatementToBatch(preparedStatement);
        }

        preparedStatement.executeLargeBatch();
        preparedStatement.close();
    }

    private void correctSightings(MySqlWorker mySqlWorker, String getSightingsFilename) throws SQLException, FileNotFoundException {

        ResultSet allSightingsResult = mySqlWorker.executeFileQuery(getSightingsFilename);

        Date currentDay = null;
        List<Double> falsePositives = new ArrayList<>(); // see README for false positive explanation
        List<DailySightings> dailySightingsList = new ArrayList<>();
        Map<String, LastSighting> sightingMap = new HashMap<>();
        while(allSightingsResult.next())
        {
            Sighting sighting = Sighting.fromResultSet(allSightingsResult);
            Instant currentSightingTime = Instant.ofEpochMilli(sighting.getGpsAtMillis());

            if (currentDay == null) {
                currentDay = Date.from(currentSightingTime.truncatedTo(ChronoUnit.DAYS));
            }

            // on first record of new day, collect all sightings from previous day
            if (!Date.from(currentSightingTime.truncatedTo(ChronoUnit.DAYS)).equals(currentDay)
                    && !sightingMap.isEmpty()
            ) {
                dailySightingsList.add(combineSightings(currentDay, sightingMap));
                sightingMap.clear();
                currentDay = Date.from(currentSightingTime.truncatedTo(ChronoUnit.DAYS));
            }

            // calculate distance using provided consumer (e.g. pythagorean, haversine)
            double distance = Distance.fromStartAndEndLocation(
                    distanceAlgorithm,
                    sighting.getObservingTeamLocation(),
                    sighting.getVisibleTeamLocation()
            );

            // increment sightings if visible team has not been since for threshold (e.g. 15 minutes)
            if (distance < Distance.getMaxViewDistanceKilometers()) {
                String key = sighting.getObservingTeam() + ":" + sighting.getVisibleTeam();
                if (sightingMap.containsKey(key)) {

                    LastSighting lastSighting = sightingMap.get(key);
                    boolean lastSeenGreaterThanThreshold = lastSighting.getLastSeen()
                            .plus(LAST_SEEN_THRESHOLD_MINUTES, ChronoUnit.MINUTES)
                            .isBefore(currentSightingTime);

                    if (lastSeenGreaterThanThreshold) {
                        lastSighting.updateLastSeen(currentSightingTime, true);
                    } else {
                        lastSighting.updateLastSeen(currentSightingTime, false);
                    }
                } else {
                    sightingMap.put(key, new LastSighting(currentSightingTime, 1));
                }
            } else {
                falsePositives.add(distance);
            }
        }

        if (!sightingMap.isEmpty()) {
            dailySightingsList.add(combineSightings(currentDay, sightingMap));
            sightingMap.clear();
        }

        printStatistics(falsePositives, dailySightingsList);
    }

    private DailySightings combineSightings(Date day, Map<String, LastSighting> sightingMap) {
        Map<Integer, Integer> sightingsPerTeam = new HashMap<>();
        for (Map.Entry<String, LastSighting> sighting : sightingMap.entrySet()) {
            int teamSerial = Integer.parseInt(sighting.getKey().split(":")[0]);
            if (sightingsPerTeam.containsKey(teamSerial)) {
                sightingsPerTeam.merge(teamSerial, sighting.getValue().getTimesSeen(), Integer::sum);
            } else {
                sightingsPerTeam.put(teamSerial, sighting.getValue().getTimesSeen());
            }
        }
        return new DailySightings(day, sightingsPerTeam);
    }

    private void printStatistics(List<Double> falsePositives, List<DailySightings> dailySightingsList) {
        System.out.printf("%nTotal false positives:          %d%n", falsePositives.size());
        System.out.printf("Max viewing distance:           %.6f km%n", Distance.getMaxViewDistanceKilometers());
        System.out.printf("Min false positive:             %.6f km%n", falsePositives.stream().min(Double::compareTo)
                .orElse(null));
        System.out.printf("Max false positive:             %.6f km%n", falsePositives.stream().max(Double::compareTo)
                .orElse(null));
        System.out.printf("Theoretical max false positive: %.6f km%n", Math.sqrt(2) * Distance.getMaxViewDistanceKilometers());

        System.out.printf("%nSightings with a threshold of: %d minutes%n", LAST_SEEN_THRESHOLD_MINUTES);
        for (DailySightings dailySightings : dailySightingsList) {
            System.out.printf(
                    "%s | %.2f%n",
                    DATE_FORMAT.format(dailySightings.getDay()),
                    dailySightings.getSightings()
                            .values()
                            .stream()
                            .mapToInt(i -> i)
                            .average()
                            .orElseThrow(IllegalArgumentException::new)
            );
        }
    }
}
