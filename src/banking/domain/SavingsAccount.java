package banking.domain;
import banking.exception.InvalidTransactionException;
import banking.exception.InsufficientBalanceException;

public class SavingsAccount extends Account {

    private static final double MIN_BALANCE = 500;

    public SavingsAccount(String accountId, String accountHolderName, double balance) {
        super(accountId, accountHolderName, balance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException, InvalidTransactionException {
    if (amount <= 0) {
        throw new InvalidTransactionException("Invalid withdrawal amount");
    } else if ((balance - amount) < MIN_BALANCE) {
        throw new InsufficientBalanceException("Minimum balance must be maintained");
    } else {
        balance -= amount;
    }
  }
}