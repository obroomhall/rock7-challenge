package com.rock7.challenge.math;

import com.rock7.challenge.model.Location;
import com.rock7.challenge.model.StartAndEndLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Proves that the Pythagorean algorithm is (about 6 times) faster than the Haversine algorithm, and both output the
 * same distance to within a metre, up to at least 60 kilometers.
 */
class DistanceTest {

    private static final Random RANDOM = new Random();
    private static final List<StartAndEndLocation> RANDOM_LOCATIONS = generateRandomStartAndEndLocations(1000);

    @Test
    public void testDistanceFindsCorrectDestination() {

        Location locationA = new Location(0.2, 0.2);
        Location locationB = new Location(-0.2, -0.2);

        double distance = Distance.fromStartAndEndLocation(Distance::pythagorean, locationA, locationB);
        System.out.println(distance);

        Location destination = Distance.destinationFromLocationDistanceBearing(
                locationA.getLatitude(),
                locationA.getLongitude(),
                distance,
                225
        );

        String expectedLatitudeRounded = String.format("%.5f", locationB.getLatitude());
        String expectedLongitudeRounded = String.format("%.5f", locationB.getLongitude());
        System.out.println(expectedLatitudeRounded + ", " + expectedLongitudeRounded);

        String calculatedLatitudeRounded = String.format("%.5f", destination.getLatitude());
        String calculatedLongitudeRounded = String.format("%.5f", destination.getLongitude());
        System.out.println(calculatedLatitudeRounded + ", " + calculatedLongitudeRounded);

        Assertions.assertEquals(expectedLatitudeRounded, calculatedLatitudeRounded);
        Assertions.assertEquals(expectedLongitudeRounded, calculatedLongitudeRounded);
    }

    @Test
    public void testOutputDifference62Km() {
        assertMetrePrecision(0.2, 0.2, -0.2, -0.2);
    }

    @Test
    public void testOutputDifference31Km() {
        assertMetrePrecision(0.1, 0.1, -0.1, -0.1);
    }

    @Test
    public void testOutputDifference15Km() {
        assertMetrePrecision(0.05, 0.05, -0.05, -0.05);
    }

    @Test
    public void testOutputDifference5Km() {
        assertMetrePrecision(0.016, 0.016, -0.016, -0.016);
    }

    @Test
    public void testOutputDifference1Km() {
        assertMetrePrecision(0.003, 0.003, -0.003, -0.003);
    }

    // asserts that the difference in formulae output is at most 1 metre
    private void assertMetrePrecision(double latA, double lonA, double latB, double lonB) {

        double haversine = Distance.haversine(latA, lonA, latB, lonB);
        double pythagorean = Distance.pythagorean(latA, lonA, latB, lonB);

        String haversineRounded = String.format("%.3fkm", haversine);
        String pythagoreanRounded = String.format("%.3fkm", pythagorean);

        System.out.println(haversineRounded);
        System.out.println(pythagoreanRounded);

        Assertions.assertEquals(haversineRounded, pythagoreanRounded);
    }

    @Test // 0.032ms
    public void haversine() {
        List<Long> executionTimes = doRepeatingDistanceTest(Distance::haversine);
        executionTimes.stream()
                .mapToLong(m -> m)
                .average()
                .ifPresent(d -> System.out.printf("Haversine Average: %.3fms%n", d));
    }

    @Test // 0.006ms
    public void pythagorean() {
        List<Long> executionTimes = doRepeatingDistanceTest(Distance::pythagorean);
        executionTimes.stream()
                .mapToLong(m -> m)
                .average()
                .ifPresent(d -> System.out.printf("Pythagorean Average: %.3fms%n", d));
    }

    private List<Long> doRepeatingDistanceTest(Distance.TwoLocationsConsumer consumer) {
        List<Long> executionTimes = new ArrayList<>();
        for (StartAndEndLocation startAndEndLocation : RANDOM_LOCATIONS) {
            Instant start = Instant.now();
            for (int i = 0; i < 1000; i++) {
                Distance.fromStartAndEndLocation(consumer, startAndEndLocation);
            }
            Instant end = Instant.now();
            executionTimes.add(Duration.between(start, end).toMillis());
        }
        return executionTimes;
    }

    private static List<StartAndEndLocation> generateRandomStartAndEndLocations(int count) {
        List<StartAndEndLocation> latLongsList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            latLongsList.add(new StartAndEndLocation(generateRandomLatLong(), generateRandomLatLong()));
        }
        return latLongsList;
    }

    private static double generateRandomDouble(double rangeMin, double rangeMax) {
        return rangeMin + (rangeMax - rangeMin) * RANDOM.nextDouble();
    }

    private static double generateRandomLatitude() {
        return generateRandomDouble(-90, 90);
    }

    private static double generateRandomLongitude() {
        return generateRandomDouble(-180, 180);
    }

    private static Location generateRandomLatLong() {
        return new Location(generateRandomLatitude(), generateRandomLongitude());
    }
}