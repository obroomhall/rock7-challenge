package com.rock7.challenge.model;

import java.util.Date;
import java.util.Map;

public class DailySightings {

    private final Date day;
    private final Map<Integer, Integer> sightings;

    public DailySightings(Date day, Map<Integer, Integer> sightings) {
        this.day = day;
        this.sightings = sightings;
    }

    public Date getDay() {
        return day;
    }

    public Map<Integer, Integer> getSightings() {
        return sightings;
    }
}
