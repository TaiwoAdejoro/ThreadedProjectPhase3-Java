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
    @FXML private TextField tfPkgStartDate;
    @FXML private TextField tfPkgEndDate;
    @FXML private TextArea tfPkgDesc;
    @FXML private TextField tfPkgBasePrice;
    @FXML private TextField tfPkgAgencyComm;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Button btnDelete;

    private Package selectedPackage;
    private boolean isEditMode = false;

    public void setPackageData(Package packageToEdit) {
        if (packageToEdit != null) {
            isEditMode = true;
            this.selectedPackage = packageToEdit;
            tfPkgId.setText(String.valueOf(packageToEdit.getPackageId()));
            tfPkgName.setText(packageToEdit.getPackageName());
            tfPkgStartDate.setText(packageToEdit.getPkgStartDate().toString());
            tfPkgEndDate.setText(packageToEdit.getPkgEndDate().toString());
            tfPkgDesc.setText(packageToEdit.getPkgDesc());
            tfPkgBasePrice.setText(String.valueOf(packageToEdit.getPkgBasePrice()));
            tfPkgAgencyComm.setText(String.valueOf(packageToEdit.getPkgAgencyCommission()));
        }
    }

    @FXML
    private void savePackage() {
        String name = tfPkgName.getText().trim();
        String desc = tfPkgDesc.getText().trim();
        LocalDate startDate = LocalDate.parse(tfPkgStartDate.getText().trim());
        LocalDate endDate = LocalDate.parse(tfPkgEndDate.getText().trim());
        Double basePrice = Double.parseDouble(tfPkgBasePrice.getText().trim());
        Double agencyCommission = Double.parseDouble(tfPkgAgencyComm.getText().trim());

        if (name.isEmpty() || desc.isEmpty()) {
            showAlert("Validation Error", "Package Name and Description cannot be empty.");
            return;
        }
        if (endDate.isBefore(startDate)) {
            showAlert("Validation Error", "End Date must be later than Start Date.");
            return;
        }
        if (agencyCommission > basePrice) {
            showAlert("Validation Error", "Agency Commission cannot be greater than Base Price.");
            return;
        }

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
