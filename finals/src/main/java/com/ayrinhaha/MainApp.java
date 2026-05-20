package com.ayrinhaha;

import com.ayrinhaha.model.UserAccount;
import com.ayrinhaha.network.Client;
import com.ayrinhaha.service.AccountService;
import com.ayrinhaha.service.FinanceService;
import com.ayrinhaha.service.JsonService;
import com.ayrinhaha.thread.AutoSaveThread;

import java.util.Scanner;

/**
 * Main entry point of the finance tracking system.
 *
 * Features:
 * - User authentication
 * - Expense budget management
 * - Expense tracking
 * - Tuition tracking
 * - Server transaction uploads
 * - Background autosave using multithreading
 *
 * @author ayrinhaha
 */
public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AccountService accountService = new AccountService();

        UserAccount currentUser = null;

        int authChoice;

        System.out.println("\n==================================================");
        System.out.println("          ACCOUNT AUTHENTICATION");
        System.out.println("==================================================");

        while (currentUser == null) {

            System.out.println("\n1. Sign In");
            System.out.println("2. Sign Up");
            System.out.println("0. Exit");

            System.out.println("==================================================");

            while (true) {

                try {

                    System.out.print("Enter choice: ");

                    authChoice = Integer.parseInt(sc.nextLine());

                    break;

                } catch (NumberFormatException e) {

                    System.out.println("[ERROR] Invalid input. Numbers only.");
                }
            }
            switch (authChoice) {

                case 1 -> {

                    System.out.println("\n==================================================");
                    System.out.println("                  SIGN IN");
                    System.out.println("==================================================");

                    System.out.print("Username : ");
                    String username = sc.nextLine();

                    System.out.print("Password : ");
                    String password = sc.nextLine();

                    currentUser = accountService.login(username, password);

                    if (currentUser != null) {

                        System.out.println("\n[SUCCESS] Login successful.");

                    } else {

                        System.out.println("\n[ERROR] Invalid username or password.");
                    }
                }

                case 2 -> {

                    System.out.println("\n==================================================");
                    System.out.println("                  SIGN UP");
                    System.out.println("==================================================");

                    System.out.print("Create Username : ");
                    String username = sc.nextLine();

                    System.out.print("Create Password : ");
                    String password = sc.nextLine();

                    currentUser = accountService.register(username, password);

                    System.out.println("\n[SUCCESS] Account successfully created.");
                }

                case 0 -> {

                    System.out.println("\n==================================================");
                    System.out.println("            SYSTEM TERMINATED");
                    System.out.println("==================================================");

                    sc.close();
                    return;
                }

                default -> System.out.println("[ERROR] Invalid choice.");
            }
        }

        FinanceService finance = new FinanceService();

        finance.setBudgetDirect(currentUser.budget);

        finance.restoreExpenses(currentUser.expenses);

        finance.restoreTuition(currentUser.tuition);

        JsonService json = new JsonService();

        Client client = new Client();

        AutoSaveThread autoSave = new AutoSaveThread(
                accountService,
                finance,
                currentUser);

        autoSave.start();

        int choice = 0;

        do {

            System.out.println("\n==================================================");
            System.out.println("     UTANG NA LOOB: STUDENT FINANCE TRACKER");
            System.out.println("==================================================");

            System.out.println("\n--------------- EXPENSE MANAGEMENT ---------------");
            System.out.println("[1] Set Budget");
            System.out.println("[2] Add Expense");
            System.out.println("[3] View Expenses");
            System.out.println("[4] View Budget");

            System.out.println("\n--------------- TUITION MANAGEMENT ---------------");
            System.out.println("[5] Setup Tuition");
            System.out.println("[6] Pay Tuition");
            System.out.println("[7] View Tuition");
            System.out.println("[8] View Tuition History");

            System.out.println("\n-------------------- SERVER ----------------------");
            System.out.println("[9] View Server Logs");

            System.out.println("\n[0] Exit");

            System.out.println("==================================================");

            while (true) {

                try {

                    System.out.print("Enter choice: ");

                    choice = Integer.parseInt(sc.nextLine());

                    break;

                } catch (NumberFormatException e) {

                    System.out.println("[ERROR] Invalid input. Numbers only.");
                }
            }

            switch (choice) {

                case 1 -> finance.setBudget(sc);

                case 2 -> {

                    boolean success = finance.addExpense(sc);

                    if (success) {

                        String data = finance.exportLatestExpense(
                                currentUser.getUsername());

                        client.send(data);

                        System.out.println("\n==================================================");
                        System.out.println("            EXPENSE UPLOADED");
                        System.out.println("==================================================");
                        System.out.println("[SUCCESS] Expense transaction sent to server.");
                    }
                }

                case 3 -> finance.viewExpenses();

                case 4 -> finance.viewBudget();

                case 5 -> finance.setupTuition(sc);

                case 6 -> {

                    boolean success = finance.payTuition(sc);

                    if (success) {

                        String data = finance.exportBulkTuition(
                                currentUser.getUsername());

                        client.send(data);

                        System.out.println("\n==================================================");
                        System.out.println("            TUITION UPLOADED");
                        System.out.println("==================================================");
                        System.out.println("[SUCCESS] Tuition transaction sent to server.");
                    }
                }

                case 7 -> finance.viewTuition();

                case 8 -> finance.viewTuitionHistory();

                case 9 -> {

                    System.out.println("\n==================================================");
                    System.out.println("          SERVER TRANSACTION LOG");
                    System.out.println("==================================================");

                    json.loadServerLogs(currentUser.getUsername());

                    System.out.println("==================================================");
                }

                case 0 -> {

                    autoSave.interrupt();

                    System.out.println("\n==================================================");
                    System.out.println("              SYSTEM TERMINATED");
                    System.out.println("==================================================");

                    System.out.println("Thank you for using");
                    System.out.println("UTANG NA LOOB: STUDENT FINANCE TRACKER");
                    System.out.println("Keep going para hindi ka mabaon sa utang :)");

                    System.out.println("==================================================");
                }

                default -> System.out.println("[ERROR] Invalid choice.");
            }

        } while (choice != 0);

        sc.close();
    }
}