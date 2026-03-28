package banking.service;

import banking.domain.Account;
import banking.domain.Transaction;
import banking.persistence.FileHandler;
import banking.repository.AccountRepository;
import banking.exception.AccountNotFoundException;
import banking.exception.InvalidTransactionException;
import banking.exception.InsufficientBalanceException;
import java.util.ArrayList;

public class BankService {

    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private AccountRepository repository;

    public BankService() {
        accounts = FileHandler.loadAccounts();
        repository = new AccountRepository(accounts);
    }

    public void addAccount(Account acc) {
    repository.addAccount(acc);
}
public ArrayList<Account> getAllAccounts() {
    return repository.getAllAccounts();
}
    public Account findAccountById(String id) {
    return repository.findById(id);
}

    public void displayAllAccounts() {
    for (Account acc : repository.getAllAccounts()) {
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
public ArrayList<Transaction> getTransactions() {
    return transactions;
}
public void saveData() {
    FileHandler.saveAccounts(accounts);
}
}