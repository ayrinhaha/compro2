package com.ayrinhaha.model;

import java.util.*;

/**
 * Represents a full user account including:
 * budget, expenses, tuition, and payment history.
 *
 * @author ayrinhaha
 */
public class UserAccount extends User {

    public double budget;
    public List<Expense> expenses = new ArrayList<>();
    public Tuition tuition = new Tuition();

    public UserAccount(String username, String password) {
        super(username, password);
    }

    public String toJson() {

        StringBuilder sb = new StringBuilder();

        sb.append("{")
                .append("\"username\":\"").append(getUsername()).append("\",")
                .append("\"password\":\"").append(getPassword()).append("\",")
                .append("\"budget\":").append(budget).append(",")
                .append("\"expenses\":[");

        for (Expense e : expenses) {
            sb.append("{")
                    .append("\"name\":\"").append(e.getName()).append("\",")
                    .append("\"amount\":").append(e.getAmount()).append(",")
                    .append("\"category\":\"").append(e.getCategory()).append("\",")
                    .append("\"timestamp\":\"").append(e.getTimestamp()).append("\"")
                    .append("},");
        }

        sb.append("],");

        sb.append("\"tuition\":")
                .append(tuition.exportTuitionData());

        sb.append("}");

        return sb.toString();
    }

    public static UserAccount fromJson(String json) {

        String username = extract(json, "username");
        String password = extract(json, "password");

        UserAccount acc = new UserAccount(username, password);

        String budgetStr = extract(json, "budget");
        if (!budgetStr.isEmpty()) {
            acc.budget = Double.parseDouble(budgetStr);
        }

        return acc;
    }

    private static String extract(String json, String key) {

        try {
            int start = json.indexOf(key + "\":") + key.length() + 3;
            int end = json.indexOf(",", start);

            if (end == -1)
                end = json.length();

            return json.substring(start, end).replace("\"", "").trim();

        } catch (Exception e) {
            return "";
        }
    }
}