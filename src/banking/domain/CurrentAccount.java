package banking.domain;

public class CurrentAccount extends Account {

    private double overdraftLimit;

    public CurrentAccount(String accountId, String accountHolderName, double balance, double overdraftLimit) {
        super(accountId, accountHolderName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount");
        } else if ((balance + overdraftLimit) < amount) {
            System.out.println("Overdraft limit exceeded");
        } else {
            balance -= amount;
        }
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}