package com.weather.app;

import java.util.Scanner;

import com.weather.app.model.Forecast;
import com.weather.app.model.WeatherResponse;
import com.weather.app.model.Wind;
import com.weather.app.service.WeatherService;

/**
 * The entry point of the Weather Data Parser application.
 * Collects user coordinates, communicates with the {@link WeatherService},
 * and prints out a human-readable forecast.
 * * @author ayrinhaha
 */
public class Main {

    /**
     * Main method to execute the application.
     * * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("---- Weather Data Parser ----");

        try {
            System.out.print("Enter Latitude (e.g., 14.59): ");
            double lat = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter Longitude (e.g., 120.98): ");
            double lon = Double.parseDouble(sc.nextLine().trim());

            System.out.println("\nFetching and parsing weather data...");

            WeatherService weatherService = new WeatherService();
            WeatherResponse response = weatherService.getForecast(lat, lon);

            if (response != null && response.getDataSeries() != null && !response.getDataSeries().isEmpty()) {
                System.out.println("\n[Success] Upcoming Forecast:");

                int limit = Math.min(3, response.getDataSeries().size());

                for (int i = 0; i < limit; i++) {
                    Forecast forecast = response.getDataSeries().get(i);
                    Wind wind = forecast.getWind();

                    System.out.printf("At hour %d: %d°C with %d speed winds from the %s.%n",
                            forecast.getTimepoint(),
                            forecast.getTemperature(),
                            wind.getSpeed(),
                            wind.getDirection());
                }
            } else {
                System.out.println("\n[Error] Could not retrieve weather data. Please try again later.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n[Input Error] Please enter valid numeric coordinates.");
        } finally {
            sc.close();
        }
    }
}