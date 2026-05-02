package Clients;

import Accounts.Account;
import Accounts.Client;

import java.util.ArrayList;

public class StandardClient extends Client {
    public StandardClient(String clientID, String name, ArrayList<Account> accounts, String password) {
        super(clientID, name, accounts, password);
    }
}
