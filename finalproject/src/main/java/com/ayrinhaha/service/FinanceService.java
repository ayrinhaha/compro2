package com.ayrinhaha.service;

import com.ayrinhaha.model.*;
import com.ayrinhaha.repo.Repository;

import java.util.Scanner;

public class FinanceService {

    private Repository<Expense> expenses = new Repository<>();
    private Tuition tuition = new Tuition();


    public void addExpense(Scanner sc) {

        System.out.print("Enter expense name: ");
        String name = sc.next();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        System.out.print("Enter category: ");
        String category = sc.next();

        Expense exp =
                new Expense(name, amount, category);

        expenses.add(exp);

        exp.process();

        System.out.println("\nExpense added successfully!");
    }

    public void viewExpenses() {

        if (expenses.getAll().isEmpty()) {

            System.out.println("\nNo expenses recorded.");
            return;
        }

        for (Expense e : expenses.getAll()) {
            System.out.println(e);
        }
    }


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