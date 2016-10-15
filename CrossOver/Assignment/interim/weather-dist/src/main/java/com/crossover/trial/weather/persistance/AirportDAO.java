package com.crossover.trial.weather.persistance;

import com.crossover.trial.weather.model.AirportData;

import java.util.Set;

public interface AirportDAO {
    void addAirport(AirportData airportData);

    void deleteAirport(String airportName);

    AirportData findOne(String airportName);

    Set<AirportData> findAll();

    void clear();
}
