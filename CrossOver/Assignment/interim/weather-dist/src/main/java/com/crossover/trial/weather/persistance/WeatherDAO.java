package com.crossover.trial.weather.persistance;

import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;

import java.util.Set;

public interface WeatherDAO {
    void update(String iataCode, DataPointType pointType, DataPoint point);

    Set<AtmosphericInformation> findAll();

    AtmosphericInformation findOne(String iata);

    void onAirportAdded(String iataCode);

    void onAirportDeleted(String iataCode);

    void clear();
}