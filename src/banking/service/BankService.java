package banking.service;

import banking.domain.Account;
import banking.domain.Transaction;
import banking.exception.AccountNotFoundException;
import banking.exception.InvalidTransactionException;
import banking.exception.InsufficientBalanceException;
import java.util.ArrayList;

public class BankService {

    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public BankService() {
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("Account added successfully.");
    }

    public Account findAccountById(String accountId) {
        for (Account acc : accounts) {
            if (acc.getAccountId().equals(accountId)) {
                return acc;
            }
        }
        return null;
    }

    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }

        for (Account acc : accounts) {
            System.out.println(acc);
        }
    }
 public void transfer(String fromId, String toId, double amount)
        throws AccountNotFoundException, InvalidTransactionException, InsufficientBalanceException {

    Account fromAccount = findAccountById(fromId);
    Account toAccount = findAccountById(toId);

    if (fromAccount == null || toAccount == null) {
        throw new AccountNotFoundException("One or both accounts not found");
    }

    if (amount <= 0) {
        throw new InvalidTransactionException("Transfer amount must be greater than zero");
    }

    // Withdraw from source account
    fromAccount.withdraw(amount);

    // Deposit into destination account
    toAccount.deposit(amount);

    // Record transaction
    Transaction transaction = new Transaction(
            "T" + (transactions.size() + 1),
            "TRANSFER",
            amount,
            fromId,
            toId
    );

    transactions.add(transaction);
}
}