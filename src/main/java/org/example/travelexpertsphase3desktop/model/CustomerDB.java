package org.example.travelexpertsphase3desktop.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertsphase3desktop.Utils.DbConfig;

import java.sql.*;

public class CustomerDB {

    //method to get db connection
    public static Connection getConnection() {
//        String url = "jdbc:mysql://localhost:3306/travelexperts";
//       String user = "root";
//       String password = "@Datetime123";

        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");

        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("could not connect to database");
        }
        return conn;
    }

    // get customers
    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        Customer customer;
        // create connection using static method
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from customers");
        while (rs.next()) {//iterate each row of result from db
            customer = new Customer(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11),
                    rs.getInt(12)
            );
            customers.add(customer); //add created customers to list
        }
        return customers;  // return observable list of customers.
    }

    //insert customer
    public static int addCustomer (Customer customer) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        //columns name specified because CustomerId will be autogenerated from db
        String sql = "insert into customers (CustFirstName," +
                " CustLastName," +
                " CustAddress, " +
                "CustCity," +
                " CustProv, " +
                " CustPostal, " +
                " CustCountry, " +
                " CustHomePhone, " +
                " CustBusPhone, " +
                " CustEmail, " +
                "AgentId ) values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, customer.getFirstName());
        stmt.setString(2, customer.getLastName());
        stmt.setString(3, customer.getAddress());
        stmt.setString(4, customer.getCity());
        stmt.setString(5, customer.getProvince());
        stmt.setString(6, customer.getPostal());
        stmt.setString(7, customer.getCountry());
        stmt.setString(8, customer.getHomePhone());
        stmt.setString(9, customer.getBusPhone());
        stmt.setString(10, customer.getEmail());
        stmt.setInt(11, customer.getAgentId());

        //run query
        numAffectedRows = stmt.executeUpdate();
        System.out.println("Rows affected (insert): " + numAffectedRows);
        if (numAffectedRows > 0) {
            System.out.println("Customer Saved");
        } else {
            System.out.println("No rows were inserted.");
        }

        conn.close();
        return numAffectedRows;

    }

    //edit customer
    //edit customer
    public static int updateCustomer(int customerId, Customer customer) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        //columns names specified because CustomerId will be auto-generated from db
        String sql = "Update customers " +
                "SET CustFirstName = ?, " +
                "CustLastName = ?, " +
                "CustAddress = ?,"+
                "CustCity = ?, CustProv = ?, " +
        "CustPostal = ?, CustCountry = ?, CustHomePhone = ?, CustBusPhone = ?, CustEmail = ?, AgentId = ? " +
                "Where CustomerId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, customer.getFirstName());
        stmt.setString(2, customer.getLastName());
        stmt.setString(3, customer.getAddress());
        stmt.setString(4, customer.getCity());
        stmt.setString(5, customer.getProvince());
        stmt.setString(6, customer.getPostal());
        stmt.setString(7, customer.getCountry());
        stmt.setString(8, customer.getHomePhone());
        stmt.setString(9, customer.getBusPhone());
        stmt.setString(10, customer.getEmail());
        stmt.setInt(11, customer.getAgentId());
        stmt.setInt(12, customerId);

        //run query
        numAffectedRows = stmt.executeUpdate();
        System.out.println("Rows affected (update): " + numAffectedRows);
        if (numAffectedRows > 0) {
            System.out.println("✅ Customer updated successfully!");
        } else {
            System.out.println("❌ No rows were updated.");
        }

        conn.close();
        return numAffectedRows;
    }


    //delete
    public static int deleteCustomer(int customerId) throws SQLException {
        int numAffectedRows = 0;
        Connection conn = getConnection();
        String sql = "delete from customers where CustomerId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, customerId);
        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;
    }


}