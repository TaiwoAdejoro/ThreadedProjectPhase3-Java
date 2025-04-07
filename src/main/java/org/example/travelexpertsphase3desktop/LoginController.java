/**
 * Sample Skeleton for 'login-view.fxml' Controller Class
 */
package org.example.travelexpertsphase3desktop;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.model.Login;
import org.example.travelexpertsphase3desktop.model.LoginDB;

import java.io.IOException;

public class LoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnLogin"
    private Button btnLogin; // Value injected by FXMLLoader

    @FXML // fx:id="tfPassword"
    private TextField tfPassword; // Value injected by FXMLLoader

    @FXML // fx:id="tfUsername"
    private TextField tfUsername; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'login-view.fxml'.";
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'login-view.fxml'.";
        assert tfPassword != null : "fx:id=\"tfPassword\" was not injected: check your FXML file 'login-view.fxml'.";
        assert tfUsername != null : "fx:id=\"tfUsername\" was not injected: check your FXML file 'login-view.fxml'.";

        System.out.println("Login Screen Loaded! ✅");
        btnLogin.setOnAction(e -> loginUser());
        btnCancel.setOnAction(e -> cancelLogin());
    }
    private void loginUser() {
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Username and Password are required!");
            return;
        }

        Login user = LoginDB.authenticateUser(username, password);

        if (user != null) {

            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + user.getEmail() + "!");
            openDashboard(user);
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid Username or Password!");
        }
    }

    private void openDashboard(Login user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard-view.fxml"));
            Parent root = loader.load();

            DashBoardController dashBoardController = loader.getController();
            int loggedInAgentId = user.getUserId();
            boolean isManager = user.isManager(); // ✅ Corrected method call

            System.out.println("DEBUG: Assigned loggedInAgentId in LoginController = " + loggedInAgentId);

            dashBoardController.setLoggedInUser(loggedInAgentId, isManager);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();

            Stage loginStage = (Stage) btnLogin.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void cancelLogin() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
