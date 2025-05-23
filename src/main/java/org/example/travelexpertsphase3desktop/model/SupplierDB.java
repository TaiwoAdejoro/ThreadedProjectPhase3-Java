package org.example.travelexpertsphase3desktop.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertsphase3desktop.Utils.DbConfig;

import java.sql.*;

public class SupplierDB {

    //method to get DB connection

    public static Connection getConnection() {
//        String url = "jdbc:mysql://localhost:3306/travelexperts";
//        String user = "root";
//        String password = "@Datetime123";

        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {

            e.printStackTrace(); // Print full error details
            throw new RuntimeException("Could not connect to database" + e.getMessage(), e);
        }
        return conn;

    }

    //get suppliers
    public static ObservableList<Supplier> getSupplier() throws SQLException {
        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        Supplier supplier;

        // create connection using static method
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM suppliers");
        while (rs.next()) {// iterate each row of result from db

            supplier = new Supplier(
                    rs.getInt(1),
                    rs.getString(2)
            );
            suppliers.add(supplier);  //add created supplier to list
        }
        return suppliers;  //returns observable list of suppliers
    }


    public static int addSupplier(Supplier supplier) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        //columns name specified because SupplierId will be autogenerated from db
        String sql = "INSERT INTO suppliers (SupName) " +
                "VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, supplier.getName());

        //run query
        numAffectedRows = stmt.executeUpdate();
        return numAffectedRows;
    }



    public static int updateSupplier(int supplierId, Supplier supplier) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        //columns name specified because Supplierid will be autogenerated from db
        String sql = "Update suppliers " +
                "SET SupName= ? " +
                "where SupplierId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, supplier.getName());
        stmt.setInt(2, supplierId);

        //run query
        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;
    }

    //delete
    public static int deleteSupplier(int supplierId) throws SQLException {
        int numAffectedRows = 0;
        Connection conn = getConnection();
        String sql = "Delete from suppliers where SupplierId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, supplierId);
        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;

    }


}
