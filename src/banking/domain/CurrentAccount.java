package banking.domain;

public class CurrentAccount extends Account {

    private double overdraftLimit;
    public CurrentAccount(String accountHolderName, double balance, int pin, double overdraftLimit) {
        super(accountHolderName, balance, pin);
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