/**
 * Sample Skeleton for 'products-view.fxml' Controller Class
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.model.Agent;
import org.example.travelexpertsphase3desktop.model.Product;
import org.example.travelexpertsphase3desktop.model.ProductDB;

public class ProductManagementController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddProduct"
    private Button btnAddProduct; // Value injected by FXMLLoader

    @FXML // fx:id="colProdId"
    private TableColumn<Product, Integer> colProdId; // Value injected by FXMLLoader

    @FXML // fx:id="colProdname"
    private TableColumn<Product, String> colProdname; // Value injected by FXMLLoader

    @FXML // fx:id="tvProducts"
    private TableView<Product> tvProducts; // Value injected by FXMLLoader

    //global list of products
    private ObservableList<Product> data = FXCollections.observableArrayList();

    String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAddProduct != null : "fx:id=\"btnAddProduct\" was not injected: check your FXML file 'products-view.fxml'.";
        assert colProdId != null : "fx:id=\"colProdId\" was not injected: check your FXML file 'products-view.fxml'.";
        assert colProdname != null : "fx:id=\"colProdname\" was not injected: check your FXML file 'products-view.fxml'.";
        assert tvProducts != null : "fx:id=\"tvProducts\" was not injected: check your FXML file 'products-view.fxml'.";

        //set up table columns
        setupProductTable();
        displayProduct();

        btnAddProduct.setOnAction(event -> {
            mode = "Add";

            openProductWindow(null, mode);// null because no agency exist when add new agency
        });

        tvProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observableValue, Product oldValue, Product newValue) {
            //get index of the selected agent and be sure it is not a deselection.
                int index = tvProducts.getSelectionModel().getSelectedIndex();
                if(tvProducts.getSelectionModel().isSelected(index)) {
                    Platform.runLater(() -> {
                        mode = "Edit";
                        openProductWindow(newValue, mode);
                    });

                }
            }
        });
    }

    private void openProductWindow(Product product, String mode) {
        FXMLLoader fxmlLoader = new FXMLLoader(DashBoardManagement.class.getResource("add-products-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddProductsController controller = fxmlLoader.getController();
        controller.setMode(mode);

        if(mode.equalsIgnoreCase("Edit")) {
            controller.setProductForm(product);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Product Addition");
        stage.setScene(scene);
        stage.showAndWait();
        displayProduct();
    }

    private void setupProductTable() {
        colProdId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        colProdname.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
        //display products
    public void displayProduct() {
        //clearing list for new data
        data.clear();
        try {
            //get products from DB
            data= ProductDB.getProducts();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load product table", e);
        }
        //populate table view
        tvProducts.setItems(data);
    }

}
