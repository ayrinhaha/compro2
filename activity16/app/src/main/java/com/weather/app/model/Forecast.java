package com.weather.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a single forecast entry containing temperature and wind
 * details for a specific timepoint.
 * * @author ayrinhaha
 */
public class Forecast {

    /**
     * The hour offset from the current time.
     */
    private int timepoint;

    /**
     * The temperature at 2 meters above ground level, in Celsius.
     * Mapped from the "temp2m" JSON key.
     */
    @SerializedName("temp2m")
    private int temperature;

    /**
     * The wind details at 10 meters above ground level.
     * Mapped from the "wind10m" JSON key.
     */
    @SerializedName("wind10m")
    private Wind wind;

    /**
     * Gets the forecast timepoint.
     * * @return an integer representing the hour offset.
     */
    public int getTimepoint() {
        return timepoint;
    }

    /**
     * Gets the forecasted temperature.
     * * @return an integer representing the temperature in Celsius.
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * Gets the wind information for this forecast.
     * * @return a {@link Wind} object containing speed and direction.
     */
    public Wind getWind() {
        return wind;
    }
}