/**
 * Sample Skeleton for 'Customer-view.fxml' Controller Class
 */

package org.example.travelexpertsphase3desktop;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.model.Customer;
import org.example.travelexpertsphase3desktop.model.CustomerDB;

public class CustomerManagementController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddCustomer"
    private Button btnAddCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="btnCustomerSearch"
    private Button btnCustomerSearch; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerAddress"
    private TableColumn<Customer, String> colCustomerAddress; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerAgentId"
    private TableColumn<Customer, Integer> colCustomerAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerBusPhone"
    private TableColumn<Customer, String> colCustomerBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerCity"
    private TableColumn<Customer, String> colCustomerCity; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerCountry"
    private TableColumn<Customer, String> colCustomerCountry; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerEmail"
    private TableColumn<Customer, String> colCustomerEmail; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerFirstname"
    private TableColumn<Customer, String> colCustomerFirstname; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerHomePhone"
    private TableColumn<Customer, String> colCustomerHomePhone; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerId"
    private TableColumn<Customer, Integer> colCustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerLastname"
    private TableColumn<Customer, String> colCustomerLastname; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerPostal"
    private TableColumn<Customer, String> colCustomerPostal; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerProvince"
    private TableColumn<Customer, String> colCustomerProvince; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustomerSearch"
    private TextField tfCustomerSearch; // Value injected by FXMLLoader

    @FXML // fx:id="tvCustomer"
    private TableView<Customer> tvCustomer; // Value injected by FXMLLoader

    //global list of customers
    private ObservableList<Customer> data = FXCollections.observableArrayList();

    String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAddCustomer != null : "fx:id=\"btnAddCustomer\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert btnCustomerSearch != null : "fx:id=\"btnCustomerSearch\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerAddress != null : "fx:id=\"colCustomerAddress\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerAgentId != null : "fx:id=\"colCustomerAgentId\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerBusPhone != null : "fx:id=\"colCustomerBusPhone\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerCity != null : "fx:id=\"colCustomerCity\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerCountry != null : "fx:id=\"colCustomerCountry\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerEmail != null : "fx:id=\"colCustomerEmail\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerFirstname != null : "fx:id=\"colCustomerFirstname\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerHomePhone != null : "fx:id=\"colCustomerHomePhone\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerId != null : "fx:id=\"colCustomerId\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerLastname != null : "fx:id=\"colCustomerLastname\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerPostal != null : "fx:id=\"colCustomerPostal\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert colCustomerProvince != null : "fx:id=\"colCustomerProvince\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert tfCustomerSearch != null : "fx:id=\"tfCustomerSearch\" was not injected: check your FXML file 'Customer-view.fxml'.";
        assert tvCustomer != null : "fx:id=\"tvCustomer\" was not injected: check your FXML file 'Customer-view.fxml'.";

        // Hide Add button to prevent adding customers
        btnAddCustomer.setVisible(false);

        //set up table columns
        setupCustomerTable();
        displayCustomer();

        btnAddCustomer.setOnAction(event -> {
            mode = "Add";
            openCustomerWindow(null, mode);// null because no customer exist when add new customer
        });

        tvCustomer.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) { // Ensure a valid selection
                        Platform.runLater(() -> {
                            mode = "Edit";
                            openCustomerWindow((Customer) newValue, mode);
                        });
                    }
                });
        tfCustomerSearch.textProperty().addListener((observable, oldValue, newValue) -> searchCustomers(newValue));
//        tvCustomer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
//            @Override
//            public void changed(ObservableValue<? extends Customer> observableValue, Customer oldValue, Customer newValue) {
//                //get index of the selected customer and be sure it is not a deselection.
//                int index = tvCustomer.getSelectionModel().getSelectedIndex();
//                if(tvCustomer.getSelectionModel().isSelected(index)) {
//                    Platform.runLater(() -> {
//                        mode = "Edit";
//                        openCustomerWindow(newValue, mode);
//                    });
//
//                }
//            }
//        });

    }


    private void openCustomerWindow(Customer customer, String mode) {
        FXMLLoader fxmlLoader = new FXMLLoader(DashBoardManagement.class.getResource("Add-customer-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddCustomerController controller = fxmlLoader.getController();
        controller.setMode("Edit");

        // if editing an existing customer, pre fill add customer form with details of the agent being edited.
        if(mode.equalsIgnoreCase("Edit")) { // Always set mode to Edit to disable Add and Delete features
            controller.setCustomerForm(customer);

        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Customer Addition");
        stage.setScene(scene);
        stage.showAndWait();
        displayCustomer();
    }

    private void setupCustomerTable() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        colCustomerFirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colCustomerLastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colCustomerProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colCustomerPostal.setCellValueFactory(new PropertyValueFactory<>("postal"));
        colCustomerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colCustomerHomePhone.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
        colCustomerBusPhone.setCellValueFactory(new PropertyValueFactory<>("busPhone"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCustomerAgentId.setCellValueFactory(new PropertyValueFactory<>("agentId"));

    }

    // display Customer
    public void displayCustomer() {
        //clearing list for new data
        data.clear();
        try {
            //get customers from DB

            data= CustomerDB.getCustomers();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load agent table", e);
        }
        //populate table view
        tvCustomer.setItems(data);
    }

    private void searchCustomers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            tvCustomer.setItems(data);
            return;
        }

        ObservableList<Customer> filteredList = FXCollections.observableArrayList();
        for (Customer c : data) {
            if (c.getFirstName().toLowerCase().contains(keyword.toLowerCase()) ||
                    c.getLastName().toLowerCase().contains(keyword.toLowerCase()) ||
                    c.getCity().toLowerCase().contains(keyword.toLowerCase()) ||
                    c.getEmail().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(c);
            }
        }
        tvCustomer.setItems(filteredList);
    }
}


