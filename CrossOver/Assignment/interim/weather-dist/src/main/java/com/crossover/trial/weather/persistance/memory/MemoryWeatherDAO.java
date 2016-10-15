package com.crossover.trial.weather.persistance.memory;

import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;
import com.crossover.trial.weather.persistance.WeatherDAO;
import com.crossover.trial.weather.validator.DataPointValidator;
import com.rits.cloning.Cloner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryWeatherDAO implements WeatherDAO {
    private final Map<String, AtmosphericInformation> atmosphericInformationMap = new ConcurrentHashMap<>();
    private final Cloner cloner = new Cloner();

    @Override
    public void update(String iataCode, DataPointType dataPointType, DataPoint dataPoint) {
        AtmosphericInformation ai = atmosphericInformationMap.get(iataCode);
        if (DataPointValidator.isValid(dataPointType, dataPoint)) {
            ai.addDataPoint(dataPointType, dataPoint);
        } else {
            throw new IllegalStateException("couldn't update atmospheric data");
        }
    }

    @Override
    public Set<AtmosphericInformation> findAll() {
        // TODO may be optimization required
        Collection<AtmosphericInformation> temp = cloner.deepClone(this.atmosphericInformationMap.values());
        return new HashSet<>(temp);
    }

    @Override
    public AtmosphericInformation findOne(String iata) {
        return atmosphericInformationMap.get(iata);
    }

    @Override
    public void onAirportAdded(String iataCode) {
        AtmosphericInformation information = atmosphericInformationMap.get(iataCode);
//        atmosphericInformationMap.merge(iataCode, new AtmosphericInformation(),
//                (BiFunction<? super AtmosphericInformation, ? super AtmosphericInformation, ? extends AtmosphericInformation>) atmosphericInformationMap.get(iataCode));
        if (information == null) {
            information = new AtmosphericInformation();
            atmosphericInformationMap.put(iataCode, information);
        }
    }

    @Override
    public void onAirportDeleted(String iataCode) {
        this.atmosphericInformationMap.remove(iataCode);
    }

    @Override
    public void clear() {
        this.atmosphericInformationMap.clear();
    }
}