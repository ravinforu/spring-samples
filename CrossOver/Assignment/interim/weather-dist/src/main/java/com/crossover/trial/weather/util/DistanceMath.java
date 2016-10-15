package com.crossover.trial.weather.util;

import com.crossover.trial.weather.model.AirportData;

public class DistanceMath {
    /**
     * earth radius in KM
     */
    private static final double R = 6372.8;

    /**
     * Haversine distance between two airports.
     *
     * @param airportData1 airport 1
     * @param airportData2 airport 2
     * @return the distance in KM
     */
    public static double calculateDistance(AirportData airportData1, AirportData airportData2) {
        double deltaLat = Math.toRadians(airportData2.getLatitude() - airportData1.getLatitude());
        double deltaLon = Math.toRadians(airportData2.getLongitude() - airportData1.getLongitude());
        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.pow(Math.sin(deltaLon / 2), 2)
                * Math.cos(airportData1.getLatitude()) * Math.cos(airportData2.getLatitude());
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
}
