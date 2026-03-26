import banking.domain.*;
import banking.service.BankService;

import java.util.Scanner;

public class Main {

    static BankService bankService = new BankService();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n--- Banking System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Accounts");
            System.out.println("6. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    bankService.displayAllAccounts();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
         static void createAccount() {
    System.out.println("Select Account Type:");
    System.out.println("1. Savings");
    System.out.println("2. Current");

    int type = sc.nextInt();

    System.out.print("Enter Name: ");
    String name = sc.next();

    System.out.print("Enter Initial Balance: ");
    double balance = sc.nextDouble();

    System.out.print("Set PIN: ");
    int pin = sc.nextInt();

    if (type == 1) {
        SavingsAccount acc = new SavingsAccount(name, balance, pin);
        bankService.addAccount(acc);
        System.out.println("Account created. ID: " + acc.getAccountId());
    } else if (type == 2) {
        System.out.print("Enter Overdraft Limit: ");
        double limit = sc.nextDouble();

        CurrentAccount acc = new CurrentAccount(name, balance, pin, limit);
        bankService.addAccount(acc);
        System.out.println("Account created. ID: " + acc.getAccountId());
    } else {
        System.out.println("Invalid choice");
    }
}

    static void deposit() {
        try {
            System.out.print("Enter Account ID: ");
            String id = sc.next();
             
            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();

            System.out.print("Amount: ");
            double amount = sc.nextDouble();

            Account acc = bankService.findAccountById(id);
            if (!acc.validatePin(pin)) {
           System.out.println("Invalid PIN");
           return;
           }
            acc.deposit(amount);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void withdraw() {
        try {
            System.out.print("Enter Account ID: ");
            String id = sc.next();

            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();

            System.out.print("Amount: ");
            double amount = sc.nextDouble();

            Account acc = bankService.findAccountById(id);
            if (!acc.validatePin(pin)) {
           System.out.println("Invalid PIN");
           return;
           }
            acc.withdraw(amount);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

   static void transfer() {
    try {
        System.out.print("From ID: ");
        String from = sc.next();

        System.out.print("To ID: ");
        String to = sc.next();

        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        System.out.print("Amount: ");
        double amount = sc.nextDouble();

        Account fromAcc = bankService.findAccountById(from);

        if (fromAcc == null) {
            System.out.println("Source account not found");
            return;
        }

        if (!fromAcc.validatePin(pin)) {
            System.out.println("Invalid PIN");
            return;
        }

        // now safe to proceed
        bankService.transfer(from, to, amount);

    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}
}