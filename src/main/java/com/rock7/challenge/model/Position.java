
package com.rock7.challenge.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.sql.*;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "alert",
    "altitude",
    "type",
    "dtfKm",
    "id",
    "gpsAt",
    "sogKnots",
    "battery",
    "cog",
    "dtfNm",
    "txAt",
    "longitude",
    "latitude",
    "gpsAtMillis",
    "sogKmph"
})
public class Position implements SqlObject {

    @JsonProperty("alert")
    private Boolean alert;
    @JsonProperty("altitude")
    private Integer altitude;
    @JsonProperty("type")
    private String type;
    @JsonProperty("dtfKm")
    private Double dtfKm;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("gpsAt")
    private Timestamp gpsAt;
    @JsonProperty("sogKnots")
    private Double sogKnots;
    @JsonProperty("battery")
    private Integer battery;
    @JsonProperty("cog")
    private Integer cog;
    @JsonProperty("dtfNm")
    private Double dtfNm;
    @JsonProperty("txAt")
    private Timestamp txAt;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("gpsAtMillis")
    private Long gpsAtMillis;
    @JsonProperty("sogKmph")
    private Double sogKmph;

    @JsonProperty("alert")
    public Boolean getAlert() {
        return alert;
    }

    @JsonProperty("alert")
    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    @JsonProperty("altitude")
    public Integer getAltitude() {
        return altitude;
    }

    @JsonProperty("altitude")
    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("dtfKm")
    public Double getDtfKm() {
        return dtfKm;
    }

    @JsonProperty("dtfKm")
    public void setDtfKm(Double dtfKm) {
        this.dtfKm = dtfKm;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("gpsAt")
    public Timestamp getGpsAt() {
        return gpsAt;
    }

    @JsonProperty("gpsAt")
    public void setGpsAt(Timestamp gpsAt) {
        this.gpsAt = gpsAt;
    }

    @JsonProperty("sogKnots")
    public Double getSogKnots() {
        return sogKnots;
    }

    @JsonProperty("sogKnots")
    public void setSogKnots(Double sogKnots) {
        this.sogKnots = sogKnots;
    }

    @JsonProperty("battery")
    public Integer getBattery() {
        return battery;
    }

    @JsonProperty("battery")
    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    @JsonProperty("cog")
    public Integer getCog() {
        return cog;
    }

    @JsonProperty("cog")
    public void setCog(Integer cog) {
        this.cog = cog;
    }

    @JsonProperty("dtfNm")
    public Double getDtfNm() {
        return dtfNm;
    }

    @JsonProperty("dtfNm")
    public void setDtfNm(Double dtfNm) {
        this.dtfNm = dtfNm;
    }

    @JsonProperty("txAt")
    public Timestamp getTxAt() {
        return txAt;
    }

    @JsonProperty("txAt")
    public void setTxAt(Timestamp txAt) {
        this.txAt = txAt;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("gpsAtMillis")
    public Long getGpsAtMillis() {
        return gpsAtMillis;
    }

    @JsonProperty("gpsAtMillis")
    public void setGpsAtMillis(Long gpsAtMillis) {
        this.gpsAtMillis = gpsAtMillis;
    }

    @JsonProperty("sogKmph")
    public Double getSogKmph() {
        return sogKmph;
    }

    @JsonProperty("sogKmph")
    public void setSogKmph(Double sogKmph) {
        this.sogKmph = sogKmph;
    }

    @Override
    public List<PreparedStatement> toSqlInsertStatements(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Positions VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        );
        preparedStatement.setBoolean(1, alert);
        preparedStatement.setInt(2, altitude);
        preparedStatement.setObject(3, type);
        preparedStatement.setDouble(4, dtfKm);
        preparedStatement.setInt(5, id);
        preparedStatement.setTimestamp(6, gpsAt);
        preparedStatement.setDouble(7, sogKnots);
        preparedStatement.setInt(8, battery);
        preparedStatement.setInt(9, cog);
        preparedStatement.setDouble(10, dtfNm);
        preparedStatement.setTimestamp(11, txAt);
        preparedStatement.setDouble(12, longitude);
        preparedStatement.setDouble(13, latitude);
        preparedStatement.setLong(14, gpsAtMillis);
        preparedStatement.setDouble(15, sogKmph);
        return Collections.singletonList(preparedStatement);
    }

    @Override
    public String toString() {
        return "Position{" +
                "alert=" + alert +
                ", altitude=" + altitude +
                ", type='" + type + '\'' +
                ", dtfKm=" + dtfKm +
                ", id=" + id +
                ", gpsAt='" + gpsAt + '\'' +
                ", sogKnots=" + sogKnots +
                ", battery=" + battery +
                ", cog=" + cog +
                ", dtfNm=" + dtfNm +
                ", txAt='" + txAt + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", gpsAtMillis=" + gpsAtMillis +
                ", sogKmph=" + sogKmph +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(alert, position.alert) &&
                Objects.equals(altitude, position.altitude) &&
                Objects.equals(type, position.type) &&
                Objects.equals(dtfKm, position.dtfKm) &&
                Objects.equals(id, position.id) &&
                Objects.equals(gpsAt, position.gpsAt) &&
                Objects.equals(sogKnots, position.sogKnots) &&
                Objects.equals(battery, position.battery) &&
                Objects.equals(cog, position.cog) &&
                Objects.equals(dtfNm, position.dtfNm) &&
                Objects.equals(txAt, position.txAt) &&
                Objects.equals(longitude, position.longitude) &&
                Objects.equals(latitude, position.latitude) &&
                Objects.equals(gpsAtMillis, position.gpsAtMillis) &&
                Objects.equals(sogKmph, position.sogKmph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alert, altitude, type, dtfKm, id, gpsAt, sogKnots, battery, cog, dtfNm, txAt, longitude, latitude, gpsAtMillis, sogKmph);
    }
}
