package JavaFx.Bank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class BankApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = BankApplication.class.getResource("/JavaFx/Bank_Scene1.fxml");

        if (fxmlLocation == null) {
            throw new IllegalStateException("Could not find FXML file at /JavaFx/Bank_Scene1.fxml");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("JavaFx Bank Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
