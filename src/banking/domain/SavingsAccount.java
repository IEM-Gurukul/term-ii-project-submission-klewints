package banking.domain;

public class SavingsAccount extends Account {

    private static final double MIN_BALANCE = 500;

    public SavingsAccount(String accountId, String accountHolderName, double balance) {
        super(accountId, accountHolderName, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount");
        } else if ((balance - amount) < MIN_BALANCE) {
            System.out.println("Cannot withdraw. Minimum balance of " + MIN_BALANCE + " must be maintained.");
        } else {
            balance -= amount;
        }
    }
}