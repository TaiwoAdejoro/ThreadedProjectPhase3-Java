/**
 * Sample Skeleton for 'Agency-view.fxml' Controller Class
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
import org.example.travelexpertsphase3desktop.model.Agency;
import org.example.travelexpertsphase3desktop.model.AgencyDB;

public class AgencyManagementController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddAgency"
    private Button btnAddAgency; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyAddress"
    private TableColumn<Agency, String> colAgencyAddress; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyCity"
    private TableColumn<Agency, String> colAgencyCity; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyCountry"
    private TableColumn<Agency, String> colAgencyCountry; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyFax"
    private TableColumn<Agency, String> colAgencyFax; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyId"
    private TableColumn<Agency, Integer> colAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyPhone"
    private TableColumn<Agency, String> colAgencyPhone; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyPostal"
    private TableColumn<Agency, String> colAgencyPostal; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyProvince"
    private TableColumn<Agency, String> colAgencyProvince; // Value injected by FXMLLoader

    @FXML // fx:id="tvAgency"
    private TableView<Agency> tvAgency; // Value injected by FXMLLoader

    //global list of agency
    private ObservableList<Agency> data = FXCollections.observableArrayList();

    String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAddAgency != null : "fx:id=\"btnAddAgency\" was not injected: check your FXML file 'Agency-view.fxml'.";
        assert colAgencyAddress != null : "fx:id=\"colAgencyAddress\" was not injected: check your FXML file 'Agency-view.fxml'.";
        assert colAgencyCity != null : "fx:id=\"colAgencyCity\" was not injected: check your FXML file 'Agency-view.fxml'.";
        assert colAgencyCountry != null : "fx:id=\"colAgencyCountry\" was not injected: check your FXML file 'Agency-view.fxml'.";
        assert colAgencyFax != null : "fx:id=\"colAgencyFax\" was not injected: check your FXML file 'Agency-view.fxml'.";
        assert colAgencyId != null : "fx:id=\"colAgencyId\" was not injected: check your FXML file 'Agency-view.fxml'.";
        assert colAgencyPhone != null : "fx:id=\"colAgencyPhone\" was not injected: check your FXML file 'Agency-view.fxml'.";
        assert colAgencyPostal != null : "fx:id=\"colAgencyPostal\" was not injected: check your FXML file 'Agency-view.fxml'.";
        assert colAgencyProvince != null : "fx:id=\"colAgencyProvince\" was not injected: check your FXML file 'Agency-view.fxml'.";
        assert tvAgency != null : "fx:id=\"tvAgency\" was not injected: check your FXML file 'Agency-view.fxml'.";

        setupAgencyTable();
        displayAgency();

        btnAddAgency.setOnAction(event -> {
            mode = "Add";

            openAgencyWindow(null, mode); // null because no agency exist when add new agency
        });

        tvAgency.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agency>() {
            @Override
            public void changed(ObservableValue<? extends Agency> observableValue, Agency oldValue, Agency newValue) {
                //get index of the selected agent and be sure it is not a deselection.
                int index = tvAgency.getSelectionModel().getSelectedIndex();
                if(tvAgency.getSelectionModel().isSelected(index)) {
                    Platform.runLater(() -> {
                        mode = "Edit";
                        openAgencyWindow(newValue, mode);
                    });

                }
            }
        });

    }

    private void openAgencyWindow(Agency agency, String mode) {
        FXMLLoader fxmlLoader = new FXMLLoader(DashBoardManagement.class.getResource("Add-agency-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //get the controller
        AddAgencyController controller = fxmlLoader.getController();
        controller.setMode(mode);

        if(mode.equalsIgnoreCase("Edit")) {
            controller.setAgencyForm(agency);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Agency Addition");
        stage.setScene(scene);
        stage.showAndWait();
        displayAgency();
    }

    private void setupAgencyTable() {
        colAgencyId.setCellValueFactory(new PropertyValueFactory<Agency, Integer>("agencyId"));
        colAgencyAddress.setCellValueFactory(new PropertyValueFactory<>("agencyAddress"));
        colAgencyCity.setCellValueFactory(new PropertyValueFactory<>("agencyCity"));
        colAgencyCountry.setCellValueFactory(new PropertyValueFactory<>("agencyCountry"));
        colAgencyFax.setCellValueFactory(new PropertyValueFactory<>("agencyFax"));
        colAgencyPhone.setCellValueFactory(new PropertyValueFactory<>("agencyPhone"));
        colAgencyPostal.setCellValueFactory(new PropertyValueFactory<>("agencyPostal"));
        colAgencyProvince.setCellValueFactory(new PropertyValueFactory<>("agencyProvince"));

    }

    // display Agency
    public void displayAgency() {
        //clearing list for new data
        data.clear();
        try {

            //get agencies from DB
            data= AgencyDB.getAgencies();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load agency table", e);
        }
        //populate table view
        tvAgency.setItems(data);
    }



}
