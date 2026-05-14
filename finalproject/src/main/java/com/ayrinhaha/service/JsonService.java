package com.ayrinhaha.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class JsonService {

    private String filePath = "finance_data.json";

    public void save(Object data) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write(data.toString());

            //System.out.println("\n[DATA SAVED SUCCESSFULLY]");

        } catch (Exception e) {

            System.out.println("Error saving file.");
        }
    }

    public void load() {

        try (BufferedReader reader = new BufferedReader(new FileReader("finance_data.json"))) {

            String line;

            System.out.println("\n=== FILE CONTENT ===");

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            System.out.println("Error reading file.");
        }
    }

}
