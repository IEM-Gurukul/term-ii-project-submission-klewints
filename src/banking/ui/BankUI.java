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
    
    // UI Color Theme
    private static final Color LIGHT_YELLOW = new Color(255, 250, 205);
    private static final Color LIGHT_GREY = new Color(240, 240, 240);
    private static final Color BUTTON_HOVER = new Color(255, 245, 157);
    private static final Color BLACK_TEXT = new Color(0, 0, 0);

    public BankUI() {
        setTitle("Banking Management System");
        
        // Set window size to 35% of screen
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int windowWidth = (int) (screenSize.width * 0.35);
        int windowHeight = (int) (screenSize.height * 0.85);
        setSize(windowWidth, windowHeight);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set light grey background
        setBackground(LIGHT_GREY);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(LIGHT_GREY);
        
        // Title label - large and centered
        JLabel titleLabel = new JLabel("Banking Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(BLACK_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        
        // Button panel with consistent spacing
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1, 0, 15));
        buttonPanel.setBackground(LIGHT_GREY);
        buttonPanel.setMaximumSize(new Dimension(350, Integer.MAX_VALUE));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton createBtn = createStyledButton("Create Account");
        JButton depositBtn = createStyledButton("Deposit");
        JButton withdrawBtn = createStyledButton("Withdraw");
        JButton transferBtn = createStyledButton("Transfer");
        JButton viewBalanceBtn = createStyledButton("View Balance");
        JButton viewBtn = createStyledButton("View All Accounts (Admin)");
        JButton transactionBtn = createStyledButton("View Transactions");
        JButton exitBtn = createStyledButton("Exit");
        
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
                showSuccessDialog("✓ Account Created Successfully!\nAccount ID: " + acc.getAccountId());

            } catch (Exception ex) {
                showErrorDialog("Error creating account: " + ex.getMessage());
            }
        });

        // 🔹 DEPOSIT
        depositBtn.addActionListener(e -> {
            try {
                String id = getCustomInputDialog("Deposit", "Enter Account ID:");
                if (id == null || id.trim().isEmpty()) return;

                String pin = getValidatedPin("Enter PIN (4 digits):");
                if (pin == null) return;

                Double amount = getValidatedAmount("Enter Amount to Deposit:");
                if (amount == null) return;

                Account acc = bankService.findAccountById(id);

                if (acc == null) {
                    showErrorDialog("Account not found. Please check the Account ID.");
                    return;
                }

                if (!acc.validatePin(Integer.parseInt(pin))) {
                    showErrorDialog("Invalid PIN. Access denied.");
                    return;
                }

                acc.deposit(amount);
                showSuccessDialog("✓ Deposit Successful!\nNew Balance: " + acc.getBalance());

            } catch (Exception ex) {
                showErrorDialog("Error processing deposit: " + ex.getMessage());
            }
        });

        // 🔹 WITHDRAW
        withdrawBtn.addActionListener(e -> {
            try {
                String id = getCustomInputDialog("Withdraw", "Enter Account ID:");
                if (id == null || id.trim().isEmpty()) return;

                String pin = getValidatedPin("Enter PIN (4 digits):");
                if (pin == null) return;

                Double amount = getValidatedAmount("Enter Amount to Withdraw:");
                if (amount == null) return;

                Account acc = bankService.findAccountById(id);

                if (acc == null) {
                    showErrorDialog("Account not found. Please check the Account ID.");
                    return;
                }

                if (!acc.validatePin(Integer.parseInt(pin))) {
                    showErrorDialog("Invalid PIN. Access denied.");
                    return;
                }

                acc.withdraw(amount);
                showSuccessDialog("✓ Withdrawal Successful!\nRemaining Balance: " + acc.getBalance());

            } catch (Exception ex) {
                showErrorDialog("Error processing withdrawal: " + ex.getMessage());
            }
        });

        // 🔹 TRANSFER
        transferBtn.addActionListener(e -> {
            try {
                String from = getCustomInputDialog("Transfer", "From Account ID:");
                if (from == null || from.trim().isEmpty()) return;

                String to = getCustomInputDialog("Transfer", "To Account ID:");
                if (to == null || to.trim().isEmpty()) return;

                String pin = getValidatedPin("Enter PIN (4 digits):");
                if (pin == null) return;

                Double amount = getValidatedAmount("Enter Amount to Transfer:");
                if (amount == null) return;

                Account fromAcc = bankService.findAccountById(from);

                if (fromAcc == null) {
                    showErrorDialog("Source account not found.");
                    return;
                }

                if (!fromAcc.validatePin(Integer.parseInt(pin))) {
                    showErrorDialog("Invalid PIN. Access denied.");
                    return;
                }

                Account toAcc = bankService.findAccountById(to);
                if (toAcc == null) {
                    showErrorDialog("Destination account not found.");
                    return;
                }

                bankService.transfer(from, to, amount);
                showSuccessDialog("✓ Transfer Successful!\nAmount: " + amount);

            } catch (Exception ex) {
                showErrorDialog("Error processing transfer: " + ex.getMessage());
            }
        });

        // 🔹 VIEW BALANCE (Normal User)
        viewBalanceBtn.addActionListener(e -> {
            try {
                String id = getCustomInputDialog("View Balance", "Enter Account ID:");
                if (id == null || id.trim().isEmpty()) return;

                String pin = getValidatedPin("Enter PIN (4 digits):");
                if (pin == null) return;

                Account acc = bankService.findAccountById(id);

                if (acc == null) {
                    showErrorDialog("Account not found.");
                    return;
                }

                if (!acc.validatePin(Integer.parseInt(pin))) {
                    showErrorDialog("Invalid PIN. Access denied.");
                    return;
                }

                String balanceInfo = "Account ID: " + acc.getAccountId() + 
                                    "\nBalance: " + acc.getBalance();
                showEnlargedDialog("Account Balance", balanceInfo);

            } catch (Exception ex) {
                showErrorDialog("Error retrieving balance: " + ex.getMessage());
            }
        });

        // 🔹 VIEW ALL ACCOUNTS (Admin Only)
        viewBtn.addActionListener(e -> {
            try {
                String adminPin = getCustomInputDialog("Admin Access", "Enter Admin PIN:");
                
                if (adminPin == null) return;

                if (!adminPin.equals(ADMIN_PIN)) {
                    showErrorDialog("Unauthorized Access! Invalid Admin PIN.");
                    return;
                }

                ArrayList<Account> accounts = bankService.getAllAccounts();

                if (accounts.isEmpty()) {
                    showEnlargedDialog("View All Accounts", "No accounts found in the system.");
                    return;
                }

                StringBuilder sb = new StringBuilder("=== ALL ACCOUNTS (ADMIN VIEW) ===\n\n");
                for (Account acc : accounts) {
                    sb.append(acc.toString()).append("\n\n");
                }

                showEnlargedDialog("All Accounts", sb.toString());

            } catch (Exception ex) {
                showErrorDialog("Error retrieving accounts: " + ex.getMessage());
            }
        });

        // 🔹 VIEW TRANSACTIONS
        transactionBtn.addActionListener(e -> {
            try {
                ArrayList<Transaction> transactions = bankService.getTransactions();

                if (transactions.isEmpty()) {
                    showEnlargedDialog("View Transactions", "No transactions found.");
                    return;
                }

                StringBuilder sb = new StringBuilder("=== TRANSACTION HISTORY ===\n\n");
                for (Transaction transaction : transactions) {
                    sb.append(transaction.toString()).append("\n");
                }

                showEnlargedDialog("Transactions", sb.toString());

            } catch (Exception ex) {
                showErrorDialog("Error retrieving transactions: " + ex.getMessage());
            }
        });

        // 🔹 EXIT
        exitBtn.addActionListener(e -> {
            bankService.saveData();
            showSuccessDialog("✓ Data saved successfully. Exiting.");
            System.exit(0);
        });
    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        
        // Font styling
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(BLACK_TEXT);
        
        // Color styling
        button.setBackground(LIGHT_YELLOW);
        button.setForeground(BLACK_TEXT);
        button.setOpaque(true);
        button.setBorderPainted(false);
        
        // Padding and sizing
        button.setPreferredSize(new Dimension(300, 50));
        button.setMaximumSize(new Dimension(300, 50));
        button.setFocusPainted(false);
        
        // Remove focus borders
        button.setFocusable(false);
        
        return button;
    }

    /**
     * Creates a custom input dialog with larger text field and font
     */
    private String getCustomInputDialog(String title, String prompt) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(LIGHT_GREY);
        
        // Prompt label with larger font
        JLabel promptLabel = new JLabel(prompt);
        promptLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        promptLabel.setForeground(BLACK_TEXT);
        promptLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(promptLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        
        // Text field with larger font and size
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setPreferredSize(new Dimension(300, 45));
        textField.setMaximumSize(new Dimension(300, 45));
        textField.setBackground(Color.WHITE);
        inputPanel.add(textField);
        
        // Show dialog
        int result = JOptionPane.showConfirmDialog(this, inputPanel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            return textField.getText();
        }
        return null;
    }

    /**
     * Shows an enlarged dialog with scrollable text area for large outputs
     */
    private void showEnlargedDialog(String title, String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(LIGHT_GREY);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows a success dialog
     */
    private void showSuccessDialog(String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(LIGHT_GREY);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows an error dialog
     */
    private void showErrorDialog(String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(LIGHT_GREY);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String getValidatedName() {
        while (true) {
            String name = getCustomInputDialog("Create Account", 
                "Enter Account Holder Name (alphabets only):");
            
            if (name == null) return null; // User cancelled
            if (name.trim().isEmpty()) {
                showErrorDialog("Name cannot be empty!");
                continue;
            }
            
            if (!name.matches("^[a-zA-Z ]+$")) {
                showErrorDialog("Invalid Name! Only alphabets and spaces are allowed.\nNo numbers or special characters.");
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
            String pin = getCustomInputDialog("PIN Entry", prompt);
            
            if (pin == null) return null; // User cancelled
            
            if (!pin.matches("^[0-9]{4}$")) {
                showErrorDialog("Invalid PIN! PIN must be exactly 4 digits.");
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
            String amountStr = getCustomInputDialog("Amount Entry", prompt);
            
            if (amountStr == null) return null; // User cancelled
            
            try {
                Double amount = Double.parseDouble(amountStr);
                
                if (amount <= 0) {
                    showErrorDialog("Invalid Amount! Amount must be greater than 0.");
                    continue;
                }
                
                return amount;
                
            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid Amount! Please enter a valid number.");
            }
        }
    }
}