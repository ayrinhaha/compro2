package Week2;

public class Week2Acitvity1 {
    public static void main(String[] args) {
        int[] theaterRow = { 0, 0, 0, 0, 0, 0, 0, 0 };
        theaterRow[3] = 1;

        int availableSeatcounter = 0;
        for (int i = 0; i < theaterRow.length; i++) {
            if (theaterRow[i] == 0) {
                System.out.println("The seat is not available.");
                availableSeatcounter++;
            } else
                System.out.println("The seat is  available.");

        }
        System.out.println("Total available seat: " + availableSeatcounter);
    }

}
