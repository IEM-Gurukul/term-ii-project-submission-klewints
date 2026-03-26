import banking.domain.SavingsAccount;
import banking.domain.CurrentAccount;
import banking.service.BankService;

public class Main {
    public static void main(String[] args) {

        BankService bankService = new BankService();

        // Create accounts
        SavingsAccount acc1 = new SavingsAccount("A101", "John Doe", 2000);
        SavingsAccount acc2 = new SavingsAccount("A102", "Jane Smith", 1500);
        CurrentAccount acc3 = new CurrentAccount("A103", "Mike Ross", 1000, 500);
        bankService.addAccount(acc3);
         acc3.withdraw(1300); // should use overdraft

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
        bankService.transfer("A101", "A102", 500);
        bankService.displayAllAccounts();
    }
}