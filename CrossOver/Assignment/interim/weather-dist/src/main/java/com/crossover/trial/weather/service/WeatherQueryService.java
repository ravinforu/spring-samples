package com.crossover.trial.weather.service;

import com.crossover.trial.weather.discovery.ServiceLocator;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.persistance.AirportDAO;
import com.crossover.trial.weather.persistance.WeatherDAO;
import com.crossover.trial.weather.util.DistanceMath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeatherQueryService implements QueryService {
    /**
     * Internal performance counter to better understand most requested information, this map can be improved but
     * for now provides the basis for future performance optimizations. Due to the stateless deployment architecture
     * we don't want to write this to disk, but will pull it off using a REST request and aggregate with other
     * performance metrics
     */
    private static final Map<AirportData, Integer> requestFrequency = new HashMap<>();
    private static final Map<Double, Integer> radiusFrequency = new HashMap();

    private final AirportDAO airportDAO;
    private final WeatherDAO weatherDAO;

    public WeatherQueryService() {
        airportDAO = (AirportDAO) ServiceLocator.lookup(AirportDAO.class);
        weatherDAO = (WeatherDAO) ServiceLocator.lookup(WeatherDAO.class);
    }

    /**
     * Retrieves weather data within the radius
     *
     * @param iata an iata code
     * @param radius query radius
     * @return All AtmosphericInformation containing weather data in radius
     */
    public List<AtmosphericInformation> getWeatherInRadius(String iata, double radius) {
        List<AtmosphericInformation> atmosphericInformations = new ArrayList<>();
        if (radius == 0) {
            AtmosphericInformation ai = this.weatherDAO.findOne(iata);
            atmosphericInformations.add(ai);
        } else {
            AirportData ad = this.airportDAO.findOne(iata);
            this.airportDAO.findAll().stream()
                    .filter(airportData -> DistanceMath.calculateDistance(ad, airportData) <= radius)
                    .forEach(filteredAirportData -> {
                        AtmosphericInformation ai = this.weatherDAO.findOne(filteredAirportData.getIata());
                        if(!ai.isEmpty())
                            atmosphericInformations.add(ai);
                    });
        }
        return atmosphericInformations;
    }

    public Long getLastDayUpdatedAICount() {
        long dataCount;
        dataCount = this.weatherDAO.findAll().stream()
                .filter(ai -> !ai.isEmpty() && ai.getLastUpdateTime() > System.currentTimeMillis() - 86400000)
                .count();
        return dataCount;
    }

    public Map<String, Double> getIataFrequencyFraction() {
        Map<String, Double> iataFrequencyFraction;
        iataFrequencyFraction = this.airportDAO.findAll().stream().collect(Collectors.toMap(AirportData::getIata, data -> (double) requestFrequency.getOrDefault(data, 0) / requestFrequency.size()));
        return iataFrequencyFraction;
    }

    public int[] getRadiusFrequency() {
        int m = radiusFrequency.keySet().stream()
                .max(Double::compare)
                .orElse(1000.0).intValue() + 1;
        int[] hist = new int[m];
        radiusFrequency.forEach((k, v) -> hist[k.intValue() % 10] += v);
        return hist;
    }

    /**
     * Records information about how often requests are made
     *
     * @param iata   an iata code
     * @param radius query radius
     */
    public void updateRequestFrequency(String iata, Double radius) {
        AirportData airportData = this.airportDAO.findOne(iata);
        requestFrequency.put(airportData, requestFrequency.getOrDefault(airportData, 0) + 1);
        radiusFrequency.put(radius, radiusFrequency.getOrDefault(radius, 0));
    }

}
