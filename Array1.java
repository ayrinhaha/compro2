import java.util.Scanner;

public class Array1 {
    public static void main(String[] args) {

        int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt();
        int index = -1;
        for (int i = 0; i < arr.length; i++)
            if (arr[i] == n) {
                index = i;
                break;
            }
            System.out.println("Index of " + n + " is " + index);
    }

}
