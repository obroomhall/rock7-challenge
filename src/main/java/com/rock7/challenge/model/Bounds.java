package com.rock7.challenge.model;

public class Bounds {

    private final double upperLatitudeBound;
    private final double lowerLatitudeBound;
    private final double upperLongitudeBound;
    private final double lowerLongitudeBound;

    public Bounds(double upperLatitudeBound, double lowerLatitudeBound, double upperLongitudeBound, double lowerLongitudeBound) {
        this.upperLatitudeBound = upperLatitudeBound;
        this.lowerLatitudeBound = lowerLatitudeBound;
        this.upperLongitudeBound = upperLongitudeBound;
        this.lowerLongitudeBound = lowerLongitudeBound;
    }

    public double getUpperLatitudeBound() {
        return upperLatitudeBound;
    }

    public double getLowerLatitudeBound() {
        return lowerLatitudeBound;
    }

    public double getUpperLongitudeBound() {
        return upperLongitudeBound;
    }

    public double getLowerLongitudeBound() {
        return lowerLongitudeBound;
    }
}
