package org.example.travelexpertsphase3desktop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/travelexperts?serverTimezone=UTC";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "Pr0f1224$"; // Replace with your MySQL password
    private static Connection conn = null;

    // Method to establish and return a database connection
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                // Load MySQL JDBC Driver (optional for modern versions but good practice)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish connection
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connection established successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Failed to connect to the database!");
                e.printStackTrace();
                throw e; // Rethrow the exception for handling in the calling method
            }
        }
        return conn;
    }

    // Method to close the database connection
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection!");
            e.printStackTrace();
        }
    }
}
