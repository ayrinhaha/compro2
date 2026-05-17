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
        System.out.println(" " + title);
        System.out.println("==================================================");
    }

    /**
     * Removes expenses that are not from the current month.
     */
    private void checkMonthlyReset() {

        int currentMonth = LocalDate.now().getMonthValue();

        expenses.getAll().removeIf(
                e -> e.getMonth() != currentMonth);
    }

    /**
     * Sets the expense budget.
     *
     * @param sc Scanner input
     */
    public void setBudget(Scanner sc) {

        header("BUDGET SETUP");

        try {

            System.out.print("Enter budget: ");
            budget = sc.nextDouble();

            if (budget < 0) {

                System.out.println("Budget cannot be negative.");
                return;
            }

        } catch (Exception e) {

            System.out.println("Invalid budget input.");
            sc.nextLine();
            return;
        }
        System.out.println("\nExpense budget successfully set.");
        System.out.println("Current Budget : " + budget);
    }

    /**
     * Displays current remaining expense budget.
     */
    public void viewBudget() {

        header("BUDGET STATUS");

        System.out.println("Remaining Expense Budget : " + budget);
    }

    /**
     * Adds an expense and deducts from budget.
     *
     * @param sc Scanner input
     */
    public void addExpense(Scanner sc) {

        header("ADD EXPENSE");

        checkMonthlyReset();

        if (budget <= 0) {

            System.out.println("Please set your expense budget first.");
            return;
        }

        System.out.print("Expense Name     : ");
        String name = sc.next();

        double amount;

        try {

            System.out.print("Amount: ");
            amount = sc.nextDouble();

            if (amount <= 0) {

                System.out.println("Amount must be positive.");
                return;
            }

        } catch (Exception e) {

            System.out.println("Invalid amount input.");
            sc.nextLine();
            return;
        }

        System.out.print("Expense Category : ");
        String category = sc.next();

        if (amount <= 0) {

            System.out.println("Expense amount must be greater than zero.");
            return;
        }

        if (amount > budget) {

            System.out.println("Insufficient expense budget.");
            return;
        }

        Expense expense = new Expense(name, amount, category);

        expenses.add(expense);

        budget -= amount;

        expense.process();

        System.out.println("Remaining Budget : " + budget);
    }

    /**
     * Displays all expense records.
     */
    public void viewExpenses() {

        header("EXPENSE LIST");

        checkMonthlyReset();

        if (expenses.getAll().isEmpty()) {

            System.out.println("No expense records found.");
            return;
        }

        for (Expense e : expenses.getAll()) {
            System.out.println(e);
        }
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

        int currentMonth = LocalDate.now().getMonthValue();

        for (Expense e : list) {

            if (e.getMonth() == currentMonth) {
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