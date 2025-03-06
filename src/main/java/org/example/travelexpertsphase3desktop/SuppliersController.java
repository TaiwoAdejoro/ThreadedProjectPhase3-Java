package org.example.travelexpertsphase3desktop;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SuppliersController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<Suppliers> tbvSupplier;

    @FXML
    private TextField txtSupplier;

    private ObservableList<Suppliers> suppliers = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Suppliers, Integer> colSuppId;

    @FXML
    private TableColumn<Suppliers, String> colSuppName;

    private final SuppliersDAO suppliersDAO = new SuppliersDAO();


    @FXML
    void initialize() throws SQLException {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert tbvSupplier != null : "fx:id=\"tbvSupplier\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert txtSupplier != null : "fx:id=\"txtSupplier\" was not injected: check your FXML file 'Suppliers-view.fxml'.";

        //load TableView Data
        setTableColumnData();
        //load SuppliersDB into TableView
        getSuppliers();

        tbvSupplier.setItems(suppliers);

        btnAdd.setOnAction(e -> {
            try {
                addSupplierWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnEdit.setOnAction(e -> {
           try {
               editSupplier();
           } catch (IOException ex) {
               throw new RuntimeException(ex);
           }
        });
        btnDelete.setOnAction(e -> {
            deleteSupplier();
        });
    }

    //declare TableView Data
    private void setTableColumnData() {
        colSuppId.setCellValueFactory(cellData -> cellData.getValue().supplierIdProperty().asObject());
        colSuppName.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());
        ;
    }

    //retrieve Supplier Data from DB
    private void getSuppliers() {
        try {
            suppliers.setAll(suppliersDAO.getSuppliers());
        } catch (Exception e) {
            System.out.println("There was a problem getting the suppliers.");
            e.printStackTrace();
        }
    }

    //open AddSupplier View
    @FXML
    private void addSupplierWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Suppliers_editor_view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add a new Supplier");

        stage.setScene(new Scene(loader.load()));
        AddSupplierController controller = loader.getController();

        stage.showAndWait();
    }

    //Edit Existing Supplier
    @FXML
    private void editSupplier() throws IOException {
        Suppliers editSuppliers = tbvSupplier.getSelectionModel().getSelectedItem();
        if (editSuppliers == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Suppliers_editor_view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Edit Supplier");

                stage.setScene(new Scene(loader.load()));
                AddSupplierController controller = loader.getController();

                stage.showAndWait();
            } catch (Exception e) {
                System.out.println("There was a problem getting the suppliers.");
            }
        } else {
            showAlert("Error", "Please select a supplier");
        }
    }

    //delete supplier from DB
    @FXML
    private void deleteSupplier() {
        Suppliers selectedSupplier = tbvSupplier.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "are you sure you wish to delete this supplier?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    suppliersDAO.deleteSupplier(selectedSupplier);
                } else {
                    showAlert("Error", "Failed to delete supplier.");
                }
            });
        } else {
            showAlert("Error", "No supplier selected.");
        }
    }

    //display Alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
