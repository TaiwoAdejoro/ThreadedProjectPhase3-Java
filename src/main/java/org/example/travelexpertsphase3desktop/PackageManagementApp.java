package org.example.travelexpertsphase3desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.dao.PackageDAO;
import org.example.travelexpertsphase3desktop.database.DBConnection;

import java.io.IOException;

public class PackageManagementApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PackageManagementApp.class.getResource("/views/PackageManagement.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // Adjusted scene size
        stage.setTitle("Package Management Application");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        System.out.println("Closing database connection...");
        DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch();
    }
}

