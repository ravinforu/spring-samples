package com.crossover.trial.weather.service;

import com.crossover.trial.weather.discovery.ServiceLocator;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;
import com.crossover.trial.weather.persistance.AirportDAO;
import com.crossover.trial.weather.persistance.WeatherDAO;

import java.util.Set;
import java.util.stream.Collectors;

public class WeatherCollectorService implements CollectorService {
    private final AirportDAO airportDAO;
    private final WeatherDAO weatherDAO;

    public WeatherCollectorService() {
        airportDAO = (AirportDAO) ServiceLocator.lookup(AirportDAO.class);
        weatherDAO = (WeatherDAO) ServiceLocator.lookup(WeatherDAO.class);
    }

    public void updateWeather(String iataCode, DataPointType pointType, DataPoint dataPoint) {
        this.weatherDAO.update(iataCode, pointType, dataPoint);
    }

    public Set<String> findAllIata() {
        return this.airportDAO.findAll().stream().map(AirportData::getIata).collect(Collectors.toSet());
    }

    public AirportData findAirportByIata(String iata) {
        return this.airportDAO.findOne(iata);
    }

    public void addAirportData(AirportData airportData) {
        this.weatherDAO.onAirportAdded(airportData.getIata());
        this.airportDAO.addAirport(airportData);
    }

    public void deleteAirportData(String iata) {
        this.airportDAO.deleteAirport(iata);
        this.weatherDAO.onAirportDeleted(iata);
    }
}
