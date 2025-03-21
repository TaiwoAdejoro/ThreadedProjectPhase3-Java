package org.example.travelexpertsphase3desktop.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertsphase3desktop.utils.DbConfig;

import java.sql.*;

public class CustomerDB {
    //establish connection to database
    public static Connection getConnection(){
        //String url = DbConfig.getProperty("url");
        //String user = DbConfig.getProperty("user");
        //String password = DbConfig.getProperty("password");
        String url = "jdbc:mysql://localhost:3306/travelexperts";
        String user = "root";
        String password = "2187";
        Connection con;
        try {
            con = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException("Database Connection Issue");
        }
        return con;
    }

    // grab customers from DB
    public static ObservableList<Customer> getCustomers() throws SQLException{
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        Customer customer;
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

        while(rs.next()){
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
            customers.add(customer);

        }

        return customers;
    }
        //create new customer in DB
        public static int addCustomer(Customer customer) throws SQLException {
            Connection con = getConnection();
            int numAffectedRows = 0;
            String sql =  "INSERT INTO customers (CustFirstName, CustLastName, CustAddress, CustCity, CustProv, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail, AgentId) VALUES(?.?.?.?.?.?.?.?.?.?.?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            //stmt.setInt(1, customer.getCustomerId());

            stmt.setString(1,customer.getCustomerFirstName());
            stmt.setString(2,customer.getCustomerLastName());
            stmt.setString(3,customer.getCustomerAddress());
            stmt.setString(4,customer.getCustomerCity());
            stmt.setString(5,customer.getCustomerProvince());
            stmt.setString(6,customer.getCustomerPostCode());
            stmt.setString(7,customer.getCustomerCountry());
            stmt.setString(8,customer.getCustomerHomePhone());
            stmt.setString(9,customer.getCustomerBusPhone());
            stmt.setString(10,customer.getCustomerEmail());
            stmt.setInt(11,customer.getAgentId());
            numAffectedRows = stmt.executeUpdate();
            return numAffectedRows;

        }

        //update existing customers data in DB
    public static int UpdateCustomer(int CustId,Customer customer) throws SQLException {
        Connection con = getConnection();
        int numAffectedRows = 0;
        String sql = "UPDATE customers SET CustFirstName = ?, CustLastName = ?, CustAddress = ?, CustCity = ?, CustProv = ?, CustPostal = ?, CustCountry = ?, CustHomePhone = ?, CustBusPhone = ?, CustEmail = ?, AgentId = ? WHERE CustomerId = ?";

        PreparedStatement stmt = con.prepareStatement(sql);
        //stmt.setInt(1, customer.getCustomerId());
        stmt.setString(1,customer.getCustomerFirstName());
        stmt.setString(2,customer.getCustomerLastName());
        stmt.setString(3,customer.getCustomerAddress());
        stmt.setString(4,customer.getCustomerCity());
        stmt.setString(5,customer.getCustomerProvince());
        stmt.setString(6,customer.getCustomerPostCode());
        stmt.setString(7,customer.getCustomerCountry());
        stmt.setString(8,customer.getCustomerHomePhone());
        stmt.setString(9,customer.getCustomerBusPhone());
        stmt.setString(10,customer.getCustomerEmail());
        stmt.setInt(11,customer.getAgentId());
        stmt.setInt(12,CustId);

        numAffectedRows = stmt.executeUpdate();
        con.close();
        return numAffectedRows;


    }

    //delete a selected customer from DB
    public static int deleteCustomers(int CustId) throws SQLException {
        int numAffectedRows = 0;
        Connection conn = getConnection();
        String sql = "DELETE FROM customers WHERE CustomerId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, CustId);
        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;
    }



}