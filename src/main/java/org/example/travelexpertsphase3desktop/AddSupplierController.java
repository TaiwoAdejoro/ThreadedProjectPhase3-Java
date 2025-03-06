package org.example.travelexpertsphase3desktop;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddSupplierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAdd;

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

    @FXML
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";
        assert lblSuppId != null : "fx:id=\"lblSuppId\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";
        assert lblSuppName != null : "fx:id=\"lblSuppName\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";
        assert txtSuppId != null : "fx:id=\"txtSuppId\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";
        assert txtSuppName != null : "fx:id=\"txtSuppName\" was not injected: check your FXML file 'Suppliers_editor_view.fxml'.";

        btnAdd.setOnAction(event -> {
            addSupplier(Integer.parseInt(txtSuppId.getText()), txtSuppName.getText());
        });

        btnExit.setOnAction(event -> {
            try {
                exit();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addSupplier(int Id, String Name) {
        int id = Integer.parseInt(txtSuppId.getText());
        String name = txtSuppName.getText();

        supplierDao.addSupplier(id, name);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to add a new supplier?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {

            }
        });
    }

    @FXML
    private void exit() throws IOException {
        FXMLLoader loader = new FXMLLoader(SuppliersApplication.class.getResource("Suppliers-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Suppliers Table");
        stage.setScene(new Scene(loader.load()));
        SuppliersController suppliersController = loader.getController();
        stage.showAndWait();
    }

}
