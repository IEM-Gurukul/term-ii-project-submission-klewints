# PCCCS495 – Term II Project

## Project Title
**Layered Banking System using Service–Repository Architecture in Java**

---

## Problem Statement

This project implements a **modular banking transaction system** that models real-world financial operations such as account creation, deposits, withdrawals, and inter-account transfers. The system addresses the need for structured transaction handling, data persistence, and maintainable code architecture by adopting a **layered design pattern** and **Service–Repository architectural pattern**.

### Key Objectives:
- **Structured Transaction Handling**: Implement robust validation rules (e.g., minimum balance enforcement, overdraft handling)
- **Data Persistence & Integrity**: Maintain account state across executions using file-based persistence
- **Separation of Concerns**: Decouple business logic from data access layers
- **Scalability & Extensibility**: Design patterns enable easy future enhancements
- **Error Handling**: Custom exception handling ensures controlled failure scenarios

---

## Target User

### Primary Users
- Small financial institutions managing limited-scale account operations
- Individual users requiring basic banking simulation and tracking
- Educational institutions teaching software architecture principles

### Secondary Users
- Developers studying **layered architecture** and **Object-Oriented Programming (OOP)** design
- Software engineers learning **design patterns** (Repository Pattern, Service Layer Pattern)
- Students understanding **separation of concerns** in enterprise applications

---

## Core Features

### Account Management
- ✓ **Account Creation**: Automatic unique Account ID generation (Format: A[1001, 1002, ...])
- ✓ **Multiple Account Types**: Support for two specialized account types:
  - **Savings Account**: Enforces minimum balance constraint (₹500)
  - **Current Account**: Supports overdraft facility with configurable limits
- ✓ **PIN-Based Authentication**: Secure 4-digit PIN validation for all transactions
- ✓ **Account Information Retrieval**: Query balance and account details

### Transaction Operations
- ✓ **Deposit Operations**: Add funds to accounts with validation constraints
- ✓ **Withdrawal Operations**: 
  - Account type-specific rules
  - Savings: Enforces minimum balance (₹500)
  - Current: Allows overdraft within configured limit
- ✓ **Inter-Account Transfers**: Fund transfer between accounts with transactional integrity
- ✓ **Transaction Logging**: Complete transaction history with timestamp tracking (using `LocalDateTime`)
- ✓ **Transaction Validation**: Amount validation, account existence checks

### Data Persistence
- ✓ **File-Based Persistence**: Accounts stored in `accounts.txt` using CSV format
- ✓ **Data Structure**: `TYPE,ACCOUNT_ID,NAME,BALANCE,PIN`
- ✓ **State Recovery**: System loads previous account data on startup
- ✓ **Automatic Save**: Data persists on application exit

### User Interface
- ✓ **Graphical User Interface (GUI)**: Java Swing-based interactive console
- ✓ **Console-Based Interaction**: User-friendly menu-driven operations
- ✓ **Admin Panel**: Administrative access to view all accounts (PIN: 9090)
- ✓ **Input Validation**: Client-side validation for names, PINs, and amounts

---

## Architecture Overview

### Layered Architecture Design

The system follows a **5-layer architecture** pattern, promoting **separation of concerns** and **loose coupling**:
# PCCCS495 – Term II Project

## Project Title
**Layered Banking System using Service–Repository Architecture in Java**

---

## Problem Statement

This project implements a **modular banking transaction system** that models real-world financial operations such as account creation, deposits, withdrawals, and inter-account transfers. The system addresses the need for structured transaction handling, data persistence, and maintainable code architecture by adopting a **layered design pattern** and **Service–Repository architectural pattern**.

### Key Objectives:
- **Structured Transaction Handling**: Implement robust validation rules (e.g., minimum balance enforcement, overdraft handling)
- **Data Persistence & Integrity**: Maintain account state across executions using file-based persistence
- **Separation of Concerns**: Decouple business logic from data access layers
- **Scalability & Extensibility**: Design patterns enable easy future enhancements
- **Error Handling**: Custom exception handling ensures controlled failure scenarios

---

## Target User

### Primary Users
- Small financial institutions managing limited-scale account operations
- Individual users requiring basic banking simulation and tracking
- Educational institutions teaching software architecture principles

### Secondary Users
- Developers studying **layered architecture** and **Object-Oriented Programming (OOP)** design
- Software engineers learning **design patterns** (Repository Pattern, Service Layer Pattern)
- Students understanding **separation of concerns** in enterprise applications

---

## Core Features

### Account Management
- **Account Creation**: Automatic unique Account ID generation (Format: A[1001, 1002, ...])
-  **Multiple Account Types**: Support for two specialized account types:
  - **Savings Account**: Enforces minimum balance constraint (₹500)
  - **Current Account**: Supports overdraft facility with configurable limits
-  **PIN-Based Authentication**: Secure 4-digit PIN validation for all transactions
-  **Account Information Retrieval**: Query balance and account details

### Transaction Operations
-  **Deposit Operations**: Add funds to accounts with validation constraints
   **Withdrawal Operations**: 
  - Account type-specific rules
  - Savings: Enforces minimum balance (₹500)
  - Current: Allows overdraft within configured limit
-  **Inter-Account Transfers**: Fund transfer between accounts with transactional integrity
-  **Transaction Logging**: Complete transaction history with timestamp tracking (using `LocalDateTime`)
-  **Transaction Validation**: Amount validation, account existence checks

### Data Persistence
-  **File-Based Persistence**: Accounts stored in `accounts.txt` using CSV format
-  **Data Structure**: `TYPE,ACCOUNT_ID,NAME,BALANCE,PIN`
-  **State Recovery**: System loads previous account data on startup
-  **Automatic Save**: Data persists on application exit

### User Interface
-  **Graphical User Interface (GUI)**: Java Swing-based interactive console
- **Console-Based Interaction**: User-friendly menu-driven operations
-  **Admin Panel**: Administrative access to view all accounts (PIN: 9090)
-  **Input Validation**: Client-side validation for names, PINs, and amounts


## Object-Oriented Programming (OOP) Concepts Implemented

### 1. **Abstraction**
- **Abstract Base Class**: `Account` abstract class defines common banking operations while hiding implementation details
- Subclasses implement specific withdrawal logic without exposing internal mechanisms
- **Example**:
  ```java
  public abstract class Account {
      public abstract void withdraw(double amount) 
          throws InsufficientBalanceException, InvalidTransactionException;
  }
  ```
2. Inheritance (Generalization/Specialization)
Specialized account types extend the base Account class
Inheritance hierarchy
```java
Account (Abstract Parent)
├── SavingsAccount (Minimum balance enforcement)
└── CurrentAccount (Overdraft facility support)
```
3. Polymorphism (Method Overriding)
Runtime Polymorphism: System handles all account types through common parent reference:
```java
Account acc = new SavingsAccount(...);  // Can also be CurrentAccount
acc.withdraw(amount);  // Calls appropriate implementation at runtime
```
4. Encapsulation (Data Hiding & Controlled Access)
Access Modifiers:
`private`: Balance, PIN (hidden from direct access)
`protected`: AccountId, AccountHolderName (accessible to subclasses)
`public`: Getter/setter methods (controlled access).

6. Exception Handling (Error Management)
Custom Exceptions: Business-logic specific exceptions
`InsufficientBalanceException`: Triggered when withdrawal exceeds available balance
`AccountNotFoundException`: Raised when account lookup fails
`InvalidTransactionException`: For invalid amounts or operations
Exception Propagation: Exceptions thrown from domain layer, caught at service layer
Checked Exceptions: Enforces explicit error handling in client code.

6. Collections Framework
Dynamic Account Storage: ArrayList<Account> maintains flexible account list
Transaction History: ArrayList<Transaction> records all transfers.

---

## Architecture Overview

### Layered Architecture Design

The system follows a **5-layer architecture** pattern, promoting **separation of concerns** and **loose coupling**:
 ```mermaid graph TD A[Presentation Layer\n(UI / Main)] --> B[Service Layer\n(Business Logic)] B --> C[Repository Layer\n(Data Access)] C --> D[Persistence Layer\n(File Handling)] D --> E[Domain Layer\n(Models & Entities)] ``` 

## System Component Breakdown

### Domain Layer (Models)
#### Account.java (Abstract Base Class)
Properties: accountId, accountHolderName, balance, pin
Static Counter: Auto-increments account IDs
Methods:
`validatePin(int)`: PIN verification
`deposit(double)`: Add funds
`withdraw(double)`: Remove funds (abstract, overridden by subclasses)
`getters`: Retrieve account information
Exception Handling: Throws `InsufficientBalanceException`, `InvalidTransactionException`

#### SavingsAccount.java (Specialized Implementation)
Minimum Balance: ₹500 enforcement
Withdrawal Override: Validates remaining balance post-withdrawal
Use Case: Personal savings accounts with protection
#### CurrentAccount.java (Specialized Implementation)
Overdraft Support: Configurable overdraft limit (default: ₹1000)
Withdrawal Override: Allows withdrawal up to (balance + overdraftLimit)
Use Case: Business accounts with flexible credit lines
#### Transaction.java (Transaction Record)
Properties: transactionId, type, amount, fromAccountId, toAccountId, timestamp
Timestamp: Uses `LocalDateTime.now()` for precise transaction tracking
String Representation: Human-readable transaction details

### Exception Layer (Custom Exceptions)
#### InsufficientBalanceException.java
Extends Exception
Triggers when withdrawal amount exceeds available balance
Includes custom error message
#### InvalidTransactionException.java
Extends Exception
Triggers for invalid transaction amounts (≤ 0)
Triggers for invalid operations
#### AccountNotFoundException.java
Extends Exception
Triggers when account lookup fails
Provides account not found error message

### Repository Layer (Data Access)
AccountRepository.java
Responsibility: In-memory data access abstraction
Methods:
addAccount(Account): Insert new account
`findById(String)`: Retrieve account by ID
`getAllAccounts()`: Return all accounts
Benefit: Abstracts data storage from business logic (enables future database integration)

### Persistence Layer (File I/O)
FileHandler.java
File Format: CSV-based storage (accounts.txt)
Data Format: TYPE,ACCOUNT_ID,NAME,BALANCE,PIN
Methods:
saveAccounts(ArrayList<Account>): Serialize accounts to file
loadAccounts(): Deserialize accounts from file

### Service Layer (Business Logic)
#### BankService.java
Core Responsibilities:
Account management (add, retrieve)
Transaction processing (transfers, deposits, withdrawals)
Data persistence coordination
Methods:
`addAccount(Account)`: Add new account via repository
`findAccountById(String)`: Locate account
`transfer(String, String, double)`: Inter-account fund transfer
`getAllAccounts()`: Retrieve all accounts
`saveData()`: Persist data to file

### Presentation Layer (User Interface)
BankUI.java (Java Swing GUI)
GUI Framework: JFrame, JPanel, JButton, JOptionPane
Layout: BoxLayout and GridLayout for responsive design
Styling: Custom colors, fonts, button theming

## Project Structure
term-ii-project-submission-klewints/
│
├── src/
│   ├── Main.java                          # Entry point
│   │
│   └── banking/
│       │
│       ├── domain/
│       │   ├── Account.java               # Abstract base class
│       │   ├── SavingsAccount.java        # Specialized implementation
│       │   ├── CurrentAccount.java        # Specialized implementation
│       │   └── Transaction.java           # Transaction model
│       │
│       ├── exception/
│       │   ├── InsufficientBalanceException.java
│       │   ├── AccountNotFoundException.java
│       │   └── InvalidTransactionException.java
│       │
│       ├── persistence/
│       │   └── FileHandler.java           # File I/O operations
│       │
│       ├── repository/
│       │   └── AccountRepository.java     # Data access layer
│       │
│       ├── service/
│       │   └── BankService.java           # Business logic
│       │
│       └── ui/
│           └── BankUI.java                # GUI implementation
│
├── accounts.txt                           # Persistent data file
├── README.md                              # Project documentation
└── .gitignore                             # Git configuration

## Technical Stack
### Language & Runtime
Language: Java (JDK 8+)
Package Structure: Layered package organization (domain, service, repository, persistence, exception, ui)
### Libraries & APIs
GUI Framework: javax.swing (JFrame, JPanel, JButton, JOptionPane)
Layout Management: java.awt (BoxLayout, GridLayout, Dimension, Color)
Collections: java.util.ArrayList (dynamic arrays)
Date/Time: java.time.LocalDateTime (timestamp tracking)
File I/O: java.io (BufferedReader, BufferedWriter, FileReader, FileWriter)
Text Processing: java.lang.String (CSV parsing with split())
### Concurrency Considerations
Single-threaded application (suitable for learning architecture patterns)
No explicit threading mechanisms implemented
Collections are not synchronized (fine for single-thread execution)

## How to Run
### Prerequisites
Java Development Kit (JDK): Version 8 or above
IDE: Any Java IDE (IntelliJ IDEA, Eclipse, VS Code with Extension Pack for Java)
Build Tool (Optional): Maven or Gradle
### Steps to Execute
Using IDE:
Import Project: Open the repository as a Java project
Build: Compile all Java files
Run: Execute Main.java as a Java application
Interact: Use the GUI to perform banking operations
### Using Command Line:
# Navigate to src directory
cd src

# Compile all Java files
```java
javac -d . banking/**/*.java Main.java
```
# Run the application
```java
java Main
```
# OR compile then run in one go
```java
javac -d . banking/domain/*.java banking/exception/*.java \
         banking/persistence/*.java banking/repository/*.java \
         banking/service/*.java banking/ui/*.java Main.java && java Main
```
### Git Discipline & Development Practices
## Commit Strategy
Incremental Commits: Each feature implemented with focused, atomic commits.
Meaningful Messages: Commit messages describe changes clearly.
Feature-Based Development: Logical separation of concerns across commits.
Minimum Requirement: 15+ meaningful commits reflecting development progress.
