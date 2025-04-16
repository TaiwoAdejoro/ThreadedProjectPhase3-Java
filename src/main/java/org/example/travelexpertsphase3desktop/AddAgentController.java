/**
 * Sample Skeleton for 'Add-agent-view.fxml' Controller Class
 */

package org.example.travelexpertsphase3desktop;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.Utils.Validator;
import org.example.travelexpertsphase3desktop.model.Agent;
import org.example.travelexpertsphase3desktop.model.AgentDB;


public class AddAgentController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancelAgent"
    private Button btnCancelAgent; // Value injected by FXMLLoader

    @FXML // fx:id="btnDeleteAgent"
    private Button btnDeleteAgent; // Value injected by FXMLLoader

    @FXML // fx:id="btnSaveAgent"
    private Button btnSaveAgent; // Value injected by FXMLLoader

    @FXML // fx:id="lblEditAgentMode"
    private Label lblEditAgentMode; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtAgencyId"
    private TextField tfAgtAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtBusPhone"
    private TextField tfAgtBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtEmail"
    private TextField tfAgtEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtFirstname"
    private TextField tfAgtFirstname; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtId"
    private TextField tfAgtId; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtInitial"
    private TextField tfAgtInitial; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtLastname"
    private TextField tfAgtLastname; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtPosition"
    private TextField tfAgtPosition; // Value injected by FXMLLoader

   private String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancelAgent != null : "fx:id=\"btnCancelAgent\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert btnDeleteAgent != null : "fx:id=\"btnDeleteAgent\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert btnSaveAgent != null : "fx:id=\"btnSaveAgent\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert lblEditAgentMode != null : "fx:id=\"lblEditAgentMode\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert tfAgtAgencyId != null : "fx:id=\"tfAgtAgencyId\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert tfAgtBusPhone != null : "fx:id=\"tfAgtBusPhone\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert tfAgtEmail != null : "fx:id=\"tfAgtEmail\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert tfAgtFirstname != null : "fx:id=\"tfAgtFirstname\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert tfAgtId != null : "fx:id=\"tfAgtId\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert tfAgtInitial != null : "fx:id=\"tfAgtInitial\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert tfAgtLastname != null : "fx:id=\"tfAgtLastname\" was not injected: check your FXML file 'Add-agent-view.fxml'.";
        assert tfAgtPosition != null : "fx:id=\"tfAgtPosition\" was not injected: check your FXML file 'Add-agent-view.fxml'.";

        btnCancelAgent.setOnMouseClicked(event -> {
            closeWindow(event);
        });

        btnDeleteAgent.setOnMouseClicked(event -> {
                deleteAgent();
            //close window when done deleting
                closeWindow(event);
        });
    }

    private void deleteAgent() {
        int numRows = 0;
        int agentId = Integer.parseInt(tfAgtId.getText());
        try {
            numRows = AgentDB.deleteAgent(agentId);
        } catch (SQLException e) {
            alertUser(Alert.AlertType.ERROR, "An error occurred while deleting the agent.\n" + e.getMessage());
        }

        //ensure agent was deleted
        if(numRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "The agent has been deleted.");
        }else{
            alertUser(Alert.AlertType.ERROR, "delete operaion failed.");
        }
    }

    @FXML
    void saveAgent(MouseEvent event) {
        // Validate input before saving**
        if (!validateAgentForm()) {
            return;
        }

    //make new agent
        int numRows = 0;
        Agent agent = getAgentFromForm();
        //add agent to db
        if (mode.equalsIgnoreCase("add")) {  //inserting new object
            try {
                numRows = AgentDB.addAgent(agent);
            } catch (SQLException e) {
                alertUser(Alert.AlertType.ERROR, "An error occurred while saving a product.\n" + e.getMessage());

            }
        } else {//updating db
            try {
                numRows = AgentDB.updateAgent(agent.getId(), agent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //give users feedback of insertion
        if(numRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "Agent added successfully.");
        }else{
            alertUser(Alert.AlertType.ERROR, mode +"Operation failed.");
        }

        //closing the window
        closeWindow(event);
    }

    private void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.close();
    }


    public void setMode(String mode) {
        this.mode = mode;

        //change header to the current mode
        lblEditAgentMode.setText(mode + " Agent Mode");

        // disable delete button when creating agent
        btnDeleteAgent.setDisable(mode.equalsIgnoreCase("add"));
    }

    private void alertUser(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setHeaderText("Header Text goes here");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Agent getAgentFromForm() {
        int agentId = 0;

        if (!tfAgtId.getText().isEmpty()) {
            agentId = Integer.parseInt(tfAgtId.getText());
        }
        Agent agent = new Agent(
                agentId,
                tfAgtFirstname.getText(),
                tfAgtInitial.getText(),
                tfAgtLastname.getText(),
                tfAgtBusPhone.getText(),
                tfAgtEmail.getText(),
                tfAgtPosition.getText(),
               Integer.parseInt(tfAgtAgencyId.getText())
        );
        return agent;
    }
    // set content of agent form when updating
    public void setAgentForm(Agent agent) {
        tfAgtId.setText(String.valueOf(agent.getId()));
        tfAgtFirstname.setText(agent.getFirstname());
        tfAgtInitial.setText(agent.getMiddleInitial());
        tfAgtLastname.setText(agent.getLastname());
        tfAgtBusPhone.setText(agent.getBusPhone());
        tfAgtEmail.setText(agent.getEmail());
        tfAgtPosition.setText(agent.getPosition());
        tfAgtAgencyId.setText(String.valueOf(agent.getAgencyId()));
    }

    private boolean validateAgentForm() {
        //  Validate First Name
        if (!Validator.isAlpha(tfAgtFirstname.getText())) {
            alertUser(Alert.AlertType.ERROR, "First name must only contain letters.");
            return false;
        }

        //  Validate Last Name
        if (!Validator.isAlpha(tfAgtLastname.getText())) {
            alertUser(Alert.AlertType.ERROR, " Last name must only contain letters.");
            return false;
        }

        // Validate Position (optional, but must be valid if entered)
        if (!tfAgtPosition.getText().isEmpty() && !Validator.isAlpha(tfAgtPosition.getText())) {
            alertUser(Alert.AlertType.ERROR, " Position must only contain letters.");
            return false;
        }

        //  Validate Email
        if (!Validator.isValidEmail(tfAgtEmail.getText())) {
            alertUser(Alert.AlertType.ERROR, " Invalid email format.");
            return false;
        }

        //  Validate Phone Number
        if (!Validator.isPhoneNumber(tfAgtBusPhone.getText())) {
            alertUser(Alert.AlertType.ERROR, " Invalid phone number format.");
            return false;
        }

        //  Validate Agency ID (must exist in DB)
        if (!Validator.isValidInteger(tfAgtAgencyId.getText())) {
            alertUser(Alert.AlertType.ERROR, " Agency ID must be a valid number.");
            return false;
        }

        int agencyId = Integer.parseInt(tfAgtAgencyId.getText());
        if (!AgentDB.isAgencyIdExists(agencyId)) { //  Calling the DB from the Controller
            alertUser(Alert.AlertType.ERROR, " The specified Agency ID does not exist. Please enter a valid Agency ID.");
            return false;
        }

        return true;
    }


}
