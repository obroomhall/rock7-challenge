
package com.rock7.challenge.model.parse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

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
}
