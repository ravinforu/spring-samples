package com.crossover.trial.weather.discovery;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceLocator {
    private static final Map<Class, Object> services = new ConcurrentHashMap<>();

    private ServiceLocator() {
    }

    public static void register(Class cls, Object service) {
        services.put(cls, service);
    }

    public static Object lookup(Class cls) {
        return services.get(cls);
    }
}