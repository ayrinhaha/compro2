import java.util.Scanner;

public class Week3Activity2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double[][] matrix1 = new double[3][4];
        System.out.print("Enter a 3-by-4 matrix row by row: ");
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                matrix1[i][j] = sc.nextDouble();
            }
        }

        for (int column = 0; column < matrix1[0].length; column++) {
            System.out.println("Sum of the elements at column " + column + " is " + sumColumn(matrix1, column));
        }

        double[][] matrix2 = new double[4][4];
        System.out.print("\nEnter a 4-by-4 matrix row by row: ");
        for (int i = 0; i < matrix2.length; i++) {
            for (int j = 0; j < matrix2[i].length; j++) {
                matrix1[i][j] = sc.nextDouble();
            }
        }

        System.out.println("Sum of the elements at column " + " is " + sumMajorDiagonal(matrix2));
        sc.close();
    }

    public static double sumColumn(double[][] m, int columnIndex) {
        double sum = 0;
        for (int row = 0; row < m.length; row++) {
            sum += m[row][columnIndex];
        }
        return sum;
    }

    public static double sumMajorDiagonal(double[][] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i][i];
        }
        return sum;

    }
    // not yet final, fix the out of bounds errorrrrrrr
}
