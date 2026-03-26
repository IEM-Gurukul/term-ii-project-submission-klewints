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
        System.out.print("Enter ID: ");
        String id = sc.next();

        System.out.print("Enter Name: ");
        String name = sc.next();

        System.out.print("Enter Balance: ");
        double balance = sc.nextDouble();

        SavingsAccount acc = new SavingsAccount(id, name, balance);
        bankService.addAccount(acc);
    }

    static void deposit() {
        try {
            System.out.print("Enter Account ID: ");
            String id = sc.next();

            System.out.print("Amount: ");
            double amount = sc.nextDouble();

            Account acc = bankService.findAccountById(id);
            acc.deposit(amount);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void withdraw() {
        try {
            System.out.print("Enter Account ID: ");
            String id = sc.next();

            System.out.print("Amount: ");
            double amount = sc.nextDouble();

            Account acc = bankService.findAccountById(id);
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

            System.out.print("Amount: ");
            double amount = sc.nextDouble();

            bankService.transfer(from, to, amount);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}