package Accounts;

public class ChequeingAccount extends Account {

    public ChequeingAccount(String accountNumber, double balance, Client owner) {
        super(accountNumber, balance, owner);
    }

    public void addTransaction(Transaction t){
       t.toString();
       balance -= t.amount;
       System.out.println("Accounts.Transaction added");
    }
}
