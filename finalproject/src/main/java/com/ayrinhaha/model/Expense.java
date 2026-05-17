package com.ayrinhaha.model;

import java.time.LocalDate;

/**
 * Represents an expense transaction in the system.
 * Extends the base Transaction class to include categorization
 * and monthly tracking.
 *
 * @author ayrinhaha
 */
public class Expense extends Transaction {

    private String category;
    private int month;

    /**
     * Constructs a new Expense.
     *
     * @param name     The description of the expense.
     * @param amount   The cost of the expense.
     * @param category The category of the expense.
     */
    public Expense(String name, double amount, String category) {
        super(name, amount);
        this.category = category;
        this.month = LocalDate.now().getMonthValue();
    }

    /**
     * Displays the processed expense information.
     */
    @Override
    public void process() {

        System.out.println("\n==================================================");
        System.out.println("               EXPENSE PROCESSING");
        System.out.println("==================================================");

        System.out.println("Name       : " + name);
        System.out.println("Amount     : " + amount);
        System.out.println("Category   : " + category);
        System.out.println("Timestamp  : " + timestamp);

        System.out.println("==================================================\n");
    }

    /**
     * Exports expense data as JSON.
     *
     * @param username account owner
     * @return JSON expense string
     */
    public String toJson(String username) {

        return "{"
                + "\"type\":\"EXPENSE\","
                + "\"username\":\"" + username + "\","
                + "\"name\":\"" + name + "\","
                + "\"amount\":" + amount + ","
                + "\"category\":\"" + category + "\","
                + "\"timestamp\":\"" + timestamp + "\""
                + "}";
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

    /**
     * Returns formatted expense details.
     *
     * @return formatted expense string
     */
    @Override
    public String toString() {

        return "\n--------------------------------------------------"
                + "\nEXPENSE RECORD"
                + "\n--------------------------------------------------"
                + "\nName       : " + name
                + "\nAmount     : " + amount
                + "\nCategory   : " + category
                + "\nTimestamp  : " + timestamp
                + "\n--------------------------------------------------";
    }
}