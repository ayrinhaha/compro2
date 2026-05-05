package com.weather.app.model;

/**
 * Represents the wind data at a specific forecast timepoint.
 * * @author ayrinhaha
 */
public class Wind {

    /**
     * The compass direction of the wind (e.g., "NE", "SW").
     */
    private String direction;

    /**
     * The speed category of the wind.
     */
    private int speed;

    /**
     * Gets the direction of the wind.
     * * @return a String representing the wind direction.
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Gets the speed of the wind.
     * * @return an integer representing the wind speed.
     */
    public int getSpeed() {
        return speed;
    }
}