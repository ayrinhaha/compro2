package com.ayrinhaha;

import com.ayrinhaha.model.Grade;
import com.ayrinhaha.thread.SaveTask;
import com.ayrinhaha.thread.LoadTask;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.InputMismatchException;

/**
 * A multithreaded grade management application.
 *
 * @author ayrinhaha
 */
public class MultiThreadedListApp {

    List<Grade> data = new CopyOnWriteArrayList<>();
    Gson gson = new Gson();

    volatile boolean changed = false;
    volatile long lastModified = 0;

    public static void main(String[] args) {

        MultiThreadedListApp app = new MultiThreadedListApp();

        app.readFile();

        Thread saver = new Thread(new SaveTask(app), "SaverThread");
        Thread reader = new Thread(new LoadTask(app), "ReaderThread");

        saver.setDaemon(true);
        reader.setDaemon(true);

        saver.start();
        reader.start();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nMAIN MENU:");
            System.out.println("[1] Enter Grades");
            System.out.println("[2] Display Grades");
            System.out.println("[3] Exit");

            try {
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {

                    System.out.print("Enter subject: ");
                    String subject = sc.nextLine();

                    double prelim = app.getValidGrade(sc, "Enter prelim: ");
                    double midterm = app.getValidGrade(sc, "Enter midterm: ");
                    double finals = app.getValidGrade(sc, "Enter finals: ");
                    sc.nextLine();

                    app.addGrade(new Grade(subject, prelim, midterm, finals));
                    System.out.println("\nGrade added.");

                } else if (choice == 2) {
                    app.displayGrades();

                } else if (choice == 3) {
                    System.out.println("Saving and Exiting...");
                    app.saveToDisk();
                    System.exit(0);
                } else {
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu number.");
                sc.next();
            }
        }
    }

    /**
     * Helper method to validate grade input continuously until valid.
     * Ensures input is numeric and within the 0 - 100 range.
     * * @param sc The Scanner object
     * 
     * @param prompt The text to display to the user
     * @return A valid double between 0 and 100
     */
    public double getValidGrade(Scanner sc, String prompt) {
        double grade;
        while (true) {
            System.out.print(prompt);
            try {
                grade = sc.nextDouble();

                if (grade >= 0 && grade <= 100) {
                    return grade;
                } else {
                    System.out.println("Invalid grade. Please enter a value between 0 and 100.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                sc.next();
            }
        }
    }

    public void addGrade(Grade grade) {
        data.add(grade);
        changed = true;
    }

    public void displayGrades() {
        System.out.println("\n------ GRADES ------");
        if (data.isEmpty()) {
            System.out.println("No records.");
        } else {
            for (Grade g : data) {
                System.out.println(g);
            }
        }
    }

    public void saveToDisk() {
        if (!changed)
            return;

        File file = new File("grades.json");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            gson.toJson(data, bw);
            changed = false;
            lastModified = file.lastModified();

        } catch (IOException e) {
            System.err.println("\n[System] Save failed: " + e.getMessage());
        }
    }

    public void readFile() {
        File file = new File("data/grades.json");

        if (!file.exists())
            return;
        if (file.lastModified() == lastModified)
            return;

        try (Reader reader = new FileReader(file)) {

            Type type = new TypeToken<List<Grade>>() {
            }.getType();
            List<Grade> temp = gson.fromJson(reader, type);

            if (temp != null) {
                data.clear();
                data.addAll(temp);
                lastModified = file.lastModified();
            }

        } catch (IOException e) {
            System.out.println("\n[Error] Failed to read data.");
        }
    }
}