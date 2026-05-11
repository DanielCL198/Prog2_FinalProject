module Prog2_FinalProject {
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;

    opens javafx.bank to javafx.fxml;
    opens Accounts to com.google.gson;
    opens Clients to com.google.gson;

    exports javafx.bank;
    exports Accounts;
    exports Clients;
}