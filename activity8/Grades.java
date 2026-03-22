package com.ayrine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Grades {
    static String[] names = new String[50];
    static double[][] gradeData = new double[50][3];
    static int subjectCount = 0; // tracks the number of subjects added

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) { // main menu loop
            System.out.println("\nMAIN MENU:");
            System.out.println("[1] Enter Grades");
            System.out.println("[2] Display Grades");
            System.out.println("[3] Exit");

            try { // outer try for main menu input
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) { // enter grades
                    boolean enterMore = true;

                    while (enterMore) {
                        System.out.println("\n[1] Add Subject Grades");
                        System.out.println("[2] Go Back");

                        try { // nested try for subject choice input
                            System.out.print("Enter choice: ");
                            int subChoice = sc.nextInt();
                            sc.nextLine();

                            if (subChoice == 2) {
                                enterMore = false; // go back to main menu
                            } else if (subChoice == 1) {

                                if (subjectCount >= 50) {
                                    System.out.println("Maximum number of subjects reached.");
                                    continue;
                                }

                                System.out.print("Enter subject name: ");
                                names[subjectCount] = sc.nextLine();

                                // loop until a valid grade is entered
                                gradeData[subjectCount][0] = getValidGrade(sc, "prelim");
                                gradeData[subjectCount][1] = getValidGrade(sc, "midterm");
                                gradeData[subjectCount][2] = getValidGrade(sc, "finals");

                                subjectCount++;
                                System.out.println("Subject added successfully!");

                            } else {
                                System.out.println("Invalid choice. Try again.");
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Enter a number.");
                            sc.next();
                        }
                    }

                } else if (choice == 2) { // display grades
                    System.out.println("\n-- Grade Table --");
                    System.out.println("Subject\tPrelim\tMidterm\tFinals");

                    for (int i = 0; i < subjectCount; i++) {
                        System.out.println(
                                names[i] + "\t" + gradeData[i][0] + "\t" + gradeData[i][1] + "\t" + gradeData[i][2]);
                    }

                } else if (choice == 3) {
                    System.out.println("Exiting program...\nGoodbye!");
                    writeData(); // save data on exit
                    break;

                } else {
                    System.out.println("Invalid choice. Try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number for main menu.");
                sc.next();
            }
        }

        sc.close();
    }

    // method to validate grades must be up to 100 only
    public static double getValidGrade(Scanner sc, String gradeName) {
        double grade = 0;
        boolean valid = false;

        do {
            System.out.print("Enter " + gradeName + " grade: ");
            try {
                grade = sc.nextDouble();
                if (grade >= 0 && grade <= 100) {
                    valid = true;
                } else {
                    System.out.println("Grade must be between 0 and 100. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
            }
        } while (!valid);

        sc.nextLine();
        return grade;
    }

    public static void writeData() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < subjectCount; i++) {
            sb.append("  {\n");
            sb.append("    \"subject\": \"").append(names[i]).append("\",\n");
            sb.append("    \"prelims\": ").append(gradeData[i][0]).append(",\n");
            sb.append("    \"midterm\": ").append(gradeData[i][1]).append(",\n");
            sb.append("    \"finals\": ").append(gradeData[i][2]).append("\n");
            sb.append("  }");

            if (i < subjectCount - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("]");

        try (FileWriter fw = new FileWriter("grades.json")) {
            fw.write(sb.toString());
            fw.flush();
            System.out.println("Data successfully saved to grades.json");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
