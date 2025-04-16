/**
 * Sample Skeleton for 'add-products-view.fxml' Controller Class
 */

package org.example.travelexpertsphase3desktop;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.Utils.Validator;
import org.example.travelexpertsphase3desktop.model.Product;
import org.example.travelexpertsphase3desktop.model.ProductDB;

public class AddProductsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancelProduct"
    private Button btnCancelProduct; // Value injected by FXMLLoader

    @FXML // fx:id="btnDeleteProduct"
    private Button btnDeleteProduct; // Value injected by FXMLLoader

    @FXML // fx:id="btnSaveProduct"
    private Button btnSaveProduct; // Value injected by FXMLLoader

    @FXML // fx:id="lblEditProductMode"
    private Label lblEditProductMode; // Value injected by FXMLLoader

    @FXML // fx:id="tfProductId"
    private TextField tfProductId; // Value injected by FXMLLoader

    @FXML // fx:id="tfProductname"
    private TextField tfProductname; // Value injected by FXMLLoader

   private String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancelProduct != null : "fx:id=\"btnCancelProduct\" was not injected: check your FXML file 'add-products-view.fxml'.";
        assert btnDeleteProduct != null : "fx:id=\"btnDeleteProduct\" was not injected: check your FXML file 'add-products-view.fxml'.";
        assert btnSaveProduct != null : "fx:id=\"btnSaveProduct\" was not injected: check your FXML file 'add-products-view.fxml'.";
        assert lblEditProductMode != null : "fx:id=\"lblEditProductMode\" was not injected: check your FXML file 'add-products-view.fxml'.";
        assert tfProductId != null : "fx:id=\"tfProductId\" was not injected: check your FXML file 'add-products-view.fxml'.";
        assert tfProductname != null : "fx:id=\"tfProductname\" was not injected: check your FXML file 'add-products-view.fxml'.";

        btnCancelProduct.setOnMouseClicked(event -> {
            closeWindow(event);
        });

        btnDeleteProduct.setOnMouseClicked(event -> {
            // delete product
                deleteProduct();
                //close window when done
                closeWindow(event);
        });


    }

    private void deleteProduct() {
        int numRows = 0;
        int productId = Integer.parseInt(tfProductId.getText());
        try {
            numRows = ProductDB.deleteProduct(productId);
        } catch (SQLException e) {
            alertUser(Alert.AlertType.ERROR, "An error occurred while deleting a product.\n" + e.getMessage());
        }

        //ensure product was deleted
        if(numRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "The product was successfully deleted.");
        }else {
            alertUser(Alert.AlertType.ERROR, "delete operation failed.");
        }
    }

    @FXML
    void saveProduct(MouseEvent event) {

        //  **Validate input before saving**
        if (!validateProductForm()) {
            return;
        }

        //make new product
        int numRows = 0;
        Product product = getProductFromForm();
        //add product to db
        if (mode.equalsIgnoreCase("add")) {  //inserting new object
            try {
                numRows = ProductDB.addProduct(product);
            } catch (SQLException e) {
                alertUser(Alert.AlertType.ERROR, "An error occurred while saving a product.\n" + e.getMessage());

            }
        } else { //updating db
            try {
                numRows = ProductDB.updateProduct(product.getId(), product);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //give users feedback of insertion
        if(numRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "Product added successfully.");
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
        lblEditProductMode.setText(mode + " Product Mode");
//        btnDeleteProduct.setDisable(true);

        // disable delete button when creating product
        btnDeleteProduct.setDisable(mode.equalsIgnoreCase("add"));
    }

    private void alertUser(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setHeaderText("Header Text goes here");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Product getProductFromForm() {
        int productId = 0;

        if (!tfProductId.getText().isEmpty()) {
            productId = Integer.parseInt(tfProductId.getText());
        }
        Product product = new Product(
                productId,
                tfProductname.getText()
        );
        return product;
    }
    // set content of product form when updating
    public void setProductForm(Product product) {
        tfProductId.setText(String.valueOf(product.getId()));
        tfProductname.setText(product.getName());
    }

    private boolean validateProductForm() {
        //  Validate Product Name
        if (!Validator.isNotEmpty(tfProductname.getText())) {
            alertUser(Alert.AlertType.ERROR, " Product name cannot be empty.");
            return false;
        }

        if (!Validator.isAlpha(tfProductname.getText())) {
            alertUser(Alert.AlertType.ERROR, " Product name must only contain letters.");
            return false;
        }

        return true;
    }

}
