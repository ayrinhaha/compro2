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

        /**
         * Academic payment stages.
         */
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
         * Initializes tuition stages as unpaid.
         */
        public Tuition() {

                for (Stage s : Stage.values()) {

                        status.put(s, false);
                }
        }

        /**
         * Sets up tuition information.
         *
         * @param sc scanner input
         */
        public void setupTuition(Scanner sc) {

                if (isInitialized) {

                        System.out.println(
                                        "Tuition already initialized.");

                        return;
                }

                System.out.println("\n==================================================");
                System.out.println("              TUITION SETUP");
                System.out.println("==================================================");

                // ==================================================
                // FULL TUITION INPUT
                // ==================================================

                try {

                        System.out.print("Enter full tuition: ");
                        fullTuition = sc.nextDouble();

                        if (fullTuition <= 0) {

                                System.out.println(
                                                "Invalid tuition amount.");

                                return;
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Invalid tuition input.");

                        sc.nextLine();

                        return;
                }

                // ==================================================
                // DISCOUNT INPUT
                // ==================================================

                try {

                        System.out.print(
                                        "Scholarship discount %: ");

                        discountRate = sc.nextDouble();

                        if (discountRate < 0
                                        || discountRate > 100) {

                                System.out.println(
                                                "Discount must be between 0-100.");

                                return;
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Invalid discount input.");

                        sc.nextLine();

                        return;
                }

                // ==================================================
                // COMPUTATION
                // ==================================================

                double discount = fullTuition * (discountRate / 100);

                discountedTuition = fullTuition - discount;

                double perStage = discountedTuition / 4;

                for (Stage s : Stage.values()) {

                        amounts.put(s, perStage);
                }

                isInitialized = true;

                // ==================================================
                // DISPLAY BREAKDOWN
                // ==================================================

                System.out.println("\nBREAKDOWN");

                System.out.printf(
                                "Original   : %.2f%n",
                                fullTuition);

                System.out.printf(
                                "Discounted : %.2f%n",
                                discountedTuition);

                System.out.printf(
                                "Per Stage  : %.2f%n",
                                perStage);

                System.out.println(
                                "==================================================\n");
        }

        /**
         * Processes tuition payment.
         *
         * @param sc scanner input
         * @return true if payment succeeds
         */
        public boolean payTuition(Scanner sc) {

                if (!isInitialized) {

                        System.out.println(
                                        "Please setup tuition first.");

                        return false;
                }

                System.out.println("\n==================================================");
                System.out.println("            TUITION PAYMENT");
                System.out.println("==================================================");

                int i = 1;

                for (Stage s : Stage.values()) {

                        System.out.printf(
                                        "%d. %s - %.2f%n",
                                        i,
                                        s,
                                        amounts.get(s));

                        i++;
                }

                // ==================================================
                // STAGE SELECTION
                // ==================================================

                int choice;

                try {

                        System.out.print("\nSelect stage: ");

                        choice = sc.nextInt();

                        if (choice < 1
                                        || choice > Stage.values().length) {

                                System.out.println(
                                                "Invalid stage.");

                                return false;
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Invalid input.");

                        sc.nextLine();

                        return false;
                }

                Stage selected = Stage.values()[choice - 1];

                // ==================================================
                // ALREADY PAID CHECK
                // ==================================================

                if (status.get(selected)) {

                        System.out.println(
                                        "Already PAID: " + selected);

                        return false;
                }

                // ==================================================
                // CONFIRMATION
                // ==================================================

                System.out.printf(
                                "Amount: %.2f%n",
                                amounts.get(selected));

                System.out.print(
                                "Confirm?\n1. Yes\n2. No\nChoice: ");

                int confirm;

                try {

                        confirm = sc.nextInt();

                } catch (Exception e) {

                        System.out.println(
                                        "Invalid input.");

                        sc.nextLine();

                        return false;
                }

                // ==================================================
                // PAYMENT PROCESSING
                // ==================================================

                if (confirm == 1) {

                        status.put(selected, true);

                        TuitionPayment payment = new TuitionPayment(
                                        selected,
                                        amounts.get(selected));

                        history.add(payment);

                        System.out.println(
                                        selected + " PAID!");

                        if (getRemainingBalance() == 0) {

                                System.out.println(
                                                "\n🎉 TUITION FULLY PAID");
                        }

                        viewStatus();

                        return true;

                } else {

                        System.out.println(
                                        "Payment cancelled.");

                        return false;
                }
        }

        /**
         * Displays tuition payment status.
         */
        public void viewStatus() {

                System.out.println("\n==================================================");
                System.out.println("            TUITION STATUS");
                System.out.println("==================================================");

                for (Stage s : Stage.values()) {

                        System.out.println(
                                        s + " : "
                                                        + (status.get(s)
                                                                        ? "PAID"
                                                                        : "UNPAID"));
                }

                System.out.printf(
                                "%nRemaining: %.2f%n",
                                getRemainingBalance());

                System.out.println(
                                "==================================================\n");
        }

        /**
         * Calculates remaining balance.
         *
         * @return remaining unpaid amount
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
         * Displays tuition payment history.
         */
        public void viewPaymentHistory() {

                System.out.println("\n==================================================");
                System.out.println("           PAYMENT HISTORY");
                System.out.println("==================================================");

                if (history.isEmpty()) {

                        System.out.println(
                                        "No payments yet.");

                        return;
                }

                for (TuitionPayment p : history) {

                        System.out.println(p);
                }

                System.out.println(
                                "==================================================\n");
        }

        /**
         * Exports tuition summary.
         *
         * @return tuition JSON
         */
        public String exportTuitionData() {

                return "{"
                                + "\"type\":\"TUITION\","
                                + "\"fullTuition\":" + fullTuition + ","
                                + "\"discountedTuition\":" + discountedTuition + ","
                                + "\"remaining\":" + getRemainingBalance()
                                + "}";
        }

        /**
         * Exports latest tuition payment.
         *
         * @param username account owner
         * @return tuition payment JSON
         */
        public String exportLatestPayment(
                        String username) {

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

        public void setInitialized(
                        boolean initialized) {

                this.isInitialized = initialized;
        }

        public void setStageAmount(
                        Stage stage,
                        double amount) {

                this.amounts.put(stage, amount);
        }

        public void markStagePaid(
                        Stage stage) {

                this.status.put(stage, true);
        }

        public void addRestoredPayment(
                        TuitionPayment payment) {

                this.history.add(payment);
        }

        public List<TuitionPayment> getHistory() {

                return this.history;
        }

        public Map<Stage, Double> getAmounts() {

                return this.amounts;
        }
}