package com.ayrinhaha.model;

public abstract class Transaction {

    protected String name;
    protected double amount;
    protected String timestamp;

    public Transaction(String name, double amount) {
        this.name = name;
        this.amount = amount;

        // simple timestamp
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public abstract void process();
}
