package banking.service;

import banking.domain.Account;
import banking.domain.Transaction;
import banking.persistence.FileHandler;
import banking.exception.AccountNotFoundException;
import banking.exception.InvalidTransactionException;
import banking.exception.InsufficientBalanceException;
import java.util.ArrayList;

public class BankService {

    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public BankService() {
        accounts = FileHandler.loadAccounts();
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
     if (fromAccount == null) {
        throw new AccountNotFoundException("Source account not found");
    }

     Account toAccount = findAccountById(toId);
     if (toAccount == null) {
        throw new AccountNotFoundException("Destination account not found");
    }

     if (amount <= 0) {
        throw new InvalidTransactionException("Transfer amount must be greater than zero");
    }

    fromAccount.withdraw(amount);
    toAccount.deposit(amount);

    Transaction transaction = new Transaction(
            "T" + (transactions.size() + 1),
            "TRANSFER",
            amount,
            fromId,
            toId
    );

    transactions.add(transaction);
}
public void displayTransactions() {
    if (transactions.isEmpty()) {
        System.out.println("No transactions found.");
        return;
    }

    for (Transaction t : transactions) {
        System.out.println(t);
    }
}
public void saveData() {
    FileHandler.saveAccounts(accounts);
}
}