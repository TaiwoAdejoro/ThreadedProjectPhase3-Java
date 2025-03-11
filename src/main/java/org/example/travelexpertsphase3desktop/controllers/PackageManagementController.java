package org.example.travelexpertsphase3desktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.dao.PackageDAO;
import org.example.travelexpertsphase3desktop.models.Package;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PackageManagementController {
    @FXML private TableView<Package> packageTable;
    @FXML private TableColumn<Package, Integer> colPackageId;
    @FXML private TableColumn<Package, String> colPackageName;
    @FXML private TableColumn<Package, LocalDate> colStartDate;
    @FXML private TableColumn<Package, LocalDate> colEndDate;
    @FXML private TableColumn<Package, String> colDescription;
    @FXML private TableColumn<Package, Double> colBasePrice;
    @FXML private TableColumn<Package, Double> colAgencyCommission;
    @FXML private TableColumn<Package, String> colProductSuppliers;

    @FXML private Button btnAddPackage;
    @FXML private Button btnEditPackage;
    @FXML private Button btnDeletePackage;

    private ObservableList<Package> packageList;

    @FXML
    public void initialize() {
        // Initialize table columns
        colPackageId.setCellValueFactory(cellData -> cellData.getValue().packageIdProperty().asObject());
        colPackageName.setCellValueFactory(cellData -> cellData.getValue().packageNameProperty());
        colStartDate.setCellValueFactory(cellData -> cellData.getValue().pkgStartDateProperty());
        colEndDate.setCellValueFactory(cellData -> cellData.getValue().pkgEndDateProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().pkgDescProperty());
        colBasePrice.setCellValueFactory(cellData -> cellData.getValue().pkgBasePriceProperty().asObject());
        colAgencyCommission.setCellValueFactory(cellData -> cellData.getValue().pkgAgencyCommissionProperty().asObject());
        colProductSuppliers.setCellValueFactory(cellData -> cellData.getValue().productSuppliersProperty());

        loadPackages();
    }

    private void loadPackages() {
        List<Package> packages = PackageDAO.getAllPackages();
        packageList = FXCollections.observableArrayList(packages);
        packageTable.setItems(packageList);
    }

    @FXML
    private void showAddPackageDialog(ActionEvent event) {
        openPackageForm(null);
    }

    @FXML
    private void editPackage(ActionEvent event) {
        Package selectedPackage = packageTable.getSelectionModel().getSelectedItem();
        if (selectedPackage != null) {
            openPackageForm(selectedPackage);
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a package to edit.");
        }
    }

    @FXML
    private void deletePackage(ActionEvent event) {
        Package selectedPackage = packageTable.getSelectionModel().getSelectedItem();
        if (selectedPackage != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this package?", ButtonType.YES, ButtonType.NO);
            confirmation.showAndWait();

            if (confirmation.getResult() == ButtonType.YES) {
                boolean success = PackageDAO.deletePackage(selectedPackage.getPackageId());
                if (success) {
                    packageList.remove(selectedPackage);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Package deleted successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete package.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a package to delete.");
        }
    }

    private void openPackageForm(Package pkg) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddPackage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(pkg == null ? "Add Package" : "Edit Package");

            AddPackageController controller = loader.getController();
            if (pkg != null) {
                controller.setPackageData(pkg);
            }

            stage.showAndWait();
            loadPackages(); // Refresh table after closing form
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
