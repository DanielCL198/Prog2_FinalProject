package javafx.bank;

import Accounts.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class BankScene2Controller {

    @FXML
    private ListView<Account> accountListView;

    @FXML
    private ListView<String> transactionListView;

    @FXML
    private Label ClientName;

    @FXML
    private Label ClientType;

    private ObservableList<Account> accounts = FXCollections.observableArrayList();

    private ObservableList<String> transactions = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> accountTypeChoice;

    private Client currentClient;

    private ArrayList<Client> clients = new ArrayList<>();

    private final DataManager dataManager = new DataManager();

    @FXML
    public void initialize() {
        accountListView.setItems(accounts);
        transactionListView.setItems(transactions);
        accountTypeChoice.getItems().setAll("Chequeing", "Investment", "Savings");
        accountTypeChoice.setValue("Chequeing");
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public void setCurrentClient(Client client) {
        if (client == null) {
            transactions.add("Cannot load client: no client selected.");
            return;
        }

        currentClient = client;
        ClientName.setText("Client: " + currentClient.getName());
        ClientType.setText("Type: " + getClientTypeName(currentClient));
        accounts.setAll(currentClient.getAccounts());
    }

    private String getClientTypeName(Client client) {
        return client.getClass().getSimpleName().replace("Client", "");
    }

    @FXML
    public void createAccount() {
        if (currentClient == null) {
            transactions.add("Cannot create account: no client selected.");
            return;
        }

        Account newAccount; // initialize account

        // gets value of account type to be created
        if (Objects.equals(accountTypeChoice.getValue(), "Chequeing")) {
            newAccount = new ChequeingAccount("ChequeingACC" + (accounts.size() + 1), 1000, currentClient);
        } else if (Objects.equals(accountTypeChoice.getValue(), "Savings")) {
            newAccount = new SavingAccount("SavingsACC" + (accounts.size() + 1), 1000, currentClient);
        } else {
            newAccount = new InvestmentAccount("InvestmentACC" + (accounts.size() + 1), 1000, currentClient);
        }
        try {
            currentClient.addAccount(newAccount); // exception thrown in here
            accounts.add(newAccount); // added account to list then saved immediately to data manager
            transactions.add("Created account: " + newAccount.getAccountNumber());
            dataManager.saveData(clients);
        } catch (MissingChequeingAccountException e) {
            transactions.add("Cannot create account: " + e.getMessage());
        }
    }

    @FXML
    public void withdrawButton() {
        Account selected = accountListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.withdraw(100);
                accountListView.refresh();
                transactions.add("Withdrew $100 from " + selected.getAccountNumber());
                dataManager.saveData(clients);
            }catch(InvestmentLockException e){
                transactions.add("Cannot withdraw: " + e.getMessage());
                transactions.add("Exception handled, transaction failed. Must wait at least 1 year to withdraw.");
            }
        }
    }

    @FXML
    public void depositButton() {
        Account selected = accountListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.deposit(100);
            accountListView.refresh();
            transactions.add("Deposited $100 into " + selected.getAccountNumber());
            dataManager.saveData(clients);
        }
    }


    // creates transaction scene without replacing current scene
    @FXML
    public void transactionButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/javafx/Transaction.fxml")));
        Parent root = loader.load();

        TransactionController controller = loader.getController();
        controller.setClient(currentClient);
        controller.setMainController(this);

        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("Transaction");
        newStage.show();
    }

    public void addTransactionMessage(String message) {
        transactions.add(message);
    }
}