package com.ayrinhaha.model;

import java.time.LocalDateTime;

public class TuitionPayment {

    private Tuition.Stage stage;
    private double amount;
    private LocalDateTime timestamp;

    public TuitionPayment(Tuition.Stage stage,
            double amount) {

        this.stage = stage;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public Tuition.Stage getStage() {
        return stage;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {

        return "\nStage: " + stage +
                "\nAmount: ₱" + amount +
                "\nDate: " + timestamp;
    }
}