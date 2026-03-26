package banking.service;

import banking.domain.Account;
import java.util.ArrayList;

public class BankService {

    private ArrayList<Account> accounts;

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
public void transfer(String fromId, String toId, double amount) {
    Account fromAccount = findAccountById(fromId);
    Account toAccount = findAccountById(toId);

    if (fromAccount == null || toAccount == null) {
        System.out.println("One or both accounts not found.");
        return;
    }

    if (amount <= 0) {
        System.out.println("Invalid transfer amount.");
        return;
    }

    double initialBalance = fromAccount.getBalance();

    fromAccount.withdraw(amount);

    // check if withdrawal actually happened
    if (fromAccount.getBalance() < initialBalance) {
        toAccount.deposit(amount);
        System.out.println("Transfer successful.");
    } else {
        System.out.println("Transfer failed.");
    }
}
}