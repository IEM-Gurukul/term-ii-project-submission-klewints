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
}