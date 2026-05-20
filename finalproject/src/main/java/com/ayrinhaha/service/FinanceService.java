package com.ayrinhaha.service;

import com.ayrinhaha.model.Expense;
import com.ayrinhaha.model.Tuition;
import com.ayrinhaha.repo.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles all finance-related operations including:
 * budget management, expense tracking, and tuition coordination.
 *
 * Budget only applies to EXPENSES.
 * Tuition payments are independent from budget.
 *
 * @author ayrinhaha
 */
public class FinanceService {

    private Repository<Expense> expenses = new Repository<>();
    private Tuition tuition = new Tuition();
    private double startingBudget = 0;
    private int lastCheckedMonth = LocalDate.now().getMonthValue();

    /**
     * Expense-only budget.
     */
    private double budget = 0;

    /**
     * Prints a clean section header.
     *
     * @param title section title
     */
    private void header(String title) {

        System.out.println("\n==================================================");
        System.out.printf("%30s%n", title);
        System.out.println("==================================================");
    }

    /**
     * Removes expenses that are not from the current month.
     */
    private void checkMonthlyReset() {

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        if (currentMonth != lastCheckedMonth) {
            this.budget = this.startingBudget;
            this.lastCheckedMonth = currentMonth;
        }

        expenses.getAll().removeIf(
                e -> e.getMonth() != currentMonth || e.getYear() != currentYear);
    }

    /**
     * Sets the expense budget.
     *
     * @param sc Scanner input
     */
    public void setBudget(Scanner sc) {

        header("BUDGET SETUP");

        while (true) {

            try {

                System.out.print("Enter budget: ");

                double inputBudget = Double.parseDouble(sc.nextLine());

                if (inputBudget <= 0) {

                    System.out.println("[ERROR] Budget must be greater than 0.");
                    continue;
                }

                this.startingBudget = inputBudget;
                this.budget = inputBudget;
                break;

            } catch (NumberFormatException e) {

                System.out.println("[ERROR] Invalid budget input.");
            }
        }

        System.out.println("\n[SUCCESS] Expense budget successfully set.");
        System.out.printf("Current Budget : %.2f%n", budget);
    }

    /**
     * Displays current remaining expense budget.
     */
    public void viewBudget() {

        header("BUDGET STATUS");

        System.out.printf("Remaining Expense Budget : %.2f%n", budget);

        System.out.println("==================================================");
    }

    /**
     * Adds an expense and deducts from budget.
     *
     * @param sc Scanner input
     */
    public boolean addExpense(Scanner sc) {

        header("ADD EXPENSE");

        checkMonthlyReset();

        if (budget <= 0) {

            System.out.println("[ERROR] Please set your expense budget first.");
            return false;
        }

        String name;

        while (true) {

            System.out.print("Expense Name     : ");

            name = sc.nextLine().trim();

            if (!name.isEmpty()) {
                break;
            }

            System.out.println("[ERROR] Expense name cannot be empty.");
        }

        double amount;

        while (true) {

            try {

                System.out.print("Amount           : ");

                amount = Double.parseDouble(sc.nextLine());

                if (amount <= 0) {

                    System.out.println("[ERROR] Amount must be greater than 0.");
                    continue;
                }

                if (amount > budget) {

                    System.out.println("[ERROR] Insufficient expense budget.");
                    return false;
                }

                break;

            } catch (NumberFormatException e) {

                System.out.println("[ERROR] Invalid amount input.");
            }
        }

        String category;

        while (true) {
            System.out.print("Expense Category : ");
            category = sc.nextLine().trim();

            if (!category.isEmpty()) {
                break;
            }

            System.out.println("[ERROR] Category cannot be empty.");
        }

        Expense expense = new Expense(
                name,
                amount,
                category);

        expenses.add(expense);

        budget -= amount;

        expense.process();

        System.out.printf("Remaining Budget : %.2f%n", budget);

        System.out.println("[SUCCESS] Budget updated successfully.");

        return true;
    }

    /**
     * Displays all expense records.
     */
    public void viewExpenses() {

        header("EXPENSE LIST");

        checkMonthlyReset();

        if (expenses.getAll().isEmpty()) {

            System.out.println("[ERROR] No expense records found.");
            System.out.println("==================================================");
            return;
        }

        int count = 1;

        for (Expense e : expenses.getAll()) {

            System.out.println("\nExpense #" + count++);
            System.out.println(e);
        }

        System.out.println("==================================================");
    }

    /**
     * Exports latest bulk tuition payments.
     *
     * @param username account owner
     * @return tuition JSON array
     */
    public String exportBulkTuition(String username) {

        return tuition.exportLatestBulkPayments(username);
    }

    /**
     * Delegates tuition setup.
     *
     * @param sc Scanner input
     */
    public void setupTuition(Scanner sc) {
        tuition.setupTuition(sc);
    }

    /**
     * Delegates tuition payment.
     *
     * @param sc Scanner input
     */
    public boolean payTuition(Scanner sc) {

        
        return tuition.payTuition(sc);
    }

    

    /**
     * Displays tuition status.
     */
    public void viewTuition() {
        tuition.viewStatus();
    }

    /**
     * Displays tuition payment history.
     */
    public void viewTuitionHistory() {
        tuition.viewPaymentHistory();
    }

    /**
     * Exports tuition JSON data.
     *
     * @return tuition JSON string
     */

    /**
     * Exports latest tuition payment.
     *
     * @param username account owner
     * @return tuition JSON
     */
    public String exportTuition(String username) {

        return tuition.exportLatestPayment(username);
    }

    /**
     * Exports latest expense transaction.
     *
     * @param username account owner
     * @return latest expense JSON
     */
    public String exportLatestExpense(String username) {

        if (expenses.getAll().isEmpty()) {
            return "{}";
        }

        Expense latest = expenses.getAll()
                .get(expenses.getAll().size() - 1);

        return latest.toJson(username);
    }

    /**
     * Directly restores budget.
     *
     * @param budget restored budget
     */
    public void setBudgetDirect(double budget) {
        this.budget = budget;
    }

    /**
     * Returns current budget.
     *
     * @return current expense budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Returns a copy of expenses.
     *
     * @return expense list copy
     */
    public List<Expense> getExpensesCopy() {
        return new ArrayList<>(expenses.getAll());
    }

    /**
     * Restores only current month expenses.
     *
     * Older expenses are automatically ignored
     * because the tracker is monthly-based.
     *
     * @param list saved expense list
     */
    public void restoreExpenses(List<Expense> list) {

        expenses.getAll().clear();

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        for (Expense e : list) {

            if (e.getMonth() == currentMonth && e.getYear() == currentYear) {
                expenses.add(e);
            }
        }
    }

    /**
     * Restores tuition object.
     *
     * @param t restored tuition
     */
    public void restoreTuition(Tuition t) {
        this.tuition = t;
    }

    /**
     * Returns tuition instance.
     *
     * @return tuition object
     */
    public Tuition getTuition() {
        return tuition;
    }
}
