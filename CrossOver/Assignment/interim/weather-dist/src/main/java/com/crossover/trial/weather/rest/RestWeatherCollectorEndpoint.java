package com.crossover.trial.weather.rest;

import com.crossover.trial.weather.discovery.ServiceLocator;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;
import com.crossover.trial.weather.service.CollectorService;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Path("/collect")
public class RestWeatherCollectorEndpoint {
    private final static Logger LOGGER = Logger.getLogger(RestWeatherCollectorEndpoint.class.getName());
    private final static Gson gson = new Gson();

    private CollectorService collectorService;

    public RestWeatherCollectorEndpoint() {
        collectorService = (CollectorService) ServiceLocator.lookup(CollectorService.class);
    }

    @GET
    @Path("/ping")
    public Response ping() {
        return Response.status(Response.Status.OK).entity("ready").build();
    }

    @POST
    @Path("/weather/{iata}/{pointType}")
    public Response updateWeather(@PathParam("iata") String iataCode,
                                  @PathParam("pointType") String pointType,
                                  String datapointJson) {
        DataPointType dataPointType = DataPointType.valueOf(pointType.toUpperCase());
        DataPoint dataPoint = gson.fromJson(datapointJson, DataPoint.class);
        collectorService.updateWeather(iataCode, dataPointType, dataPoint);
        return Response.status(Response.Status.OK).build();
    }


    @GET
    @Path("/airports")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAirports() {
        return Response.status(Response.Status.OK).entity(collectorService.findAllIata()).build();
    }


    @GET
    @Path("/airport/{iata}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAirport(@PathParam("iata") String iata) {
        return Response.status(Response.Status.OK).entity(collectorService.findAirportByIata(iata)).build();
    }


    @POST
    @Path("/airport/{iata}/{lat}/{long}")
    public Response addAirport(@PathParam("iata") String iata,
                               @PathParam("lat") String latString,
                               @PathParam("long") String longString) {
        AirportData newAirportData = new AirportData();
        newAirportData.setIata(iata);
        newAirportData.setLatitude(Double.valueOf(latString));
        newAirportData.setLongitude(Double.valueOf(longString));
        collectorService.addAirportData(newAirportData);
        return Response.status(Response.Status.OK).build();
    }


    @DELETE
    @Path("/airport/{iata}")
    public Response deleteAirport(@PathParam("iata") String iata) {
        collectorService.deleteAirportData(iata);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/exit")
    public Response exit() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            LOGGER.info("shutdown will shutdown in 3 seconds");
            System.exit(0);
        }, 3, TimeUnit.SECONDS);
        return Response.noContent().build();
    }
}