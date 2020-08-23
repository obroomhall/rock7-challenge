
package com.rock7.challenge.model.parse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "teams",
    "raceUrl"
})
public class Race {

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
}
