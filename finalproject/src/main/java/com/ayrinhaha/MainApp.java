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
            System.out.println("8. Save");
            System.out.println("9. Send to Server");
            System.out.println("10. Exit");

            System.out.print("\nEnter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1 -> finance.setBudget(sc);

                case 2 -> finance.addExpense(sc);

                case 3 -> finance.viewExpenses();

                case 4 -> finance.viewBudget();

                case 5 -> finance.setupTuition(sc);

                case 6 -> finance.payTuition(sc);

                case 7 -> finance.viewTuition();

                case 8 -> json.save(finance);

                case 9 -> client.send("Finance data sent");

                case 10 -> System.out.println("System closed.");

                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 10);

        sc.close();
    }
}
