
package com.rock7.challenge.model;

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
public class Positions {

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
        Positions positions = (Positions) o;
        return Objects.equals(teams, positions.teams) &&
                Objects.equals(raceUrl, positions.raceUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teams, raceUrl);
    }
}
