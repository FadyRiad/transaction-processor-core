package com.finance.core;

import java.util.Arrays;

public class Account {

    private final int id;
    private double balance;
    private Transaction[] transactions;
    private int transactionCount;

    public Account(int id, double initialBalance) {
        this.id = id;
        this.balance = initialBalance;
        this.transactions = new Transaction[1]; // Initial capacity
        this.transactionCount = 0;
    }

    public static class Transaction {
        private final Account accountFrom;
        private final Account accountTo;
        private final double amount;
        private final StandardAccountOperations operation;

        public Transaction(Account from, Account to, double amt, StandardAccountOperations op) {
            this.accountFrom = from;
            this.accountTo = to;
            this.amount = amt;
            this.operation = op;
        }

        public Account getAccountFrom() {
            return accountFrom;
        }

        public Account getAccountTo() {
            return accountTo;
        }

        public double getAmount() {
            return amount;
        }

        public StandardAccountOperations getOperation() {
            return operation;
        }
    }

    public void sendMoneyToAccount(Account target, double amount) {
        // Validation
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
        if (this.balance < amount) throw new RuntimeException("Insufficient funds.");

        // Dual-entry bookkeeping logic
        this.balance -= amount;
        this.addTransaction(new Transaction(this, target, amount, StandardAccountOperations.MONEY_TRANSFER_SEND));

        target.balance += amount;
        target.addTransaction(new Transaction(this, target, amount, StandardAccountOperations.MONET_TRANSFER_RECEIVE));
    }

    private void addTransaction(Transaction tx) {
        ensureCapasity();
        transactions[transactionCount++] = tx;
    }

    private void ensureCapasity() {
        if (transactionCount == transactions.length) {
            // Manual array doubling strategy using native memory copy
            int newSize = transactions.length * 2;
            Transaction[] newArr = new Transaction[newSize];
            // Native memory copy
            System.arraycopy(transactions, 0, newArr, 0, transactions.length);
            this.transactions = newArr;
        }
    }

    public Transaction[] getTransaction() {
        // Defensive copy to protect internal state encapsulation
        return Arrays.copyOf(transactions, transactionCount);
    }

    public double getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }

}
