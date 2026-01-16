package com.finance.core;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Account myAcc = new Account(1, 1000.0);
        int choice;
        do {
            System.out.println("\n--- Payment Menu ---");
            System.out.println("1. Check Balance\n2 Transfare\n3 View History\n4. Exit");

            // Robust input validation
            while (!input.hasNextInt()) {
                System.out.println("Invalid input. Enter a number:");
                input.next();
            }

            choice = input.nextInt();
            input.nextLine(); // Clear buffer

            switch (choice) {
                case 1 -> System.out.println("Balance: $" + myAcc.getBalance());
                case 2 -> {
                    System.out.print("Enter amount: ");
                    double amt = input.nextDouble();
                    try {
                        myAcc.sendMoneyToAccount(new Account(2, 0), amt);
                        System.out.println("Success!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    for (Account.Transaction tx : myAcc.getTransaction()) {
                        System.out.println("TX Amount: $" + tx.getAmount());
                    }
                }
            }

        } while (choice != 4);
    }
}