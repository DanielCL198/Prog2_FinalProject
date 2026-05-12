package Accounts;

public class SavingAccount extends ChequeingAccount implements InterestBearing {
    public static double INTEREST_RATE = 0.02;

    public SavingAccount(String accountNumber, double balance, Client owner) {
        super(accountNumber, balance, owner);
    }

    @Override
    public void applyInterest() {
        balance += balance * INTEREST_RATE;
    }
}
