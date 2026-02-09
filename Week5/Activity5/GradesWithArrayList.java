package Week5.Activity5;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Grade {
    String subject;
    double prelim;
    double midterm;
    double finals;

    public Grade(String subject, double prelim, double midterm, double finals) {
        this.subject = subject;
        this.prelim = prelim;
        this.midterm = midterm;
        this.finals = finals;
    }
}

public class GradesWithArrayList {

    static ArrayList<Grade> gradeList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\nMenu");
            System.out.println("[1] Add Grade for subject");
            System.out.println("[2] Display grades");
            System.out.println("[3] Exit");
            System.out.print("Input choice: ");

            try {
                choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    addGrade(sc);
                } else if (choice == 2) {
                    displayGrades();
                } else if (choice == 3) {
                    System.out.println("Goodbye... Muwah!");
                } else {
                    System.out.println("Invalid menu choice.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
            }

        } while (choice != 3);

        sc.close();
    }

    // add grade feature
    public static void addGrade(Scanner sc) {
        try {
            System.out.print("Enter subject name: ");
            String subject = sc.nextLine();

            System.out.print("Enter prelim grade: ");
            double prelim = sc.nextDouble();

            System.out.print("Enter midterm grade: ");
            double midterm = sc.nextDouble();

            System.out.print("Enter finals grade: ");
            double finals = sc.nextDouble();
            sc.nextLine();

            gradeList.add(new Grade(subject, prelim, midterm, finals));
            System.out.println("Grade added successfully!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Grades must be numbers.");
            sc.nextLine();
        }
    }

    // display
    public static void displayGrades() {
        if (gradeList.size() == 0) {
            System.out.println("No grades recorded yet.");
            return;
        }

        System.out.println("\nSubject,Prelim,Midterm,Finals");

        for (int i = 0; i < gradeList.size(); i++) {
            Grade g = gradeList.get(i);
            System.out.println(g.subject + "," + g.prelim + "," + g.midterm + "," + g.finals);
        }
    }
}
