package com.codsoft.Task3;



class BankAccount {

    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        balance += amount;

        System.out.printf(
            "Deposit successful! Amount: Rs. %.2f%n", amount
        );
    }

    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;

            System.out.printf(
                "Withdrawal successful! Amount: Rs. %.2f%n", amount
            );
        }
    }

    public void checkBalance() {
        System.out.printf("Current balance: Rs. %.2f%n", balance);
    }
}

