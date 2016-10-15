package com.crossover.trial.weather.model;

/**
 * encapsulates sensor information for a particular location
 */
public class AtmosphericInformation {

    /**
     * temperature in degrees celsius
     */
    private DataPoint temperature;

    /**
     * wind speed in km/h
     */
    private DataPoint wind;

    /**
     * humidity in percent
     */
    private DataPoint humidity;

    /**
     * precipitation in cm
     */
    private DataPoint precipitation;

    /**
     * pressure in mmHg
     */
    private DataPoint pressure;

    /**
     * cloud cover percent from 0 - 100 (integer)
     */
    private DataPoint cloudCover;

    /**
     * the last time this data was updated, in milliseconds since UTC epoch
     */
    private long lastUpdateTime;

    public AtmosphericInformation() {

    }

//    protected AtmosphericInformation(DataPoint temperature, DataPoint wind, DataPoint humidity, DataPoint percipitation, DataPoint pressure, DataPoint cloudCover) {
//        this.temperature = temperature;
//        this.wind = wind;
//        this.humidity = humidity;
//        this.precipitation = percipitation;
//        this.pressure = pressure;
//        this.cloudCover = cloudCover;
//        this.lastUpdateTime = System.currentTimeMillis();
//    }

    public DataPoint getTemperature() {
        return temperature;
    }

    public DataPoint getWind() {
        return wind;
    }

    public void addDataPoint(DataPointType pointType, DataPoint dp) {
        if (pointType == DataPointType.WIND) {
            this.wind = dp;
            this.lastUpdateTime = System.currentTimeMillis();
            return;
        } else if (pointType == DataPointType.TEMPERATURE) {
            this.temperature = dp;
            this.lastUpdateTime = System.currentTimeMillis();
            return;
        } else if (pointType == DataPointType.HUMIDTY) {
            this.humidity = dp;
            this.lastUpdateTime = System.currentTimeMillis();
            return;
        } else if (pointType == DataPointType.PRESSURE) {
            this.pressure = dp;
            this.lastUpdateTime = System.currentTimeMillis();
            return;
        } else if (pointType == DataPointType.CLOUDCOVER) {
            this.cloudCover = dp;
            this.lastUpdateTime = System.currentTimeMillis();
            return;
        } else if (pointType == DataPointType.PRECIPITATION) {
            this.precipitation = dp;
            this.lastUpdateTime = System.currentTimeMillis();
            return;
        }
        throw new IllegalStateException("couldn't update atmospheric data");
    }

    public DataPoint getHumidity() {
        return humidity;
    }

    public DataPoint getPrecipitation() {
        return precipitation;
    }

    public DataPoint getPressure() {
        return pressure;
    }

    public DataPoint getCloudCover() {
        return cloudCover;
    }

    public long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public boolean isEmpty() {
        return cloudCover == null
                && humidity == null
                && precipitation == null
                && pressure == null
                && temperature == null
                && wind == null;
    }
}
