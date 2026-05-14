
package com.ayrinhaha.service;

import com.google.gson.Gson;
import java.io.*;

public class JsonService {

    private Gson gson = new Gson();
    private String filePath = "data.json";

    public void save(Object data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            System.out.println("[Saved to JSON]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T load(Class<T> type) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return gson.fromJson(reader, type);
        } catch (Exception e) {
            System.out.println("[No saved data found]");
            return null;
        }
    }
}