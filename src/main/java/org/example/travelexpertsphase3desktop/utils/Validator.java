package org.example.travelexpertsphase3desktop.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.travelexpertsphase3desktop.models.Package;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Validates the package data before saving.
     * @param pkg The package to be validated.
     * @return true if all validations pass, false otherwise.
     */
    public static boolean validatePackage(Package pkg) {
        if (pkg == null) {
            showErrorAlert("Package cannot be null.");
            return false;
        }

        // Validate required fields are not null or empty
        if (isNullOrEmpty(pkg.getPackageName())) {
            showErrorAlert("Package Name cannot be empty.");
            return false;
        }
        if (isNullOrEmpty(pkg.getPkgDesc())) {
            showErrorAlert("Package Description cannot be empty.");
            return false;
        }
        if (pkg.getPkgStartDate() == null) {
            showErrorAlert("Package Start Date cannot be empty.");
            return false;
        }
        if (pkg.getPkgEndDate() == null) {
            showErrorAlert("Package End Date cannot be empty.");
            return false;
        }
        if (pkg.getPkgBasePrice() <= 0) {
            showErrorAlert("Base Price must be greater than zero.");
            return false;
        }

        if (pkg.getPkgAgencyCommission() < 0) { // Assuming commission can be zero but not negative
            showErrorAlert("Agency Commission cannot be negative.");
            return false;
        }


        // Validate date format (this assumes date is coming as a string before conversion)
        if (!isValidDate(pkg.getPkgStartDate().toString())) {
            showErrorAlert("Invalid date format for Start Date. Use yyyy-MM-dd.");
            return false;
        }
        if (!isValidDate(pkg.getPkgEndDate().toString())) {
            showErrorAlert("Invalid date format for End Date. Use yyyy-MM-dd.");
            return false;
        }

        // Validate that the End Date is later than the Start Date
        if (pkg.getPkgEndDate().isBefore(pkg.getPkgStartDate())) {
            showErrorAlert("Package End Date must be later than the Start Date.");
            return false;
        }

        // Validate that Base Price is greater than zero
        if (pkg.getPkgBasePrice() <= 0) {
            showErrorAlert("Base Price must be a positive number.");
            return false;
        }

// Validate that Agency Commission is not negative
        if (pkg.getPkgAgencyCommission() < 0) {
            showErrorAlert("Agency Commission cannot be negative.");
            return false;
        }


        return true;
    }

    /**
     * Checks if a given string is null or empty.
     */
    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Validates the date format.
     */
    private static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks if a string is a valid number.
     */
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Shows an error alert with a given message.
     */
    private static void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Invalid Data Entry");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

