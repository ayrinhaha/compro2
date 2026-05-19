package com.ayrinhaha.service;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Handles reading transaction logs
 * from the server log directory.
 *
 * @author ayrinhaha
 */
public class JsonService {

    /**
     * Loads and prints the server logs
     * of a specific user account.
     *
     * @param username account owner
     */
    public void loadServerLogs(String username) {

        String file = "server_logs/" + username + "_logs.json";

        try (BufferedReader br = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                System.out.println(line);
            }

        } catch (Exception e) {

            System.out.println("[ERROR] No server logs found.");
        }
    }
}