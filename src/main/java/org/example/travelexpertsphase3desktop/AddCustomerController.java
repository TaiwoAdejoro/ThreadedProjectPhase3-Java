/**
 * Sample Skeleton for 'Add-customer-view.fxml' Controller Class
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
import org.example.travelexpertsphase3desktop.Utils.Validator;
import org.example.travelexpertsphase3desktop.model.Customer;
import org.example.travelexpertsphase3desktop.model.CustomerDB;

public class AddCustomerController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancelCustomer"
    private Button btnCancelCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDeleteCustomer"
    private Button btnDeleteCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="btnSaveCustomer"
    private Button btnSaveCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerAddress"
    private TextField tfCustomerAddress; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerAgentId"
    private TextField tfCustomerAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerBusinessPhone"
    private TextField tfCustomerBusinessPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerCity"
    private TextField tfCustomerCity; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerCountry"
    private TextField tfCustomerCountry; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerEmail"
    private TextField tfCustomerEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerFirstname"
    private TextField tfCustomerFirstname; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerHomePhone"
    private TextField tfCustomerHomePhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerId"
    private TextField tfCustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerLastname"
    private TextField tfCustomerLastname; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerPostal"
    private TextField tfCustomerPostal; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerProvince"
    private TextField tfCustomerProvince; // Value injected by FXMLLoader

    private String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancelCustomer != null : "fx:id=\"btnCancelCustomer\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert btnDeleteCustomer != null : "fx:id=\"btnDeleteCustomer\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert btnSaveCustomer != null : "fx:id=\"btnSaveCustomer\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerAddress != null : "fx:id=\"tfCustomerAddress\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerAgentId != null : "fx:id=\"tfCustomerAgentId\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerBusinessPhone != null : "fx:id=\"tfCustomerBusinessPhone\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerCity != null : "fx:id=\"tfCustomerCity\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerCountry != null : "fx:id=\"tfCustomerCountry\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerEmail != null : "fx:id=\"tfCustomerEmail\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerFirstname != null : "fx:id=\"tfCustomerFirstname\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerHomePhone != null : "fx:id=\"tfCustomerHomePhone\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerId != null : "fx:id=\"tfCustomerId\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerLastname != null : "fx:id=\"tfCustomerLastname\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerPostal != null : "fx:id=\"tfCustomerPostal\" was not injected: check your FXML file 'Add-customer-view.fxml'.";
        assert tfCustomerProvince != null : "fx:id=\"tfCustomerProvince\" was not injected: check your FXML file 'Add-customer-view.fxml'.";

        btnDeleteCustomer.setVisible(false);
        btnCancelCustomer.setOnMouseClicked(event -> {
            closeWindow(event);
        });

        btnDeleteCustomer.setOnMouseClicked(event -> {
            deleteCustomer();
            //close window when done deleting
            closeWindow(event);
        });
    }

    private void deleteCustomer() {
        int numRows = 0;
        int customerId = Integer.parseInt(tfCustomerId.getText());
        try {
            numRows = CustomerDB.deleteCustomer(customerId);
        } catch (SQLException e) {
            alertUser(Alert.AlertType.ERROR, "An error occurred while deleting the customer.\n" + e.getMessage());
        }

        //ensure customer was deleted
        if(numRows == 1) {
            alertUser(Alert.AlertType.CONFIRMATION, "The customer has been deleted.");
        }else{
            alertUser(Alert.AlertType.ERROR, "delete operaion failed.");
        }
    }

    @FXML
    void saveCustomer(MouseEvent event) {
        // Validate input before saving**
        if (!validateCustomerForm()) {
            return;
        }
        // Create a new Customer object from form inputs
        Customer customer = getCustomerFromForm();

        int numAffectedRows = 0; // Ensure this variable is properly declared

        try {
            if (mode.equalsIgnoreCase("add")) {
                System.out.println(" Adding a new customer...");
                numAffectedRows = CustomerDB.addCustomer(customer);
            } else {
                System.out.println(" Updating existing customer with ID: " + customer.getId());
                numAffectedRows = CustomerDB.updateCustomer(customer.getId(), customer);
            }
        } catch (SQLException e) {
            System.err.println(" SQL Exception: " + e.getMessage());
            alertUser(Alert.AlertType.ERROR, "An error occurred while saving a customer.\n" + e.getMessage());
            return; // Stop execution if there’s an error
        }

        // Provide user feedback
        if (numAffectedRows > 0) {
            System.out.println("Customer saved successfully!");
            alertUser(Alert.AlertType.CONFIRMATION, "Customer saved successfully.");
        } else {
            System.out.println(" No rows affected. Check input values.");
            alertUser(Alert.AlertType.ERROR, mode + " operation failed.");
        }

        // Close the window after saving
        closeWindow(event);
    }

    //    @FXML
//    void saveCustomer(MouseEvent event) {
//
//
//        //make new customer
//        int numRows = 0;
//        Customer customer = getCustomerFromForm();
//        //add customer to db
//        if (mode.equalsIgnoreCase("add")) {  //inserting new object
//            try {
//                numRows = CustomerDB.addCustomer(customer);
//            } catch (SQLException e) {
//                alertUser(Alert.AlertType.ERROR, "An error occurred while saving a product.\n" + e.getMessage());
//
//            }
//        } else {//updating db
//            try {
//                numRows = CustomerDB.updateCustomer(customer.getId(), customer);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        //give users feedback of insertion
//        if(numRows == 1) {
//            alertUser(Alert.AlertType.CONFIRMATION, "Costomer added successfully.");
//        }else{
//            alertUser(Alert.AlertType.ERROR, mode +"Operation failed.");
//        }
//
//        //closing the window
//       closeWindow(event);
//    }
    private void closeWindow(javafx.scene.input.MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.close();
    }


    public void setMode(String mode) {
        this.mode = mode;

        //change header to the current mode
        // lblEditAgentMode.setText(mode + " Customer Mode");

        // disable delete button when creating customer
        btnDeleteCustomer.setDisable(mode.equalsIgnoreCase("add"));
    }

    private void alertUser(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setHeaderText("Header Text goes here");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Customer getCustomerFromForm() {
        int customerId = 0;

        if (!tfCustomerId.getText().isEmpty()) {
            customerId = Integer.parseInt(tfCustomerId.getText());
        }
        Customer customer = new Customer(
                customerId,
                tfCustomerFirstname.getText(),
                tfCustomerLastname.getText(),
                tfCustomerAddress.getText(),
                tfCustomerCity.getText(),
                tfCustomerProvince.getText(),
                tfCustomerPostal.getText(),
                tfCustomerCountry.getText(),
                tfCustomerHomePhone.getText(),
                tfCustomerBusinessPhone.getText(),
                tfCustomerEmail.getText(),
                Integer.parseInt(tfCustomerAgentId.getText())
        );
        return customer;
    }
    // set content of agent form when updating
    public void setCustomerForm(Customer customer) {
        tfCustomerId.setText(String.valueOf(customer.getId()));
        tfCustomerFirstname.setText(customer.getFirstName());
        tfCustomerLastname.setText(customer.getLastName());
        tfCustomerAddress.setText(customer.getAddress());
        tfCustomerCity.setText(customer.getCity());
        tfCustomerProvince.setText(customer.getProvince());
        tfCustomerPostal.setText(customer.getPostal());
        tfCustomerCountry.setText(customer.getCountry());
        tfCustomerHomePhone.setText(customer.getHomePhone());
        tfCustomerBusinessPhone.setText(customer.getBusPhone());
        tfCustomerEmail.setText(customer.getEmail());
        tfCustomerAgentId.setText(String.valueOf(customer.getAgentId()));
    }

    private boolean validateCustomerForm() {
        if (!Validator.isAlpha(tfCustomerFirstname.getText())) {
            alertUser(Alert.AlertType.ERROR, "First name must only contain letters.");
            return false;
        }

        if (!Validator.isAlpha(tfCustomerLastname.getText())) {
            alertUser(Alert.AlertType.ERROR, "Last name must only contain letters.");
            return false;
        }

        if (!Validator.isAlpha(tfCustomerCity.getText())) {
            alertUser(Alert.AlertType.ERROR, "City must only contain letters.");
            return false;
        }

        if (!Validator.isProvinceCode(tfCustomerProvince.getText())) {
            alertUser(Alert.AlertType.ERROR, "Province code must be 2 uppercase letters.");
            return false;
        }

        if (!Validator.isPostalCode(tfCustomerPostal.getText())) {
            alertUser(Alert.AlertType.ERROR, "Invalid postal code format.");
            return false;
        }

        if (!Validator.isAlpha(tfCustomerCountry.getText())) {
            alertUser(Alert.AlertType.ERROR, "Country must only contain letters.");
            return false;
        }

        if (!Validator.isPhoneNumber(tfCustomerHomePhone.getText())) {
            alertUser(Alert.AlertType.ERROR, "Invalid home phone number.");
            return false;
        }

        if (!Validator.isPhoneNumber(tfCustomerBusinessPhone.getText())) {
            alertUser(Alert.AlertType.ERROR, "Invalid business phone number.");
            return false;
        }

        if (!Validator.isValidEmail(tfCustomerEmail.getText())) {
            alertUser(Alert.AlertType.ERROR, "Invalid email address.");
            return false;
        }

        if (!Validator.isValidInteger(tfCustomerAgentId.getText())) {
            alertUser(Alert.AlertType.ERROR, "Agent ID must be numeric.");
            return false;
        }

        return true;
    }


}
