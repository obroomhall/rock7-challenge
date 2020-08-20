
package com.rock7.challenge.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rock7.challenge.math.Distance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Moment implements SqlObject {

    private final Integer id;
    private final Double latitude;
    private final Double longitude;
    private final Long gpsAtMillis;
    private final Integer teamSerial;

    private final Double latitudeUpperVisibleBound;
    private final Double latitudeLowerVisibleBound;
    private final Double longitudeUpperVisibleBound;
    private final Double longitudeLowerVisibleBound;

    public Moment(Integer id, Double latitude, Double longitude, Long gpsAtMillis, Integer teamSerial,
                  Double latitudeUpperVisibleBound, Double latitudeLowerVisibleBound, Double longitudeUpperVisibleBound,
                  Double longitudeLowerVisibleBound
    ) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.gpsAtMillis = gpsAtMillis;
        this.teamSerial = teamSerial;
        this.latitudeUpperVisibleBound = latitudeUpperVisibleBound;
        this.latitudeLowerVisibleBound = latitudeLowerVisibleBound;
        this.longitudeUpperVisibleBound = longitudeUpperVisibleBound;
        this.longitudeLowerVisibleBound = longitudeLowerVisibleBound;
    }

    public static Moment fromPosition(Position position) {
        Bounds bounds = Distance.getUpperAndLowerVisibleBounds(position.getLatitude(), position.getLongitude());
        return new Moment(
                position.getId(),
                position.getLatitude(),
                position.getLongitude(),
                position.getGpsAtMillis(),
                position.getTeamSerial(),
                bounds.getUpperLatitudeBound(),
                bounds.getLowerLatitudeBound(),
                bounds.getUpperLongitudeBound(),
                bounds.getLowerLongitudeBound()
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

    @Override
    public List<PreparedStatement> toSqlInsertStatements(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Positions VALUES (?,?,?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.setDouble(2, latitude);
        preparedStatement.setDouble(3, longitude);
        preparedStatement.setLong(4, gpsAtMillis);
        preparedStatement.setInt(5, teamSerial);
        preparedStatement.setDouble(6, latitudeUpperVisibleBound);
        preparedStatement.setDouble(7, latitudeLowerVisibleBound);
        preparedStatement.setDouble(8, longitudeUpperVisibleBound);
        preparedStatement.setDouble(9, longitudeLowerVisibleBound);
        return Collections.singletonList(preparedStatement);
    }
}
