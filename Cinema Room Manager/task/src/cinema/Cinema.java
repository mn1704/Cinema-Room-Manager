package cinema;
import java.util.Objects;
import java.util.Scanner;

public class Cinema {
    static int rows;
    static int seats;
    static int income;
    static int row;
    static int seat;
    static int purchasedTickets = 0;
    static double occupancy = 0.00;
    static int currentIncome = 0;

    static String[][] cinemaRoom = new String[100][100];

    public static void setRowsAndSeats() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of rows:\n" + "> ");
        rows = scanner.nextInt();
        System.out.print("Enter the number of seats in each row:\n" + "> ");
        seats = scanner.nextInt();
    }

    public static int calculateTotalIncome() { // calculates income if all seats are bought
        if((seats * rows) <= 60) {
            income = seats * rows * 10;
        } else income = ((rows / 2) * seats * 10) + ((rows - (rows / 2)) * seats * 8);
        return income;
    }

    public static void bookSeat() { // booking a seat after checking availability
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seats; j++) {
                if (Objects.equals(cinemaRoom[i][j], "B"))
                    continue;
                if(i == row && j == seat) {
                    cinemaRoom[i][j] = "B";
                } else cinemaRoom[i][j] = "S";
            }
        }
    }
    // displaying current status of booked seats inside the cinema room
    public static void viewCinemaRoom() {
        bookSeat();
        System.out.println();
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 1; i <= seats; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seats; j++) {
                if(j == 1) {
                    System.out.print(i + " ");
                }
                System.out.print(cinemaRoom[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int checkTicketPrice() {
        if(rows * seats <= 60)
            return 10;
        else if(row <= (rows / 2))
            return 10;
        return 8;
    }

    public static void chooseATicket() { // buying a ticket IF the seat is available
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter a row number:\n" + "> ");
        row = scanner.nextInt();
        System.out.print("Enter a seat number in that row:\n" + "> ");
        seat = scanner.nextInt();
        checkSeatAvailability();
    }

    public static void checkSeatAvailability() {
        if(row > rows || row <= 0 || seat > seats || seat <= 0) {
            System.out.println();
            System.out.println("Wrong input!");
            chooseATicket();
        } else if (Objects.equals(cinemaRoom[row][seat], "B")) {
            System.out.println();
            System.out.println("That ticket has already been purchased!");
            chooseATicket();
        } else {
            ++purchasedTickets;
            setOccupancy();
            bookSeat();
            System.out.println();
            System.out.println("Ticket price: $" + checkTicketPrice());
            currentIncome += checkTicketPrice();
        }
    }

    public static void setOccupancy() {
        double a = purchasedTickets, b = rows, c = seats;
        occupancy = (a * 100) / (b * c);
    }

    public static void viewStatistics() {
        System.out.println();
        System.out.printf("Number of purchased tickets: %d\nPercentage: %,.2f%%\nCurrent income: $%d\nTotal income: $%d%n", purchasedTickets, occupancy, currentIncome, calculateTotalIncome());
    }

    public static void interact() {
        Scanner scanner = new Scanner(System.in);
        for(;;) {
            System.out.println();
        System.out.print("""
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                >\s""");
        int input = scanner.nextInt();
        if(input == 0) return;
        checkUserInput(input);
        }
    }

    public static void checkUserInput(int input) {
        switch (input) {
            case 1 -> viewCinemaRoom();
            case 2 -> chooseATicket();
            case 3 -> viewStatistics();
        }
    }
    public static void startApp() {
            setRowsAndSeats();
            interact();
        }

    public static void main(String[] args) {
        startApp();
    }
}
