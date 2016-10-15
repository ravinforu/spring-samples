package com.crossover.trial.weather;

import com.crossover.trial.weather.discovery.ServiceLocator;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.persistance.AirportDAO;
import com.crossover.trial.weather.persistance.WeatherDAO;
import com.crossover.trial.weather.persistance.memory.MemoryAirportDAO;
import com.crossover.trial.weather.persistance.memory.MemoryWeatherDAO;
import com.crossover.trial.weather.rest.RestWeatherCollectorEndpoint;
import com.crossover.trial.weather.rest.RestWeatherQueryEndpoint;
import com.crossover.trial.weather.service.CollectorService;
import com.crossover.trial.weather.service.QueryService;
import com.crossover.trial.weather.service.WeatherCollectorService;
import com.crossover.trial.weather.service.WeatherQueryService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class WeatherEndpointTest {

    static {
        AirportDAO airportDAO = new MemoryAirportDAO();
        WeatherDAO weatherDAO = new MemoryWeatherDAO();

        ServiceLocator.register(AirportDAO.class, airportDAO);
        ServiceLocator.register(WeatherDAO.class, weatherDAO);
        ServiceLocator.register(QueryService.class, new WeatherQueryService());
        ServiceLocator.register(CollectorService.class, new WeatherCollectorService());
    }

    private RestWeatherQueryEndpoint _query = new RestWeatherQueryEndpoint();

    private RestWeatherCollectorEndpoint _update = new RestWeatherCollectorEndpoint();

    private Gson _gson = new Gson();

    private DataPoint _dp;

    @Before
    public void setUp() throws Exception {
        AirportDAO airportDAO = (AirportDAO) ServiceLocator.lookup(AirportDAO.class);
        WeatherDAO weatherDAO = (WeatherDAO) ServiceLocator.lookup(WeatherDAO.class);

        airportDAO.clear();
        weatherDAO.clear();
        // TODO clear all request frequency data

        airportDAO.addAirport(new AirportData("BOS", 42.364347, -71.005181));
        airportDAO.addAirport(new AirportData("EWR", 40.6925, -74.168667));
        airportDAO.addAirport(new AirportData("JFK", 40.639751, -73.778925));
        airportDAO.addAirport(new AirportData("LGA", 40.777245, -73.872608));
        airportDAO.addAirport(new AirportData("MMU", 40.79935, -74.4148747));
        weatherDAO.onAirportAdded("BOS");
        weatherDAO.onAirportAdded("EWR");
        weatherDAO.onAirportAdded("JFK");
        weatherDAO.onAirportAdded("LGA");
        weatherDAO.onAirportAdded("MMU");

        _dp = new DataPoint.Builder()
                .withCount(10).withFirst(10).withMedian(20).withLast(30).withMean(22).build();
        _update.updateWeather("BOS", "wind", _gson.toJson(_dp));
        _query.weather("BOS", "0").getEntity();
    }

    @Test
    public void testPing() throws Exception {
        String ping = _query.ping();
        JsonElement pingResult = new JsonParser().parse(ping);
        assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());
        assertEquals(5, pingResult.getAsJsonObject().get("iata_freq").getAsJsonObject().entrySet().size());
    }

    @Test
    public void testGet() throws Exception {
        List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.weather("BOS", "0").getEntity();
        assertEquals(ais.get(0).getWind(), _dp);
    }

    @Test
    public void testGetNearby() throws Exception {
        // check datasize response
        _update.updateWeather("JFK", "wind", _gson.toJson(_dp));
        _dp.setMean(40);
        _update.updateWeather("EWR", "wind", _gson.toJson(_dp));
        _dp.setMean(30);
        _update.updateWeather("LGA", "wind", _gson.toJson(_dp));

        List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.weather("JFK", "200").getEntity();
        assertEquals(3, ais.size());
    }

    @Test
    public void testUpdate() throws Exception {

        DataPoint windDp = new DataPoint.Builder()
                .withCount(10).withFirst(10).withMedian(20).withLast(30).withMean(22).build();
        _update.updateWeather("BOS", "wind", _gson.toJson(windDp));
        _query.weather("BOS", "0").getEntity();

        String ping = _query.ping();
        JsonElement pingResult = new JsonParser().parse(ping);
        assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());

        DataPoint cloudCoverDp = new DataPoint.Builder()
                .withCount(4).withFirst(10).withMedian(60).withLast(100).withMean(50).build();
        _update.updateWeather("BOS", "cloudcover", _gson.toJson(cloudCoverDp));

        List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.weather("BOS", "0").getEntity();
        assertEquals(ais.get(0).getWind(), windDp);
        assertEquals(ais.get(0).getCloudCover(), cloudCoverDp);
    }

}