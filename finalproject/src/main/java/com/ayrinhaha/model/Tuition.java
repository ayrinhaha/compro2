package com.ayrinhaha.model;

import java.util.HashMap;

public class Tuition {

    public enum Stage {
        DOWNPAYMENT, PRELIM, MIDTERM, FINALS
    }

    private HashMap<Stage, Boolean> status;

    public Tuition() {

        status = new HashMap<>();
        for (Stage s : Stage.values()) {
            status.put(s, false);
        }
    }

    public void pay(Stage stage) {
        status.put(stage, true);
    }

    public void viewStatus() {
        for (Stage s : status.keySet()) {
            System.out.println(s + ": " + (status.get(s) ? "PAID" : "UNPAID"));
        }
    }
}