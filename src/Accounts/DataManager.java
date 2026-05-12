package Accounts;

import Clients.CorporateClient;
import Clients.IndividualClient;
import Clients.StudentClient;
import Clients.VIPClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataManager {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveData(ArrayList<Client> clients) {
        JsonArray jsonArray = new JsonArray();

        for (Client client : clients) {
            JsonObject object = new JsonObject();

            object.addProperty("type", getClientType(client));
            object.addProperty("clientID", client.getClientID());
            object.addProperty("name", client.getName());
            object.add("accounts", getJsonAccounts(client));
            object.addProperty("password", client.getPassword());

            jsonArray.add(object);
        }

        try (FileWriter writer = new FileWriter("clients.json")) {
            gson.toJson(jsonArray, writer);
            System.out.println("Client data saved.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving data.");
        }
    }

    public ArrayList<Client> loadData() {
        ArrayList<Client> clients = new ArrayList<>();
        File file = new File("clients.json");
        if (!file.exists() || file.length() == 0) {
            saveData(clients);
            return clients;
        }
        try (FileReader reader = new FileReader(file)) {
            JsonElement root = JsonParser.parseReader(reader);
            if (!root.isJsonArray()) {
                return clients;
            }
            JsonArray jsonArray = root.getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject object = element.getAsJsonObject();
                String type = "";
                if( object.has("type")){
                    type = object.get("type").getAsString();
                }
                else{
                    type = "Individual";
                }
                String clientID = object.get("clientID").getAsString();
                String name = object.get("name").getAsString();
                String password = object.get("password").getAsString();
                Client client;
                switch (type) {
                    case "Corporate":
                        client = new CorporateClient(clientID, name, new ArrayList<>(), password);
                        break;
                    case "Student":
                        client = new StudentClient(clientID, name, new ArrayList<>(), password);
                        break;
                    case "VIP":
                        client = new VIPClient(clientID, name, new ArrayList<>(), password);
                        break;
                    default:
                        client = new IndividualClient(clientID, name, new ArrayList<>(), password);
                }
                if (object.has("accounts") && object.get("accounts").isJsonArray()) {
                    loadAccountsForClient(client, object.getAsJsonArray("accounts"));
                }
                clients.add(client);
            }
            System.out.println("Client data loaded.");
        } catch (IOException | JsonSyntaxException | IllegalStateException e) {
            System.out.println("Could not load clients.json. Starting with an empty client list.");
            e.printStackTrace();
        }
        return clients;
    }

    private JsonArray getJsonAccounts(Client client) {
        JsonArray accountsArray = new JsonArray();  // creates array for accounts a client has,
        // then creates an Json object of accounts and its details to add it to the array
        for (Account account : client.getAccounts()) {
            JsonObject accountObject = new JsonObject();
            accountObject.addProperty("accountType", getAccountType(account));
            accountObject.addProperty("accountNumber", account.getAccountNumber());
            accountObject.addProperty("balance", account.getBalance());
            accountsArray.add(accountObject);
        }
        return accountsArray;
    }

    private String getClientType(Client client) {
        if (client instanceof CorporateClient) {
            return "Corporate";
        } else if (client instanceof StudentClient) {
            return "Student";
        } else if (client instanceof VIPClient) {
            return "VIP";
        } else {
            return "Individual";
        }
    }
    private void loadAccountsForClient(Client client, JsonArray accountsArray) {
        for (JsonElement accountArray : accountsArray) { // search through Array of accounts
            JsonObject accountObject = accountArray.getAsJsonObject(); // turn Array into Json object
            String accountType = "";

            accountType = accountObject.get("accountType").getAsString();// gets account type

            String accountNumber = accountObject.get("accountNumber").getAsString();
            double balance = accountObject.get("balance").getAsDouble();
            Account account;

            switch (accountType) { //initializes account to its type
                case "Investment":
                    account = new InvestmentAccount(accountNumber, balance, client);
                    break;
                case "Savings":
                    account = new SavingAccount(accountNumber, balance, client);
                    break;
                default:
                    account = new ChequeingAccount(accountNumber, balance, client);
            }
            client.getAccounts().add(account);
        }
    }
    private String getAccountType(Account account) {
        if (account instanceof InvestmentAccount) {
            return "Investment";
        } else if (account instanceof SavingAccount) {
            return "Savings";
        } else {
            return "Chequeing";
        }
    }
}
