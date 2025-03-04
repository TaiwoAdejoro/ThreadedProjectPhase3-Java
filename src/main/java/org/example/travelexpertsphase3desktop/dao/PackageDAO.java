package org.example.travelexpertsphase3desktop.dao;

import org.example.travelexpertsphase3desktop.database.DBConnection;
import org.example.travelexpertsphase3desktop.models.Package;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PackageDAO {

    public static boolean insertPackage(Package pkg) {
        String query = "INSERT INTO Packages (PkgName, PkgStartDate, PkgEndDate, PkgDesc, PkgBasePrice, PkgAgencyCommission) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pkg.getPackageName());
            stmt.setDate(2, Date.valueOf(pkg.getPkgStartDate()));
            stmt.setDate(3, Date.valueOf(pkg.getPkgEndDate()));
            stmt.setString(4, pkg.getPkgDesc());
            stmt.setDouble(5, pkg.getPkgBasePrice());
            stmt.setDouble(6, pkg.getPkgAgencyCommission());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePackage(Package pkg) {
        String query = "UPDATE Packages SET PkgName=?, PkgStartDate=?, PkgEndDate=?, PkgDesc=?, PkgBasePrice=?, PkgAgencyCommission=? WHERE PackageId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pkg.getPackageName());
            stmt.setDate(2, Date.valueOf(pkg.getPkgStartDate()));
            stmt.setDate(3, Date.valueOf(pkg.getPkgEndDate()));
            stmt.setString(4, pkg.getPkgDesc());
            stmt.setDouble(5, pkg.getPkgBasePrice());
            stmt.setDouble(6, pkg.getPkgAgencyCommission());
            stmt.setInt(7, pkg.getPackageId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deletePackage(int packageId) {
        String query = "DELETE FROM Packages WHERE PackageId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, packageId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Package> getAllPackages() {
        List<Package> packageList = new ArrayList<>();
        String query = "SELECT PackageId, PkgName, PkgStartDate, PkgEndDate, PkgDesc, PkgBasePrice, PkgAgencyCommission FROM Packages";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Loop through the result set and create Package objects
            while (rs.next()) {
                int packageId = rs.getInt("PackageId");
                String packageName = rs.getString("PkgName");
                LocalDate pkgStartDate = rs.getDate("PkgStartDate").toLocalDate();
                LocalDate pkgEndDate = rs.getDate("PkgEndDate").toLocalDate();
                String pkgDesc = rs.getString("PkgDesc");
                double pkgBasePrice = rs.getDouble("PkgBasePrice");
                double pkgAgencyCommission = rs.getDouble("PkgAgencyCommission");

                Package pkg = new Package(packageId, packageName, pkgStartDate, pkgEndDate, pkgDesc, pkgBasePrice, pkgAgencyCommission);
                packageList.add(pkg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packageList;
    }
}
