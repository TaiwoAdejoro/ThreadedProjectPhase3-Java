/**
 * Sample Skeleton for 'AgentManager-view.fxml' Controller Class
 */

package org.example.travelexpertsphase3desktop;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.model.Agent;
import org.example.travelexpertsphase3desktop.model.AgentManager;
import org.example.travelexpertsphase3desktop.model.AgentManagerDB;

public class AgentManagerController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddManager"
    private Button btnAddManager; // Value injected by FXMLLoader

    @FXML // fx:id="btnManageAgencies"
    private Button btnManageAgencies; // Value injected by FXMLLoader

    @FXML // fx:id="btnManageAgents"
    private Button btnManageAgents; // Value injected by FXMLLoader

    @FXML // fx:id="btnManagerHome"
    private Button btnManagerHome; // Value injected by FXMLLoader

    @FXML // fx:id="btnManagerSearch"
    private Button btnManagerSearch; // Value injected by FXMLLoader

    @FXML // fx:id="colManagerEmail"
    private TableColumn<AgentManager, String> colManagerEmail; // Value injected by FXMLLoader

    @FXML // fx:id="colManagerFirstname"
    private TableColumn<AgentManager, String> colManagerFirstname; // Value injected by FXMLLoader

    @FXML // fx:id="colManagerId"
    private TableColumn<AgentManager, Integer> colManagerId; // Value injected by FXMLLoader

    @FXML // fx:id="colManagerLastname"
    private TableColumn<AgentManager, String> colManagerLastname; // Value injected by FXMLLoader

    @FXML // fx:id="colManagerPassword"
    private TableColumn<AgentManager, String> colManagerPassword; // Value injected by FXMLLoader

    @FXML // fx:id="colManagerPhone"
    private TableColumn<AgentManager, String> colManagerPhone; // Value injected by FXMLLoader

    @FXML // fx:id="colManagerRole"
    private TableColumn<AgentManager, String> colManagerRole; // Value injected by FXMLLoader

    @FXML // fx:id="tfManagerSearch"
    private TextField tfManagerSearch; // Value injected by FXMLLoader

    @FXML // fx:id="tvAgentManager"
    private TableView<AgentManager> tvAgentManager; // Value injected by FXMLLoader

    //global list of agents
    private ObservableList<AgentManager> data = FXCollections.observableArrayList();

    String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAddManager != null : "fx:id=\"btnAddManager\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert btnManageAgencies != null : "fx:id=\"btnManageAgencies\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert btnManageAgents != null : "fx:id=\"btnManageAgents\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert btnManagerHome != null : "fx:id=\"btnManagerHome\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert btnManagerSearch != null : "fx:id=\"btnManagerSearch\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert colManagerEmail != null : "fx:id=\"colManagerEmail\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert colManagerFirstname != null : "fx:id=\"colManagerFirstname\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert colManagerId != null : "fx:id=\"colManagerId\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert colManagerLastname != null : "fx:id=\"colManagerLastname\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert colManagerPassword != null : "fx:id=\"colManagerPassword\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert colManagerPhone != null : "fx:id=\"colManagerPhone\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert colManagerRole != null : "fx:id=\"colManagerRole\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert tfManagerSearch != null : "fx:id=\"tfManagerSearch\" was not injected: check your FXML file 'AgentManager-view.fxml'.";
        assert tvAgentManager != null : "fx:id=\"tvAgentManager\" was not injected: check your FXML file 'AgentManager-view.fxml'.";

        //set up table columns
        setupAgentManagerTable();
        displayAgentManager();

        btnAddManager.setOnAction(event -> {
            mode = "Add";
            openAgentManagerWindow(null, mode);// null because no agentmanager exist when add new agent manager
        });

        tvAgentManager.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AgentManager>() {
            @Override
            public void changed(ObservableValue<? extends AgentManager> observableValue, AgentManager oldValue, AgentManager newValue) {
                //get index of the selected agent and be sure it is not a deselection.
                int index = tvAgentManager.getSelectionModel().getSelectedIndex();
                if(tvAgentManager.getSelectionModel().isSelected(index)) {
                    Platform.runLater(() -> {
                        mode = "Edit";
                        openAgentManagerWindow(newValue, mode);
                    });

                }
            }
        });
    }

    private void openAgentManagerWindow(AgentManager agentmanager, String mode) {
        FXMLLoader fxmlLoader = new FXMLLoader(DashBoardManagement.class.getResource("Add-agentmanager-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddAgentManagerController controller = fxmlLoader.getController();
        controller.setMode(mode);

        // if editing an existing agent, pre-fill add agentmanager form with details of the agentmanager being edited.
        if(mode.equalsIgnoreCase("Edit")) {
            controller.setAgentManagerForm(agentmanager);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Agent Manager Addition");
        stage.setScene(scene);
        stage.showAndWait();
        displayAgentManager();
    }

    private void setupAgentManagerTable() {
        colManagerId.setCellValueFactory(new PropertyValueFactory<AgentManager, Integer>("managerId"));
        colManagerFirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colManagerLastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colManagerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colManagerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colManagerPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colManagerRole.setCellValueFactory(new PropertyValueFactory<>("role"));

    }

    // display AgentManager
    public void displayAgentManager() {
        //clearing list for new data
        data.clear();
        try {
            //get agentmanagers from DB

            data= AgentManagerDB.getAgentManagers();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load agent table", e);
        }
        //populate table view
        tvAgentManager.setItems(data);
    }

}
