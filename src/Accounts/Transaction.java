package Accounts;

public class Transaction {
    public String transactionId;
    public double amount;
    public String type;

    public String toString() {
        return "Accounts.Transaction: " + transactionId + " " + amount + " " + type;
    }
}
