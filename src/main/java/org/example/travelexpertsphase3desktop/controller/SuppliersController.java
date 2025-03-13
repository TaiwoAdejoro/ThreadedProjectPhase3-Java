package org.example.travelexpertsphase3desktop.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.DAO.SuppliersDAO;
import org.example.travelexpertsphase3desktop.model.Suppliers;

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

    @FXML
    private TableColumn<Suppliers, Integer> colProductId;

    private final SuppliersDAO suppliersDAO = new SuppliersDAO();

    private Suppliers selectedSupplier;

    private FilteredList<Suppliers> filteredData;


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

        //filtered list based on search bar
        filteredData = new FilteredList<>(suppliers);

        //add listener to search bar for filtered list
        txtSupplier.textProperty().addListener((observable, oldValue, newValue) -> filterSuppliers(newValue));

        tbvSupplier.setItems(filteredData);

        btnAdd.setOnAction(e -> {
            try {
                addSupplierWindow();
                getSuppliers();
                tbvSupplier.setItems(filteredData);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnEdit.setOnAction(e -> {
            try {
                editSupplier();
                getSuppliers();
                tbvSupplier.setItems(filteredData);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnDelete.setOnAction(e -> {
            deleteSupplier();
            getSuppliers();
            tbvSupplier.setItems(filteredData);
        });
    }

    //declare TableView Data
    private void setTableColumnData() {
        colSuppId.setCellValueFactory(cellData -> cellData.getValue().supplierIdProperty().asObject());
        colSuppName.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());
        colProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty().asObject());
    }

    //method to filter based on search data
    private void filterSuppliers(String filterText) {
        filteredData.setPredicate(suppliers -> {
            if (filterText.isEmpty() || filterText == null) {
                return true;
            }
            String lowerCaseFilterText = filterText.toLowerCase();

            return suppliers.supplierIdProperty().getValue().toString().toLowerCase().contains(lowerCaseFilterText) ||
                    suppliers.supplierNameProperty().getValue().toLowerCase().contains(lowerCaseFilterText);
        });
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
        getSelectedSupplier();
        if (selectedSupplier != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Suppliers_editor_view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Edit Supplier");

                stage.setScene(new Scene(loader.load()));
                AddSupplierController controller = loader.getController();
                controller.setSupplierDetails(selectedSupplier);
                System.out.println(selectedSupplier);

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
        getSelectedSupplier();
        if (selectedSupplier != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "are you sure you wish to delete this supplier?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    suppliersDAO.deleteSupplier(selectedSupplier.getSupplierId());
                } else {
                    showAlert("Error", "Failed to delete supplier.");
                }
            });
        } else {
            showAlert("Error", "No supplier selected.");
        }
    }

    private void getSelectedSupplier() {
        try {
            selectedSupplier = tbvSupplier.getSelectionModel().getSelectedItem();
            System.out.println(selectedSupplier);
        } catch (Exception e) {
            System.out.println("There was a problem getting the selected supplier");
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
