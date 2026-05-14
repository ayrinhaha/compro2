package com.ayrinhaha.service;

import com.ayrinhaha.model.*;
import com.ayrinhaha.repo.Repository;

import java.util.Scanner;

public class FinanceService {

    private Repository<Expense> expenses = new Repository<>();
    private Tuition tuition = new Tuition();

    private double budget = 0;

    // ==============================
    // SET BUDGET
    // ==============================
    public void setBudget(Scanner sc) {

        System.out.print("\nEnter your monthly budget: ");
        budget = sc.nextDouble();

        System.out.println("Budget set successfully: ₱" + budget);
    }

    // ==============================
    // ADD EXPENSE
    // ==============================
    public void addExpense(Scanner sc) {

        if (budget <= 0) {
            System.out.println("\n Please set your budget first!");
            return;
        }

        System.out.print("Enter expense name: ");
        String name = sc.next();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        System.out.print("Enter category: ");
        String category = sc.next();

        if (amount > budget) {
            System.out.println("\nNot enough budget!");
            return;
        }

        Expense exp = new Expense(name, amount, category);

        expenses.add(exp);

        budget -= amount;

        exp.process();

        System.out.println("\nExpense added successfully!");
        System.out.println("Remaining budget: " + budget);
    }

    // ==============================
    // VIEW EXPENSES
    // ==============================
    public void viewExpenses() {

        if (expenses.getAll().isEmpty()) {
            System.out.println("\nNo expenses yet.");
            return;
        }

        for (Expense e : expenses.getAll()) {
            System.out.println(e);
        }
    }

    // ==============================
    // VIEW BUDGET
    // ==============================
    public void viewBudget() {

        System.out.println("\nRemaining Budget: " + budget);
    }

    // ==============================
    // TUITION METHODS
    // ==============================
    public void setupTuition(Scanner sc) {
        tuition.setupTuition(sc);
    }

    public void payTuition(Scanner sc) {
        tuition.payTuition(sc);
    }

    public void viewTuition() {
        tuition.viewStatus();
    }
}
