package Activity2;

import java.util.Scanner;

public class ColumnDiagonal {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        // PROBLEM 1
        // 1. Prompts the user to enter a 3-by-4 matrix row by row
        double[][] matrix1 = new double[3][4];

        // 2. Reads the matrix
        System.out.println("Enter a 3-by-4 matrix row by row:");
        for (int i = 0; i < matrix1.length; i++) { // rows
            for (int j = 0; j < matrix1[i].length; j++) { // columns
                matrix1[i][j] = sc.nextDouble();
            }
        }

        // 3. Displays the sum of each column by calling the sumColumn method
        for (int col = 0; col < matrix1[0].length; col++) {
            System.out.println(
                    "Sum of the elements at column " + col + " is " +
                            sumColumn(matrix1, col));
        }

        // PROBLEM 2
        // 1. Prompts the user to enter a 4-by-4 matrix row by row
        // 2. Reads the matrix
        double[][] matrix2 = new double[4][4];
        System.out.println("\nEnter a 4-by-4 matrix row by row:");
        for (int i = 0; i < matrix2.length; i++) { // rows
            for (int j = 0; j < matrix2[i].length; j++) { // columns
                matrix2[i][j] = sc.nextDouble();
            }
        }

        // 3. Displays the sum of the elements in the major diagonal by calling the sumMajorDiagonal method
        System.out.println("Sum of the elements in the major diagonal is " + sumMajorDiagonal(matrix2));
        sc.close();
    }

    // computes the sum of a specific column in a matrix
    public static double sumColumn(double[][] m, int columnIndex) {
        double sum = 0;
        for (int row = 0; row < m.length; row++) {
            sum += m[row][columnIndex];
        }
        return sum;
    }

    // computes the sum of the major diagonal
    public static double sumMajorDiagonal(double[][] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i][i]; // row index equals column index
        }
        return sum;
    }
}
