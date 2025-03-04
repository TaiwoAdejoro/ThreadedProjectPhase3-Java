package org.example.travelexpertsphase3desktop.controllers;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.dao.PackageDAO;
import org.example.travelexpertsphase3desktop.models.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;

import java.time.LocalDate;

public class PackageManagementController {

    @FXML private TableView<Package> tvPackages;
    @FXML private TableColumn<Package, Integer> colPkgId;
    @FXML private TableColumn<Package, String> colPkgName;
    @FXML private TableColumn<Package, LocalDate> colPkgStartDate;
    @FXML private TableColumn<Package, LocalDate> colPkgEndDate;
    @FXML private TableColumn<Package, String> colPkgDesc;
    @FXML private TableColumn<Package, Double> colPkgBasePrice;
    @FXML private TableColumn<Package, Double> colPkgAgencyComm;

    @FXML private Button btnAddPackage;
    @FXML private Button btnEditPackage;
    @FXML private Button btnDeletePackage;

    @FXML private TextField tfSearch;

    private ObservableList<Package> packageList = FXCollections.observableArrayList();
    private FilteredList<Package> filteredData;
    private final PackageDAO packageDAO;

    public PackageManagementController() {
        packageDAO = new PackageDAO(); // Initialize your DAO
    }

    @FXML
    public void initialize() {
        // Set up TableView columns
        setupTableColumns();

        // Load all packages from the database
        loadPackages();

        // Initialize FilteredList with the original package list
        filteredData = new FilteredList<>(packageList, p -> true);

        // Bind the filtered list to the TableView
        tvPackages.setItems(filteredData);

        // Add listener for search bar
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> filterPackages(newValue));

        // Add button actions
        btnAddPackage.setOnAction(e -> showAddPackageDialog());
        btnEditPackage.setOnAction(e -> editPackage());
        btnDeletePackage.setOnAction(e -> deletePackage());
    }

    private void setupTableColumns() {
        colPkgId.setCellValueFactory(cellData -> cellData.getValue().packageIdProperty().asObject());
        colPkgName.setCellValueFactory(cellData -> cellData.getValue().packageNameProperty());
        colPkgStartDate.setCellValueFactory(cellData -> cellData.getValue().pkgStartDateProperty());
        colPkgEndDate.setCellValueFactory(cellData -> cellData.getValue().pkgEndDateProperty());
        colPkgDesc.setCellValueFactory(cellData -> cellData.getValue().pkgDescProperty());
        colPkgBasePrice.setCellValueFactory(cellData -> cellData.getValue().pkgBasePriceProperty().asObject());
        colPkgAgencyComm.setCellValueFactory(cellData -> cellData.getValue().pkgAgencyCommissionProperty().asObject());
    }

    private void loadPackages() {
        try {
            // Fetch the list of packages from the database
            packageList.setAll(packageDAO.getAllPackages());
        } catch (Exception e) {
            showAlert("Error", "Failed to load packages: " + e.getMessage());
        }
    }

    private void refreshFilteredData() {
        // Update the filtered data to reflect changes
        filteredData = new FilteredList<>(packageList, p -> true);
        tvPackages.setItems(filteredData);
    }

    private void filterPackages(String searchText) {
        filteredData.setPredicate(packageItem -> {
            // If the search text is empty, display all packages
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            // Convert search text to lower case for case-insensitive search
            String lowerCaseFilter = searchText.toLowerCase();

            // Check if any of the package properties match the search text
            return packageItem.getPackageName().toLowerCase().contains(lowerCaseFilter) ||
                    packageItem.getPkgDesc().toLowerCase().contains(lowerCaseFilter) ||
                    String.valueOf(packageItem.getPackageId()).contains(lowerCaseFilter) ||
                    packageItem.getPkgStartDate().toString().contains(lowerCaseFilter) ||
                    packageItem.getPkgEndDate().toString().contains(lowerCaseFilter) ||
                    String.valueOf(packageItem.getPkgBasePrice()).contains(lowerCaseFilter) ||
                    String.valueOf(packageItem.getPkgAgencyCommission()).contains(lowerCaseFilter);
        });
    }

    @FXML
    private void showAddPackageDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddPackage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add New Package"); // Default title

            // Create the scene and load the fxml
            stage.setScene(new Scene(loader.load()));

            // Get the controller of the dialog and pass the package data to it (null for adding)
            AddPackageController controller = loader.getController();
            controller.setPackageData(null); // In this case, no package data passed (it's for adding)

            stage.showAndWait();
            loadPackages(); // Refresh the table after adding a package
            refreshFilteredData(); // Refresh the filtered data
        } catch (Exception e) {
            showAlert("Error", "Failed to open Add Package dialog: " + e.getMessage());
        }
    }

    @FXML
    private void editPackage() {
        Package selectedPackage = tvPackages.getSelectionModel().getSelectedItem();
        if (selectedPackage != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddPackage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Edit Package");  // Modify the title for editing

                stage.setScene(new Scene(loader.load()));

                // Now pass the selectedPackage data to the controller for editing
                AddPackageController controller = loader.getController();
                controller.setPackageData(selectedPackage); // Pass the selected package to the controller for editing

                stage.showAndWait();
                loadPackages(); // Refresh the table after editing
                refreshFilteredData(); // Refresh the filtered data
            } catch (Exception e) {
                showAlert("Error", "Failed to open Edit Package dialog: " + e.getMessage());
            }
        } else {
            showAlert("Error", "No package selected for editing.");
        }
    }

    @FXML
    private void deletePackage() {
        Package selectedPackage = tvPackages.getSelectionModel().getSelectedItem();
        if (selectedPackage != null) {
            // Confirm deletion
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this package?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    boolean deleted = packageDAO.deletePackage(selectedPackage.getPackageId());
                    if (deleted) {
                        showAlert("Success", "Package deleted successfully.");
                        loadPackages(); // Reload the packages after deletion
                        refreshFilteredData(); // Refresh the filtered data
                    } else {
                        showAlert("Error", "Failed to delete package.");
                    }
                }
            });
        } else {
            showAlert("Error", "No package selected for deletion.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
