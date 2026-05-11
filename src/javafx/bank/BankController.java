package javafx.bank;

import Accounts.DataManager;
import Accounts.Client;

import java.util.ArrayList;

import Clients.CorporateClient;
import Clients.IndividualClient;
import Clients.StudentClient;
import Clients.VIPClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class BankController {

    public Label createErrorLabel;
    public Label loginErrorLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private final DataManager dataManager = new DataManager();
    private ArrayList<Client> clients = new ArrayList<>();

    @FXML private TextField FirstName_Create;
    @FXML private TextField LastName_Create;
    @FXML private PasswordField Password_create;


    @FXML private TextField FirstName_Login;
    @FXML private TextField LastName_Login;
    @FXML private PasswordField Password_Login;
    @FXML private ChoiceBox<String> clientTypeChoice;

    @FXML
    public void initialize() {
        clients = dataManager.loadData();
        clientTypeChoice.getItems().setAll("Individual", "Corporate", "Student", "VIP");
        clientTypeChoice.setValue("Individual");
    }

    @FXML
    public void handleCreate(ActionEvent event) throws IOException {
        String first = FirstName_Create.getText();
        String last = LastName_Create.getText();
        String password = Password_create.getText();
        String type = clientTypeChoice.getValue();

        if (first.isEmpty() || last.isEmpty() || password.isEmpty()) {
            createErrorLabel.setVisible(true);
            return;
        }
        createErrorLabel.setVisible(false);
        String fullName = first + " " + last;
        String id = first + last;
        Client newClient;
        switch (type) {
            case "Corporate":
                newClient = new CorporateClient(id, fullName, new ArrayList<>(), password);
                break;
            case "VIP":
                newClient = new VIPClient(id, fullName, new ArrayList<>(), password);
                break;
            case "Student":
                newClient = new StudentClient(id, fullName, new ArrayList<>(), password);
                break;
            default:
                newClient = new IndividualClient(id, fullName, new ArrayList<>(), password);
        }

        clients.add(newClient);
        dataManager.saveData(clients);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/JavaFx/Bank_Scene2.fxml")));
        Parent root = loader.load();
        BankScene2Controller controller = loader.getController();
        controller.setCurrentClient(newClient);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String password = Password_Login.getText();
        String fullName = FirstName_Login.getText() + " " + LastName_Login.getText();

        for (Client c : clients) {
            if (c.getName().equals(fullName) && c.getPassword().equals(password)) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/javafx/Bank_Scene2.fxml")));
                Parent root = loader.load();
                BankScene2Controller controller = loader.getController();
                controller.setClients(clients);
                controller.setCurrentClient(c);
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                return;
            }
        }
        loginErrorLabel.setVisible(true);
    }
}
