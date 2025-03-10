package org.example.travelexpertsphase3desktop;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddSupplierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnExit;

    @FXML
    private Label lblSuppId;

    @FXML
    private Label lblSuppName;

    @FXML
    private TextField txtSuppId;

    @FXML
    private TextField txtSuppName;

    private final SuppliersDAO supplierDao = new SuppliersDAO();

    public Suppliers selectedSupplier;

    private boolean isEditMode = false;

    public void setSupplierDetails(Suppliers selectedSupplier) {
        if (selectedSupplier != null) {
            isEditMode = true;
            txtSuppId.setText(String.valueOf(selectedSupplier.getSupplierId()));
            txtSuppId.setDisable(true);
            txtSuppName.setText(selectedSupplier.getSupplierName());
        }
    }

    @FXML
    void initialize() {
        assert btnUpdate != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";
        assert lblSuppId != null : "fx:id=\"lblSuppId\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";
        assert lblSuppName != null : "fx:id=\"lblSuppName\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";
        assert txtSuppId != null : "fx:id=\"txtSuppId\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";
        assert txtSuppName != null : "fx:id=\"txtSuppName\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";

        btnUpdate.setOnAction(event -> {
            if (isEditMode) {
                updateSupplier(selectedSupplier);
            }
            addSupplier(Integer.parseInt(txtSuppId.getText()), txtSuppName.getText());
            closeWindow();
        });

        btnExit.setOnAction(event -> {
            closeWindow();
        });
    }

    private void addSupplier(int id, String Name) {
        if (txtSuppId.getText().isEmpty()) {
            showAlert("Error", "Please enter a Supplier ID");
            return;
        }
        if (txtSuppName.getText().isEmpty()) {
            showAlert("Error", "Please enter a Supplier Name");
            return;
        }
        id = Integer.parseInt(txtSuppId.getText());
        Name = txtSuppName.getText();

        supplierDao.addSupplier(id, Name);
    }

    private void updateSupplier(Suppliers selectedSupplier) {
            selectedSupplier.setSupplierId(Integer.parseInt(txtSuppId.getText()));
            selectedSupplier.setSupplierName(txtSuppName.getText());

            supplierDao.updateSupplier(selectedSupplier);
    }

    //display Alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
    }
}
