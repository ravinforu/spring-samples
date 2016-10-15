package com.crossover.trial.weather.model;

import java.util.HashSet;
import java.util.Set;

public class AirportConnection {
    private double distance = -1;
    private String airportData;
    private AirportConnection nextConnection;
    private AirportConnection previousConnection;

    public AirportConnection(String airportData) {
        this.airportData = airportData;
    }

    public AirportConnection getNextConnection() {
        return nextConnection;
    }

    public void connect(AirportConnection connection, double distance) {
        if (this.distance == -1) {
            this.setNext(connection, distance);
            return;
        } else if (this.distance > distance) {
            // add it as next connection
            AirportConnection oldNext = this.nextConnection;
            double oldDistance = this.distance;
            this.setNext(connection, distance);
            this.nextConnection.connect(oldNext, oldDistance - distance);
        } else if (this.distance < distance || this.distance == distance) {
            // delegate connection to next connection
            this.nextConnection.connect(connection, distance - this.distance);
        }
    }

    private void setNext(AirportConnection connection, double distance) {
        this.distance = distance;
        this.nextConnection = connection;
        this.nextConnection.connectReference(this);
    }

    public void connectReference(AirportConnection connection) {
        this.previousConnection = connection;
    }

    public void disConnect() {
        this.previousConnection.reConnect(this.nextConnection, this.distance);
        this.distance = -1;
        this.nextConnection = null;
        this.previousConnection = null;
    }

    public void reConnect(AirportConnection connection, double distance) {
        this.setNext(connection, distance + this.distance);
    }

    public Set<String> findInRadius(double radius) {
        Set<String> airportDataSet = new HashSet<>();
        airportDataSet.add(this.airportData);
        AirportConnection currentConnection = this;
        double currentDistance = 0;
        while (currentConnection.nextConnection != null) {
            currentDistance += currentConnection.distance;
            AirportConnection temp = currentConnection.nextConnection;
            if (currentDistance <= radius) {
                airportDataSet.add(temp.airportData);
            }
            currentConnection = temp;
        }
        return airportDataSet;
    }
}