package Activity3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Week3Activity4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] subjects = { "COMPRO2", "DSA", "OOP" };
        int[] grades = new int[subjects.length];
        boolean[] isGradeEntered = new boolean[subjects.length];

        while (true) { // main menu loop
            System.out.println("\nMAIN MENU:");
            System.out.println("[1] Enter Grades");
            System.out.println("[2] Display Grades");
            System.out.println("[3] Exit");

            try { // outer try for main menu input
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                if (choice == 1) { // enter grades
                    boolean enterMore = true;
                    while (enterMore) {
                        System.out.println("\nEnter grade for:");
                        for (int i = 0; i < subjects.length; i++) {
                            System.out.println("[" + (i + 1) + "] " + subjects[i]);
                        }
                        System.out.println("[0] Go Back");

                        try { // nested try for subject choice input
                            System.out.print("Enter choice: ");
                            int subChoice = sc.nextInt();

                            if (subChoice == 0) {
                                enterMore = false; // go back to main menu
                            } else if (subChoice >= 1 && subChoice <= subjects.length) {
                                try {
                                    int grade = -1;
                                    boolean validGrade = false;
                                    while (!validGrade) {
                                        try {
                                            System.out.print("Enter grade for " + subjects[subChoice - 1] + ": ");
                                            grade = sc.nextInt();
                                            if (grade < 0 || grade > 100) {
                                                System.out.println("Grade must be between 0 and 100.");
                                            } else {
                                                validGrade = true;
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Enter a number.");
                                            sc.next();
                                        }
                                    }
                                    grades[subChoice - 1] = grade;
                                    isGradeEntered[subChoice - 1] = true;
                                    System.out.println("Grade saved...");
                                } catch (Exception e) {
                                    System.out.println("Unexpected error while entering grade.");
                                }

                            } else {
                                System.out.println("Invalid choice. Try again.");
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Enter a number for subject choice.");
                            sc.next(); 
                        }
                    }

                } else if (choice == 2) { // display grades
                    System.out.println("\n-- Grade Table --");
                    System.out.println("Subject\tGrade");
                    for (int i = 0; i < subjects.length; i++) {
                        if (isGradeEntered[i]) {
                            System.out.println(subjects[i] + "\t" + grades[i]);
                        } else {
                            System.out.println(subjects[i] + "\tNo grade entered");
                        }
                    }

                } else if (choice == 3) { 
                    System.out.println("Exiting program...\nGoodbye!");
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
}
