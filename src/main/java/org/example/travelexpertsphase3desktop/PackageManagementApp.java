package org.example.travelexpertsphase3desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PackageManagementApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PackageManagementApp.class.getResource("/org/example/travelexpertsphase3desktop/packages-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // Adjusted scene size
        stage.setTitle("Package Management Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
