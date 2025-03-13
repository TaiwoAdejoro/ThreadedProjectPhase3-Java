package org.example.travelexpertsphase3desktop.DAO;

import javafx.scene.control.Alert;
import org.example.travelexpertsphase3desktop.DBconnection;
import org.example.travelexpertsphase3desktop.model.Suppliers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Created Class for SQLQueries
 * All methods for SupplierDB manipulation
 */

public class SuppliersDAO {

    public static List<Suppliers> getSuppliers() {
        List<Suppliers> suppliersList = new ArrayList<>();
        DBconnection db = new DBconnection();
        String query = "select * from suppliers inner join products_suppliers on suppliers.SupplierId=products_suppliers.SupplierId";

        try (Connection conn = db.getConnection();
             Statement stmnt = conn.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            //receive info from Suppliers DB to insert into new List
            while (rs.next()) {
                int supplierId = rs.getInt("SupplierID");
                String supplierName = rs.getString("SupName");
                int productId = rs.getInt("ProductId");
                Suppliers supp = new Suppliers(supplierId, supplierName, productId);
                suppliersList.add(supp);
            }
        } catch (SQLException e) {
            System.out.println("There was an error connecting to the database");
            e.printStackTrace();
        }
        return suppliersList;
    }

    public void addSupplier(Integer supplierId, String supplierName) {
        String query = "insert into suppliers values(?,?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmnt = conn.prepareStatement(query)) {
            //receive supplier Information to input into database
            stmnt.setInt(1, supplierId);
            stmnt.setString(2, supplierName);
            try {
                stmnt.executeUpdate();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Sorry, That supplier ID already exists");
            }
        } catch (SQLException e) {
            System.out.println("There was an error connecting to the database");
            e.printStackTrace();
        }
    }

    public void updateSupplier(String supplierName, Integer supplierId) {
        String query = "update suppliers set SupName=? where SupplierId=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmnt = conn.prepareStatement(query)) {
            //receive supplier name and id for update
            stmnt.setString(1, supplierName);
            stmnt.setInt(2, supplierId);
            stmnt.executeUpdate();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Sorry, There was an error updating the supplier");
            System.out.println("There was an error connecting to the database");
            e.printStackTrace();
        }
    }

    public void deleteSupplier(int supplierId) {
        String query = "delete from suppliers where SupplierId=?";
        try (Connection conn = DBconnection.getConnection();
        PreparedStatement stmnt = conn.prepareStatement(query)) {
            //check for supplier ID for deletion
            stmnt.setInt(1, supplierId);
            stmnt.executeUpdate();
        } catch (SQLException r) {
            System.out.println("There was an error connecting to the database");
            r.printStackTrace();
        }
    }
}
