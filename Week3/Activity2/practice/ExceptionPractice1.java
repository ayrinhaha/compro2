import java.util.Scanner;

public class ExceptionPractice1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        try {

            int n = sc.nextInt();

        } catch (Exception e) {
            System.out.println("nyak! mag enter ka ng number kase");
        }

        System.out.println("Input another number: ");
        int number = inputNumber();

    }

    public static int inputNumber() {
        Scanner sc = new Scanner(System.in);
        int number = 0;

        while (true) {
            try {
                number = sc.nextInt();
                return number;
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("wow maliii");
                System.out.println("Enter a number: ");
            }
        }
    }
}
