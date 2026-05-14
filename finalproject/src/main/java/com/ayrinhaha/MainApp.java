
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

        // MULTITHREADING
        AutoSaveThread thread = new AutoSaveThread(json, finance);
        thread.start();

        int choice;

        do {
            System.out.println("\n1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Pay Tuition");
            System.out.println("4. Save");
            System.out.println("5. Send to Server");
            System.out.println("6. Exit");

            choice = sc.nextInt();

            switch (choice) {

                case 1 -> finance.addExpense(sc);

                case 2 -> finance.viewExpenses();

                case 3 -> finance.payTuition(sc);

                case 4 -> json.save(finance);

                case 5 -> client.send("Finance data sent");

            }

        } while (choice != 6);

        System.out.println("System closed.");
    }
}