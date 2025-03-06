package org.example.travelexpertsphase3desktop;

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.Supplier;

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
    private TableView<String> tbvSupplier;

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

        setTableData();
        getSuppliers();

    }

    //declare TableView Data
    private void setTableData() {
        colSuppId.setCellValueFactory(cellData -> cellData.getValue().supplierIdProperty().asObject());
        colSuppName.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());;
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
    private void addSupplier() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
        Stage stage = new Stage();
        stage.setTitle("Add a new Supplier");

        stage.setScene(new Scene(loader.load()));
    }

    //Edit Existing Supplier
    private void editSupplier() {}

    //delete supplier from DB
    private void deleteSupplier() {}

    //display Alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
