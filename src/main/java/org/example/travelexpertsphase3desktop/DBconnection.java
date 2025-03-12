package org.example.travelexpertsphase3desktop;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBconnection {
    public static Properties loadProperties() {
        Properties prop = new Properties();

        try (InputStream in = DBconnection.class.getClassLoader().getResourceAsStream("DBconfig.properties")) {
            if (in == null) {
                System.out.println("Sorry, there was an error loading the Config file");
                return null;
            }
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public static Connection getConnection() {
        Properties prop = DBconnection.loadProperties();
        if (prop == null) {
            System.out.println("Sorry, there was an error loading the Config file");
            return null;
        }

        String url = prop.getProperty("URL");
        String user = prop.getProperty("USER");
        String password = prop.getProperty("PASS");

        try {
            System.out.println("Connection to " + url + " successful");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
