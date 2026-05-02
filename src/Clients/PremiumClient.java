package Clients;

import Accounts.Account;
import Accounts.Client;

import java.util.ArrayList;

public class PremiumClient extends Client {
    public PremiumClient(String clientID, String name, ArrayList<Account> accounts, String password) {
        super(clientID, name, accounts, password);
    }
}
