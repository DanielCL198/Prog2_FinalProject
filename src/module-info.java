module Prog2.FinalProject {
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;

    opens JavaFx.Bank to javafx.fxml;
    opens Accounts to com.google.gson;
    opens Clients to com.google.gson;
    exports JavaFx.Bank;
    exports Accounts;
    exports Clients;
}
