package org.example.travelexpertsphase3desktop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.dao.PackageDAO;
import org.example.travelexpertsphase3desktop.models.Package;
import java.time.LocalDate;

public class AddPackageController {

    @FXML private TextField tfPkgId;
    @FXML private TextField tfPkgName;
    @FXML private TextArea tfPkgDesc;
    @FXML private DatePicker dpPkgStartDate; // Changed to DatePicker
    @FXML private DatePicker dpPkgEndDate;   // Changed to DatePicker
    @FXML private TextField tfPkgBasePrice;
    @FXML private TextField tfPkgAgencyComm;
    @FXML private Button btnSave;
   // @FXML private Button btnCancel;
    //@FXML private Button btnDelete;

    private Package selectedPackage;
    private boolean isEditMode = false;

    public void setPackageData(Package packageToEdit) {
        if (packageToEdit != null) {
            isEditMode = true;
            this.selectedPackage = packageToEdit;
            tfPkgId.setText(String.valueOf(packageToEdit.getPackageId()));
            tfPkgName.setText(packageToEdit.getPackageName());
            tfPkgDesc.setText(packageToEdit.getPkgDesc());
            dpPkgStartDate.setValue(packageToEdit.getPkgStartDate()); // Set DatePicker value
            dpPkgEndDate.setValue(packageToEdit.getPkgEndDate());     // Set DatePicker value
            tfPkgBasePrice.setText(String.valueOf(packageToEdit.getPkgBasePrice()));
            tfPkgAgencyComm.setText(String.valueOf(packageToEdit.getPkgAgencyCommission()));
        }
    }

    @FXML
    private void savePackage() {
        String name = tfPkgName.getText().trim();
        String desc = tfPkgDesc.getText().trim();
        LocalDate startDate = dpPkgStartDate.getValue(); // Get Date from DatePicker
        LocalDate endDate = dpPkgEndDate.getValue();     // Get Date from DatePicker
        String basePriceText = tfPkgBasePrice.getText().trim();
        String agencyCommissionText = tfPkgAgencyComm.getText().trim();

        // Validation
        if (name.isEmpty() || desc.isEmpty()) {
            showAlert("Validation Error", "Package Name and Description cannot be empty.");
            return;
        }
        if (startDate == null || endDate == null) {
            showAlert("Validation Error", "Please select both Start Date and End Date.");
            return;
        }
        if (endDate.isBefore(startDate)) {
            showAlert("Validation Error", "End Date must be later than Start Date.");
            return;
        }

        double basePrice, agencyCommission;
        try {
            basePrice = Double.parseDouble(basePriceText);
            agencyCommission = Double.parseDouble(agencyCommissionText);
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Base Price and Agency Commission must be valid numbers.");
            return;
        }

        if (agencyCommission > basePrice) {
            showAlert("Validation Error", "Agency Commission cannot be greater than Base Price.");
            return;
        }

        // Convert dates to "YYYY-MM-DD" format
       //String formattedStartDate = startDate.toString();
        //String formattedEndDate = endDate.toString();

        if (isEditMode) {
            selectedPackage.setPackageName(name);
            selectedPackage.setPkgDesc(desc);
            selectedPackage.setPkgStartDate(startDate);
            selectedPackage.setPkgEndDate(endDate);
            selectedPackage.setPkgBasePrice(basePrice);
            selectedPackage.setPkgAgencyCommission(agencyCommission);

            if (PackageDAO.updatePackage(selectedPackage)) {
                showAlert("Success", "Package updated successfully.");
            } else {
                showAlert("Error", "Failed to update package.");
            }
        } else {
            Package newPackage = new Package(0, name, startDate, endDate, desc, basePrice, agencyCommission);
            if (PackageDAO.insertPackage(newPackage)) {
                showAlert("Success", "Package added successfully.");
            } else {
                showAlert("Error", "Failed to add package.");
            }
        }
        closeWindow();
    }

    @FXML
    private void deletePackage() {
        if (selectedPackage != null) {
            boolean deleted = PackageDAO.deletePackage(selectedPackage.getPackageId());
            if (deleted) {
                showAlert("Success", "Package deleted successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Failed to delete package.");
            }
        } else {
            showAlert("Error", "No package selected.");
        }
    }

    @FXML
    private void cancel() {
        closeWindow();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
}
