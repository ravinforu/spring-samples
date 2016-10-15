package com.crossover.trial.weather.persistance;

/*
    To store http request frequency
 */
public interface MetricsDAO {
    void markRequest(String iata, String radius);
}