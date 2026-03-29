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
    private static final String ADMIN_PIN = "9090";

    public BankUI() {
        setTitle("Banking Management System");
        setSize(900, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Title label
        JLabel titleLabel = new JLabel("Banking Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(25));
        
        // Button panel with consistent spacing
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1, 10, 10));
        
        JButton createBtn = new JButton("Create Account");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton transferBtn = new JButton("Transfer");
        JButton viewBalanceBtn = new JButton("View Balance");
        JButton viewBtn = new JButton("View All Accounts (Admin)");
        JButton transactionBtn = new JButton("View Transactions");
        JButton exitBtn = new JButton("Exit");
        
        // Set button sizes
        Dimension buttonSize = new Dimension(300, 45);
        createBtn.setMaximumSize(buttonSize);
        depositBtn.setMaximumSize(buttonSize);
        withdrawBtn.setMaximumSize(buttonSize);
        transferBtn.setMaximumSize(buttonSize);
        viewBalanceBtn.setMaximumSize(buttonSize);
        viewBtn.setMaximumSize(buttonSize);
        transactionBtn.setMaximumSize(buttonSize);
        exitBtn.setMaximumSize(buttonSize);
        
        buttonPanel.add(createBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(transferBtn);
        buttonPanel.add(viewBalanceBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(transactionBtn);
        buttonPanel.add(exitBtn);
        
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
        setVisible(true);

        // 🔹 CREATE ACCOUNT
        createBtn.addActionListener(e -> {
            try {
                String[] options = {"Savings", "Current"};
                int choice = JOptionPane.showOptionDialog(this, "Select Account Type",
                        "Create Account", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (choice == -1) return; // User cancelled

                // Validate Name - only alphabets
                String name = getValidatedName();
                if (name == null) return;

                // Validate Initial Balance
                Double balance = getValidatedAmount("Enter Initial Balance:");
                if (balance == null) return;

                // Validate PIN - exactly 4 digits
                String pin = getValidatedPin("Set PIN (4 digits):");
                if (pin == null) return;

                Account acc;

                if (choice == 0) {
                    acc = new SavingsAccount(name, balance, Integer.parseInt(pin));
                } else {
                    Double limit = getValidatedAmount("Enter Overdraft Limit:");
                    if (limit == null) return;
                    acc = new CurrentAccount(name, balance, Integer.parseInt(pin), limit);
                }

                bankService.addAccount(acc);
                JOptionPane.showMessageDialog(this, 
                    "✓ Account Created Successfully!\nAccount ID: " + acc.getAccountId(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error creating account: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 🔹 DEPOSIT
        depositBtn.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog(this, "Enter Account ID:", "Deposit", JOptionPane.QUESTION_MESSAGE);
                if (id == null || id.trim().isEmpty()) return;

                String pin = getValidatedPin("Enter PIN (4 digits):");
                if (pin == null) return;

                Double amount = getValidatedAmount("Enter Amount to Deposit:");
                if (amount == null) return;

                Account acc = bankService.findAccountById(id);

                if (acc == null) {
                    JOptionPane.showMessageDialog(this, 
                        "Account not found. Please check the Account ID.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!acc.validatePin(Integer.parseInt(pin))) {
                    JOptionPane.showMessageDialog(this, 
                        "Invalid PIN. Access denied.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                acc.deposit(amount);
                JOptionPane.showMessageDialog(this, 
                    "✓ Deposit Successful!\nNew Balance: " + acc.getBalance(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error processing deposit: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 🔹 WITHDRAW
        withdrawBtn.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog(this, "Enter Account ID:", "Withdraw", JOptionPane.QUESTION_MESSAGE);
                if (id == null || id.trim().isEmpty()) return;

                String pin = getValidatedPin("Enter PIN (4 digits):");
                if (pin == null) return;

                Double amount = getValidatedAmount("Enter Amount to Withdraw:");
                if (amount == null) return;

                Account acc = bankService.findAccountById(id);

                if (acc == null) {
                    JOptionPane.showMessageDialog(this, 
                        "Account not found. Please check the Account ID.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!acc.validatePin(Integer.parseInt(pin))) {
                    JOptionPane.showMessageDialog(this, 
                        "Invalid PIN. Access denied.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                acc.withdraw(amount);
                JOptionPane.showMessageDialog(this, 
                    "✓ Withdrawal Successful!\nRemaining Balance: " + acc.getBalance(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error processing withdrawal: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 🔹 TRANSFER
        transferBtn.addActionListener(e -> {
            try {
                String from = JOptionPane.showInputDialog(this, "From Account ID:", "Transfer", JOptionPane.QUESTION_MESSAGE);
                if (from == null || from.trim().isEmpty()) return;

                String to = JOptionPane.showInputDialog(this, "To Account ID:", "Transfer", JOptionPane.QUESTION_MESSAGE);
                if (to == null || to.trim().isEmpty()) return;

                String pin = getValidatedPin("Enter PIN (4 digits):");
                if (pin == null) return;

                Double amount = getValidatedAmount("Enter Amount to Transfer:");
                if (amount == null) return;

                Account fromAcc = bankService.findAccountById(from);

                if (fromAcc == null) {
                    JOptionPane.showMessageDialog(this, 
                        "Source account not found.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!fromAcc.validatePin(Integer.parseInt(pin))) {
                    JOptionPane.showMessageDialog(this, 
                        "Invalid PIN. Access denied.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Account toAcc = bankService.findAccountById(to);
                if (toAcc == null) {
                    JOptionPane.showMessageDialog(this, 
                        "Destination account not found.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                bankService.transfer(from, to, amount);
                JOptionPane.showMessageDialog(this, 
                    "✓ Transfer Successful!\nAmount: " + amount,
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error processing transfer: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 🔹 VIEW BALANCE (Normal User)
        viewBalanceBtn.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog(this, "Enter Account ID:", "View Balance", JOptionPane.QUESTION_MESSAGE);
                if (id == null || id.trim().isEmpty()) return;

                String pin = getValidatedPin("Enter PIN (4 digits):");
                if (pin == null) return;

                Account acc = bankService.findAccountById(id);

                if (acc == null) {
                    JOptionPane.showMessageDialog(this, 
                        "Account not found.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!acc.validatePin(Integer.parseInt(pin))) {
                    JOptionPane.showMessageDialog(this, 
                        "Invalid PIN. Access denied.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String balanceInfo =  "Account ID: " + acc.getAccountId() + 
                                    "\nBalance: " + acc.getBalance();
                JOptionPane.showMessageDialog(this, balanceInfo, 
                    "Account Balance", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error retrieving balance: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 🔹 VIEW ALL ACCOUNTS (Admin Only)
        viewBtn.addActionListener(e -> {
            try {
                String adminPin = JOptionPane.showInputDialog(this, 
                    "Enter Admin PIN:", "Admin Access", JOptionPane.QUESTION_MESSAGE);
                
                if (adminPin == null) return;

                if (!adminPin.equals(ADMIN_PIN)) {
                    JOptionPane.showMessageDialog(this, 
                        "Unauthorized Access! Invalid Admin PIN.",
                        "Access Denied", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<Account> accounts = bankService.getAllAccounts();

                if (accounts.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "No accounts found in the system.",
                        "View All Accounts", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                StringBuilder sb = new StringBuilder("=== ALL ACCOUNTS (ADMIN VIEW) ===\n\n");
                for (Account acc : accounts) {
                    sb.append(acc.toString()).append("\n\n");
                }

                JOptionPane.showMessageDialog(this, sb.toString(), 
                    "All Accounts", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error retrieving accounts: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 🔹 VIEW TRANSACTIONS
        transactionBtn.addActionListener(e -> {
            try {
                ArrayList<Transaction> transactions = bankService.getTransactions();

                if (transactions.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "No transactions found.",
                        "View Transactions", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                StringBuilder sb = new StringBuilder("=== TRANSACTION HISTORY ===\n\n");
                for (Transaction transaction : transactions) {
                    sb.append(transaction.toString()).append("\n");
                }

                JOptionPane.showMessageDialog(this, sb.toString(), 
                    "Transactions", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error retrieving transactions: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 🔹 EXIT
        exitBtn.addActionListener(e -> {
            bankService.saveData();
            JOptionPane.showMessageDialog(this, 
                "✓ Data saved successfully. Goodbye!",
                "Exit", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });
    }

    // ============ VALIDATION HELPER METHODS ============

    /**
     * Validates and returns name with only alphabets
     */
    private String getValidatedName() {
        while (true) {
            String name = JOptionPane.showInputDialog(this, 
                "Enter Account Holder Name (alphabets only):", 
                "Create Account", JOptionPane.QUESTION_MESSAGE);
            
            if (name == null) return null; // User cancelled
            if (name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            
            if (!name.matches("^[a-zA-Z ]+$")) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid Name! Only alphabets and spaces are allowed.\nNo numbers or special characters.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            
            return name.trim();
        }
    }

    /**
     * Validates and returns PIN (exactly 4 digits)
     */
    private String getValidatedPin(String prompt) {
        while (true) {
            String pin = JOptionPane.showInputDialog(this, prompt, JOptionPane.QUESTION_MESSAGE);
            
            if (pin == null) return null; // User cancelled
            
            if (!pin.matches("^[0-9]{4}$")) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid PIN! PIN must be exactly 4 digits.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            
            return pin;
        }
    }

    /**
     * Validates and returns positive amount
     */
    private Double getValidatedAmount(String prompt) {
        while (true) {
            String amountStr = JOptionPane.showInputDialog(this, prompt, JOptionPane.QUESTION_MESSAGE);
            
            if (amountStr == null) return null; // User cancelled
            
            try {
                Double amount = Double.parseDouble(amountStr);
                
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Invalid Amount! Amount must be greater than 0.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                
                return amount;
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid Amount! Please enter a valid number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}