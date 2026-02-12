package Activity5;

import java.io.*;
import java.util.*;

class Grade {
    String subject;
    double prelim;
    double midterm;
    double finals;
}

public class GradesWithArrayList {

    static List<Grade> gradeList;

    public static void main(String[] args) {
        gradeList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            displayMenu();
            System.out.print("Enter choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine(); 
                if (choice == 1) {
                    addGrade(sc);
                } else if (choice == 2) {
                    displayGrades();
                } else if (choice == 3) {
                    System.out.print("Enter keyword (subject or grade) to search: ");
                    String keyword = sc.nextLine();
                    searchGrades(keyword);
                } else if (choice == 4) {
                    saveData();
                    System.out.println("Goodbye... Muwah!");
                } else {
                    System.out.println("Invalid menu choice.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine(); 
            }

        } while (choice != 4);

        sc.close();
    }

    public static void displayMenu() {
        System.out.println("""
                Menu
                [1] Add Grade
                [2] Display Grades
                [3] Search Grades
                [4] Exit
                """);
    }

    public static void addGrade(Scanner sc) {
        Grade g = new Grade();
        System.out.print("Enter subject name: ");
        g.subject = sc.nextLine();

        // prelim grade
        while (true) {
            System.out.print("Enter prelim grade: ");
            try {
                g.prelim = sc.nextDouble();
                sc.nextLine(); 
                break; // valid number, exit loop
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); 
            }
        }

        // midterm grade
        while (true) {
            System.out.print("Enter midterm grade: ");
            try {
                g.midterm = sc.nextDouble();
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        // finals grade
        while (true) {
            System.out.print("Enter finals grade: ");
            try {
                g.finals = sc.nextDouble();
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        gradeList.add(g);
        saveData(); // save immediately after adding
        System.out.println("Grade added successfully!");
    }

    public static void displayGrades() {
        if (gradeList.isEmpty()) {
            System.out.println("No grades recorded yet.");
            return;
        }
        System.out.printf("%-14s %-8s %-8s %-8s\n", "Subject", "Prelim", "Midterm", "Finals");

        for (Grade g : gradeList) {
            System.out.printf("%-14s %-8.2f %-8.2f %-8.2f\n", g.subject, g.prelim, g.midterm, g.finals);
        }
    }

    public static void searchGrades(String keyword) {
        if (gradeList.isEmpty()) {
            System.out.println("No grades recorded yet.");
            return;
        }

        System.out.println("Search results:");
        List<Grade> filtered = gradeList.stream().filter(g -> g.subject.toLowerCase().contains(keyword.toLowerCase())
                || Double.toString(g.prelim).equals(keyword)
                || Double.toString(g.midterm).equals(keyword)
                || Double.toString(g.finals).equals(keyword)).toList();

        for (Grade g : filtered) {
            System.out.printf("%-14s %-8.2f %-8.2f %-8.2f\n", g.subject, g.prelim, g.midterm, g.finals);
        }

        if (filtered.size() == 0) {
            System.out.println("No results found...");
        }
    }

    public static void saveData() {
        StringBuilder data = new StringBuilder("Subject,Prelim,Midterm,Finals");
        for (Grade g : gradeList) {
            data.append("\n")
                    .append(g.subject).append(",")
                    .append(g.prelim).append(",")
                    .append(g.midterm).append(",")
                    .append(g.finals);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("grades.csv"))) {
            bw.write(data.toString());
            bw.close();
            System.out.println("File saved...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader("grades.csv"))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                Grade g = new Grade();
                g.subject = arr[0];
                g.prelim = Double.parseDouble(arr[1]);
                g.midterm = Double.parseDouble(arr[2]);
                g.finals = Double.parseDouble(arr[3]);
                gradeList.add(g);
            }
        } catch (IOException e) {
            
        }
    }
}
