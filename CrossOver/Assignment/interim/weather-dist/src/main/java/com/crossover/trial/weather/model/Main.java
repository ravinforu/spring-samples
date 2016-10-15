package com.crossover.trial.weather.model;

import java.util.Set;

public class Main {
    public static void main(String args[]) {
        AirportConnection a = new AirportConnection("A");
        AirportConnection b = new AirportConnection("B");
        AirportConnection c = new AirportConnection("C");
        AirportConnection d = new AirportConnection("D");

        a.connect(b, 2);
        a.connect(c, 3);
        a.connect(d, 4);
        b.disConnect();

        Set<String> airports = a.findInRadius(3);
        System.out.println(airports);
    }
}