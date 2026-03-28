package banking.ui;

import banking.service.BankService;
import banking.domain.Account;
import banking.domain.SavingsAccount;
import banking.domain.CurrentAccount;
import banking.domain.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BankUI extends JFrame {

    private BankService bankService = new BankService();

    public BankUI() {

        setTitle("Banking System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1, 10, 10));

        JButton createBtn = new JButton("Create Account");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton transferBtn = new JButton("Transfer");
        JButton viewBtn = new JButton("View Accounts");
        JButton transactionBtn = new JButton("View Transactions");
        JButton exitBtn = new JButton("Exit");

        add(createBtn);
        add(depositBtn);
        add(withdrawBtn);
        add(transferBtn);
        add(viewBtn);
        add(transactionBtn);
        add(exitBtn);

        // 🔹 CREATE ACCOUNT
        createBtn.addActionListener(e -> {
            try {
                String[] options = {"Savings", "Current"};
                int choice = JOptionPane.showOptionDialog(this, "Select Account Type",
                        "Account Type", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                String name = JOptionPane.showInputDialog("Enter Name:");
                double balance = Double.parseDouble(JOptionPane.showInputDialog("Enter Initial Balance:"));
                int pin = Integer.parseInt(JOptionPane.showInputDialog("Set PIN:"));

                Account acc;

                if (choice == 0) {
                    acc = new SavingsAccount(name, balance, pin);
                } else {
                    double limit = Double.parseDouble(JOptionPane.showInputDialog("Enter Overdraft Limit:"));
                    acc = new CurrentAccount(name, balance, pin, limit);
                }

                bankService.addAccount(acc);
                JOptionPane.showMessageDialog(this, "Account Created. ID: " + acc.getAccountId());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error creating account");
            }
        });

        // 🔹 DEPOSIT
        depositBtn.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog("Enter Account ID:");
                int pin = Integer.parseInt(JOptionPane.showInputDialog("Enter PIN:"));
                double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Amount:"));

                Account acc = bankService.findAccountById(id);

                if (acc == null) {
                    JOptionPane.showMessageDialog(this, "Account not found");
                    return;
                }

                if (!acc.validatePin(pin)) {
                    JOptionPane.showMessageDialog(this, "Invalid PIN");
                    return;
                }

                acc.deposit(amount);
                JOptionPane.showMessageDialog(this, "Deposit Successful");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error in deposit");
            }
        });

        // 🔹 WITHDRAW
        withdrawBtn.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog("Enter Account ID:");
                int pin = Integer.parseInt(JOptionPane.showInputDialog("Enter PIN:"));
                double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Amount:"));

                Account acc = bankService.findAccountById(id);

                if (acc == null) {
                    JOptionPane.showMessageDialog(this, "Account not found");
                    return;
                }

                if (!acc.validatePin(pin)) {
                    JOptionPane.showMessageDialog(this, "Invalid PIN");
                    return;
                }

                acc.withdraw(amount);
                JOptionPane.showMessageDialog(this, "Withdrawal Successful");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        // 🔹 TRANSFER
        transferBtn.addActionListener(e -> {
            try {
                String from = JOptionPane.showInputDialog("From Account ID:");
                String to = JOptionPane.showInputDialog("To Account ID:");
                int pin = Integer.parseInt(JOptionPane.showInputDialog("Enter PIN:"));
                double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Amount:"));

                Account fromAcc = bankService.findAccountById(from);

                if (fromAcc == null) {
                    JOptionPane.showMessageDialog(this, "Source account not found");
                    return;
                }

                if (!fromAcc.validatePin(pin)) {
                    JOptionPane.showMessageDialog(this, "Invalid PIN");
                    return;
                }

                bankService.transfer(from, to, amount);

                JOptionPane.showMessageDialog(this, "Transfer Successful");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        // 🔹 VIEW ACCOUNTS
        viewBtn.addActionListener(e -> {
            ArrayList<Account> accounts = bankService.getAllAccounts();

            if (accounts.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No accounts found");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (Account acc : accounts) {
                sb.append(acc.toString()).append("\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());
        });

        // 🔹 VIEW TRANSACTIONS
        transactionBtn.addActionListener(e -> {
            ArrayList<Transaction> transactions = bankService.getTransactions();

            if (transactions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No transactions found");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (Transaction t : transactions) {
                sb.append(t.toString()).append("\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());
        });

        // 🔹 EXIT
        exitBtn.addActionListener(e -> {
            bankService.saveData();
            JOptionPane.showMessageDialog(this, "Data saved. Exiting...");
            System.exit(0);
        });
    }
}