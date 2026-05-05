package com.weather.app.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.weather.app.model.WeatherResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Service class responsible for handling HTTP communication with the
 * 7Timer! Weather API and deserializing the JSON response into Java objects.
 * * @author ayrinhaha
 */
public class WeatherService {

    private final HttpClient client;
    private final Gson gson;

    /**
     * Constructs a new WeatherService and initializes the HttpClient
     * and Gson instances for reuse.
     */
    public WeatherService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Fetches the weather forecast for the specified coordinates and
     * deserializes it into a {@link WeatherResponse} object.
     * * @param lat the latitude of the location.
     * 
     * @param lon the longitude of the location.
     * @return a mapped {@link WeatherResponse} object if successful; {@code null}
     *         otherwise.
     */
    public WeatherResponse getForecast(double lat, double lon) {
        String urlString = String.format(
                "https://www.7timer.info/bin/astro.php?lon=%f&lat=%f&ac=0&unit=metric&output=json",
                lon, lat);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), WeatherResponse.class);
            } else {
                System.err.println("[Error] Server returned HTTP Status: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("[Network Error] Could not connect to the API. Details: " + e.getMessage());
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        } catch (JsonSyntaxException e) {
            System.err.println("[Parsing Error] Could not parse the JSON response. Details: " + e.getMessage());
        }

        return null;
    }
}