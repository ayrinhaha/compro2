package com.ayrinhaha.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        System.out.println("              EXPENSE RECORDED");
        System.out.println("==================================================");

        System.out.printf("%-12s: %s%n", "Name", name);
        System.out.printf("%-12s: %.2f%n", "Amount", amount);
        System.out.printf("%-12s: %s%n", "Category", category);
        System.out.printf("%-12s: %s%n", "Timestamp", timestamp);

        System.out.println("==================================================");
        System.out.println("[SUCCESS] Expense successfully added.\n");
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

    public int getYear() {
        return LocalDateTime.parse(this.timestamp).getYear();
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
                + "\n                EXPENSE RECORD"
                + "\n--------------------------------------------------"
                + String.format("\n%-12s: %s", "Name", name)
                + String.format("\n%-12s: %.2f", "Amount", amount)
                + String.format("\n%-12s: %s", "Category", category)
                + String.format("\n%-12s: %s", "Timestamp", timestamp)
                + "\n--------------------------------------------------";
    }
}
