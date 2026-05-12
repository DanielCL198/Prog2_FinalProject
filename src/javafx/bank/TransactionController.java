package javafx.bank;

import Accounts.Account;
import Accounts.Client;
import Accounts.DataManager;
import Accounts.InsufficientFundsException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransactionController {

    @FXML
    private TextField sendingAccountField;

    @FXML
    private TextField amountField;

    @FXML
    private TextField receivingAccountField;

    private Client currentClient;
    private BankScene2Controller mainController;

    public void setClient(Client client) {
        this.currentClient = client;
    }

    public void setMainController(BankScene2Controller mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void done() {
        if (currentClient == null) {
            mainController.addTransactionMessage("Transction failed: no client selected.");
            return;
        }
        String sendingAccountNumber = sendingAccountField.getText();
        String receivingAccountNumber = receivingAccountField.getText();
        String amountText = amountField.getText();
        if (sendingAccountNumber.isEmpty() || receivingAccountNumber.isEmpty() || amountText.isEmpty()) {
            mainController.addTransactionMessage("Transaction failed:please fill in all fields.");
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            mainController.addTransactionMessage("Transaction failed: amount must be a number.");
            return;
        }
        if (amount <= 0) {
            mainController.addTransactionMessage("Transaction failed: amount must be greater than 0.");
            return;
        }
        Account sendingAccount = findAccountByNumber(sendingAccountNumber); // gets account fullname using helper method
        Account receivingAccount = findAccountByNumber(receivingAccountNumber); // gets receiving account fullname using helper method
        if (sendingAccount == null) {
            mainController.addTransactionMessage("Transaction failed: sending account was not found.");
            return;
        }
        if (receivingAccount == null) {
            mainController.addTransactionMessage(" Transaction failed: receiving account was not found.");
            return;
        }
        if (sendingAccount == receivingAccount) {
            mainController.addTransactionMessage("Transaction failed: cannot transfer money to the same account.");
            return;
        }
        try { // Once account is found and amounts are withing range, proceed to deposit and withdraw as well as removing Transaction scene
            sendingAccount.withdraw(amount);
            receivingAccount.deposit(amount);
            mainController.refreshAccounts(); // refresh AccountListView in BankScene2Controller
            mainController.addTransactionMessage("Transferred $" + amount + " from " + sendingAccount.getAccountNumber() + " to " + receivingAccount.getAccountNumber());
            DataManager dataManager = new DataManager();
            dataManager.saveData(mainController.getClients()); // get clients from BankScene2Controller and save to data manager
            Stage stage = (Stage) amountField.getScene().getWindow();
            stage.close();
        } catch (InsufficientFundsException e) {
            mainController.addTransactionMessage("Transaction failed: " + e.getMessage());
        }
    }

    private Account findAccountByNumber(String accountNumber) {
        for (Account account : currentClient.getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

}

