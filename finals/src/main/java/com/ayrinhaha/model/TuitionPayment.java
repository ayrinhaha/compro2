package com.ayrinhaha.model;

import java.time.LocalDateTime;

/**
 * Represents a tuition payment record.
 * Tracks payment stage, amount, and timestamp.
 *
 * @author ayrinhaha
 */
public class TuitionPayment {

    private Tuition.Stage stage;

    private double amount;

    /**
     * Stored as String for simpler JSON serialization.
     */
    private String timestamp;

    /**
     * Constructs a tuition payment.
     *
     * @param stage  academic stage
     * @param amount payment amount
     */
    public TuitionPayment(
            Tuition.Stage stage,
            double amount) {

        this.stage = stage;
        this.amount = amount;

        this.timestamp = LocalDateTime.now().toString();
    }

    /**
     * Used for restoring saved payment history.
     *
     * @param stage     payment stage
     * @param amount    payment amount
     * @param timestamp saved timestamp
     */
    public TuitionPayment(
            Tuition.Stage stage,
            double amount,
            String timestamp) {

        this.stage = stage;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Tuition.Stage getStage() {
        return stage;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Returns formatted payment details.
     *
     * @return formatted string
     */
    @Override
    public String toString() {

        return "\n--------------------------------------------------"
                + "\n               TUITION PAYMENT"
                + "\n--------------------------------------------------"
                + String.format("\n%-12s: %s", "Stage", stage)
                + String.format("\n%-12s: %.2f", "Amount", amount)
                + String.format("\n%-12s: %s", "Timestamp", timestamp)
                + "\n--------------------------------------------------";
    }
}