package banking.persistence;

import banking.domain.Account;
import banking.domain.SavingsAccount;
import banking.domain.CurrentAccount;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    private static final String FILE_NAME = "accounts.txt";

    // SAVE accounts
    public static void saveAccounts(ArrayList<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Account acc : accounts) {
                String type = acc instanceof SavingsAccount ? "SAVINGS" : "CURRENT";

                writer.write(type + "," +
                        acc.getAccountId() + "," +
                        acc.getAccountHolderName() + "," +
                        acc.getBalance());

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving accounts");
        }
    }

    // LOAD accounts
    public static ArrayList<Account> loadAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String type = parts[0];
                String id = parts[1];
                String name = parts[2];
                double balance = Double.parseDouble(parts[3]);

                Account acc;

                if (type.equals("SAVINGS")) {
                    acc = new SavingsAccount(name, balance, 1234); // dummy PIN
                } else {
                    acc = new CurrentAccount(name, balance, 1234, 1000);
                }
               acc.setAccountId(id);
                accounts.add(acc);
            }

        } catch (IOException e) {
            System.out.println("No previous data found");
        }

        return accounts;
    }
}