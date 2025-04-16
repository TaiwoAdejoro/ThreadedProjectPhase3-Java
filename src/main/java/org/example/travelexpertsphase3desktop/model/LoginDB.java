package org.example.travelexpertsphase3desktop.model;

import org.example.travelexpertsphase3desktop.Utils.DbConfig;
import org.example.travelexpertsphase3desktop.Utils.HashingUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import static org.example.travelexpertsphase3desktop.Utils.HashingUtil.hashPassword;

/**
 * LoginDB - Handles User Authentication
 */
public class LoginDB {

    public static Login authenticateUser(String username, String password) {
        String sql = "SELECT AgentId AS UserID, AgtEmail AS Email, PasswordHash, 'Agent' AS Role "
                + "FROM agents WHERE AgtEmail = ? "
                + "UNION "
                + "SELECT ManagerID AS UserID, Email, Password, 'Agent Manager' AS Role "
                + "FROM agentmanagers WHERE Email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // ✅ Debugging Before Query Execution
            System.out.println("DEBUG: Username entered = " + username);
            System.out.println("DEBUG: Password entered = " + password);
            System.out.println("DEBUG: About to execute SQL query...");

            stmt.setString(1, username);  // For Agent Email
            stmt.setString(2, username);  // For Manager Email

            ResultSet rs = stmt.executeQuery();

            // ✅ Debugging After Query Execution
            System.out.println("DEBUG: SQL Query Executed Successfully");

            if (rs.next()) {
                // ✅ Display Raw Data from ResultSet
                System.out.println("DEBUG: Raw Data - UserID = " + rs.getInt("UserID"));
                System.out.println("DEBUG: Raw Data - Email = " + rs.getString("Email"));
                System.out.println("DEBUG: Raw Data - Role = " + rs.getString("Role"));

                // Correctly determine the password column
                String storedHash = rs.getString("PasswordHash");  // For Agents
                if (storedHash == null) {
                    storedHash = rs.getString("Password");         // For Managers
                }

                if (storedHash == null || storedHash.isEmpty()) {
                    System.out.println("❗ WARNING: No stored password hash found for " + username);
                    return null;
                }

                int userId = rs.getInt("UserID");
                String role = rs.getString("Role");

                // ✅ Display Assigned User Details
                System.out.println("DEBUG: User authenticated with AgentId = " + userId);

                // ✅ Final Verification with Both Hashing Methods
                if (verifyPassword(password, storedHash) || verifyPasswordSHA256(password, storedHash)) {
                    System.out.println("✅ User Found: " + username + " | Role: " + role);
                    return new Login(userId, username, role);
                } else {
                    System.out.println("❌ Login Failed: Incorrect password for " + username);
                }
            } else {
                System.out.println("❗ DEBUG: No user found for " + username);
            }
        } catch (SQLException e) {
            System.out.println("❗ SQL Exception Occurred: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }





//    public static Login authenticateUser(String email, String password) {
//        String query = "SELECT ManagerID AS UserID, Email, Password, 'Agent Manager' AS Role " +
//                "FROM agentmanagers WHERE Email = ? " +
//                "UNION " +
//                "SELECT AgentId AS UserID, AgtEmail AS Email, PasswordHash, 'Agent' AS Role " +
//                "FROM agents WHERE AgtEmail = ?";
//
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, email); // Manager Email
//            stmt.setString(2, email); // Agent Email
//
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                String storedHash = rs.getString("Password"); // Correct column name for agentmanagers
//                int userId = rs.getInt("UserID");
//                String role = rs.getString("Role");
//
//                // Try both hashing methods
//                if (verifyPassword(password, storedHash) || verifyPasswordSHA256(password, storedHash)) {
//                    System.out.println("User Found: " + email + " | Role: " + role);
//                    return new Login(userId, email, role);
//                } else {
//                    System.out.println("Login Failed: Incorrect password for " + email);
//                }
//            } else {
//                System.out.println("Login Failed: No user found for " + email);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


    // ✅ Existing SHA-512 Verifier
    private static boolean verifyPassword(String enteredPassword, String storedHash) {
        return hashPassword(enteredPassword).equalsIgnoreCase(storedHash);
    }

    // ✅ New SHA-256 Verifier
    private static boolean verifyPasswordSHA256(String enteredPassword, String storedHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(enteredPassword.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString().equalsIgnoreCase(storedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    // ✅ Method to Authenticate User
//    public static Login authenticateUser(String email, String password) {
//        String query = "SELECT AgentId AS UserID, AgtEmail AS Email, 'Agent' AS Role " +
//                "FROM agents WHERE AgtEmail = ? AND PasswordHash = SHA2(?, 512) " +
//                "UNION " +
//                "SELECT ManagerID AS UserID, Email, 'Agent Manager' AS Role " +
//                "FROM agentmanagers WHERE Email = ? AND Password = SHA2(?, 512)";
//
//
//
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, email);       // For Agent Email
//            stmt.setString(2, password);    // For Agent Password (hashed)
//            stmt.setString(3, email);       // For Manager Email
//            stmt.setString(4, password);    // For Manager Password (hashed)
////            stmt.setString(5, password);    // For Manager Password (hashed)
//
//
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                int userId = rs.getInt("UserID");
//                String role = rs.getString("Role");
//
//                return new Login(userId, email, role); // Pass role correctly
//            } else {
//                System.out.println("Invalid Username or Password");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null; // Login failed
//    }

    // ✅ SHA-512 Hashing Function
//    private static String hashPassword(String password) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-512");
//            byte[] hash = md.digest(password.getBytes());
//            StringBuilder hexString = new StringBuilder();
//
//            for (byte b : hash) {
//                hexString.append(String.format("%02x", b));
//            }
//
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }

    // ✅ Verify Password
//    private static boolean verifyPassword(String enteredPassword, String storedHash) {
//        String hashedPassword = hashPassword(enteredPassword);
//        return hashedPassword.equalsIgnoreCase(storedHash);
//    }

    // ✅ Database Connection using DbConfig
    private static Connection getConnection() {
        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to database", e);
        }
    }
}
