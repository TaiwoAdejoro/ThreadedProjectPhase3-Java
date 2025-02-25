package org.example.travelexpertsphase3desktop.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertsphase3desktop.Utils.DbConfig;

import java.sql.*;

public class PackageDB {
    // Method to get db connection
    public static Connection getConnection() {
//        String url = "jdbc:mysql://localhost:3306/travelexperts";
//        String user = "root";
//        String password = "Pr0f1224$";

        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");

        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not connect to database");
        }
        return conn;
    }

    // Get Packages
    public static ObservableList<Package> getPackages() throws SQLException {
        ObservableList<Package> packages = FXCollections.observableArrayList();
        Package travelpackage;
        // Get connection using static method
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM packages");
        while (rs.next()) {
            travelpackage = new Package(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getDouble(6),
                    rs.getDouble(7)
            );
            packages.add(travelpackage); // Add created packages to list
        }
        return packages; // Returns observable list of packages
    }

    //Insert Packages
    public static int addPackage(Package travelpackage) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "INSERT INTO packages (PkgName, PkgStartDate, PkgEndDate, PkgDesc, PkgBasePrice, PkgAgencyCommission) VALUES (?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, travelpackage.getName());
        stmt.setString(2, String.valueOf(travelpackage.getStartDate()));
        stmt.setString(3, String.valueOf(travelpackage.getEndDate()));
        stmt.setString(4, travelpackage.getDescription());
        stmt.setDouble(5, travelpackage.getPrice());
        stmt.setDouble(6, travelpackage.getCommission());

        //run query
        numAffectedRows = stmt.executeUpdate();
        return numAffectedRows;
    }

    public static int updatePackage(Package travelPackage) throws SQLException {
        String query = "UPDATE packages SET PkgName = ?, PkgStartDate = ?, PkgEndDate = ?, PkgDesc = ?, PkgBasePrice = ?, PkgAgencyCommission = ? WHERE PackageId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, travelPackage.getName());
            stmt.setString(2, travelPackage.getFormattedStartDate());
            stmt.setString(3, travelPackage.getFormattedEndDate());
            stmt.setString(4, travelPackage.getDescription());
            stmt.setDouble(5, travelPackage.getPrice());
            stmt.setDouble(6, travelPackage.getCommission());
            stmt.setInt(7, travelPackage.getId());
            return stmt.executeUpdate();
        }
    }

    public static int deletePackage(int packageId) throws SQLException {
        String query = "DELETE FROM packages WHERE PackageId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, packageId);
            return stmt.executeUpdate();
        }
    }
}
