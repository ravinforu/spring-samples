package com.crossover.trial.weather.rest;

import com.crossover.trial.weather.discovery.ServiceLocator;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.service.QueryService;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The Weather App REST endpoint allows clients to query, update and check health stats. Currently, all data is
 * held in memory. The end point deploys to a single container
 *
 * @author code test administrator
 */
@Path("/query")
public class RestWeatherQueryEndpoint {

    public final static Logger LOGGER = Logger.getLogger("WeatherQuery");

    /**
     * shared gson json to object factory
     */
    private static final Gson gson = new Gson();

    private final QueryService queryService;

    public RestWeatherQueryEndpoint() {
        queryService = (QueryService) ServiceLocator.lookup(QueryService.class);
    }

    /**
     * Retrieve service health including total size of valid data points and request frequency information.
     *
     * @return health stats for the service as a string
     */

    @GET
    @Path("/ping")
    public String ping() {
        Map<String, Object> pingDataValue = new HashMap<>();

        Long dataCount = queryService.getLastDayUpdatedAICount();
        pingDataValue.put("datasize", dataCount);

        // fraction of queries
        Map<String, Double> iataFrequencyFraction = queryService.getIataFrequencyFraction();
        pingDataValue.put("iata_freq", iataFrequencyFraction);

        int[] hist = queryService.getRadiusFrequency();
        pingDataValue.put("radius_freq", hist);

        return gson.toJson(pingDataValue);
    }



    /**
     * Given a query in json format {'iata': CODE, 'radius': km} extracts the requested airport information and
     * return a list of matching atmosphere information.
     *
     * @param iata         the iataCode
     * @param radiusString the radius in km
     * @return a list of atmospheric information
     */
    @GET
    @Path("/weather/{iata}/{radius}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response weather(@PathParam("iata") String iata, @PathParam("radius") String radiusString) {
        double radius = radiusString == null || radiusString.trim().isEmpty() ? 0 : Double.valueOf(radiusString);
        queryService.updateRequestFrequency(iata, radius);
        List<AtmosphericInformation> weatherDataInRadius = queryService.getWeatherInRadius(iata, radius);
        return Response.status(Response.Status.OK).entity(weatherDataInRadius).build();
    }


}