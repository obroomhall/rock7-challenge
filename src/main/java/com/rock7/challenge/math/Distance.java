package com.rock7.challenge.math;

import com.rock7.challenge.model.Bounds;
import com.rock7.challenge.model.Location;
import com.rock7.challenge.model.StartAndEndLocation;

public class Distance {

    public static final double RADIUS_OF_EARTH = 6372.8; // kilometers

    private static final double FEET_ABOVE_SEA_LEVEL = 10;
    private static final double VIEW_DISTANCE_CONSTANT = 0.5736;
    private static final double MILES_TO_KILOMETER_CONSTANT = 1.60934;
    private static final double MAX_VIEW_DISTANCE_KILOMETERS =
            Math.sqrt(FEET_ABOVE_SEA_LEVEL/VIEW_DISTANCE_CONSTANT) * MILES_TO_KILOMETER_CONSTANT;

    public static double haversine(double latA, double lonA, double latB, double lonB) {
        double dLat = Math.toRadians(latB - latA);
        double dLon = Math.toRadians(lonB - lonA);
        latA = Math.toRadians(latA);
        latB = Math.toRadians(latB);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(latA) * Math.cos(latB);
        double c = 2 * Math.asin(Math.sqrt(a));
        return RADIUS_OF_EARTH * c;
    }

    public static double pythagorean(double latA, double lonA, double latB, double lonB) {
        double x = Math.toRadians(lonB-lonA) * Math.cos(Math.toRadians(latA+latB)/2);
        double y = Math.toRadians(latB-latA);
        return Math.sqrt(x*x + y*y) * RADIUS_OF_EARTH;
    }

    public static Location destinationFromLocationDistanceBearing(double latA, double lonA, double distance, int degrees) {

        double angularDistance = distance / RADIUS_OF_EARTH;
        double radians = Math.toRadians(degrees);

        double radLatA = Math.toRadians(latA);
        double radLonA = Math.toRadians(lonA);

        double latB = Math.asin(
                Math.sin(radLatA) * Math.cos(angularDistance) +
                Math.cos(radLatA) * Math.sin(angularDistance) * Math.cos(radians)
        );

        double lonB = radLonA + Math.atan2(
                Math.sin(radians) * Math.sin(angularDistance) * Math.cos(radLatA),
                Math.cos(angularDistance) - Math.sin(radLatA) * Math.sin(latB)
        );

        return Location.fromRadians(latB, lonB);
    }

    public static Bounds getUpperAndLowerVisibleBounds(double lat, double lon) {
        return new Bounds(
                destinationFromLocationDistanceBearing(lat, lon, MAX_VIEW_DISTANCE_KILOMETERS, 0).getLatitude(),
                destinationFromLocationDistanceBearing(lat, lon, MAX_VIEW_DISTANCE_KILOMETERS, 180).getLatitude(),
                destinationFromLocationDistanceBearing(lat, lon, MAX_VIEW_DISTANCE_KILOMETERS, 90).getLongitude(),
                destinationFromLocationDistanceBearing(lat, lon, MAX_VIEW_DISTANCE_KILOMETERS, 270).getLongitude()
        );
    }

    @FunctionalInterface
    public interface TwoLocationsConsumer {
        double accept(double latA, double lonA, double latB, double lonB);
    }

    public static double fromStartAndEndLocation(TwoLocationsConsumer consumer, Location locationA, Location locationB) {
        return consumer.accept(
                locationA.getLatitude(),
                locationA.getLongitude(),
                locationB.getLatitude(),
                locationB.getLongitude()
        );
    }

    public static double fromStartAndEndLocation(TwoLocationsConsumer consumer, StartAndEndLocation startAndEndLocation) {
        return fromStartAndEndLocation(
                consumer,
                startAndEndLocation.getStart(),
                startAndEndLocation.getEnd()
        );
    }
}
