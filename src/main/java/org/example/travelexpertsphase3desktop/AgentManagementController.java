/**
 * Sample Skeleton for 'Agent-view.fxml' Controller Class
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.model.Agent;
import org.example.travelexpertsphase3desktop.model.AgentDB;

public class AgentManagementController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddAgent"
    private Button btnAddAgent; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtAgencyId"
    private TableColumn<Agent, Integer> colAgtAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtBusPhone"
    private TableColumn<Agent, String> colAgtBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtEmail"
    private TableColumn<Agent, String> colAgtEmail; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtFirstname"
    private TableColumn<Agent, String> colAgtFirstname; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtId"
    private TableColumn<Agent, Integer> colAgtId; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtInitial"
    private TableColumn<Agent, String> colAgtInitial; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtLastname"
    private TableColumn<Agent, String> colAgtLastname; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtPosition"
    private TableColumn<Agent, String> colAgtPosition; // Value injected by FXMLLoader

    @FXML // fx:id="tvAgents"
    private TableView<Agent> tvAgents; // Value injected by FXMLLoader

    //global list of agents
    private ObservableList<Agent> data = FXCollections.observableArrayList();

    String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAddAgent != null : "fx:id=\"btnAddAgent\" was not injected: check your FXML file 'Agent-view.fxml'.";
        assert colAgtAgencyId != null : "fx:id=\"colAgtAgencyId\" was not injected: check your FXML file 'Agent-view.fxml'.";
        assert colAgtBusPhone != null : "fx:id=\"colAgtBusPhone\" was not injected: check your FXML file 'Agent-view.fxml'.";
        assert colAgtEmail != null : "fx:id=\"colAgtEmail\" was not injected: check your FXML file 'Agent-view.fxml'.";
        assert colAgtFirstname != null : "fx:id=\"colAgtFirstname\" was not injected: check your FXML file 'Agent-view.fxml'.";
        assert colAgtId != null : "fx:id=\"colAgtId\" was not injected: check your FXML file 'Agent-view.fxml'.";
        assert colAgtInitial != null : "fx:id=\"colAgtInitial\" was not injected: check your FXML file 'Agent-view.fxml'.";
        assert colAgtLastname != null : "fx:id=\"colAgtLastname\" was not injected: check your FXML file 'Agent-view.fxml'.";
        assert colAgtPosition != null : "fx:id=\"colAgtPosition\" was not injected: check your FXML file 'Agent-view.fxml'.";
        assert tvAgents != null : "fx:id=\"tvAgents\" was not injected: check your FXML file 'Agent-view.fxml'.";

        //set up table columns
        setupAgentTable();
        displayAgent();

        btnAddAgent.setOnAction(event -> {
            mode = "Add";
            openAgentWindow(null, mode);// null because no agency exist when add new agency
        });

        tvAgents.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agent>() {
            @Override
            public void changed(ObservableValue<? extends Agent> observableValue, Agent oldValue, Agent newValue) {
                //get index of the selected agent and be sure it is not a deselection.
                int index = tvAgents.getSelectionModel().getSelectedIndex();
                if(tvAgents.getSelectionModel().isSelected(index)) {
                    Platform.runLater(() -> {
                        mode = "Edit";
                        openAgentWindow(newValue, mode);
                    });

                }
            }
        });

    }

    private void openAgentWindow(Agent agent, String mode) {
        FXMLLoader fxmlLoader = new FXMLLoader(DashBoardManagement.class.getResource("Add-agent-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddAgentController controller = fxmlLoader.getController();
        controller.setMode(mode);

        // if editing an existing agent, pre fill add agent form with details of the agent being edited.
        if(mode.equalsIgnoreCase("Edit")) {
            controller.setAgentForm(agent);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Agent Addition");
        stage.setScene(scene);
        stage.showAndWait();
        displayAgent();
    }

    private void setupAgentTable() {
        colAgtId.setCellValueFactory(new PropertyValueFactory<Agent, Integer>("id"));
        colAgtFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colAgtInitial.setCellValueFactory(new PropertyValueFactory<>("middleInitial"));
        colAgtLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        colAgtBusPhone.setCellValueFactory(new PropertyValueFactory<>("busPhone"));
        colAgtEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAgtPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colAgtAgencyId.setCellValueFactory(new PropertyValueFactory<>("agencyId"));

    }

    // display Agents
    public void displayAgent() {
        //clearing list for new data
        data.clear();
        try {
            //get agents from DB

            data= AgentDB.getAgents();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load agent table", e);
        }
        //populate table view
        tvAgents.setItems(data);
    }
}
