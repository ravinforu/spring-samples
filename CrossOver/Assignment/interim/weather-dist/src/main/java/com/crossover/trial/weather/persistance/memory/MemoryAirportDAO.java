package com.crossover.trial.weather.persistance.memory;

import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.persistance.AirportDAO;
import com.rits.cloning.Cloner;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MemoryAirportDAO implements AirportDAO {
    // TODO messure thread locking overhead

    private final Lock writeLock;
    private final Lock readLock;

    private final Cloner cloner = new Cloner();

    private final Map<String, AirportData> airportDataMap;

    public MemoryAirportDAO() {
        airportDataMap = new HashMap<>();
        ReadWriteLock reentrantLock = new ReentrantReadWriteLock();
        writeLock = reentrantLock.writeLock();
        readLock = reentrantLock.readLock();
    }

    @Override
    public void addAirport(AirportData airportData) {
        writeLock.lock();
        try {
            this.airportDataMap.put(airportData.getIata(), airportData);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void deleteAirport(String iata) {
        writeLock.lock();
        try {
            this.airportDataMap.remove(iata);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public AirportData findOne(String iata) {
        readLock.lock();
        try {
            return this.airportDataMap.get(iata);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<AirportData> findAll() {
        // TODO may be optimization required
        Collection<AirportData> temp = cloner.deepClone(this.airportDataMap.values());
        return new HashSet<>(temp);
    }

    @Override
    public void clear() {
        writeLock.lock();
        try {
            this.airportDataMap.clear();
        } finally {
            writeLock.unlock();
        }
    }
}