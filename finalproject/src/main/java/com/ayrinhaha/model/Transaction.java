package com.ayrinhaha.model;

import java.time.LocalDateTime;

/**
 * Represents an abstract financial transaction.
 * Serves as the base class for specific transaction types.
 *
 * @author ayrinhaha
 */
public abstract class Transaction {

    protected String name;
    protected double amount;
    protected String timestamp;

    /**
     * Constructs a base transaction with a name and amount.
     * Automatically assigns the current date and time as the timestamp.
     *
     * @param name   The name or description of the transaction.
     * @param amount The monetary amount of the transaction.
     */
    public Transaction(String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.timestamp = LocalDateTime.now().toString();
    }

    /**
     * Processes the specific logic for the transaction.
     * To be implemented by subclasses.
     */
    public abstract void process();
}
