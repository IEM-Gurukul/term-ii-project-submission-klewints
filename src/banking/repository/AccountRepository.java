package banking.repository;

import banking.domain.Account;
import java.util.ArrayList;

public class AccountRepository {

    private ArrayList<Account> accounts;

    public AccountRepository(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    public Account findById(String id) {
        for (Account acc : accounts) {
            if (acc.getAccountId().equals(id)) {
                return acc;
            }
        }
        return null;
    }

    public ArrayList<Account> getAllAccounts() {
        return accounts;
    }
}