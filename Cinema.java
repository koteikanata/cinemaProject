package cinema;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Cinema {
    private static int rows, seats, sumSeats, backHalf;
    private static int purchasedTickets = 0;
    private static double percentage = 0;
    private static int currentIncome = 0;
    private static StringBuilder[] cinema;


    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();
        cinema = createCinema(rows, seats);
        sumSeats = rows * seats;

        int totalIncome;
        if (sumSeats > 60) {
            int frontHalf = rows / 2;
            backHalf = rows - frontHalf;
            totalIncome = frontHalf * seats * 10 + backHalf * seats * 8;
        } else {
            totalIncome = sumSeats * 10;
        }

        do {
            System.out.println("1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit");
            switch (scanner.nextInt()) {
                case 1:
                    showCinema();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    statistics(purchasedTickets, percentage, currentIncome, totalIncome);
                    break;
                case 0:
                    return;
            }
        } while (scanner.hasNextLine());
    }

    public static StringBuilder[] createCinema(int rows, int seats) {
        StringBuilder[] cinema = new StringBuilder[rows + 2];
        cinema[0] = new StringBuilder("Cinema:");
        cinema[1] = new StringBuilder();
        cinema[1].append("  ");
        int numsFirstStr;
        for (numsFirstStr = 1; numsFirstStr < seats; numsFirstStr++) {
            cinema[1].append(numsFirstStr).append(" ");
        }
        cinema[1].append(numsFirstStr);
        int count = 1;
        for (int i = 2; i < cinema.length; i++) {
            cinema[i] = new StringBuilder();
            cinema[i].append(count++).append(" ");
            for (int j = 0; j < seats; j++) {
                cinema[i].append("S ");
            }
        }
        cinema[cinema.length - 1].append("\n");
        return cinema;
    }

    public static void showCinema() {
        for (StringBuilder str : cinema) {
            System.out.println(str);
        }
    }

    public static void buyTicket() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        int selectedRow = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int selectedSeat = scanner.nextInt();

        if (selectedRow > rows || selectedSeat > seats || selectedRow < 0 || selectedSeat < 0) {
            System.out.println("Wrong input!");
            System.out.println("");
            buyTicket();
            return;
        }
        String selectedPlace;
        selectedPlace = String.valueOf(cinema[++selectedRow].charAt(selectedSeat * 2));
        if (selectedPlace.equals("B")) {
            System.out.println("That ticket has already been purchased");
            buyTicket();
        }

        int price;
        if (sumSeats < 61) {
            price = 10;
        } else if (selectedRow <= backHalf) {
            price = 10;
        } else price = 8;
        System.out.println("Ticket price: $" + price);
        System.out.println("");
        /*--------------------------------------------*/
        purchasedTickets++;
        currentIncome += price;
        percentage = (double) purchasedTickets / sumSeats * 100.0;
        for (int k = 1; k <= cinema.length - 1; k++) {
            if (k == selectedRow) {
                cinema[selectedRow].setCharAt(selectedSeat * 2, 'B');
                break;
            }
        }
    }

    public static void statistics(int purchasedTickets, double percentage, int currentIncome, int totalIncome) {
        String str = String.format(Locale.ENGLISH, "%.2f", percentage);
        System.out.println("Number of purchased tickets: " + purchasedTickets + "\n" +
                "Percentage: " + str + "%\n" +
                "Current income: $" + currentIncome + "\n" +
                "Total income: $" + totalIncome + "\n");
    }

}
