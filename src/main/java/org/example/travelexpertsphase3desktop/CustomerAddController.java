package org.example.travelexpertsphase3desktop; /**
 * Sample Skeleton for 'CustomerAdd.fxml' Controller Class
 */

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.model.Customer;
import org.example.travelexpertsphase3desktop.model.CustomerDB;

public class CustomerAddController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="addUpdateCustomer"
    private Label addUpdateCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="addUpdatePop"
    private VBox addUpdatePop; // Value injected by FXMLLoader

    @FXML // fx:id="addressField"
    private TextField addressField; // Value injected by FXMLLoader

    @FXML // fx:id="addressLbl"
    private Label addressLbl; // Value injected by FXMLLoader

    @FXML // fx:id="agentIdField"
    private TextField agentIdField; // Value injected by FXMLLoader

    @FXML // fx:id="agentIdLbl"
    private Label agentIdLbl; // Value injected by FXMLLoader

    @FXML // fx:id="bPhoneField"
    private TextField bPhoneField; // Value injected by FXMLLoader

    @FXML // fx:id="bPhoneLbl"
    private Label bPhoneLbl; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBut"
    private Button cancelBut; // Value injected by FXMLLoader

    @FXML // fx:id="cityField"
    private TextField cityField; // Value injected by FXMLLoader

    @FXML // fx:id="cityLbl"
    private Label cityLbl; // Value injected by FXMLLoader

    @FXML // fx:id="confirmBut"
    private Button confirmBut; // Value injected by FXMLLoader

    @FXML // fx:id="countryField"
    private TextField countryField; // Value injected by FXMLLoader

    @FXML // fx:id="countryLbl"
    private Label countryLbl; // Value injected by FXMLLoader

    @FXML // fx:id="custIdField"
    private TextField custIdField; // Value injected by FXMLLoader

    @FXML // fx:id="custIdLbl"
    private Label custIdLbl; // Value injected by FXMLLoader

    @FXML // fx:id="deleteBut"
    private Button deleteBut; // Value injected by FXMLLoader

    @FXML // fx:id="emailField"
    private TextField emailField; // Value injected by FXMLLoader

    @FXML // fx:id="emailLbl"
    private Label emailLbl; // Value injected by FXMLLoader

    @FXML // fx:id="fNameField"
    private TextField fNameField; // Value injected by FXMLLoader

    @FXML // fx:id="fNameLbl"
    private Label fNameLbl; // Value injected by FXMLLoader

    @FXML // fx:id="hPhoneField"
    private TextField hPhoneField; // Value injected by FXMLLoader

    @FXML // fx:id="hPhoneLbl"
    private Label hPhoneLbl; // Value injected by FXMLLoader

    @FXML // fx:id="lNameField"
    private TextField lNameField; // Value injected by FXMLLoader

    @FXML // fx:id="lNameLbl"
    private Label lNameLbl; // Value injected by FXMLLoader

    @FXML // fx:id="postCodeLbl"
    private Label postCodeLbl; // Value injected by FXMLLoader

    @FXML // fx:id="postalCodeField"
    private TextField postalCodeField; // Value injected by FXMLLoader

    @FXML // fx:id="provField"
    private TextField provField; // Value injected by FXMLLoader

    @FXML // fx:id="provLbl"
    private Label provLbl; // Value injected by FXMLLoader
    private String mode;



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert addUpdateCustomer != null : "fx:id=\"addUpdateCustomer\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert addUpdatePop != null : "fx:id=\"addUpdatePop\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert addressField != null : "fx:id=\"addressField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert addressLbl != null : "fx:id=\"addressLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert agentIdField != null : "fx:id=\"agentIdField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert agentIdLbl != null : "fx:id=\"agentIdLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert bPhoneField != null : "fx:id=\"bPhoneField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert bPhoneLbl != null : "fx:id=\"bPhoneLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert cancelBut != null : "fx:id=\"cancelBut\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert cityField != null : "fx:id=\"cityField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert cityLbl != null : "fx:id=\"cityLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert confirmBut != null : "fx:id=\"confirmBut\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert countryField != null : "fx:id=\"countryField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert countryLbl != null : "fx:id=\"countryLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert custIdField != null : "fx:id=\"custIdField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert custIdLbl != null : "fx:id=\"custIdLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert deleteBut != null : "fx:id=\"deleteBut\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert emailLbl != null : "fx:id=\"emailLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert fNameField != null : "fx:id=\"fNameField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert fNameLbl != null : "fx:id=\"fNameLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert hPhoneField != null : "fx:id=\"hPhoneField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert hPhoneLbl != null : "fx:id=\"hPhoneLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert lNameField != null : "fx:id=\"lNameField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert lNameLbl != null : "fx:id=\"lNameLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert postCodeLbl != null : "fx:id=\"postCodeLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert postalCodeField != null : "fx:id=\"postalCodeField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert provField != null : "fx:id=\"provField\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert provLbl != null : "fx:id=\"provLbl\" was not injected: check your FXML file 'CustomerAdd.fxml'.";

        cancelBut.setOnMouseClicked(event -> {
            closeEditForm(event);
            custIdField.setEditable(false);
        });
        deleteBut.setOnMouseClicked(event -> {
            deleteCustomer();
            closeEditForm(event);
        });
        //Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //alert.setContentText(mode);
        //System.out.println(mode);

    }

    private void deleteCustomer() { // grab selected customer for deletion print message if successful/fail
        int numberOfRows = 0;
        int customerId = Integer.parseInt(custIdField.getText());
        try {
            numberOfRows = CustomerDB.deleteCustomer(customerId);
        } catch (SQLException e) {
            //custAddAlert(Alert.AlertType.ERROR, " Deletion Failed");
        }
        if(numberOfRows == 1){
            custAddAlert(Alert.AlertType.CONFIRMATION," Operation successful.");

        }else{
            custAddAlert(Alert.AlertType.ERROR, " Deletion has Failed");
        }
    }

    @FXML
    void saveCust(MouseEvent event){//


        int numberOfRows = 0;
        Customer customer = getCustomerFormInfo();
        if(mode.equalsIgnoreCase("add")){
            try{
                numberOfRows = CustomerDB.addCustomer(customer);

            }catch(SQLException e){
                custAddAlert(Alert.AlertType.ERROR, " Operation Failed" + e.getMessage());}
        }else {
            try {
                numberOfRows = CustomerDB.UpdateCustomer(customer.getCustomerId(), customer);

            } catch (SQLException e) {
                custAddAlert(Alert.AlertType.ERROR, " Operation Failed" + e.getMessage());
            }
        }

        closeEditForm(event);
    }

    @FXML
    void ConfirmUpdate(MouseEvent event) {// save selected customer, print message if successful/fail

        int numberOfRows = 0;
        Customer customer = getCustomerFormInfo();

        if (mode.equalsIgnoreCase("add")) {
            try {
                numberOfRows = CustomerDB.addCustomer(customer);
            } catch (SQLException e) {
                custAddAlert(Alert.AlertType.ERROR,"Error retrieving data from form. " + e.getMessage());
                //throw new RuntimeException("Error Updating Customer", e);
            }
        }else{
            try {
                numberOfRows = CustomerDB.UpdateCustomer(customer.getCustomerId(), customer);
            } catch (SQLException e) {
                custAddAlert(Alert.AlertType.ERROR,"Error retrieving data from form. " + e.getMessage());
            }
        }
        if (numberOfRows == 1) {
            custAddAlert(Alert.AlertType.CONFIRMATION,"Customer added successfully.");
        }else{
            custAddAlert(Alert.AlertType.ERROR, "Operation Failed");
        }

        closeEditForm(event);

    }

    //close the window
    private static void closeEditForm(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.close();
    }

    //set update or add mode for window
    public void setMode(String mode) {
        this.mode = mode;
        //lblEditMode.setText(mode + " Customer Mode");---------------------------------------------
        //deleteBut.setDisable(true);
        deleteBut.setDisable(mode.equalsIgnoreCase("add"));
    }


    private static void custAddAlert(Alert.AlertType type, String ErrorMes) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setHeaderText(null);
        alert.setContentText("Error retrieving data" + ErrorMes);
        alert.showAndWait();
    }

    private Customer getCustomerFormInfo() {//get information from customer form inputs
        int CustId = 0;
        if(!custIdField.getText().isEmpty()){
            CustId = Integer.parseInt(custIdField.getText());
        }
        Customer customer = new Customer(
                CustId,
                fNameField.getText(),
                lNameField.getText(),
                addressField.getText(),
                cityField.getText(),
                provLbl.getText(),
                postCodeLbl.getText(),
                countryField.getText(),
                hPhoneField.getText(),
                bPhoneField.getText(),
                emailField.getText(),
                Integer.parseInt(agentIdField.getText())
        );
        return customer;
    }

    public void setCustomerPage(Customer customer) { //fill in pre-existing customer info
        custIdField.setText(String.valueOf(customer.getCustomerId()));
        fNameField.setText(customer.getCustomerFirstName());
        lNameField.setText(customer.getCustomerLastName());
        addressField.setText(customer.getCustomerAddress());
        cityField.setText(customer.getCustomerCity());
        provField.setText(customer.getCustomerProvince());
        postalCodeField.setText(customer.getCustomerPostCode());
        countryField.setText(customer.getCustomerCountry());
        hPhoneField.setText(customer.getCustomerHomePhone());
        bPhoneField.setText(customer.getCustomerBusPhone());
        emailField.setText(customer.getCustomerEmail());
        agentIdField.setText(String.valueOf(customer.getAgentId()));
        custIdField.setEditable(false);

    }
}
