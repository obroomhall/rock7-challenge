package com.rock7.challenge.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Sighting {

    private final Long gpsAtMillis;
    private final Integer observingTeam;
    private final Location observingTeamLocation;
    private final Integer visibleTeam;
    private final Location visibleTeamLocation;

    public Sighting(Long gpsAtMillis, Integer observingTeam, Double observingTeamLatitude,
                    Double observingTeamLongitude, Integer visibleTeam, Double visibleTeamLatitude,
                    Double visibleTeamLongitude
    ) {
        this.gpsAtMillis = gpsAtMillis;
        this.observingTeam = observingTeam;
        this.observingTeamLocation = new Location(observingTeamLatitude, observingTeamLongitude);
        this.visibleTeam = visibleTeam;
        this.visibleTeamLocation = new Location(visibleTeamLatitude, visibleTeamLongitude);
    }

    public static Sighting fromResultSet(ResultSet resultSet) throws SQLException {
        return new Sighting(
                resultSet.getLong("gpsAtMillis"),
                resultSet.getInt("observingTeam"),
                resultSet.getDouble("observingTeamLatitude"),
                resultSet.getDouble("observingTeamLongitude"),
                resultSet.getInt("visibleTeam"),
                resultSet.getDouble("visibleTeamLatitude"),
                resultSet.getDouble("visibleTeamLongitude")
        );

    }

    public Long getGpsAtMillis() {
        return gpsAtMillis;
    }

    public Integer getObservingTeam() {
        return observingTeam;
    }

    public Location getObservingTeamLocation() {
        return observingTeamLocation;
    }

    public Integer getVisibleTeam() {
        return visibleTeam;
    }

    public Location getVisibleTeamLocation() {
        return visibleTeamLocation;
    }

    @Override
    public String toString() {
        return "Sighting{" +
                "gpsAtMillis=" + gpsAtMillis +
                ", observingTeam=" + observingTeam +
                ", observingTeamLocation=" + observingTeamLocation +
                ", visibleTeam=" + visibleTeam +
                ", visibleTeamLocation=" + visibleTeamLocation +
                '}';
    }
}
