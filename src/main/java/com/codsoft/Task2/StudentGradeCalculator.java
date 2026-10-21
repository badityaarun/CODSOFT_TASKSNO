package com.codsoft.Task2;


import java.util.Scanner;

public class StudentGradeCalculator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int numberOfSubjects;
        int totalMarks = 0;

        // Step 1: Get the number of subjects
        System.out.print("Enter the number of subjects: ");

        while (!sc.hasNextInt()) {
            System.out.println("Please enter a valid whole number.");
            sc.next();
            System.out.print("Enter the number of subjects: ");
        }

        numberOfSubjects = sc.nextInt();

        while (numberOfSubjects <= 0) {
            System.out.println("Number of subjects must be positive.");
            System.out.print("Enter the number of subjects: ");

            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid whole number.");
                sc.next();
                System.out.print("Enter the number of subjects: ");
            }

            numberOfSubjects = sc.nextInt();
        }

        // Step 2: Create an array to store marks
        int[] marks = new int[numberOfSubjects];

        // Step 3: Read marks for every subject
        for (int i = 0; i < numberOfSubjects; i++) {

            System.out.print(
                "Enter marks for Subject " + (i + 1) + ": "
            );

            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid whole number.");
                sc.next();
                System.out.print(
                    "Enter marks for Subject " + (i + 1) + ": "
                );
            }

            marks[i] = sc.nextInt();

            while (marks[i] < 0 || marks[i] > 100) {

                System.out.println(
                    "Marks must be between 0 and 100."
                );

                System.out.print(
                    "Enter marks for Subject " + (i + 1) + ": "
                );

                while (!sc.hasNextInt()) {
                    System.out.println(
                        "Please enter a valid whole number."
                    );
                    sc.next();
                    System.out.print(
                        "Enter marks for Subject " + (i + 1) + ": "
                    );
                }

                marks[i] = sc.nextInt();
            }

            totalMarks += marks[i];
        }

        // Step 4: Calculate the average percentage
        double averagePercentage =
            (double) totalMarks / numberOfSubjects;

        // Step 5: Calculate the grade
        String grade;

        if (averagePercentage >= 90) {
            grade = "A+";
        } else if (averagePercentage >= 80) {
            grade = "A";
        } else if (averagePercentage >= 70) {
            grade = "B";
        } else if (averagePercentage >= 60) {
            grade = "C";
        } else if (averagePercentage >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }

        // Step 6: Display the results
        System.out.println("\n===== STUDENT GRADE REPORT =====");

        for (int i = 0; i < numberOfSubjects; i++) {
            System.out.println(
                "Subject " + (i + 1) + ": " + marks[i] + "/100"
            );
        }

        System.out.println("-------------------------------");
        System.out.println(
            "Total Marks: " + totalMarks
            + "/" + (numberOfSubjects * 100)
        );

        System.out.printf(
            "Average Percentage: %.2f%%%n",
            averagePercentage
        );

        System.out.println("Grade: " + grade);
        System.out.println("================================");

        sc.close();
    }
}

