package com.ayrinhaha.model;

import java.util.*;

/**
 * Handles tuition setup, payment processing, status tracking,
 * and payment history management.
 *
 * @author ayrinhaha
 */
public class Tuition {

        private List<TuitionPayment> history = new ArrayList<>();

        public enum Stage {
                DOWNPAYMENT,
                PRELIM,
                MIDTERM,
                FINALS
        }

        private double fullTuition;
        private double discountedTuition;
        private double discountRate;
        private boolean isInitialized = false;

        private Map<Stage, Double> amounts = new HashMap<>();
        private Map<Stage, Boolean> status = new HashMap<>();

        /**
         * Initializes the tuition system with default unpaid statuses.
         */
        public Tuition() {
                for (Stage s : Stage.values()) {
                        status.put(s, false);
                }
        }

        /**
         * Interactively sets up the tuition breakdown from user input.
         *
         * @param sc Scanner for user input.
         */
        public void setupTuition(Scanner sc) {
                System.out.println("\n==================================================");
                System.out.println("              TUITION SETUP");
                System.out.println("==================================================");

                try {

                        System.out.print("Enter full tuition: ");
                        fullTuition = sc.nextDouble();

                        if (fullTuition <= 0) {

                                System.out.println("Invalid tuition amount.");
                                return;
                        }

                } catch (Exception e) {

                        System.out.println("Invalid tuition input.");
                        sc.nextLine();
                        return;
                }

                try {

                        System.out.print("Scholarship discount %: ");
                        discountRate = sc.nextDouble();

                        if (discountRate < 0 || discountRate > 100) {

                                System.out.println("Discount must be between 0-100.");
                                return;
                        }

                } catch (Exception e) {

                        System.out.println("Invalid discount input.");
                        sc.nextLine();
                        return;
                }

                double discount = fullTuition * (discountRate / 100);
                discountedTuition = fullTuition - discount;

                double perStage = discountedTuition / 4;

                for (Stage s : Stage.values()) {
                        amounts.put(s, perStage);
                }

                isInitialized = true;

                System.out.println("\nBREAKDOWN");
                System.out.println("Original   : " + fullTuition);
                System.out.println("Discounted : " + discountedTuition);
                System.out.println("Per Stage  : " + perStage);
                System.out.println("==================================================\n");
        }

        /**
         * Processes a tuition payment interactively.
         *
         * @param sc Scanner for user input.
         */
        public void payTuition(Scanner sc) {
                if (!isInitialized) {
                        System.out.println("Please setup tuition first.");
                        return;
                }

                System.out.println("\n==================================================");
                System.out.println("            TUITION PAYMENT");
                System.out.println("==================================================");

                int i = 1;
                for (Stage s : Stage.values()) {
                        System.out.println(i + ". " + s + " - " + amounts.get(s));
                        i++;
                }

                int choice;

                try {

                        System.out.print("\nSelect stage: ");
                        choice = sc.nextInt();

                        if (choice < 1 || choice > Stage.values().length) {

                                System.out.println("Invalid stage.");
                                return;
                        }

                } catch (Exception e) {

                        System.out.println("Invalid input.");
                        sc.nextLine();
                        return;
                }

                Stage selected = Stage.values()[choice - 1];

                if (status.get(selected)) {
                        System.out.println("Already PAID: " + selected);
                        return;
                }

                System.out.println("Amount: " + amounts.get(selected));
                System.out.print("Confirm?\n1. Yes\n2. No\nChoice: ");
                int confirm;

                try {

                        confirm = sc.nextInt();

                } catch (Exception e) {

                        System.out.println("Invalid input.");
                        sc.nextLine();
                        return;
                }

                if (confirm == 1) {
                        status.put(selected, true);
                        TuitionPayment payment = new TuitionPayment(selected, amounts.get(selected));
                        history.add(payment);
                        System.out.println(selected + " PAID!");
                } else {
                        System.out.println("Cancelled");
                }

                viewStatus();
        }

        /**
         * Displays the current paid/unpaid status for all academic stages.
         */
        public void viewStatus() {
                System.out.println("\n==================================================");
                System.out.println("            TUITION STATUS");
                System.out.println("==================================================");

                for (Stage s : Stage.values()) {
                        System.out.println(s + " : " + (status.get(s) ? "PAID" : "UNPAID"));
                }

                System.out.println("\nRemaining: " + getRemainingBalance());
                System.out.println("==================================================\n");
        }

        /**
         * Calculates the remaining unpaid balance.
         *
         * @return The total remaining balance.
         */
        public double getRemainingBalance() {
                double paid = 0;
                for (Stage s : Stage.values()) {
                        if (status.get(s)) {
                                paid += amounts.get(s);
                        }
                }
                return discountedTuition - paid;
        }

        /**
         * Displays the history of all processed tuition payments.
         */
        public void viewPaymentHistory() {
                System.out.println("\n==================================================");
                System.out.println("           PAYMENT HISTORY");
                System.out.println("==================================================");

                if (history.isEmpty()) {
                        System.out.println("No payments yet.");
                        return;
                }

                for (TuitionPayment p : history) {
                        System.out.println(p);
                }

                System.out.println("==================================================\n");
        }

        /**
         * Exports base tuition data as a JSON string.
         *
         * @return Formatted JSON string.
         */
        public String exportTuitionData() {
                StringBuilder sb = new StringBuilder();
                sb.append("{\"type\":\"TUITION\",")
                                .append("\"fullTuition\":").append(fullTuition).append(",")
                                .append("\"discountedTuition\":").append(discountedTuition).append(",")
                                .append("\"remaining\":").append(getRemainingBalance())
                                .append("}");
                return sb.toString();
        }

        /**
         * Exports the latest tuition payment as JSON.
         *
         * @return JSON string of latest tuition payment.
         */
        /**
         * Exports latest tuition payment.
         *
         * @param username account owner
         * @return tuition payment JSON
         */
        public String exportLatestPayment(String username) {

                if (history.isEmpty()) {
                        return "{}";
                }

                TuitionPayment latest = history.get(history.size() - 1);

                return "{"
                                + "\"type\":\"TUITION\","
                                + "\"username\":\"" + username + "\","
                                + "\"stage\":\"" + latest.getStage() + "\","
                                + "\"amount\":" + latest.getAmount() + ","
                                + "\"timestamp\":\"" + latest.getTimestamp() + "\""
                                + "}";
        }


        public void setInitialized(boolean initialized) {
                this.isInitialized = initialized;
        }

        public void setStageAmount(Stage stage, double amount) {
                this.amounts.put(stage, amount);
        }

        public void markStagePaid(Stage stage) {
                this.status.put(stage, true);
        }

        public void addRestoredPayment(TuitionPayment payment) {
                this.history.add(payment);
        }

        public List<TuitionPayment> getHistory() {
                return this.history;
        }

        public Map<Stage, Double> getAmounts() {
                return this.amounts;
        }
}