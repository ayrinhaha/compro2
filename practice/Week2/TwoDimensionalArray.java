package Week2.practice;

public class TwoDimensionalArray {
    public static void main(String[] args) {
        String[][] clothColors = new String[2][3];
        // first row
        clothColors[0][0] = "red";
        clothColors[0][1] = "blue";
        clothColors[0][2] = "green";

        // second row
        clothColors[1][0] = "orange";
        clothColors[1][1] = "yellow";
        clothColors[1][2] = "violet";

        for (int i = 0; i < clothColors.length; i++) {
            for (int j = 0; j < clothColors[i].length; j++) {
                System.out.printf("%-8s", clothColors[i][j]);

            }
            System.out.println();
        }

    }

}
