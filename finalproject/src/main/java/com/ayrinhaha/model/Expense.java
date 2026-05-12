package com.ayrinhaha.model;

import java.time.LocalDateTime;


public class Expense extends Transaction {


    private String category;

    public Expense(String name, double amount, String category) {
        super(name, amount);
        tegory = this.category = category;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public void process() {
        // TODO: Add logic
    }

    @Override
    public String toString() {
        return name + " " + category + "|₱" + amount + " | " + timestamp;
    }
}