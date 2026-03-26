package banking.domain;
import banking.exception.InsufficientBalanceException;
import banking.exception.InvalidTransactionException;

public abstract class Account {
    protected String accountId;
    protected String accountHolderName;
    protected double balance;

    public Account(String accountId, String accountHolderName, double balance) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid deposit amount");
        }
    }


  public void withdraw(double amount) throws InsufficientBalanceException, InvalidTransactionException {
    if (amount <= 0) {
        throw new InvalidTransactionException("Invalid withdrawal amount");
    } else if (amount > balance) {
        throw new InsufficientBalanceException("Insufficient balance");
    } else {
        balance -= amount;
    }
 }

    @Override
    public String toString() {
        return "Account ID: " + accountId +
               ", Name: " + accountHolderName +
               ", Balance: " + balance;
    }
}