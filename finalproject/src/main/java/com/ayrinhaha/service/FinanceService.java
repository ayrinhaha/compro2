package com.ayrinhaha.service;

import com.ayrinhaha.model.*;
import com.ayrinhaha.repository.Repository;
import java.util.Scanner;

public class FinanceService {

    private Repository<Expense> expenses = new Repository<>();
    private Tuition tuition = new Tuition();

    public void addExpense(Scanner sc) {
        System.out.print("Enter name: ");

        String name = sc.next();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        System.out.print("Enter category: ");
        String category = sc.next();

        Expense exp = new Expense(name, amount, category);
        expenses.add(exp);

        System.out.println("Expense added!");
    }

    public void viewExpenses() {
        for (Expense e : expenses.getAll()) {
            System.out.println(e);
        }
    }

    public void payTuition(Scanner sc) {
        System.out.println("[1] DOWNPAYMENT\n[2] PRELIM\n[3] MIDTERMS\n[4] FINALS");
        int choice = sc.nextInt();

        Tuition.Stage stage = Tuition.Stage.values()[choice - 1];
        tuition.pay(stage);

        System.out.println(stage + "paid!");
    }

    public void viewTuition() {
        tuition.viewStatus();
    }
}
