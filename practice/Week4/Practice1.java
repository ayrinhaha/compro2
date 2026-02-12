package practice.Week4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Practice1 {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        try (Scanner sc = new Scanner(System.in)) {
            sb.append("First Name: ");
            System.out.print("First Name: ");

            sb.append(sc.nextLine()).append("\nLast Name: ");
            System.out.print("Last Name: ");

            sb.append(sc.nextLine()).append("\nAge: ");
            System.out.print("Age: ");

            sb.append(sc.nextLine()).append("\nEmail: ");
            System.out.print("Email: ");

            sb.append(sc.nextLine()).append("\nPhone: ");
            System.out.print("Phone:");

            sb.append(sc.nextLine());
        }

        catch (InputMismatchException e) {
            System.out.println("Invalid input");
        }

        

        // try-with-resource
        try (FileWriter fw = new FileWriter("data.txt")) {
            fw.write(sb.toString());
            System.out.println("Data is saved...");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}