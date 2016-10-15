package com.crossover.trial.weather;

import com.crossover.trial.weather.model.AirportData;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.*;

/**
 * A simple airport loader which reads a file from disk and sends entries to the webservice
 * <p>
 * TODO: Implement the Airport Loader
 *
 * @author code test administrator
 */
public class AirportLoader {

    /**
     * end point for read queries
     */
    private final WebTarget query;

    /**
     * end point to supply updates
     */
    private final WebTarget collect;

    private AirportLoader() {
        Client client = ClientBuilder.newClient();
        query = client.target("http://localhost:8080/query");
        collect = client.target("http://localhost:9090/collect");
    }

    private void upload(InputStream airportDataStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(airportDataStream));
        String l;
        while ((l = reader.readLine()) != null) {
            AirportData airportData = build(l);
            WebTarget path = collect.path("/airport/"
                    + airportData.getIata()
                    + "/" + airportData.getLatitude() + "/"
                    + airportData.getLongitude() + "");
            path.request().post(Entity.entity("", MediaType.TEXT_PLAIN));
        }
    }

    public static void main(String args[]) throws IOException {
        File airportDataFile = new File("src\\main\\resources\\airports.dat");
        System.out.println(airportDataFile.getAbsoluteFile());
        if (!airportDataFile.exists() || airportDataFile.length() == 0) {
            System.err.println(airportDataFile + " is not a valid input");
            System.exit(1);
        }

        AirportLoader al = new AirportLoader();
        al.upload(new FileInputStream(airportDataFile));
        System.exit(0);
    }

    private AirportData build(String line) {
        String data[] = line.split(",");

        int ii = 2;
        String city = data[ii++];
        String country = data[ii++];
        String iata = data[ii++];
        String icao = data[ii++];
        double latitude = Double.parseDouble(data[ii++]);
        double longitude = Double.parseDouble(data[ii++]);
        String altitude = data[ii++];
        String timezone = data[ii++];
        String dst = data[ii++];

        return new AirportData(iata, latitude, longitude);
    }
}