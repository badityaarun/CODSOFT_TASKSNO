package com.codsoft.Task3;

import java.util.Scanner;

public class ATMInterface {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        BankAccount account = new BankAccount(1000.00);

        int choice;

        System.out.println("==============================");
        System.out.println("       WELCOME TO ATM");
        System.out.println("==============================");

        do {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    account.checkBalance();
                    break;

                case 2:
                    double depositAmount =
                        readDouble("Enter deposit amount: Rs. ");

                    account.deposit(depositAmount);
                    break;

                case 3:
                    double withdrawAmount =
                        readDouble("Enter withdrawal amount: Rs. ");

                    account.withdraw(withdrawAmount);
                    break;

                case 4:
                    System.out.println("Thank you for using our ATM!");
                    break;

                default:
                    System.out.println(
                        "Invalid choice! Please select 1 to 4."
                    );
            }

        } while (choice != 4);

        sc.close();
    }

    private static int readInt(String message) {

        while (true) {
            System.out.print(message);

            if (sc.hasNextInt()) {
                return sc.nextInt();
            }

            System.out.println("Please enter a valid whole number.");
            sc.next();
        }
    }

    private static double readDouble(String message) {

        while (true) {
            System.out.print(message);

            if (sc.hasNextDouble()) {
                return sc.nextDouble();
            }

            System.out.println("Please enter a valid numeric amount.");
            sc.next();
        }
    }
}
