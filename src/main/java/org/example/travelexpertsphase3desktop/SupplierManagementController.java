/**
 * Sample Skeleton for 'Supplier-view.fxml' Controller Class
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
import org.example.travelexpertsphase3desktop.model.Supplier;
import org.example.travelexpertsphase3desktop.model.SupplierDB;

public class SupplierManagementController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddSupplier"
    private Button btnAddSupplier; // Value injected by FXMLLoader

    @FXML // fx:id="btnSupplierSearch"
    private Button btnSupplierSearch; // Value injected by FXMLLoader

    @FXML // fx:id="colSupplierId"
    private TableColumn<Supplier, Integer> colSupplierId; // Value injected by FXMLLoader

    @FXML // fx:id="colSupplierName"
    private TableColumn<Supplier, String> colSupplierName; // Value injected by FXMLLoader

    @FXML // fx:id="tfSupplierSearch"
    private TextField tfSupplierSearch; // Value injected by FXMLLoader

    @FXML // fx:id="tvSupplier"
    private TableView<Supplier> tvSupplier; // Value injected by FXMLLoader

    //global list of suppliers
    private ObservableList<Supplier> data = FXCollections.observableArrayList();

    String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAddSupplier != null : "fx:id=\"btnAddSupplier\" was not injected: check your FXML file 'Supplier-view.fxml'.";
        assert btnSupplierSearch != null : "fx:id=\"btnSupplierSearch\" was not injected: check your FXML file 'Supplier-view.fxml'.";
        assert colSupplierId != null : "fx:id=\"colSupplierId\" was not injected: check your FXML file 'Supplier-view.fxml'.";
        assert colSupplierName != null : "fx:id=\"colSupplierName\" was not injected: check your FXML file 'Supplier-view.fxml'.";
        assert tfSupplierSearch != null : "fx:id=\"tfSupplierSearch\" was not injected: check your FXML file 'Supplier-view.fxml'.";
        assert tvSupplier != null : "fx:id=\"tvSupplier\" was not injected: check your FXML file 'Supplier-view.fxml'.";

        //set up table columns
        setupSupplierTable();
        displaySupplier();

        btnAddSupplier.setOnAction(event -> {
            mode = "Add";

            openSupplierWindow(null, mode);// null because no agency exist when add new supplier
        });

        tvSupplier.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Supplier>() {
            @Override
            public void changed(ObservableValue<? extends Supplier> observableValue, Supplier oldValue, Supplier newValue) {
                //get index of the selected agent and be sure it is not a deselection.
                int index = tvSupplier.getSelectionModel().getSelectedIndex();
                if(tvSupplier.getSelectionModel().isSelected(index)) {
                    Platform.runLater(() -> {
                        mode = "Edit";
                        openSupplierWindow(newValue, mode);
                    });

                }
            }
        });

    }

    private void openSupplierWindow(Supplier supplier, String mode) {
        FXMLLoader fxmlLoader = new FXMLLoader(DashBoardManagement.class.getResource("add-supplier-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddSupplierController controller = fxmlLoader.getController();
        controller.setMode(mode);

        if(mode.equalsIgnoreCase("Edit")) {
            controller.setSupplierForm(supplier);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Supplier Addition");
        stage.setScene(scene);
        stage.showAndWait();
        displaySupplier();
    }

    private void setupSupplierTable() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<Supplier, Integer>("id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    //display products
    public void displaySupplier() {
        //clearing list for new data
        data.clear();
        try {
            //get suppliers from DB
           data= SupplierDB.getSupplier();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load supplier table", e);
        }
        //populate table view
        tvSupplier.setItems(data);
    }




}
