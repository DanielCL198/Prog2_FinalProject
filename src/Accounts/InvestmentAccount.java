package Accounts;

import java.time.LocalDate;
import java.time.Period;

public class InvestmentAccount extends ChequeingAccount implements InterestBearing {
    private static double INTEREST_RATE = 0.05;
    private String startDate = LocalDate.now().toString();

    public InvestmentAccount(String accountNumber, double balance, Client owner) {
        super(accountNumber, balance, owner);
    }

    @Override
    public void applyInterest() {
        balance += balance * INTEREST_RATE;
    }

    private boolean isLocked() {
        LocalDate openedDate = LocalDate.parse(startDate); // gets the date of the account opening
        Period period = Period.between(openedDate, LocalDate.now()); // gets the date difference between now and when it opened
        return(period.getYears() < 1); //boolean
    }

    @Override
    public void withdraw(double amount) {
        if (isLocked()) {
            throw new InvestmentLockException("Investment account is locked for 1 year.");
        }
        balance -= amount;
    }
}
