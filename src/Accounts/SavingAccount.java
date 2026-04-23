package Accounts;

public class SavingAccount extends ChequeingAccount implements InterestBearing {
    public double interestRate = 0.02;

    public SavingAccount(String accountNumber, double balance, Client owner) {
        super(accountNumber, balance, owner);
    }

    @Override
    public void applyInterest() {
        balance += balance * interestRate;
    }
}
