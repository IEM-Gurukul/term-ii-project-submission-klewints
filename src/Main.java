import banking.domain.SavingsAccount;
import banking.service.BankService;

public class Main {
    public static void main(String[] args) {

        BankService bankService = new BankService();

        // Create accounts
        SavingsAccount acc1 = new SavingsAccount("A101", "John Doe", 2000);
        SavingsAccount acc2 = new SavingsAccount("A102", "Jane Smith", 1500);

        // Add accounts to service
        bankService.addAccount(acc1);
        bankService.addAccount(acc2);

        // Perform operations
        acc1.deposit(500);
        acc1.withdraw(1000);

        acc2.withdraw(1200); // should respect minimum balance

        // Display all accounts
        System.out.println("\n--- Account Details ---");
        bankService.displayAllAccounts();
    }
}