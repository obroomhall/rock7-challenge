package com.rock7.challenge.math;

import com.rock7.challenge.model.StartAndEndLocation;

public class Distance {

    public static final double R = 6372.8; // kilometers

    @FunctionalInterface
    public interface TwoLocationsConsumer {
        double accept(double latA, double lonA, double latB, double lonB);
    }

    public static double fromStartAndEndLocation(TwoLocationsConsumer consumer, StartAndEndLocation startAndEndLocation) {
        return consumer.accept(
                startAndEndLocation.getStart().getLatitude(),
                startAndEndLocation.getStart().getLongitude(),
                startAndEndLocation.getEnd().getLatitude(),
                startAndEndLocation.getEnd().getLongitude()
        );
    }

    public static double haversine(double latA, double lonA, double latB, double lonB) {
        double dLat = Math.toRadians(latB - latA);
        double dLon = Math.toRadians(lonB - lonA);
        latA = Math.toRadians(latA);
        latB = Math.toRadians(latB);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(latA) * Math.cos(latB);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    public static double pythagorean(double latA, double lonA, double latB, double lonB) {
        double x = Math.toRadians(lonB-lonA) * Math.cos(Math.toRadians(latA+latB)/2);
        double y = Math.toRadians(latB-latA);
        return Math.sqrt(x*x + y*y) * R;
    }
}
