import java.util.*;
import java.net.http.*;
import java.net.URI;
import java.io.*;

/**
 * WeatherFetcher is a simple Java program that retrieves weather data
 * from an external API based on user-provided latitude and longitude.
 *
 * It sends an HTTP GET request and prints the raw JSON response.
 *
 * @author ayrinhaha
 */
public class WeatherFetcher {

    /**
     * Entry point of the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("---- Weather Data Fetcher ----");

        System.out.print("Enter Latitude (e.g., 14.59): ");
        String lat = sc.nextLine().trim();

        System.out.print("Enter Longitude (e.g., 120.98): ");
        String lon = sc.nextLine().trim();

        String urlString = String.format(
                "https://www.7timer.info/bin/astro.php?lon=%s&lat=%s&ac=0&unit=metric&output=json",
                lon, lat);

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            System.out.println("\nFetching weather data...");

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("\n[Success] \nRaw JSON Data:");
                System.out.println(response.body());

            } else {
                System.out.println("\n[Error] Failed to fetch data.");
                System.out.println("HTTP Status Code: " + response.statusCode());
            }

        } catch (IOException e) {
            System.err
                    .println("\n[Network Error] Could not connect to the API. Please check your internet connection.");
            System.err.println("Details: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("\n[System Error] The request was interrupted.");
            System.err.println("Details: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            sc.close();
        }
    }
}