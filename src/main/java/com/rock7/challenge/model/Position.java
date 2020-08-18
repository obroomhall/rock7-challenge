
package com.rock7.challenge.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

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
public class Position {

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
    private String gpsAt;
    @JsonProperty("sogKnots")
    private Double sogKnots;
    @JsonProperty("battery")
    private Integer battery;
    @JsonProperty("cog")
    private Integer cog;
    @JsonProperty("dtfNm")
    private Double dtfNm;
    @JsonProperty("txAt")
    private String txAt;
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
    public String getGpsAt() {
        return gpsAt;
    }

    @JsonProperty("gpsAt")
    public void setGpsAt(String gpsAt) {
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
    public String getTxAt() {
        return txAt;
    }

    @JsonProperty("txAt")
    public void setTxAt(String txAt) {
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
