/**
 * Sample Skeleton for 'DashBoard-view.fxml' Controller Class
 */

package org.example.travelexpertsphase3desktop;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.travelexpertsphase3desktop.model.DashBoard;
import org.example.travelexpertsphase3desktop.model.DashBoardDB;


public class DashBoardController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="barAgentPerformance"
    private BarChart<String, Number> barAgentPerformance; // Value injected by FXMLLoader

    @FXML // fx:id="btnAgency"
    private Button btnAgency; // Value injected by FXMLLoader

    @FXML // fx:id="btnAgent"
    private Button btnAgent; // Value injected by FXMLLoader

    @FXML // fx:id="btnAgentManager"
    private Button btnAgentManager; // Value injected by FXMLLoader

    @FXML // fx:id="btnChatDashboard"
    private Button btnChatDashboard; // Value injected by FXMLLoader

    @FXML // fx:id="btnCustomer"
    private Button btnCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="btnHome"
    private Button btnHome; // Value injected by FXMLLoader

    @FXML // fx:id="btnLogout"
    private Button btnLogout; // Value injected by FXMLLoader

    @FXML // fx:id="btnPackage"
    private Button btnPackage; // Value injected by FXMLLoader

    @FXML // fx:id="btnProduct"
    private Button btnProduct; // Value injected by FXMLLoader

    @FXML // fx:id="btnSupplier"
    private Button btnSupplier; // Value injected by FXMLLoader

    @FXML // fx:id="imgAgentAvatar"
    private ImageView imgAgentAvatar; // Value injected by FXMLLoader

    @FXML // fx:id="lblTotalCommission"
    private Label lblTotalCommission; // Value injected by FXMLLoader

    @FXML // fx:id="lblTotalCustomers"
    private Label lblTotalCustomers; // Value injected by FXMLLoader

    @FXML // fx:id="lblTotalSales"
    private Label lblTotalSales; // Value injected by FXMLLoader

    @FXML // fx:id="lineSalesOverTime"
    private LineChart<String, Number> lineSalesOverTime; // Value injected by FXMLLoader

    @FXML // fx:id="pieTopSellingPackages"
    private PieChart pieTopSellingPackages; // Value injected by FXMLLoader

    @FXML // fx:id="tfSearchDashboard"
    private TextField tfSearchDashboard; // Value injected by FXMLLoader

    @FXML // fx:id="txtUsername"
    private TextField txtUsername; // Value injected by FXMLLoader

    private int loggedInAgentId;
    private boolean isManager;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert barAgentPerformance != null : "fx:id=\"barAgentPerformance\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert btnAgency != null : "fx:id=\"btnAgency\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert btnAgent != null : "fx:id=\"btnAgent\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert btnAgentManager != null : "fx:id=\"btnAgentManager\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert btnChatDashboard != null : "fx:id=\"btnChatDashboard\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert btnCustomer != null : "fx:id=\"btnCustomer\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert btnHome != null : "fx:id=\"btnHome\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert btnLogout != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert btnPackage != null : "fx:id=\"btnPackage\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert btnProduct != null : "fx:id=\"btnProduct\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert btnSupplier != null : "fx:id=\"btnSupplier\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert imgAgentAvatar != null : "fx:id=\"imgAgentAvatar\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert lblTotalCommission != null : "fx:id=\"lblTotalCommission\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert lblTotalCustomers != null : "fx:id=\"lblTotalCustomers\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert lblTotalSales != null : "fx:id=\"lblTotalSales\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert lineSalesOverTime != null : "fx:id=\"lineSalesOverTime\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert pieTopSellingPackages != null : "fx:id=\"pieTopSellingPackages\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert tfSearchDashboard != null : "fx:id=\"tfSearchDashboard\" was not injected: check your FXML file 'DashBoard-view.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'DashBoard-view.fxml'.";

        // ✅ Attach an event listener to the search field
        tfSearchDashboard.setOnKeyReleased(event -> searchDashboard());

        // ✅ Load default dashboard data on startup
       // loadDashboardData();
        Platform.runLater(this::loadDashboardData);
        setDefaultAvatar();
        setupButtonNavigation();
    }

    /**
     * Loads Dashboard data based on logged-in user.
     */

    private void loadDashboardData() {
        DashBoard dashboardData = DashBoardDB.getDashboardData(loggedInAgentId, isManager);

        lblTotalSales.setText(String.format("$%,.2f", dashboardData.getTotalSales()));
        lblTotalCommission.setText(String.format("$%,.2f", dashboardData.getTotalCommission()));
        lblTotalCustomers.setText(String.valueOf(dashboardData.getTotalCustomers()));

        // ✅ Clear and reload Pie Chart data
        pieTopSellingPackages.getData().clear();
        pieTopSellingPackages.getData().addAll(dashboardData.getTopSellingPackages());

        // ✅ Load Line Chart data
        lineSalesOverTime.getData().clear();
        lineSalesOverTime.getData().add(dashboardData.getSalesOverTime());

        // ✅ Load Bar Chart data (For Managers Only)
        if (isManager) {
            barAgentPerformance.getData().clear();
            barAgentPerformance.getData().add(dashboardData.getAgentPerformance());
            barAgentPerformance.setVisible(true);
        } else {
            barAgentPerformance.setVisible(false);
        }

        System.out.println("DEBUG: Dashboard data loaded successfully.");
    }

//    private void loadDashboardData() {
//        System.out.println("DEBUG: loggedInAgentId in loadDashboardData() = " + loggedInAgentId);
//        DashBoard dashboardData = DashBoardDB.getDashboardData(loggedInAgentId, isManager);
//
//        lblTotalSales.setText(String.format("$%,.2f", dashboardData.getTotalSales()));
//        lblTotalCommission.setText(String.format("$%,.2f", dashboardData.getTotalCommission()));
//        lblTotalCustomers.setText(String.valueOf(dashboardData.getTotalCustomers()));
//
//
//        // Populate Pie Chart (Top Selling Packages)
//        pieTopSellingPackages.getData().setAll(dashboardData.getTopSellingPackages());
//
//        // Populate Line Chart (Sales Over Time)
//        lineSalesOverTime.getData().clear();
//        lineSalesOverTime.getData().add(dashboardData.getSalesOverTime());
//
//        // Populate Bar Chart (Agent Performance)
//        if (isManager) {
//            barAgentPerformance.getData().clear();
//            barAgentPerformance.getData().add(dashboardData.getAgentPerformance());
//            barAgentPerformance.setVisible(true);
//        } else {
//            barAgentPerformance.setVisible(false);
//        }
//    }

    /**
     * Sets a default avatar for all users.
     */
    private void setDefaultAvatar() {
        Image defaultAvatar = new Image(getClass().getResourceAsStream("/images/agava.jpg"));
        imgAgentAvatar.setImage(defaultAvatar);
    }

    /**
     * Configures button navigation.
     */
    private void setupButtonNavigation() {
        btnHome.setOnAction(e -> openView("DashBoard-view.fxml"));
        btnAgent.setOnAction(e -> openView("Agent-view.fxml"));
        btnAgency.setOnAction(e -> openView("Agency-view.fxml"));
        btnAgentManager.setOnAction(e -> openView("AgentManager-view.fxml"));
        btnCustomer.setOnAction(e -> openView("Customer-view.fxml"));
        btnPackage.setOnAction(e -> openView("Package-view.fxml"));
        btnProduct.setOnAction(e -> openView("products-view.fxml"));
        btnSupplier.setOnAction(e -> openView("Supplier-view.fxml"));
        btnChatDashboard.setOnAction(e -> openView("Chat-view.fxml"));
        btnLogout.setOnAction(e -> logout());
    }

    /**
     * Opens a new FXML view.
     * @param fxmlFile The FXML file to open.
     */
    private void openView(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles Logout and redirects to Login.
     */
    private void logout() {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();
        openView("Login-view.fxml");
    }

    /**
     * Handles search functionality.
     */
    @FXML
    private void searchDashboard() {
        String keyword = tfSearchDashboard.getText().trim();
        if (keyword.isEmpty()) {
            loadDashboardData(); // Reloads the original data if search is empty
            return;
        }

        // Filtered data logic
        ObservableList<PieChart.Data> filteredPackages = pieTopSellingPackages.getData().filtered(
                data -> data.getName().toLowerCase().contains(keyword.toLowerCase())
        );

        // Clear the chart before adding new filtered data
        pieTopSellingPackages.getData().clear();

        // Add filtered data back into the PieChart
        pieTopSellingPackages.getData().addAll(filteredPackages);

        System.out.println("DEBUG: Filtered packages count = " + filteredPackages.size());
    }




    /**
     * Sets the logged-in user details.
     * @param agentId Agent ID
     * @param isManager True if user is an Agent Manager, False if an Agent.
     */
    public void setLoggedInUser(int agentId, boolean isManager) {
        System.out.println("DEBUG: setLoggedInUser() called with agentId = " + agentId);
        this.loggedInAgentId = agentId;
        this.isManager = isManager;
        System.out.println("DEBUG: loggedInAgentId set to = " + loggedInAgentId);
        txtUsername.setText(isManager ? "Agent Manager" : "Agent " + agentId);
        loadDashboardData();
    }

}
