[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/pG3gvzt-)
# PCCCS495 – Term II Project

## Project Title
Layered Banking System using Service–Repository Architecture in Java

## Problem Statement (max 150 words)
This project implements a modular banking transaction system that models real-world financial operations such as account creation, deposits, withdrawals, and inter-account transfers. The system addresses the need for structured transaction handling, data persistence, and maintainable code architecture by adopting a layered design pattern. It ensures data integrity through validation rules (e.g., minimum balance enforcement, overdraft handling), and supports transaction traceability using a logging mechanism. The system also incorporates file-based persistence to maintain state across executions. A Service–Repository architectural pattern is used to separate business logic from data access, improving scalability and extensibility. Custom exception handling ensures controlled failure scenarios, while encapsulated domain models enforce data consistency.

## Target User
Primary Users
Small financial institutions managing limited-scale account operations
Individual users requiring basic banking simulation and tracking
Secondary Users
Developers studying layered architecture and OOP design.

## Core Features
Account creation with automatic unique account ID generation
Support for multiple account types (Savings, Current)
Secure PIN-based authentication for transaction operations
Deposit and withdrawal operations with validation constraints
Inter-account fund transfer with transactional integrity
Transaction logging with timestamp tracking
File-based persistence (accounts + PIN + balance retention)
Console-based interactive interface for user operations
Layered architecture separating domain, service, repository, and persistence.

## OOP Concepts Used
This Project makes the use of the following Object Oriented Programming Concepts:
- Abstraction: An abstract base class Account defines common banking operations while hiding implementation details.
- 
- Inheritance: Specialized account types extend the base class:
- Account
├── SavingsAccount
└── CurrentAccount

- Polymorphism: The system handles all account types through a common reference:
Account acc = new SavingsAccount(...);
acc.withdraw(amount);

- Encapsulation: Sensitive data such as balance and PIN are protected using controlled access:
private double balance;
protected int pin;
public boolean validatePin(int inputPin)

- Exception Handling: Custom exceptions ensure controlled error handling:
InsufficientBalanceException
AccountNotFoundException
InvalidTransactionException
Used to enforce business rules and avoid invalid states.

- Collections / Threads: ArrayList<Account> → dynamic storage of accounts
ArrayList<Transaction> → transaction history

## Proposed Architecture Description
Layer Breakdown:
Presentation Layer (Main / Console UI)
        ↓
Service Layer (BankService)
        ↓
Repository Layer (AccountRepository)
        ↓
Persistence Layer (FileHandler)
        ↓
Domain Layer (Account, Transaction)
## How to Run
Prerequisites
Java JDK (version 8 or above)
Any IDE

## Git Discipline Notes
Incremental commits reflecting feature-based development
Logical separation of concerns across commits

Minimum 10 meaningful commits required.
