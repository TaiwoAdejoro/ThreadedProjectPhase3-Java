package org.example.travelexpertsphase3desktop.Dao;

import org.example.travelexpertsphase3desktop.DBconnection;
import org.example.travelexpertsphase3desktop.Models.Suppliers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDAO {

    public static List<Suppliers> getSuppliers() {

        List<Suppliers> suppliersList = new ArrayList<>();
        DBconnection db = new DBconnection();
        String query = "select * from suppliers";

        try (Connection conn = db.getConnection();
             Statement stmnt = conn.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {

            while (rs.next()) {
                int supplierId = rs.getInt("supplier_id");
                String supplierName = rs.getString("supplier_name");

                Suppliers supp = new Suppliers(supplierId, supplierName);
                suppliersList.add(supp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suppliersList;

    }

}
