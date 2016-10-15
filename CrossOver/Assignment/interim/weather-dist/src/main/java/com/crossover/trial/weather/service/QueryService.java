package com.crossover.trial.weather.service;

import com.crossover.trial.weather.model.AtmosphericInformation;

import java.util.List;
import java.util.Map;

public interface QueryService {
    List<AtmosphericInformation> getWeatherInRadius(String iata, double radius);

    Long getLastDayUpdatedAICount();

    Map<String, Double> getIataFrequencyFraction();

    int[] getRadiusFrequency();

    void updateRequestFrequency(String iata, Double radius);
}
