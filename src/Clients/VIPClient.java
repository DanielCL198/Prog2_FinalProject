package Clients;

import Accounts.Account;

import java.util.ArrayList;

public class VIPClient extends PremiumClient {
    public VIPClient(String clientID, String name, ArrayList<Account> accounts, String password) {
        super(clientID, name, accounts, password);
    }
}
