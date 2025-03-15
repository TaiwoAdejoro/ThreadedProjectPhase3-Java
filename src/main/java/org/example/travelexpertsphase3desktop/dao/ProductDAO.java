package org.example.travelexpertsphase3desktop.dao;

import org.example.travelexpertsphase3desktop.models.Product;
import org.example.travelexpertsphase3desktop.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Retrieve all products
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                products.add(new Product(rs.getInt("ProductId"), rs.getString("ProdName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    // Associate a product and supplier with a package
    public static boolean addProductToPackage(int packageId, int productId, int supplierId) {
        String sql = "INSERT INTO Packages_Products_Suppliers (PackageId, ProductSupplierId) " +
                "SELECT ?, ProductSupplierId FROM Products_Suppliers WHERE ProductId = ? AND SupplierId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, packageId);
            stmt.setInt(2, productId);
            stmt.setInt(3, supplierId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}