package mypackage;

import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        int choice = 1;
        Scanner sc = new Scanner(System.in);
        while (choice != 0) {
            System.out.println("Please Choose:");
            System.out.println("1. Make Reservation");
            System.out.println("2. Update Reservation");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Details Reservation");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    Reservation.makeRes();
                    choice =-1;
                    break;

                case 2:
                    Reservation.updateT();
                    choice =-1;
                    break;

                case 3:
                    Reservation.cancelRes();
                    choice =-1;
                    break;

                case 4:
                    Reservation.getDet();
                    choice =-1;
                    break;

                case 0:
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }
}