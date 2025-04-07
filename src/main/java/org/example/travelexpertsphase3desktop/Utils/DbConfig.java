package org.example.travelexpertsphase3desktop.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConfig {
    //object of class properties to access any *.properties file
    private static Properties prop = new Properties();
    //create static block of code
    static {
        //reading the db.properties file
        try(InputStream in = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new RuntimeException("db.properties file not found");
            }
            //loads the input stream from the .properties files into Properties object
            prop.load(in);

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading properties file", e);
        }
    }

    //read from properties object
    public static String getProperty(String key) {
        String value = prop.getProperty(key);
        if (value == null) {

        }
        return value;
    }

    // Get Database Connection
    public static Connection getConnection() throws SQLException {
        String url = getProperty("url");
        String user = getProperty("user");
        String password = getProperty("password");

        if (url == null || user == null || password == null) {
            throw new RuntimeException("Database configuration missing. Check your `db.properties` file.");
        }

        return DriverManager.getConnection(url, user, password);
    }
}
