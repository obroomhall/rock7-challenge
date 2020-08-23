package com.rock7.challenge.math;

import com.rock7.challenge.model.VisibleBounds;
import com.rock7.challenge.model.Location;

// algorithms converted to Java from https://www.movable-type.co.uk/scripts/latlong.html
public class Distance {

    public static final double RADIUS_OF_EARTH_KM = 6372.8; // kilometers

    private static final double EYE_HEIGHT_ABOVE_SEA_LEVEL_FEET = 10;
    private static final double VIEW_DISTANCE_CONSTANT = 0.5736;
    private static final double MILES_TO_KILOMETER_CONSTANT = 1.60934;
    private static final double MAX_VIEW_DISTANCE_KM =
            Math.sqrt(EYE_HEIGHT_ABOVE_SEA_LEVEL_FEET /VIEW_DISTANCE_CONSTANT) * MILES_TO_KILOMETER_CONSTANT;

    public static double getMaxViewDistanceKilometers() {
        return MAX_VIEW_DISTANCE_KM;
    }

    public static double haversine(double latA, double lonA, double latB, double lonB) {
        double dLat = Math.toRadians(latB - latA);
        double dLon = Math.toRadians(lonB - lonA);
        latA = Math.toRadians(latA);
        latB = Math.toRadians(latB);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(latA) * Math.cos(latB);
        double c = 2 * Math.asin(Math.sqrt(a));
        return RADIUS_OF_EARTH_KM * c;
    }

    public static double pythagorean(double latA, double lonA, double latB, double lonB) {
        double x = Math.toRadians(lonB-lonA) * Math.cos(Math.toRadians(latA+latB)/2);
        double y = Math.toRadians(latB-latA);
        return Math.sqrt(x*x + y*y) * RADIUS_OF_EARTH_KM;
    }

    public static Location findDestination(double latA, double lonA, double distance, int degrees) {

        double angularDistance = distance / RADIUS_OF_EARTH_KM;
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

    // furthest visible point north, east, south, and west for approximate latitude and longitude bounds
    public static VisibleBounds getUpperAndLowerVisibleBounds(double lat, double lon) {
        return new VisibleBounds(
                findDestination(lat, lon, MAX_VIEW_DISTANCE_KM, 0).getLatitude(),
                findDestination(lat, lon, MAX_VIEW_DISTANCE_KM, 180).getLatitude(),
                findDestination(lat, lon, MAX_VIEW_DISTANCE_KM, 90).getLongitude(),
                findDestination(lat, lon, MAX_VIEW_DISTANCE_KM, 270).getLongitude()
        );
    }

    // an interface to make swapping distance calculating algorithm easier
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
}
