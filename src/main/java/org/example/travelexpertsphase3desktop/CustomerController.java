/**
 * Sample Skeleton for 'Customer.fxml' Controller Class
 */

package org.example.travelexpertsphase3desktop;

import java.io.IOException;
import java.lang.management.PlatformManagedObject;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.model.Customer;
import org.example.travelexpertsphase3desktop.model.CustomerDB;

import javax.swing.event.ChangeEvent;
import javafx.beans.value.ChangeListener ;
public class CustomerController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="NewBut"
    private Button NewBut; // Value injected by FXMLLoader

    @FXML // fx:id="RemoveBut"
    private Button RemoveBut; // Value injected by FXMLLoader

    @FXML // fx:id="UpdateBut"
    private Button UpdateBut; // Value injected by FXMLLoader

    @FXML // fx:id="addressColumn"
    private TableColumn<Customer, String> addressColumn; // Value injected by FXMLLoader

    @FXML // fx:id="bPhoneColumn"
    private TableColumn<Customer, String> bPhoneColumn; // Value injected by FXMLLoader

    @FXML // fx:id="cityColumn"
    private TableColumn<Customer, String> cityColumn; // Value injected by FXMLLoader

    @FXML // fx:id="countryColumn"
    private TableColumn<Customer, String> countryColumn; // Value injected by FXMLLoader

    @FXML // fx:id="customerLbl"
    private Label customerLbl; // Value injected by FXMLLoader

    @FXML // fx:id="emailColumn"
    private TableColumn<Customer, String> emailColumn; // Value injected by FXMLLoader

    @FXML // fx:id="fNameColumn"
    private TableColumn<Customer, String> fNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="hPhoneColumn"
    private TableColumn<Customer, String> hPhoneColumn; // Value injected by FXMLLoader

    @FXML // fx:id="idAgentColumn"
    private TableColumn<Customer, Integer> idAgentColumn; // Value injected by FXMLLoader

    @FXML // fx:id="idColumn"
    private TableColumn<Customer, Integer> idColumn; // Value injected by FXMLLoader

    @FXML // fx:id="lNameColumn"
    private TableColumn<Customer, String> lNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="postCodeColumn"
    private TableColumn<Customer, String> postCodeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="provColumn"
    private TableColumn<Customer, String> provColumn; // Value injected by FXMLLoader

    @FXML // fx:id="tableView"
    private TableView<Customer> tableView; // Value injected by FXMLLoader

    private ObservableList<Customer> entries = FXCollections.observableArrayList();

    private String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert NewBut != null : "fx:id=\"NewBut\" was not injected: check your FXML file 'Customer.fxml'.";
        assert RemoveBut != null : "fx:id=\"RemoveBut\" was not injected: check your FXML file 'Customer.fxml'.";
        assert UpdateBut != null : "fx:id=\"UpdateBut\" was not injected: check your FXML file 'Customer.fxml'.";
        assert addressColumn != null : "fx:id=\"addressColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert bPhoneColumn != null : "fx:id=\"bPhoneColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert cityColumn != null : "fx:id=\"cityColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert countryColumn != null : "fx:id=\"countryColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert customerLbl != null : "fx:id=\"customerLbl\" was not injected: check your FXML file 'Customer.fxml'.";
        assert emailColumn != null : "fx:id=\"emailColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert fNameColumn != null : "fx:id=\"fNameColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert hPhoneColumn != null : "fx:id=\"hPhoneColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert idAgentColumn != null : "fx:id=\"idAgentColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert idColumn != null : "fx:id=\"idColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert lNameColumn != null : "fx:id=\"lNameColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert postCodeColumn != null : "fx:id=\"postCodeColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert provColumn != null : "fx:id=\"provColumn\" was not injected: check your FXML file 'Customer.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'Customer.fxml'.";

        populateTableView();
        displayCustomers();

        //open create customer window
        NewBut.setOnAction(e -> {
            mode = "Add";
            CustomerEditor(null, mode);
            FXMLLoader fxmlLoader = new FXMLLoader(CustomerApplication.class.getResource("CustomerAdd.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            CustomerAddController controller = fxmlLoader.getController();
            controller.setMode(mode);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Customer Editor");
            stage.setScene(scene);
            stage.showAndWait();
            displayCustomers();

        });

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>(){
            @Override
            public void changed(ObservableValue<? extends Customer> observableValue, Customer oldValue, Customer newValue) {
                int index = tableView.getSelectionModel().getSelectedIndex();
                newValue = tableView.getSelectionModel().getSelectedItem();
                if(tableView.getSelectionModel().isSelected(index)) {
                    Customer NewValue = newValue;
                    Platform.runLater(() -> {
                        mode = "Edit";
                        CustomerEditor(NewValue, mode);
                    });
                }
            }
        });
    }

    //pop up window for update/create customer
    private void CustomerEditor(Customer customer, String mode) {
        FXMLLoader fxmlLoader = new FXMLLoader(CustomerApplication.class.getResource("CustomerAdd.fxml"));
       Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        CustomerAddController controller = fxmlLoader.getController();
        controller.setMode(mode);
        if(mode.equalsIgnoreCase("Edit")) {
            controller.setCustomerPage(customer);
        }
       // if(mode.equalsIgnoreCase("Add")) {
       //     controller.setMode("Add");}
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Customer Editor");
        stage.setScene(scene);
        stage.showAndWait();
        displayCustomers();
    }

    private void populateTableView() {//fill table view
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        bPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerBusPhone"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        fNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerFirstName"));
        hPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerHomePhone"));
        lNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerLastName"));
        postCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostCode"));
        provColumn.setCellValueFactory(new PropertyValueFactory<>("customerProvince"));
        idAgentColumn.setCellValueFactory(new PropertyValueFactory<>("agentId"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    //load customers into tableview
    public void displayCustomers(){
       entries.clear();
        try {
            entries = CustomerDB.getCustomers();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load Customers table contents", e);
        }
        tableView.setItems(entries);

    }
}
