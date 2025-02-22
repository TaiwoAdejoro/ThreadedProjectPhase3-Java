package org.example.travelexpertsphase3desktop;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SuppliersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<?> tbvSupplier;

    @FXML
    private TextField txtSupplier;

    @FXML
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert tbvSupplier != null : "fx:id=\"tbvSupplier\" was not injected: check your FXML file 'Suppliers-view.fxml'.";
        assert txtSupplier != null : "fx:id=\"txtSupplier\" was not injected: check your FXML file 'Suppliers-view.fxml'.";

    }

}
