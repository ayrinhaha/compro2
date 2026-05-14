package com.ayrinhaha.model;

public class Expense extends Transaction {

    private String category;

    public Expense(String name, double amount, String category) {
        super(name, amount);
        this.category = category;
    }

    @Override
    public void process() {

        System.out.println("\nProcessing expense...");
        System.out.println("Expense: " + name);
        System.out.println("Amount: ₱" + amount);
        System.out.println("Category: " + category);
        System.out.println("Timestamp: " + timestamp);
    }

    @Override
    public String toString() {

        return "Expese Name: " + name +
                "Amount: " + amount +
                "Category: " + category +
                "Time: " + timestamp;
    }

}
