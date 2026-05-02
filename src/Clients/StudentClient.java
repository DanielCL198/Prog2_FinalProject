package Clients;

import Accounts.Account;

import java.util.ArrayList;

public class StudentClient extends StandardClient {
    public StudentClient(String clientID, String name, ArrayList<Account> accounts, String password) {
        super(clientID, name, accounts, password);
    }
}
