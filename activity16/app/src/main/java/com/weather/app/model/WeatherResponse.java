package com.weather.app.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents the root JSON response returned by the 7Timer! API.
 * * @author ayrinhaha
 */
public class WeatherResponse {
    
    /**
     * The type of weather product returned by the API.
     */
    private String product;

    /**
     * The list of forecast data points.
     * Mapped from the "dataseries" JSON key.
     */
    @SerializedName("dataseries")
    private List<Forecast> dataSeries;

    /**
     * Gets the product name.
     * * @return a String representing the API product type.
     */
    public String getProduct() {
        return product;
    }

    /**
     * Gets the list of weather forecasts.
     * * @return a List of {@link Forecast} objects representing the data series.
     */
    public List<Forecast> getDataSeries() {
        return dataSeries;
    }
}