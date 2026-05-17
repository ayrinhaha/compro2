// ==========================================
// Expense.java
// ==========================================

package com.ayrinhaha.model;

import java.time.LocalDate;

public class Expense extends Transaction {

    private String category;
    private int month;

    public Expense(String name,
                   double amount,
                   String category) {

        super(name, amount);

        this.category = category;

        this.month =
                LocalDate.now().getMonthValue();
    }

    @Override
    public void process() {

        System.out.println("\nProcessing expense...");
        System.out.println("Name: " + name);
        System.out.println("Amount: " + amount);
        System.out.println("Category: " + category);
    }

    public String getCategory() {
        return category;
    }

    public int getMonth() {
        return month;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {

        return "\nExpense" +
                "\nName: " + name +
                "\nAmount: " + amount +
                "\nCategory: " + category +
                "\nTimestamp: " + timestamp;
    }
}