package Accounts;

import java.time.LocalDate;
import java.time.Period;

public class InvestmentAccount extends ChequeingAccount implements InterestBearing {
    public double interestRate = 0.05;
    LocalDate endDate = LocalDate.now();
    LocalDate startDate = LocalDate.now().minusYears(1);
    public InvestmentAccount(String accountNumber, double balance, Client owner) {
        super(accountNumber, balance, owner);
    }

    @Override
    public void applyInterest() {
        balance += balance * interestRate;
    }

    public void chequeingTransaction(double amount){
        try {
            Period period = Period.between(startDate, endDate);
            if (period.getYears() < 1) {
                throw new InvestmentLockException("Transaction Lock exception");
            }

            balance -= amount;
        }catch(InvestmentLockException e){
            System.out.println(e);
            System.out.println("Exception handled, transaction failed, Must wait at least 1 year to withdraw");

        }
    }

    @Override
    public void withdraw(double amount){
        balance -= amount;
    }
}
