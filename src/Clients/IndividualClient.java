package Clients;

import Accounts.Account;

import java.util.ArrayList;

public class IndividualClient extends StandardClient implements Maintainable {
    public IndividualClient(String clientID, String name, ArrayList<Account> accounts, String password) {
        super(clientID, name, accounts, password);
    }

    @Override
    public void applyMonthlyFee() {
        for(Account a : accounts){
            a.setBalance(a.getBalance() - 10);
        }
    }
}
