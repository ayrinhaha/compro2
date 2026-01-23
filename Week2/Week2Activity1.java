package Week2;

public class Week2Activity1 {

    public static void main(String[] args) {
        int[] theaterRow = {0, 0, 0, 0, 0, 0, 0, 0};
        theaterRow[3] = 1;

        int availableSeatcounter = 0;
        for (int i = 0; i < theaterRow.length; i++) {
            if (theaterRow[i] == 0) {
                System.out.println("The seat is available.");
                availableSeatcounter++;
            } else {
                System.out.println("The seat is not available.");
            }
        }
        System.out.println("Total available seat: " + availableSeatcounter);

        // 1. Declare and initialize the 2D array for the theater
        int[][] theater = new int[5][8]; // 5 rows, 8 columns

        // 2. Book the seat at row 2, column 5
        theater[2][5] = 1;

        // 3. Book the seat at row 0, column 0
        theater[0][0] = 1;

        // 4. Use nested loops to print the seating chart
        int bookedSeatCount = 0;
        int notBookedSeatCount = 0;
        String seat = "";

        // header
        System.out.println("\n---- Theater Seating Chart ----");
        System.out.println("[x] = Booked    [-] = Available\n");

        for (int i = 0; i < theater.length; i++) {
            for (int j = 0; j < theater[i].length; j++) {

                if (theater[i][j] == 1) {
                    seat = "[x]";
                    bookedSeatCount++;
                } else {
                    seat = "[-]";
                    notBookedSeatCount++;
                }

                // print seat whether booked or not
                System.out.print(seat + " ");
            }
            System.out.println();
        }

        System.out.println("\nTotal booked seats: " + bookedSeatCount);
        System.out.println("Total available seats: " + notBookedSeatCount);
    }
}
