package Activity4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    static String[] names = new String[50];
    static double[][] gradeData = new double[50][3];
    static int subjectCount = 0; // tracks the number of subjects added

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int menuChoice;

        do {
            System.out.print("Menu\n[1] Add Grade for subject\n[2] Exit\nInput choice: ");
            menuChoice = sc.nextInt();
            sc.nextLine(); 

            if (menuChoice == 1) {
                if (subjectCount >= 50) {
                    System.out.println("Maximum number of subjects reached.\n");
                    continue;
                }

                System.out.print("Enter subject name: ");
                names[subjectCount] = sc.nextLine();

                // loop until a valid grade is entered
                gradeData[subjectCount][0] = getValidGrade(sc, "Prelim");
                gradeData[subjectCount][1] = getValidGrade(sc, "Midterm");
                gradeData[subjectCount][2] = getValidGrade(sc, "Finals");

                subjectCount++;
                System.out.println("Subject added successfully!\n");

            } else if (menuChoice == 2) {
                System.out.println("Goodbye... Muwah!");
                writeData(); // save data on exit
            } else {
                System.out.println("Invalid choice, try again.\n");
            }

        } while (menuChoice != 2);

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
        sb.append("Subject,Prelim,Midterm,Finals\n");

        for (int i = 0; i < subjectCount; i++) {
            sb.append(names[i]);
            for (int j = 0; j < 3; j++) {
                sb.append(",").append(gradeData[i][j]);
            }
            sb.append("\n");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("grades.csv"))) {
            bw.write(sb.toString());
            bw.flush();
            System.out.println("Data successfully saved to grades.csv");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
