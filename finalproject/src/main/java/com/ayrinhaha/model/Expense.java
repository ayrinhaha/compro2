package com.ayrinhaha.model;

import java.time.LocalDateTime;

public class Expense extends Transaction {

    private String category;

    public Expense(String name,
            double amount,
            String category) {

        super(name, amount);

        this.category = category;
        this.timestamp = LocalDateTime.now();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void process() {

        System.out.println("Processing expense...");
        System.out.println("Expense: " + name);
        System.out.println("Amount: ₱" + amount);
        System.out.println("Category: " + category);
    }

    @Override
    public String toString() {

        return "\n=== EXPENSE DETAILS ===" +
                "\nName: " + name +
                "\nAmount: ₱" + amount +
                "\nCategory: " + category +
                "\nTimestamp: " + timestamp;
    }
}