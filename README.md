# transaction-processor-core

A high-performance Java transaction system featuring dual-entry bookkeeping, defensive state encapsulation, and manual memory management logic

-----------------------------------

I. Technical Highlights

Don't just say what it does; explain how it does it. List the advanced concepts you implemented:

Dual-Entry Bookkeeping: Ensures mathematical integrity where every debit has a corresponding credit.

Static Nested Entities: Implemented the Transaction class as a static nested class to minimize memory overhead and prevent outer-class reference leaks.

Defensive Encapsulation: Used Arrays.copyOf to prevent external modification of internal ledger states.

Amortized Array Resizing: Utilized a doubling strategy with System.arraycopy for O(1) amortized insertion performance.

-----------------------------------

II. Tech Stack

Language: Java 21 (or your version)

Build Tool: Gradle 

CI/CD: GitHub Actions

-----------------------------------

III. Architectural Diagram

classDiagram
    class Account {
        -int id
        -double balance
        -Transaction transactions
        +sendMoneyToAccount(Account, double)
        +getTransactions() Transaction
        -ensureCapacity()
    }
    class Transaction {
        <<static nested>>
        -Account accountFrom
        -Account accountTo
        -double amount
        -StandardAccountOperations operation
    }
    class StandardAccountOperations {
        <<enumeration>>
        MONEY_TRANSFER_SEND
        MONEY_TRANSFER_RECEIVE
        WITHDRAW
    }
    Account "1" *-- "*" Transaction : manages
    Transaction..> StandardAccountOperations : uses

-----------------------------------

IV. Getting Started

Provide clear instructions so anyone can run your tests immediately:

Clone: git clone https://github.com/FadyRiad/transaction-processor-core.git

Build & Test: ./gradlew clean test

Run CLI: ./gradlew run


    
