
package com.rock7.challenge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "positions",
    "marker",
    "name",
    "serial"
})
public class Team {

    @JsonProperty("positions")
    private List<Position> positions = new ArrayList<Position>();
    @JsonProperty("marker")
    private Integer marker;
    @JsonProperty("name")
    private String name;
    @JsonProperty("serial")
    private Integer serial;

    @JsonProperty("positions")
    public List<Position> getPositions() {
        return positions;
    }

    @JsonProperty("positions")
    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @JsonProperty("marker")
    public Integer getMarker() {
        return marker;
    }

    @JsonProperty("marker")
    public void setMarker(Integer marker) {
        this.marker = marker;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("serial")
    public Integer getSerial() {
        return serial;
    }

    @JsonProperty("serial")
    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "Team{" +
                "positions=" + positions +
                ", marker=" + marker +
                ", name='" + name + '\'' +
                ", serial=" + serial +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(positions, team.positions) &&
                Objects.equals(marker, team.marker) &&
                Objects.equals(name, team.name) &&
                Objects.equals(serial, team.serial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions, marker, name, serial);
    }
}
