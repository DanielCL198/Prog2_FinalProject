import java.util.ArrayList;

public abstract class Client {
    protected String clientID;
    protected String name;
    protected ArrayList<Account> accounts;

    public Client(String clientID, String name) {
        this.clientID = clientID;
        this.name = name;
        this.accounts = new ArrayList<>();
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
        if (account != null) {
            accounts.add(account);
        }
    }

    @Override
    public String toString() {
        return "Client{id='" + clientID + "', name='" + name + "', accounts=" + accounts.size() + "}";
    }
}
