package com.crossover.trial.weather.model;

import com.crossover.trial.weather.validator.Mean;

/**
 * The various types of data points we can collect.
 *
 * @author code test administrator
 */
public enum DataPointType {
    @Mean(minMean=0,isAvoidMax=true)
    WIND,
    @Mean(minMean=-50,maxMean=100)
    TEMPERATURE,
    @Mean(minMean=0,maxMean=100)
    HUMIDTY,
    @Mean(minMean=650,maxMean=800)
    PRESSURE,
    @Mean(minMean=0,maxMean=100)
    CLOUDCOVER,
    @Mean(minMean=0,maxMean=100)
    PRECIPITATION
}
