package Accounts;

import java.util.ArrayList;

public abstract class Client {
    protected String clientID;
    protected String name;
    protected ArrayList<Account> accounts;

    public Client(String clientID, String name, ArrayList<Account> accounts) {
        this.clientID = clientID;
        this.name = name;
        this.accounts = accounts;
    }

    public String getClientID() {
        return clientID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
      if (account instanceof SavingAccount || account instanceof InvestmentAccount && !hasChequeingAccount()) {
         throw new MissingChequeingAccountException("Client must have a chequeing account to open an investment account or savings account");
      } else {
        accounts.add(account);
      }
    }

    public boolean hasChequeingAccount() {
        for (Account account : accounts) {
            if (account instanceof ChequeingAccount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Accounts.Client = id: " + clientID + ", name: " + name + ", accounts: " + accounts.size();
    }
}
