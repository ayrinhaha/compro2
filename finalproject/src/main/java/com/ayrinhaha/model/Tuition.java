package com.ayrinhaha.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tuition {

        public enum Stage {
                DOWNPAYMENT,
                PRELIM,
                MIDTERM,
                FINALS
        }

        private double fullTuition;
        private double discountedTuition;
        private double discountRate;

        private Map<Stage, Double> amounts = new HashMap<>();
        private Map<Stage, Boolean> status = new HashMap<>();

        public Tuition() {
                for (Stage s : Stage.values()) {
                        status.put(s, false);
                }
        }

        // SETUP TUITION
        public void setupTuition(Scanner sc) {

                System.out.print("\nEnter full tuition: ₱");
                fullTuition = sc.nextDouble();

                System.out.print("Scholarship discount % (0 if none): ");
                discountRate = sc.nextDouble();

                double discount = fullTuition * (discountRate / 100);
                discountedTuition = fullTuition - discount;

                double perStage = discountedTuition * 0.25;

                for (Stage s : Stage.values()) {
                        amounts.put(s, perStage);
                }

                System.out.println("\n=== TUITION BREAKDOWN ===");
                System.out.println("Original: " + fullTuition);
                System.out.println("Discounted: " + discountedTuition);
                System.out.println("Each stage: " + perStage);
        }

        // PAY TUITION
        public void payTuition(Scanner sc) {

                System.out.println("\n=== PAYMENT STAGES ===");

                int i = 1;
                for (Stage s : Stage.values()) {
                        System.out.println(i + ". " + s + " - ₱" + amounts.get(s));
                        i++;
                }

                System.out.print("\nSelect stage: ");
                int choice = sc.nextInt();

                Stage selected = Stage.values()[choice - 1];

                System.out.println("You will pay: " + amounts.get(selected));

                System.out.print("Confirm? (1-Yes / 2-No): ");
                int confirm = sc.nextInt();

                if (confirm == 1) {
                        status.put(selected, true);
                        System.out.println(selected + " PAID!");
                } else {
                        System.out.println("Cancelled.");
                }

                viewStatus();
        }

        public void viewStatus() {

                System.out.println("\n=== TUITION STATUS ===");

                for (Stage s : Stage.values()) {
                        System.out.println(s + " : " +
                                        (status.get(s) ? "PAID" : "UNPAID"));
                }
        }

        public Map<Stage, Double> getAmounts() {
                return amounts;
        }

        public Map<Stage, Boolean> getStatus() {
                return status;
        }
}
