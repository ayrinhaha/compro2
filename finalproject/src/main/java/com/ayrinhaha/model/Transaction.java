package com.ayrinhaha.model;

import java.time.LocalDateTime;

public abstract class Transaction {
    protected String name;
    protected double amount;
    protected LocalDateTime timestamp;

    public Transaction(String name, double amount) {
        this.name = name;
        setAmount(amount);
    }

    public void setAmount(double amount) {
        if (amount >= 0) {
            this.amount = amount;
        } else {

            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public abstract void process();
}
