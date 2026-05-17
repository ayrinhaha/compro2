package com.ayrinhaha;

import com.ayrinhaha.network.Client;
import com.ayrinhaha.service.*;
import com.ayrinhaha.thread.AutoSaveThread;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        FinanceService finance = new FinanceService();

        JsonService json = new JsonService();

        Client client = new Client();

        // ==============================
        // MULTITHREADING
        // ==============================

        AutoSaveThread thread = new AutoSaveThread(json, finance);

        thread.start();

        int choice;

        do {

            System.out.println("\n====== MAIN MENU ======");
            System.out.println("1. Set Budget");
            System.out.println("2. Add Expense");
            System.out.println("3. View Expenses");
            System.out.println("4. View Budget");
            System.out.println("5. Setup Tuition");
            System.out.println("6. Pay Tuition");
            System.out.println("7. View Tuition");
            System.out.println("8. View Server Data");
            System.out.println("9. View Tuition Payment History");
            System.out.println("0. Exit");

            System.out.print("\nEnter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1 -> finance.setBudget(sc);

                case 2 -> finance.addExpense(sc);

                case 3 -> finance.viewExpenses();

                case 4 -> finance.viewBudget();

                case 5 -> finance.setupTuition(sc);

                case 6 -> {

                    finance.payTuition(sc);

                    String data = finance.exportTuition();

                    client.send(data);

                    System.out.println(
                            "[TUITION SENT]");
                }

                case 7 -> finance.viewTuition();

                // ==============================
                // SAVE + SEND
                // ==============================

                case 8 -> {

                    System.out.println("\n=== EXPENSES (SERVER) ===");
                    json.loadExpenses(); // reads server_data.json

                    System.out.println("\n=== TUITION (SERVER) ===");
                    json.loadTuition(); // reads tuition_server.json
                }


                case 9 -> finance.viewTuitionHistory();

                case 0 ->
                    System.out.println(
                            "System closed.");

                default ->
                    System.out.println(
                            "Invalid choice.");
            }

        } while (choice != 0);

        sc.close();
    }
}