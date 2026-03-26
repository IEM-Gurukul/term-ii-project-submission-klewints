package banking.domain;

import java.time.LocalDateTime;

public class Transaction {

    private String transactionId;
    private String type;
    private double amount;
    private String fromAccountId;
    private String toAccountId;
    private LocalDateTime timestamp;

    public Transaction(String transactionId, String type, double amount, String fromAccountId, String toAccountId) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + transactionId + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", from='" + fromAccountId + '\'' +
                ", to='" + toAccountId + '\'' +
                ", time=" + timestamp +
                '}';
    }
}