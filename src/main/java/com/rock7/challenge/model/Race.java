
package com.rock7.challenge.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "teams",
    "raceUrl"
})
public class Race implements SqlObject {

    @JsonProperty("teams")
    private List<Team> teams = new ArrayList<Team>();
    @JsonProperty("raceUrl")
    private String raceUrl;

    @JsonProperty("teams")
    public List<Team> getTeams() {
        return teams;
    }

    @JsonProperty("teams")
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @JsonProperty("raceUrl")
    public String getRaceUrl() {
        return raceUrl;
    }

    @JsonProperty("raceUrl")
    public void setRaceUrl(String raceUrl) {
        this.raceUrl = raceUrl;
    }

    @Override
    public List<PreparedStatement> toSqlInsertStatements(Connection connection) throws SQLException {
        List<PreparedStatement> preparedStatements = new ArrayList<>();
        for (Team team : teams) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Races VALUES (?,?)");
            preparedStatement.setString(1, raceUrl);
            preparedStatement.setInt(2, team.getSerial());
            preparedStatements.add(preparedStatement);
        }
        return preparedStatements;
    }

    @Override
    public String toString() {
        return "Positions{" +
                "teams=" + teams +
                ", raceUrl='" + raceUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return Objects.equals(teams, race.teams) &&
                Objects.equals(raceUrl, race.raceUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teams, raceUrl);
    }
}
