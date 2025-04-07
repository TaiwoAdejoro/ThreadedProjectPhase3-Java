package org.example.travelexpertsphase3desktop.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.example.travelexpertsphase3desktop.Utils.DbConfig;

import java.sql.*;

/**
 * DashBoardDB - Fetches all dashboard-related data from the database.
 * Handles both Agent and Agent Manager dashboard data.
 */
public class DashBoardDB {
    //  Database Connection Method
    public static Connection getConnection() {
        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");

        System.out.println("DEBUG: Connection URL = " + url);
        System.out.println("DEBUG: Connection User = " + user);

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            //  Add Debugging Statements Here
            System.out.println("DEBUG: Connected to database successfully");
            System.out.println("DEBUG: Using Database: " + conn.getCatalog());

            return conn;
        } catch (SQLException e) {
            System.out.println(" ERROR: Database connection failed.");
            e.printStackTrace();
            throw new RuntimeException("Could not connect to database", e);
        }
    }


    //  Fetch Dashboard Data (For Agent OR Agent Manager)
    public static DashBoard getDashboardData(int agentId, boolean isManager) {
        double totalSales = 0, totalCommission = 0;
        int totalCustomers = 0;
        ObservableList<PieChart.Data> topSellingPackages = FXCollections.observableArrayList();
        XYChart.Series<String, Number> salesOverTime = new XYChart.Series<>();
        XYChart.Series<String, Number> agentPerformance = new XYChart.Series<>();

        System.out.println("DEBUG: About to connect to the database...");
        Connection conn = getConnection();
        System.out.println("DEBUG: Connection method called successfully!");

        try {
            // Fetch Total Sales, Commission, and Customers
            String totalSalesQuery = getTotalSalesQuery(isManager);

            try (PreparedStatement stmt = conn.prepareStatement(totalSalesQuery)) {
                if (!isManager) {
                    System.out.println("DEBUG: AgentId passed to query = " + agentId);
                    stmt.setInt(1, agentId);
                }

                ResultSet rs = stmt.executeQuery();
                System.out.println("DEBUG: Query Executed Successfully");

                if (rs.next()) {
                    System.out.println("DEBUG: Raw Data - TotalSales = " + rs.getString("TotalSales"));
                    System.out.println("DEBUG: Raw Data - TotalCommission = " + rs.getString("TotalCommission"));
                    System.out.println("DEBUG: Raw Data - TotalCustomers = " + rs.getString("TotalCustomers"));

                    totalSales = rs.getDouble("TotalSales");
                    totalCommission = rs.getDouble("TotalCommission");
                    totalCustomers = rs.getInt("TotalCustomers");

                    System.out.println("Total Sales from DB = " + totalSales);
                    System.out.println(" Total Commission from DB = " + totalCommission);
                    System.out.println(" Total Customers from DB = " + totalCustomers);
                } else {
                    System.out.println(" WARNING: No data found for AgentId = " + agentId);
                }
            }

            // Retrieve Top-Selling Packages (Pie Chart)
            String topSellingPackagesQuery = getTopSellingPackagesQuery(isManager);

            try (PreparedStatement stmt = conn.prepareStatement(topSellingPackagesQuery)) {
                if (!isManager) {
                    stmt.setInt(1, agentId);
                    System.out.println("DEBUG: AgentId passed to query = " + agentId);
                }

                System.out.println("DEBUG: About to execute Top-Selling Packages Query...");
                ResultSet rs = stmt.executeQuery();
                System.out.println("DEBUG: Query Executed Successfully for Top-Selling Packages");

                //  Debugging Before Data Population
                boolean hasData = false; // Track if any data is retrieved

                while (rs.next()) {
                    hasData = true; // Mark data as found
                    String packageName = rs.getString("PkgName");
                    int sales = rs.getInt("Sales");

                    //  Debugging for each data point
                    System.out.println("DEBUG: Package = " + packageName + " | Sales = " + sales);

                    topSellingPackages.add(new PieChart.Data(packageName, sales));
                }

                //  Final Debugging — Total Data Points Added
                if (!hasData) {
                    System.out.println(" WARNING: No Top-Selling Packages found for AgentId = " + agentId);
                } else {
                    System.out.println(" Total Packages Added to Pie Chart = " + topSellingPackages.size());
                }
            }


            // Retrieve Sales Over Time (Line Chart)
            String salesOverTimeQuery = getSalesOverTimeQuery(isManager);

            try (PreparedStatement stmt = conn.prepareStatement(salesOverTimeQuery)) {
                if (!isManager) {
                    stmt.setInt(1, agentId);
                }

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String date = rs.getString("BookingDate");
                    double sales = rs.getDouble("Sales");
                    salesOverTime.getData().add(new XYChart.Data<>(date, sales));
                }
            }

            // Retrieve Agent Performance (Bar Chart) **Only for Managers**
            if (isManager) {
                String agentPerformanceQuery = getAgentPerformanceQuery();

                try (PreparedStatement stmt = conn.prepareStatement(agentPerformanceQuery)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String agent = "Agent " + rs.getInt("AgentId");
                        double sales = rs.getDouble("Sales");
                        agentPerformance.getData().add(new XYChart.Data<>(agent, sales));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return new DashBoard(totalSales, totalCommission, totalCustomers, topSellingPackages, salesOverTime, agentPerformance);
    }

    //  SQL Queries
    private static String getTotalSalesQuery(boolean isManager) {
        return isManager
                ? "SELECT SUM(IFNULL(bd.BasePrice, 0)) AS TotalSales, "
                + "SUM(IFNULL(bd.AgencyCommission, 0)) AS TotalCommission, "
                + "COUNT(DISTINCT b.CustomerId) AS TotalCustomers "
                + "FROM bookingdetails bd "
                + "JOIN bookings b ON bd.BookingId = b.BookingId"
                : "SELECT SUM(IFNULL(bd.BasePrice, 0)) AS TotalSales, "
                + "SUM(IFNULL(bd.AgencyCommission, 0)) AS TotalCommission, "
                + "COUNT(DISTINCT b.CustomerId) AS TotalCustomers "
                + "FROM bookingdetails bd "
                + "JOIN bookings b ON bd.BookingId = b.BookingId "
                + "WHERE b.AgentId = ?";
    }

    private static String getTopSellingPackagesQuery(boolean isManager) {
        return isManager
                ? "SELECT p.PkgName, COUNT(*) AS Sales FROM bookings b "
                + "JOIN packages p ON b.PackageId = p.PackageId "
                + "GROUP BY p.PkgName ORDER BY Sales DESC LIMIT 5"
                : "SELECT p.PkgName, COUNT(*) AS Sales FROM bookings b "
                + "JOIN packages p ON b.PackageId = p.PackageId "
                + "WHERE b.AgentId = ? "
                + "GROUP BY p.PkgName ORDER BY Sales DESC LIMIT 5";
    }

    private static String getSalesOverTimeQuery(boolean isManager) {
        return isManager
                ? "SELECT DATE(bd.TripStart) AS BookingDate, "
                + "SUM(bd.BasePrice) AS Sales "
                + "FROM bookingdetails bd "
                + "JOIN bookings b ON bd.BookingId = b.BookingId "
                + "GROUP BY DATE(bd.TripStart) ORDER BY BookingDate"
                : "SELECT DATE(bd.TripStart) AS BookingDate, "
                + "SUM(bd.BasePrice) AS Sales "
                + "FROM bookingdetails bd "
                + "JOIN bookings b ON bd.BookingId = b.BookingId "
                + "WHERE b.AgentId = ? "
                + "GROUP BY DATE(bd.TripStart) ORDER BY BookingDate";
    }

    private static String getAgentPerformanceQuery() {
        return "SELECT c.AgentId, SUM(p.PkgBasePrice) AS Sales FROM bookings b "
                + "JOIN customers c ON b.CustomerId = c.CustomerId "
                + "JOIN packages p ON b.PackageId = p.PackageId "
                + "GROUP BY c.AgentId ORDER BY Sales DESC";
    }
}
