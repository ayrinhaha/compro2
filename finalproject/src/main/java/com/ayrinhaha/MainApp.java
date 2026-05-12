

package com.ayrinhaha;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // TODO:
        // Initialize services

        // TODO:
        // Start AutoSaveThread

        int choice;

        do {

            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Pay Tuition");
            System.out.println("4. Save Data");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    // TODO:
                    // Add expense logic

                    break;

                case 2:

                    // TODO:
                    // View expenses

                    break;

                case 3:

                    // TODO:
                    // Tuition payment logic

                    break;

                case 4:

                    // TODO:
                    // Save data manually

                    break;

                case 5:

                    System.out.println("Exiting system...");
                    break;

                default:

                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);
    }
}
