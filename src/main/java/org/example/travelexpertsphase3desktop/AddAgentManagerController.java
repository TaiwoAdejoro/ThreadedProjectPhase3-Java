/**
 * Sample Skeleton for 'Add-agentmanager-view.fxml' Controller Class
 */

package org.example.travelexpertsphase3desktop;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.model.AgentManager;
import org.example.travelexpertsphase3desktop.model.AgentManagerDB;

import static org.example.travelexpertsphase3desktop.model.AgentDB.deleteAgent;

public class AddAgentManagerController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancelManager"
    private Button btnCancelManager; // Value injected by FXMLLoader

    @FXML // fx:id="btnDeleteManager"
    private Button btnDeleteManager; // Value injected by FXMLLoader

    @FXML // fx:id="btnSaveManager"
    private Button btnSaveManager; // Value injected by FXMLLoader

    @FXML // fx:id="tfManagerEmail"
    private TextField tfManagerEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfManagerFirstname"
    private TextField tfManagerFirstname; // Value injected by FXMLLoader

    @FXML // fx:id="tfManagerId"
    private TextField tfManagerId; // Value injected by FXMLLoader

    @FXML // fx:id="tfManagerLastname"
    private TextField tfManagerLastname; // Value injected by FXMLLoader

    @FXML // fx:id="tfManagerPassword"
    private TextField tfManagerPassword; // Value injected by FXMLLoader

    @FXML // fx:id="tfManagerPhone"
    private TextField tfManagerPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfManagerRole"
    private TextField tfManagerRole; // Value injected by FXMLLoader
    private String mode;


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancelManager != null : "fx:id=\"btnCancelManager\" was not injected: check your FXML file 'Add-agentmanager-view.fxml'.";
        assert btnDeleteManager != null : "fx:id=\"btnDeleteManager\" was not injected: check your FXML file 'Add-agentmanager-view.fxml'.";
        assert btnSaveManager != null : "fx:id=\"btnSaveManager\" was not injected: check your FXML file 'Add-agentmanager-view.fxml'.";
        assert tfManagerEmail != null : "fx:id=\"tfManagerEmail\" was not injected: check your FXML file 'Add-agentmanager-view.fxml'.";
        assert tfManagerFirstname != null : "fx:id=\"tfManagerFirstname\" was not injected: check your FXML file 'Add-agentmanager-view.fxml'.";
        assert tfManagerId != null : "fx:id=\"tfManagerId\" was not injected: check your FXML file 'Add-agentmanager-view.fxml'.";
        assert tfManagerLastname != null : "fx:id=\"tfManagerLastname\" was not injected: check your FXML file 'Add-agentmanager-view.fxml'.";
        assert tfManagerPassword != null : "fx:id=\"tfManagerPassword\" was not injected: check your FXML file 'Add-agentmanager-view.fxml'.";
        assert tfManagerPhone != null : "fx:id=\"tfManagerPhone\" was not injected: check your FXML file 'Add-agentmanager-view.fxml'.";
        assert tfManagerRole != null : "fx:id=\"tfManagerRole\" was not injected: check your FXML file 'Add-agentmanager-view.fxml'.";

        btnCancelManager.setOnMouseClicked(event -> {
            closeWindow(event);
        });

        btnDeleteManager.setOnMouseClicked(event -> {
            deleteAgentManager();
            //close window when done deleting
            closeWindow(event);
        });
    }

    //delete Agent manager
    private void deleteAgentManager() {
        int numRows = 0;
        int managerId = Integer.parseInt(tfManagerId.getText());
        try {
            numRows = AgentManagerDB.deleteAgentManager(managerId);
        } catch (SQLException e) {
            alertUser(Alert.AlertType.ERROR, "An error occurred while deleting the agent manager.\n"
                    + e.getMessage());
        }

        //ensure that agent manager was deleted

        if (numRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "The manager has been deleted.");
        }else{
            alertUser(Alert.AlertType.ERROR, "delete operation failed");
        }
    }

    @FXML
    void saveAgentManager(MouseEvent event) {

        AgentManager agentManager = getAgentManagerForm();
        int numRows = 0; // Declare inside the try block

        try {
            if (mode.equalsIgnoreCase("add")) { // Inserting new agent manager

                numRows = AgentManagerDB.addAgentManager(agentManager);
            } else { // Updating existing record

                numRows = AgentManagerDB.updateAgentManager(agentManager.getManagerId(), agentManager);
            }
        } catch (SQLException e) {
            alertUser(Alert.AlertType.ERROR, " Error while saving agent manager.\n" + e.getMessage());
            e.printStackTrace();
            return; // Exit if error occurs
        }

        // Provide feedback
        if (numRows > 0) {
            alertUser(Alert.AlertType.CONFIRMATION, " Agent Manager saved successfully!");
        } else {
            alertUser(Alert.AlertType.ERROR,  mode + " operation failed. No changes made.");
        }
        // Close window
        closeWindow(event);
    }
    private void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.close();
    }

    public void setMode(String mode) {
        this.mode = mode;
        //disable delete button when creating AgentManager
        btnDeleteManager.setDisable(mode.equalsIgnoreCase("add"));
    }
    private void alertUser(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setContentText(message);
        alert.showAndWait();
    }

    private AgentManager getAgentManagerForm() {
        int managerId = 0;
        if (!tfManagerId.getText().isEmpty() && !mode.equalsIgnoreCase("add"))  {
            managerId = Integer.parseInt(tfManagerId.getText());
        }

        return new AgentManager(
                managerId,
                tfManagerFirstname.getText(),
                tfManagerLastname.getText(),
                tfManagerEmail.getText(),
                tfManagerPhone.getText(),
                tfManagerPassword.getText(),
                tfManagerRole.getText()
        );
    }

    //set content of agent manager form when updating
    public void setAgentManagerForm(AgentManager agentmanager) {
        tfManagerId.setText(String.valueOf(agentmanager.getManagerId()));
        tfManagerFirstname.setText(agentmanager.getFirstName());
        tfManagerLastname.setText(agentmanager.getLastName());
        tfManagerEmail.setText(agentmanager.getEmail());
        tfManagerPhone.setText(agentmanager.getPhone());
        tfManagerPassword.setText(agentmanager.getPassword());
        tfManagerRole.setText(agentmanager.getRole());
    }
}
