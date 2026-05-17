// ==========================================
// JsonService.java
// ==========================================

package com.ayrinhaha.service;

import java.io.*;

public class JsonService {

    private final String FILE = "server_data.json";

    // ==============================
    // SAVE
    // ==============================

    public void save(String data) {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE))) {

            writer.write(data);

            // System.out.println(
            // "\n[JSON FILE SAVED]");

        } catch (IOException e) {

            System.out.println(
                    "Error writing JSON file.");
        }
    }

    // ==============================
    // LOAD
    // ==============================

    public void loadExpenses() {

        try (BufferedReader br = new BufferedReader(
                new FileReader("server_data.json"))) {

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            System.out.println("No expense data found.");
        }
    }

    public void loadTuition() {

        try (BufferedReader br = new BufferedReader(
                new FileReader("tuition_server.json"))) {

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            System.out.println("No tuition data found.");
        }
    }
}