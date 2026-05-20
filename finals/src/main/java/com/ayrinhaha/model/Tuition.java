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

                        System.out.println("[ERROR] Tuition already initialized.");
                        return;
                }

                System.out.println("\n==================================================");
                System.out.println("              TUITION SETUP");
                System.out.println("==================================================");

                while (true) {

                        try {

                                System.out.print("Enter full tuition: ");

                                fullTuition = Double.parseDouble(sc.nextLine());

                                if (fullTuition <= 0) {

                                        System.out.println("[ERROR] Invalid tuition amount.");
                                        continue;
                                }

                                break;

                        } catch (NumberFormatException e) {

                                System.out.println("[ERROR] Invalid tuition input.");
                        }
                }

                while (true) {

                        try {

                                System.out.print("Scholarship discount %: ");

                                discountRate = Double.parseDouble(sc.nextLine());

                                if (discountRate < 0 || discountRate > 100) {

                                        System.out.println("[ERROR] Discount must be between 0-100.");
                                        continue;
                                }

                                break;

                        } catch (NumberFormatException e) {

                                System.out.println("[ERROR] Invalid discount input.");
                        }
                }

                double discount = fullTuition * (discountRate / 100);

                discountedTuition = fullTuition - discount;

                double perStage = discountedTuition / 4;

                for (Stage s : Stage.values()) {

                        amounts.put(s, perStage);
                }

                isInitialized = true;

                System.out.println("\n==================================================");
                System.out.println("                TUITION BREAKDOWN");
                System.out.println("==================================================");

                System.out.printf("%-15s: %.2f%n", "Original", fullTuition);
                System.out.printf("%-15s: %.2f%n", "Discounted", discountedTuition);
                System.out.printf("%-15s: %.2f%n", "Per Stage", perStage);

                System.out.println("==================================================");
                System.out.println("[SUCCESS] Tuition setup completed.\n");
        }

        /**
         * Processes tuition payment.
         *
         * @param sc scanner input
         * @return true if payment succeeds
         */
        public boolean payTuition(Scanner sc) {

                if (!isInitialized) {
                        System.out.println("[ERROR] Please setup tuition first.");
                        return false;
                }

                System.out.println("\n==================================================");
                System.out.println("            TUITION PAYMENT");
                System.out.println("==================================================");

                int i = 1;

                // Print individual stages
                for (Stage s : Stage.values()) {

                        System.out.printf(
                                        "%d. %-12s - %.2f%n",
                                        i,
                                        s,
                                        amounts.get(s));

                        i++;
                }

                int payAllChoice = i;

                System.out.printf(
                                "%d. %-12s - %.2f%n",
                                payAllChoice,
                                "PAY ALL",
                                getRemainingBalance());

                int choice;

                while (true) {

                        try {

                                System.out.print("\nSelect stage: ");

                                choice = Integer.parseInt(sc.nextLine());

                                if (choice < 1 || choice > payAllChoice) {

                                        System.out.println("[ERROR] Invalid stage.");
                                        continue;
                                }

                                break;

                        } catch (NumberFormatException e) {

                                System.out.println("[ERROR] Invalid input.");
                        }
                }

                /**
                 * Pay all logic with confirmation.
                 */
                if (choice == payAllChoice) {

                        if (getRemainingBalance() == 0) {

                                System.out.println("[ERROR] Tuition already fully paid.");
                                return false;
                        }

                        System.out.printf(
                                        "\nTotal Remaining Amount : %.2f%n",
                                        getRemainingBalance());

                        System.out.println("\nConfirm Full Payment?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.print("Enter choice: ");

                        int confirm = getConfirmation(sc);

                        if (confirm == 1) {

                                /**
                                 * Shared timestamp for all stages.
                                 */
                                String sharedTimestamp = java.time.LocalDateTime.now().toString();

                                for (Stage s : Stage.values()) {

                                        if (!status.get(s)) {

                                                status.put(s, true);

                                                TuitionPayment payment = new TuitionPayment(
                                                                s,
                                                                amounts.get(s),
                                                                sharedTimestamp);

                                                history.add(payment);
                                        }
                                }

                                System.out.println(
                                                "\n[SUCCESS] All remaining tuition fully paid.");

                                viewStatus();

                                return true;

                        } else {

                                System.out.println("[INFO] Payment cancelled.");
                                return false;
                        }
                }

                /**
                 * SINGLE PAYMENT LOGIC
                 */
                Stage selected = Stage.values()[choice - 1];

                if (status.get(selected)) {

                        System.out.println(
                                        "[ERROR] Already PAID: " + selected);

                        return false;
                }

                System.out.printf(
                                "\nSelected Amount : %.2f%n",
                                amounts.get(selected));

                System.out.println("\nConfirm Payment?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Enter choice: ");

                int confirm = getConfirmation(sc);

                if (confirm == 1) {

                        status.put(selected, true);

                        TuitionPayment payment = new TuitionPayment(
                                        selected,
                                        amounts.get(selected));

                        history.add(payment);

                        System.out.println(
                                        "\n[SUCCESS] "
                                                        + selected
                                                        + " payment completed.");

                        if (getRemainingBalance() == 0) {

                                System.out.println(
                                                "\n[SUCCESS] Tuition fully paid.");
                        }

                        viewStatus();

                        return true;

                } else {

                        System.out.println("[INFO] Payment cancelled.");
                        return false;
                }

        }

        /**
         * Handles confirmation input validation.
         *
         * @param sc scanner input
         * @return validated confirmation choice
         */
        private int getConfirmation(Scanner sc) {

                int confirm;

                while (true) {

                        try {

                                confirm = Integer.parseInt(sc.nextLine());

                                if (confirm != 1 && confirm != 2) {

                                        System.out.println("Enter 1 or 2:");
                                        continue;
                                }

                                break;

                        } catch (NumberFormatException e) {

                                System.out.println("[ERROR] Invalid input.");
                        }
                }

                return confirm;
        }

        /**
         * Exports all latest tuition payments
         * that share the same timestamp.
         *
         * Used for PAY ALL transactions.
         *
         * @param username account owner
         * @return JSON array string
         */
        public String exportLatestBulkPayments(String username) {

                if (history.isEmpty()) {

                        return "[]";
                }

                String latestTimestamp = history.get(history.size() - 1)
                                .getTimestamp();

                StringBuilder json = new StringBuilder();

                json.append("[");

                boolean first = true;

                for (TuitionPayment payment : history) {

                        if (payment.getTimestamp()
                                        .equals(latestTimestamp)) {

                                if (!first) {

                                        json.append(",");
                                }

                                json.append("{")
                                                .append("\"type\":\"TUITION\",")
                                                .append("\"username\":\"")
                                                .append(username)
                                                .append("\",")
                                                .append("\"stage\":\"")
                                                .append(payment.getStage())
                                                .append("\",")
                                                .append("\"amount\":")
                                                .append(payment.getAmount())
                                                .append(",")
                                                .append("\"timestamp\":\"")
                                                .append(payment.getTimestamp())
                                                .append("\"")
                                                .append("}");

                                first = false;
                        }
                }

                json.append("]");

                return json.toString();
        }

        /**
         * Displays tuition payment status.
         */
        public void viewStatus() {

                System.out.println("\n==================================================");
                System.out.println("                TUITION STATUS");
                System.out.println("==================================================");

                for (Stage s : Stage.values()) {

                        System.out.printf(
                                        "%-12s : %s%n",
                                        s,
                                        status.get(s) ? "PAID" : "UNPAID");
                }

                System.out.printf(
                                "\nRemaining Balance : %.2f%n",
                                getRemainingBalance());

                System.out.println("==================================================\n");
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
                System.out.println("               PAYMENT HISTORY");
                System.out.println("==================================================");

                if (history.isEmpty()) {

                        System.out.println("[ERROR] No payments found.");
                        System.out.println("==================================================");
                        return;
                }

                int count = 1;

                for (TuitionPayment p : history) {

                        System.out.println("\nPayment #" + count++);
                        System.out.println(p);
                }

                System.out.println("==================================================\n");
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