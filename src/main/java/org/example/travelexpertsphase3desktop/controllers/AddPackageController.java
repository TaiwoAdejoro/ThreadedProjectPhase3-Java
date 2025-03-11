package org.example.travelexpertsphase3desktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.dao.PackageDAO;
import org.example.travelexpertsphase3desktop.dao.ProductDAO;
import org.example.travelexpertsphase3desktop.dao.SupplierDAO;
import org.example.travelexpertsphase3desktop.models.Package;
import org.example.travelexpertsphase3desktop.models.Product;
import org.example.travelexpertsphase3desktop.models.Supplier;

import java.time.LocalDate;
import java.util.List;

public class AddPackageController {
    @FXML private TextField tfPkgName;
    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;
    @FXML private TextArea tfPkgDesc;
    @FXML private TextField tfBasePrice;
    @FXML private TextField tfAgencyCommission;
    @FXML private ComboBox<Product> cbProducts;
    @FXML private ComboBox<Supplier> cbSuppliers;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private Package currentPackage;

    @FXML
    public void initialize() {
        loadProducts();

        // Load eligible suppliers when a product is selected
        cbProducts.setOnAction(event -> {
            Product selectedProduct = cbProducts.getValue();
            if (selectedProduct != null) {
                loadSuppliers(selectedProduct.getProductId());
            }
        });
    }

    // Load products into ComboBox
    private void loadProducts() {
        List<Product> products = ProductDAO.getAllProducts();
        ObservableList<Product> productOptions = FXCollections.observableArrayList(products);
        cbProducts.setItems(productOptions);
    }

    // Load eligible suppliers for the selected product
    private void loadSuppliers(int productId) {
        List<Supplier> suppliers = SupplierDAO.getSuppliersByProduct(productId);
        ObservableList<Supplier> supplierOptions = FXCollections.observableArrayList(suppliers);
        cbSuppliers.setItems(supplierOptions);
    }

    // Set package data if editing an existing package
    public void setPackageData(Package pkg) {
        this.currentPackage = pkg;
        tfPkgName.setText(pkg.getPackageName());
        dpStartDate.setValue(pkg.getPkgStartDate());
        dpEndDate.setValue(pkg.getPkgEndDate());
        tfPkgDesc.setText(pkg.getPkgDesc());
        tfBasePrice.setText(String.valueOf(pkg.getPkgBasePrice()));
        tfAgencyCommission.setText(String.valueOf(pkg.getPkgAgencyCommission()));
    }

    // Handle Save button click

    @FXML
    private void savePackage(ActionEvent event) {
        String name = tfPkgName.getText().trim();
        LocalDate startDate = dpStartDate.getValue();
        LocalDate endDate = dpEndDate.getValue();
        String desc = tfPkgDesc.getText().trim();
        double basePrice, agencyCommission;

        if (name.isEmpty() || startDate == null || tfBasePrice.getText().trim().isEmpty() ||
                tfAgencyCommission.getText().trim().isEmpty() || cbProducts.getValue() == null ||
                cbSuppliers.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all required fields.");
            return;
        }

        try {
            basePrice = Double.parseDouble(tfBasePrice.getText().trim());
            agencyCommission = Double.parseDouble(tfAgencyCommission.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Base price and agency commission must be numbers.");
            return;
        }

        if (endDate != null && endDate.isBefore(startDate)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "End date cannot be before start date.");
            return;
        }

        Product selectedProduct = cbProducts.getValue();
        Supplier selectedSupplier = cbSuppliers.getValue();

        // Check if the package name already exists before creating a new package
        if (currentPackage == null && PackageDAO.doesPackageNameExist(name)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "A package with this name already exists.");
            return;
        }

        if (currentPackage == null) {
            // Creating a new package
            Package newPackage = new Package(0, name, startDate, endDate, desc, basePrice, agencyCommission, "");
            boolean success = PackageDAO.insertPackage(newPackage, selectedProduct.getProductId(), selectedSupplier.getSupplierId());

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Package saved successfully.");
                closeWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save package.");
            }
        } else {
            // Editing an existing package
            currentPackage.setPackageName(name);
            currentPackage.setPkgStartDate(startDate);
            currentPackage.setPkgEndDate(endDate);
            currentPackage.setPkgDesc(desc);
            currentPackage.setPkgBasePrice(basePrice);
            currentPackage.setPkgAgencyCommission(agencyCommission);

            boolean success = PackageDAO.updatePackage(currentPackage, selectedProduct.getProductId(), selectedSupplier.getSupplierId());

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Package updated successfully.");
                closeWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update package.");
            }
        }
    }



    // Handle Cancel button click
    @FXML
    private void cancel(ActionEvent event) {
        closeWindow();
    }

    // Show alert message
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Close window
    private void closeWindow() {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
}
