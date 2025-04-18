package org.example.travelexpertsphase3desktop.dao;

import org.example.travelexpertsphase3desktop.database.DBConnection;
import org.example.travelexpertsphase3desktop.models.Package;
import org.example.travelexpertsphase3desktop.models.Product;
import org.example.travelexpertsphase3desktop.models.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDAO {

    // Get all packages with product-suppliers included
    public static List<org.example.travelexpertsphase3desktop.models.Package> getAllPackages() {
        List<Package> packageList = new ArrayList<>();
        String query = """
            SELECT p.PackageId, p.PkgName, p.PkgStartDate, p.PkgEndDate, p.PkgDesc,\s
                                        p.PkgBasePrice, p.PkgAgencyCommission,\s
                                        COALESCE(GROUP_CONCAT(CONCAT(pr.ProdName, ' - ', s.SupName) SEPARATOR ', '), '') AS ProductSuppliers
                                 FROM Packages p
                                 LEFT JOIN Packages_Products_Suppliers pps ON p.PackageId = pps.PackageId
                                 LEFT JOIN Products_Suppliers ps ON pps.ProductSupplierId = ps.ProductSupplierId
                                 LEFT JOIN Products pr ON ps.ProductId = pr.ProductId
                                 LEFT JOIN Suppliers s ON ps.SupplierId = s.SupplierId
                                 GROUP BY p.PackageId;
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Package pkg = new Package(
                        rs.getInt("PackageId"),
                        rs.getString("PkgName"),
                        rs.getDate("PkgStartDate").toLocalDate(),
                        rs.getDate("PkgEndDate") != null ? rs.getDate("PkgEndDate").toLocalDate() : null,
                        rs.getString("PkgDesc"),
                        rs.getDouble("PkgBasePrice"),
                        rs.getDouble("PkgAgencyCommission"),
                        rs.getString("ProductSuppliers") // Storing as a single string for TableView
                );
                packageList.add(pkg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packageList;
    }

    public static Product getProductForPackage(Package pkg) {
        String query = """
        SELECT pr.ProductId, pr.ProdName
        FROM Products pr
        JOIN Products_Suppliers ps ON pr.ProductId = ps.ProductId
        JOIN Packages_Products_Suppliers pps ON ps.ProductSupplierId = pps.ProductSupplierId
        WHERE pps.PackageId = ?
        LIMIT 1
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, pkg.getPackageId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(rs.getInt("ProductId"), rs.getString("ProdName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no product is found
    }

    public static Supplier getSupplierForPackage(Package pkg) {
        String query = """
        SELECT s.SupplierId, s.SupName
        FROM Suppliers s
        JOIN Products_Suppliers ps ON s.SupplierId = ps.SupplierId
        JOIN Packages_Products_Suppliers pps ON ps.ProductSupplierId = pps.ProductSupplierId
        WHERE pps.PackageId = ?
        LIMIT 1
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, pkg.getPackageId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Supplier(rs.getInt("SupplierId"), rs.getString("SupName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no supplier is found
    }


    // Insert a new package
    public static boolean insertPackage(Package pkg, int productId, int supplierId) {
        String query = """
        INSERT INTO Packages (PkgName, PkgStartDate, PkgEndDate, PkgDesc, PkgBasePrice, PkgAgencyCommission) 
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Insert package
            stmt.setString(1, pkg.getPackageName());
            stmt.setDate(2, Date.valueOf(pkg.getPkgStartDate()));
            stmt.setDate(3, (pkg.getPkgEndDate() != null) ? Date.valueOf(pkg.getPkgEndDate()) : null);
            stmt.setString(4, pkg.getPkgDesc());
            stmt.setDouble(5, pkg.getPkgBasePrice());
            stmt.setDouble(6, pkg.getPkgAgencyCommission());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false; // Insert failed
            }

            // Get the generated PackageId
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int packageId = generatedKeys.getInt(1);

                // Link product and supplier to the package
                return ProductDAO.addProductToPackage(packageId, productId, supplierId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Update an existing package
    public static boolean updatePackage(Package pkg, int productId, int supplierId) {
        String query = """
        UPDATE Packages 
        SET PkgName = ?, PkgStartDate = ?, PkgEndDate = ?, PkgDesc = ?, PkgBasePrice = ?, PkgAgencyCommission = ?
        WHERE PackageId = ?
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pkg.getPackageName());
            stmt.setDate(2, Date.valueOf(pkg.getPkgStartDate()));
            stmt.setDate(3, (pkg.getPkgEndDate() != null) ? Date.valueOf(pkg.getPkgEndDate()) : null);
            stmt.setString(4, pkg.getPkgDesc());
            stmt.setDouble(5, pkg.getPkgBasePrice());
            stmt.setDouble(6, pkg.getPkgAgencyCommission());
            stmt.setInt(7, pkg.getPackageId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Ensure the product-supplier association is updated
                return updateProductSupplierAssociation(pkg.getPackageId(), productId, supplierId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateProductSupplierAssociation(int packageId, int productId, int supplierId) {
        String deleteQuery = "DELETE FROM Packages_Products_Suppliers WHERE PackageId = ?";
        String insertQuery = """
        INSERT INTO Packages_Products_Suppliers (PackageId, ProductSupplierId)
        SELECT ?, ProductSupplierId FROM Products_Suppliers WHERE ProductId = ? AND SupplierId = ?
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Delete old association
            deleteStmt.setInt(1, packageId);
            deleteStmt.executeUpdate();

            // Insert new association
            insertStmt.setInt(1, packageId);
            insertStmt.setInt(2, productId);
            insertStmt.setInt(3, supplierId);

            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //To prevent duplicate package creation
    public static boolean doesPackageNameExist(String packageName) {
        String query = "SELECT COUNT(*) FROM Packages WHERE PkgName = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, packageName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Delete a package
    public static boolean deletePackage(int packageId) {
        Connection conn = null;
        PreparedStatement deleteRelationsStmt = null;
        PreparedStatement deletePackageStmt = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Step 1: Delete related records first
            deleteRelationsStmt = conn.prepareStatement("DELETE FROM packages_products_suppliers WHERE PackageId = ?");
            deleteRelationsStmt.setInt(1, packageId);
            deleteRelationsStmt.executeUpdate();

            // Step 2: Delete the package
            deletePackageStmt = conn.prepareStatement("DELETE FROM packages WHERE PackageId = ?");
            deletePackageStmt.setInt(1, packageId);
            int rowsDeleted = deletePackageStmt.executeUpdate();

            conn.commit(); // Commit transaction
            return rowsDeleted > 0;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback if error occurs
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally {
            // Close resources safely
            if (deleteRelationsStmt != null) {
                try {
                    deleteRelationsStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (deletePackageStmt != null) {
                try {
                    deletePackageStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}