package com.ayrinhaha.model;

import java.util.*;

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

        // =========================
        // CONSTRUCTOR
        // =========================

        public Tuition() {
                for (Stage s : Stage.values()) {
                        status.put(s, false);
                }
        }

        // =========================
        // SETUP TUITION
        // =========================

        public void setupTuition(Scanner sc) {

                System.out.print("\nEnter full tuition: ₱");
                fullTuition = sc.nextDouble();

                System.out.print("Scholarship discount % (0 if none): ");
                discountRate = sc.nextDouble();

                double discount = fullTuition * (discountRate / 100);
                discountedTuition = fullTuition - discount;

                double perStage = discountedTuition / 4;

                for (Stage s : Stage.values()) {
                        amounts.put(s, perStage);
                }

                isInitialized = true;

                System.out.println("\n=== TUITION BREAKDOWN ===");
                System.out.println("Original: " + fullTuition);
                System.out.println("Discounted: " + discountedTuition);
                System.out.println("Per Stage: " + perStage);
        }

        // =========================
        // PAY TUITION
        // =========================

        public void payTuition(Scanner sc) {

                // VALIDATION
                if (!isInitialized) {
                        System.out.println(
                                        "❌ You need to setup tuition first.");
                        return;
                }

                System.out.println("\n=== PAYMENT STAGES ===");

                int i = 1;
                for (Stage s : Stage.values()) {
                        System.out.println(i + ". " + s + " - ₱" + amounts.get(s));
                        i++;
                }

                System.out.print("\nSelect stage: ");
                int choice = sc.nextInt();

                Stage selected = Stage.values()[choice - 1];

                // =========================
                // ALREADY PAID CHECK
                // =========================

                if (status.get(selected)) {
                        System.out.println(
                                        "❌ " + selected + " already PAID!");
                        return;
                }

                double amount = amounts.get(selected);

                System.out.println("You will pay: ₱" + amount);

                System.out.print("Confirm? (1-Yes | 2-No): ");
                int confirm = sc.nextInt();

                if (confirm == 1) {

                        status.put(selected, true);

                        TuitionPayment payment = new TuitionPayment(selected, amount);

                        history.add(payment);

                        System.out.println(selected + " PAID!");
                        System.out.println("Logged successfully!");
                        System.out.println("[TUITION SENT TO SERVER]");
                } else {
                        System.out.println("Cancelled payment.");
                }

                viewStatus();
        }

        // =========================
        // VIEW STATUS
        // =========================

        public void viewStatus() {

                System.out.println("\n=== TUITION STATUS ===");

                for (Stage s : Stage.values()) {
                        System.out.println(s + " : " +
                                        (status.get(s) ? "PAID" : "UNPAID"));
                }

                System.out.println("\nRemaining Balance: ₱" + getRemainingBalance());
        }

        // =========================
        // REMAINING BALANCE
        // =========================

        public double getRemainingBalance() {

                double paid = 0;

                for (Stage s : Stage.values()) {

                        if (status.get(s)) {
                                paid += amounts.get(s);
                        }
                }

                return discountedTuition - paid;
        }

        // =========================
        // PAYMENT HISTORY
        // =========================

        public void viewPaymentHistory() {

                System.out.println("\n=== TUITION PAYMENT HISTORY ===");

                if (history.isEmpty()) {
                        System.out.println("No payments yet.");
                        return;
                }

                for (TuitionPayment p : history) {
                        System.out.println(p);
                }
        }

        // =========================
        // EXPORT TUITION DATA
        // =========================

        public String exportTuitionData() {

                StringBuilder sb = new StringBuilder();

                sb.append("{")
                                .append("\"type\":\"TUITION\",")

                                .append("\"fullTuition\":")
                                .append(fullTuition).append(",")

                                .append("\"discountedTuition\":")
                                .append(discountedTuition).append(",")

                                .append("\"remaining\":")
                                .append(getRemainingBalance()).append(",")

                                .append("\"status\":{");

                for (Stage s : Stage.values()) {
                        sb.append("\"")
                                        .append(s)
                                        .append("\":")
                                        .append(status.get(s))
                                        .append(",");
                }

                sb.append("}}");

                return sb.toString();
        }

        // =========================
        // HISTORY EXPORT
        // =========================

        public String exportPaymentHistory() {

                StringBuilder sb = new StringBuilder();

                sb.append("{\"type\":\"TUITION_HISTORY\",\"payments\":[");

                for (TuitionPayment p : history) {

                        sb.append("{")
                                        .append("\"stage\":\"").append(p.getStage()).append("\",")
                                        .append("\"amount\":").append(p.getAmount()).append(",")
                                        .append("\"timestamp\":\"").append(p.getTimestamp()).append("\"")
                                        .append("},");
                }

                sb.append("]}");

                return sb.toString();
        }
}