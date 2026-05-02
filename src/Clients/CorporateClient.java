package Clients;

import Accounts.Account;

import java.util.ArrayList;

public class CorporateClient extends PremiumClient implements Maintainable {
    public CorporateClient(String clientID, String name, ArrayList<Account> accounts, String password) {
        super(clientID, name, accounts, password);
    }

    public void applyMonthlyFee() {
        for(Account a : accounts){
            a.setBalance(a.getBalance() - 10);
        }
    }
}
