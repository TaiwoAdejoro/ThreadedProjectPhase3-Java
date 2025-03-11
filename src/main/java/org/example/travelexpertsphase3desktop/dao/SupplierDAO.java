package org.example.travelexpertsphase3desktop.dao;

import org.example.travelexpertsphase3desktop.models.Supplier;
import org.example.travelexpertsphase3desktop.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    // Retrieve suppliers for a given product
    public static List<Supplier> getSuppliersByProduct(int productId) {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT s.SupplierId, s.SupName FROM Suppliers s " +
                "JOIN Products_Suppliers ps ON s.SupplierId = ps.SupplierId " +
                "WHERE ps.ProductId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                suppliers.add(new Supplier(rs.getInt("SupplierId"), rs.getString("SupName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suppliers;
    }
}
