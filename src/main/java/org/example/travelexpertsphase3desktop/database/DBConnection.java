package org.example.travelexpertsphase3desktop.database;

import org.example.travelexpertsphase3desktop.utils.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn = null;

    // Method to establish and return a database connection
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                // Load MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Fetch database credentials from properties file
                String url = DbConfig.getProperty("db.url");
                String user = DbConfig.getProperty("db.user");
                String password = DbConfig.getProperty("db.password");

                // Establish connection
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Database connection established successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Failed to connect to the database!");
                e.printStackTrace();
                throw e; // Rethrow exception for handling in the calling method
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
