
package com.rock7.challenge.model;

import com.rock7.challenge.math.Distance;
import com.rock7.challenge.model.parse.Position;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Moment {

    private static final String databaseTableName = "Moments";
    private static final Integer databaseTableColumns = 10;

    private final Integer id;
    private final Double latitude;
    private final Double longitude;
    private final Timestamp gpsAt;
    private final Long gpsAtMillis;
    private final Integer teamSerial;

    private final Double latitudeUpperVisibleBound;
    private final Double latitudeLowerVisibleBound;
    private final Double longitudeUpperVisibleBound;
    private final Double longitudeLowerVisibleBound;

    public Moment(Integer id, Double latitude, Double longitude, Timestamp gpsAt, Long gpsAtMillis, Integer teamSerial,
                  Double latitudeUpperVisibleBound, Double latitudeLowerVisibleBound, Double longitudeUpperVisibleBound,
                  Double longitudeLowerVisibleBound
    ) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.gpsAt = gpsAt;
        this.gpsAtMillis = gpsAtMillis;
        this.teamSerial = teamSerial;
        this.latitudeUpperVisibleBound = latitudeUpperVisibleBound;
        this.latitudeLowerVisibleBound = latitudeLowerVisibleBound;
        this.longitudeUpperVisibleBound = longitudeUpperVisibleBound;
        this.longitudeLowerVisibleBound = longitudeLowerVisibleBound;
    }

    public static Moment fromPosition(Position position, int teamSerial) {
        VisibleBounds visibleBounds = Distance.getUpperAndLowerVisibleBounds(position.getLatitude(), position.getLongitude());
        return new Moment(
                position.getId(),
                position.getLatitude(),
                position.getLongitude(),
                position.getGpsAt(),
                position.getGpsAtMillis(),
                teamSerial,
                visibleBounds.getUpperLatitudeBound(),
                visibleBounds.getLowerLatitudeBound(),
                visibleBounds.getUpperLongitudeBound(),
                visibleBounds.getLowerLongitudeBound()
        );
    }

    public Integer getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Timestamp getGpsAt() {
        return gpsAt;
    }

    public Long getGpsAtMillis() {
        return gpsAtMillis;
    }

    public Integer getTeamSerial() {
        return teamSerial;
    }

    public Double getLatitudeUpperVisibleBound() {
        return latitudeUpperVisibleBound;
    }

    public Double getLatitudeLowerVisibleBound() {
        return latitudeLowerVisibleBound;
    }

    public Double getLongitudeUpperVisibleBound() {
        return longitudeUpperVisibleBound;
    }

    public Double getLongitudeLowerVisibleBound() {
        return longitudeLowerVisibleBound;
    }

    public static String getDatabaseTableName() {
        return databaseTableName;
    }

    public static Integer getDatabaseTableColumns() {
        return databaseTableColumns;
    }

    public void addSqlInsertStatementToBatch(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, id);
        preparedStatement.setDouble(2, latitude);
        preparedStatement.setDouble(3, longitude);
        preparedStatement.setTimestamp(4, gpsAt);
        preparedStatement.setLong(5, gpsAtMillis);
        preparedStatement.setInt(6, teamSerial);
        preparedStatement.setDouble(7, latitudeUpperVisibleBound);
        preparedStatement.setDouble(8, latitudeLowerVisibleBound);
        preparedStatement.setDouble(9, longitudeUpperVisibleBound);
        preparedStatement.setDouble(10, longitudeLowerVisibleBound);
        preparedStatement.addBatch();
        preparedStatement.clearParameters();
    }
}
