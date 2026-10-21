package com.codsoft.Task1;


import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int roundsWon = 0;
        int totalAttempts = 0;
        String playAgain;

        System.out.println("================================");
        System.out.println("       NUMBER GUESSING GAME");
        System.out.println("================================");

        do {
            int number = random.nextInt(100) + 1;
            int maxAttempts = 7;
            boolean guessedCorrectly = false;

            System.out.println("\nGuess a number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts.");

            for (int attempt = 1;
                 attempt <= maxAttempts; attempt++) {

                System.out.print("Attempt " + attempt + ": ");

                if (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid integer.");
                    sc.next();
                    attempt--;
                    continue;
                }

                int guess = sc.nextInt();

                if (guess < 1 || guess > 100) {
                    System.out.println(
                        "Enter a number between 1 and 100."
                    );
                    attempt--;
                    continue;
                }

                totalAttempts++;

                if (guess == number) {
                    System.out.println("Correct! You won!");
                    roundsWon++;
                    guessedCorrectly = true;
                    break;
                } else if (guess < number) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Out of attempts!");
                System.out.println("The number was: " + number);
            }

            System.out.println("\nRounds won: " + roundsWon);
            System.out.println("Valid guesses made: " + totalAttempts);

            System.out.print("Play again? (yes/no): ");
            playAgain = sc.next();

        } while (playAgain.equalsIgnoreCase("yes"));

        System.out.println("\nThanks for playing!");
        System.out.println("Final rounds won: " + roundsWon);
        System.out.println("Total valid guesses: " + totalAttempts);

        sc.close();
    }
}
