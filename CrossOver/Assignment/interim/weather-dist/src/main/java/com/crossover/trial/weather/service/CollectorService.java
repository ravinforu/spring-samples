package com.crossover.trial.weather.service;

import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;

import java.util.Set;

public interface CollectorService {
    void updateWeather(String iataCode, DataPointType pointType, DataPoint dataPoint);

    Set<String> findAllIata();

    AirportData findAirportByIata(String iata);

    void addAirportData(AirportData airportData);

    void deleteAirportData(String iata);
}
