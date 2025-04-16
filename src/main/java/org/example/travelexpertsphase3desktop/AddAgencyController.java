/**
 * Sample Skeleton for 'Add-agency-view.fxml' Controller Class
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
import org.example.travelexpertsphase3desktop.model.Agency;
import org.example.travelexpertsphase3desktop.model.AgencyDB;

public class AddAgencyController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancelAgency"
    private Button btnCancelAgency; // Value injected by FXMLLoader

    @FXML // fx:id="btnDeleteAgency"
    private Button btnDeleteAgency; // Value injected by FXMLLoader

    @FXML // fx:id="btnSaveAgency"
    private Button btnSaveAgency; // Value injected by FXMLLoader

    @FXML // fx:id="lblEditAgencyMode"
    private Label lblEditAgencyMode; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyAddress"
    private TextField tfAgencyAddress; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyCity"
    private TextField tfAgencyCity; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyCountry"
    private TextField tfAgencyCountry; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyFax"
    private TextField tfAgencyFax; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyId"
    private TextField tfAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyPhone"
    private TextField tfAgencyPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyPostal"
    private TextField tfAgencyPostal; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyProvince"
    private TextField tfAgencyProvince; // Value injected by FXMLLoader

   private String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancelAgency != null : "fx:id=\"btnCancelAgency\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert btnDeleteAgency != null : "fx:id=\"btnDeleteAgency\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert btnSaveAgency != null : "fx:id=\"btnSaveAgency\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert lblEditAgencyMode != null : "fx:id=\"lblEditAgencyMode\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert tfAgencyAddress != null : "fx:id=\"tfAgencyAddress\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert tfAgencyCity != null : "fx:id=\"tfAgencyCity\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert tfAgencyCountry != null : "fx:id=\"tfAgencyCountry\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert tfAgencyFax != null : "fx:id=\"tfAgencyFax\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert tfAgencyId != null : "fx:id=\"tfAgencyId\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert tfAgencyPhone != null : "fx:id=\"tfAgencyPhone\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert tfAgencyPostal != null : "fx:id=\"tfAgencyPostal\" was not injected: check your FXML file 'Add-agency-view.fxml'.";
        assert tfAgencyProvince != null : "fx:id=\"tfAgencyProvince\" was not injected: check your FXML file 'Add-agency-view.fxml'.";

        btnCancelAgency.setOnMouseClicked(event -> {
            closeWindow(event);

        });

        btnDeleteAgency.setOnMouseClicked(event -> {
                deleteAgency();
            //close window when done deleting
                closeWindow(event);
        });
    }

    private void deleteAgency() {
        int numRows = 0;
        int agencyId = Integer.parseInt(tfAgencyId.getText());
        try {
            numRows = AgencyDB.deleteAgency(agencyId);
        } catch (SQLException e) {
            alertUser(Alert.AlertType.ERROR, "An error occurred while deleting the agency. \n" + e.getMessage());
        }

        //ensure agency was deleted
        if (numRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "The agency has been deleted successfully.");
        }else{
            alertUser(Alert.AlertType.ERROR, "delete operation failed.");
        }
    }

    @FXML
    void saveAgency(MouseEvent event) {

        //  Validate input before saving**
        if (!validateAgencyForm()) {
            return;
        }


        // make new agency
        int numRows = 0;
        Agency agency = getAgencyFromForm();
        //add agency to db
        if (mode.equalsIgnoreCase("add")) { //inserting new object
            try {
                numRows = AgencyDB.addAgency(agency);
            } catch (SQLException e) {
                alertUser(Alert.AlertType.ERROR, "An error occurred while saving a product.\n" + e.getMessage());

            }
        }else{ //updating db
            try {
                numRows = AgencyDB.updateAgency(agency.getAgencyId(), agency);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //give users feedback of insertion
        if(numRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "Agency added successfully.");
        }else{
            alertUser(Alert.AlertType.ERROR, mode +"Operation failed.");
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
        lblEditAgencyMode.setText(mode + " Agency Mode");
//        btnDeleteAgency.setDisable(true);

        // disable delete button when creating agency
        btnDeleteAgency.setDisable(mode.equalsIgnoreCase("add"));
    }

    private void alertUser(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setHeaderText("Header Text goes here");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Agency getAgencyFromForm() {
        int agencyId = 0;

        if (!tfAgencyId.getText().isEmpty()) {
            agencyId = Integer.parseInt(tfAgencyId.getText());
        }
        Agency agency = new Agency(
                agencyId,
                tfAgencyAddress.getText(),
                tfAgencyCity.getText(),
                tfAgencyProvince.getText(),
                tfAgencyPostal.getText(),
                tfAgencyCountry.getText(),
                tfAgencyPhone.getText(),
                tfAgencyFax.getText()

        );
        return agency;
    }

    // set content of agency form when updating
    public void setAgencyForm(Agency agency) {
        tfAgencyId.setText(String.valueOf(agency.getAgencyId()));
        tfAgencyAddress.setText(agency.getAgencyAddress());
        tfAgencyCity.setText(agency.getAgencyCity());
        tfAgencyProvince.setText(agency.getAgencyProvince());
        tfAgencyPostal.setText(agency.getAgencyPostal());
        tfAgencyCountry.setText(agency.getAgencyCountry());
        tfAgencyPhone.setText(agency.getAgencyPhone());
        tfAgencyFax.setText(agency.getAgencyFax());

    }

    private boolean validateAgencyForm() {
        //  Validate Address
        if (!Validator.isNotEmpty(tfAgencyAddress.getText())) {
            alertUser(Alert.AlertType.ERROR, " Address is required.");
            return false;
        }

        //  Validate City (only letters)
        if (!Validator.isAlpha(tfAgencyCity.getText())) {
            alertUser(Alert.AlertType.ERROR, " City must only contain letters.");
            return false;
        }

        //  Validate Province (only two-letter codes)
        if (!Validator.isProvinceCode(tfAgencyProvince.getText())) {
            alertUser(Alert.AlertType.ERROR, " Province must be a valid two-letter code (e.g., AB, ON).");
            return false;
        }

        //  Validate Postal Code (Canada/US formats)
        if (!Validator.isPostalCode(tfAgencyPostal.getText())) {
            alertUser(Alert.AlertType.ERROR, " Invalid postal code format.");
            return false;
        }

        //  Validate Country (only letters)
        if (!Validator.isAlpha(tfAgencyCountry.getText())) {
            alertUser(Alert.AlertType.ERROR, " Country must only contain letters.");
            return false;
        }

        //  Validate Phone (valid phone number format)
        if (!Validator.isPhoneNumber(tfAgencyPhone.getText())) {
            alertUser(Alert.AlertType.ERROR, " Invalid phone number format.");
            return false;
        }

        //  Validate Fax (optional, but if provided, must be valid)
        if (!tfAgencyFax.getText().isEmpty() && !Validator.isPhoneNumber(tfAgencyFax.getText())) {
            alertUser(Alert.AlertType.ERROR, " Invalid fax number format.");
            return false;
        }

        return true; // ✅ All fields are valid
    }

}
