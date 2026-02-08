package Week4.Activity4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Grades {
    static String[] names;
    static double[][] gradeData;

    public static void main(String[] args) {
        names = new String[50];
        gradeData = new double[50][3];

        Scanner sc = new Scanner(System.in);
        int menuChoice;
        
        do{
        System.out.print("Menu\n[1] Add Grade for subject\n[2] Exit\nInput choice: ");
        menuChoice = sc.nextInt();
        if (menuChoice == 1){
            for (int r = 0; r < 5; r++) {
                System.out.print("Enter subject name: ");
                names[r] = sc.next();

                System.out.print("Enter prelim grade: ");
                try {
                    gradeData[r][0] = sc.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid number.");
                }

                System.out.print("Enter midterm grade: ");
                try {
                    gradeData[r][1] = sc.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid number");
                }

                System.out.print("Enter finals grade: ");
                try {
                    gradeData[r][2] = sc.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid number");
                }

                sc.nextLine();
                System.out.println();

            }
        }
        else 
            System.out.println("Goodbye... Muwah!");
    

        } while(menuChoice!=2);

    writeData();
    }
    public static void writeData() {
        StringBuilder sb = new StringBuilder();

        sb.append("Subject,Prelim,Midterm,Finals\n");
        for (int r = 0; r < names.length; r++) {
            if (names[r] == null)
                break;

            sb.append(names[r]);
            for (int c = 0; c < gradeData[r].length; c++) {
                sb.append(",").append(gradeData[r][c]);
            }
            sb.append("\n");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data.csv"))) {
            bw.write(sb.toString());
            bw.flush();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println(sb.toString());
    }
}

