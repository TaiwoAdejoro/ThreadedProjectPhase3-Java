/**
 * Sample Skeleton for 'Add-supplier-view.fxml' Controller Class
 */

package org.example.travelexpertsphase3desktop;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.model.Supplier;
import org.example.travelexpertsphase3desktop.model.SupplierDB;

public class AddSupplierController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancelSupplier"
    private Button btnCancelSupplier; // Value injected by FXMLLoader

    @FXML // fx:id="btnDeleteSupplier"
    private Button btnDeleteSupplier; // Value injected by FXMLLoader

    @FXML // fx:id="btnSaveSupplier"
    private Button btnSaveSupplier; // Value injected by FXMLLoader

    @FXML // fx:id="tfSupplierId"
    private TextField tfSupplierId; // Value injected by FXMLLoader

    @FXML // fx:id="tfSupplierName"
    private TextField tfSupplierName; // Value injected by FXMLLoader

    private String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancelSupplier != null : "fx:id=\"btnCancelSupplier\" was not injected: check your FXML file 'Add-supplier-view.fxml'.";
        assert btnDeleteSupplier != null : "fx:id=\"btnDeleteSupplier\" was not injected: check your FXML file 'Add-supplier-view.fxml'.";
        assert btnSaveSupplier != null : "fx:id=\"btnSaveSupplier\" was not injected: check your FXML file 'Add-supplier-view.fxml'.";
        assert tfSupplierId != null : "fx:id=\"tfSupplierId\" was not injected: check your FXML file 'Add-supplier-view.fxml'.";
        assert tfSupplierName != null : "fx:id=\"tfSupplierName\" was not injected: check your FXML file 'Add-supplier-view.fxml'.";

        btnCancelSupplier.setOnMouseClicked(event -> {
            closeWindow(event);
        });

        btnDeleteSupplier.setOnMouseClicked(event -> {
            // delete supplier
            deleteSupplier();
            //close window when done
            closeWindow(event);
        });

    }

    private void deleteSupplier() {
        int numRows = 0;
        int supplierId = Integer.parseInt(tfSupplierId.getText());
        try {
            numRows = SupplierDB.deleteSupplier(supplierId);
        } catch (SQLException e) {
            alertUser(Alert.AlertType.ERROR, "An error occurred while deleting a supplier.\n" + e.getMessage());
        }

        //ensure supplier was deleted
        if(numRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "The supplier was successfully deleted.");
        }else {
            alertUser(Alert.AlertType.ERROR, "delete operation failed.");
        }
    }

    @FXML
    void saveSupplier(MouseEvent event) {


        //make new product
        int numRows = 0;
        Supplier supplier = getSupplierFromForm();
        //add supplier to db
        if (mode.equalsIgnoreCase("add")) {  //inserting new object
            try {
                numRows = SupplierDB.addSupplier(supplier);
            } catch (SQLException e) {
                alertUser(Alert.AlertType.ERROR, "An error occurred while saving a supplier.\n" + e.getMessage());

            }
        } else { //updating db
            try {
                numRows = SupplierDB.updateSupplier(supplier.getId(), supplier);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //give users feedback of insertion
        if(numRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "Supplier added successfully.");
        }else{
            alertUser(Alert.AlertType.ERROR, mode+ "Operation failed.");
        }
        //closing the window
        closeWindow(event);
    }

    private void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.close();
    }

    public void setMode(String mode) {
        this.mode = mode;
        //change header to the current mode
        //   lblEditProductMode.setText(mode + " Product Mode");
//        btnDeleteProduct.setDisable(true);

        // disable delete button when creating product
        btnDeleteSupplier.setDisable(mode.equalsIgnoreCase("add"));
    }

    private void alertUser(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setHeaderText("Header Text goes here");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Supplier getSupplierFromForm() {
        int supplierId = 0;

        if (!tfSupplierId.getText().isEmpty()) {
            supplierId = Integer.parseInt(tfSupplierId.getText());
        }
        Supplier supplier = new Supplier(
                supplierId,
                tfSupplierName.getText()
        );
        return supplier;
    }
    // set content of supplier form when updating
    public void setSupplierForm(Supplier supplier) {
        tfSupplierId.setText(String.valueOf(supplier.getId()));
        tfSupplierName.setText(supplier.getName());
    }

}
