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
    private double scholarshipDiscount;

    private Map<Stage, Double> paymentAmounts;
    private Map<Stage, Boolean> paymentStatus;

    public Tuition() {

        paymentAmounts = new HashMap<>();
        paymentStatus = new HashMap<>();

        for (Stage stage : Stage.values()) {
            paymentStatus.put(stage, false);
        }
    }

    // =====================================
    // Setup Tuition
    // =====================================

    public void setupTuition(Scanner sc) {

        System.out.println("\n========== TUITION SETUP ==========");

        System.out.print("Enter full semester tuition: ₱");
        fullTuition = sc.nextDouble();

        System.out.println("\nDo you have scholarship discount?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        System.out.print("\nChoice: ");
        int choice = sc.nextInt();

        if (choice == 1) {

            System.out.print(
                    "Enter scholarship discount percentage: ");

            scholarshipDiscount = sc.nextDouble();

        } else {

            scholarshipDiscount = 0;
        }

        computeTuitionBreakdown();
    }

    // =====================================
    // Compute Tuition
    // =====================================

    private void computeTuitionBreakdown() {

        double discountAmount =
                fullTuition * (scholarshipDiscount / 100);

        discountedTuition =
                fullTuition - discountAmount;

        double perStage =
                discountedTuition * 0.25;

        for (Stage stage : Stage.values()) {
            paymentAmounts.put(stage, perStage);
        }

        showBreakdown();
    }

    // =====================================
    // Display Breakdown
    // =====================================

    public void showBreakdown() {

        System.out.println(
                "\n========== TUITION BREAKDOWN ==========");

        System.out.println(
                "Original Tuition: ₱" + fullTuition);

        System.out.println(
                "Scholarship Discount: "
                        + scholarshipDiscount + "%");

        System.out.println(
                "Discounted Tuition: ₱"
                        + discountedTuition);

        System.out.println();

        for (Stage stage : Stage.values()) {

            System.out.println(
                    stage + " (25%): ₱"
                            + paymentAmounts.get(stage));
        }
    }

    // =====================================
    // Pay Tuition
    // =====================================

    public void payTuition(Scanner sc) {

        System.out.println(
                "\n====== PAYMENT STAGES ======");

        int index = 1;

        for (Stage stage : Stage.values()) {

            System.out.println(
                    index + ". "
                            + stage
                            + " - ₱"
                            + paymentAmounts.get(stage));

            index++;
        }

        System.out.print(
                "\nSelect payment stage: ");

        int choice = sc.nextInt();

        Stage selectedStage =
                Stage.values()[choice - 1];

        System.out.println(
                "\nYou are about to pay:");

        System.out.println(
                selectedStage
                        + " - ₱"
                        + paymentAmounts.get(selectedStage));

        System.out.println("\nConfirm payment?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        System.out.print("\nChoice: ");

        int confirm = sc.nextInt();

        if (confirm == 1) {

            paymentStatus.put(selectedStage, true);

            System.out.println(
                    "\n✔ "
                            + selectedStage
                            + " successfully paid!");

        } else {

            System.out.println("\nPayment cancelled.");
        }

        viewStatus();
    }

    // =====================================
    // View Payment Status
    // =====================================

    public void viewStatus() {

        System.out.println(
                "\n========== UPDATED STATUS ==========");

        for (Stage stage : Stage.values()) {

            String status =
                    paymentStatus.get(stage)
                            ? "PAID"
                            : "UNPAID";

            System.out.printf(
                    "%-12s : %s%n",
                    stage,
                    status);
        }
    }
}