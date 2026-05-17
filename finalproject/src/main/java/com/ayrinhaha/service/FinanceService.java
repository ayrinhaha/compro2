package com.ayrinhaha.service;

import com.ayrinhaha.model.Expense;
import com.ayrinhaha.model.Tuition;
import com.ayrinhaha.repo.Repository;

import java.time.LocalDate;
import java.util.Scanner;

public class FinanceService {

    private Repository<Expense> expenses = new Repository<>();
    private Tuition tuition = new Tuition();

    private double budget = 0;

    // ==============================
    // MONTH RESET
    // ==============================

    private void checkMonthlyReset() {

        int currentMonth = LocalDate.now().getMonthValue();

        expenses.getAll().removeIf(
                e -> e.getMonth() != currentMonth);
    }

    // ==============================
    // BUDGET
    // ==============================

    public void setBudget(Scanner sc) {

        System.out.print("\nEnter budget: ₱");
        budget = sc.nextDouble();

        System.out.println("Budget set: ₱" + budget);
    }

    public void viewBudget() {

        System.out.println(
                "\nRemaining Budget: ₱" + budget);
    }

    // ==============================
    // EXPENSE
    // ==============================

    public void addExpense(Scanner sc) {

        checkMonthlyReset();

        if (budget <= 0) {
            System.out.println("Set budget first!");
            return;
        }

        System.out.print("Name: ");
        String name = sc.next();

        System.out.print("Amount: ");
        double amount = sc.nextDouble();

        System.out.print("Category: ");
        String category = sc.next();

        if (amount <= 0 || amount > budget) {
            System.out.println("Invalid or insufficient budget!");
            return;
        }

        Expense e = new Expense(name, amount, category);

        expenses.add(e);

        budget -= amount;

        e.process();

        System.out.println(
                "Remaining Budget: ₱" + budget);
    }

    public void viewExpenses() {

        checkMonthlyReset();

        for (Expense e : expenses.getAll()) {
            System.out.println(e);
        }
    }

    // ==============================
    // TUITION
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

    public String exportTuition() {
        return tuition.exportTuitionData();
    }

    public void viewTuitionHistory() {
        tuition.viewPaymentHistory();
    }

    // ==============================
    // EXPORT DATA (FOR SERVER ONLY)
    // ==============================

    public String exportData() {

        StringBuilder sb = new StringBuilder();

        sb.append("{")
                .append("\"type\":\"EXPENSE\",")
                .append("\"budget\":").append(budget).append(",")
                .append("\"expenses\":[");

        for (Expense e : expenses.getAll()) {

            sb.append("{")
                    .append("\"name\":\"").append(e.getName()).append("\",")
                    .append("\"amount\":").append(e.getAmount()).append(",")
                    .append("\"category\":\"").append(e.getCategory()).append("\"")
                    .append("}");
        }

        sb.append("]}");

        return sb.toString();
    }
}