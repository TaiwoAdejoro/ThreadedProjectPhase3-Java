package org.example.travelexpertsphase3desktop.Controllers;

import java.sql.SQLException;
import java.util.function.Supplier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.travelexpertsphase3desktop.Dao.SuppliersDAO;
import org.example.travelexpertsphase3desktop.Models.Suppliers;
import org.example.travelexpertsphase3desktop.SuppliersTableController;

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

    private SuppliersTableController suppliersTbl;

    private ObservableList<Suppliers> suppliers = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Supplier, Integer> colSuppId;

    @FXML
    private TableColumn<Supplier, String> colSuppName;

    private final SuppliersDAO suppliersDAO;

    public SuppliersController(SuppliersDAO suppliersDAO) {
        this.suppliersDAO = suppliersDAO;
    }


    @FXML
    void initialize() throws SQLException {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert tbvSupplier != null : "fx:id=\"tbvSupplier\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert txtSupplier != null : "fx:id=\"txtSupplier\" was not injected: check your FXML file 'Suppliers-view.fxml'.";

        getSuppliers();

        setTable();
    }

    private void getSuppliers() {
        try {
            suppliers.setAll(suppliersDAO.getSuppliers());
        } catch (Exception e) {
            System.out.println("There was a problem getting the suppliers.");
            e.printStackTrace();
        }
    }

    private void addSupplier() {}

    private void editSupplier() {}

    private void deleteSupplier() {}

    private void setTable() {
        colSuppId.setCellValueFactory(colSuppId.getCellValueFactory());
        colSuppName.setCellValueFactory(colSuppName.getCellValueFactory());
    }

}
