package Accounts;

public class InvestmentAccount extends ChequeingAccount implements InterestBearing {
    public double interestRate = 0.05;

    public InvestmentAccount(String accountNumber, double balance, Client owner) {
        super(accountNumber, balance, owner);
    }

    @Override
    public void applyInterest() {
        balance += balance * interestRate;
    }

    public void chequeingTransaction(double amount){
        //implement chequeingAccountException
        balance -= amount;

    }

    @Override
    public void withdraw(double amount){
        balance -= amount;
    }
}
