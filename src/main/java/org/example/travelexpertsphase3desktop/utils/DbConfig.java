package org.example.travelexpertsphase3desktop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DbConfig.class.getClassLoader().getResourceAsStream("dbconfig.properties")) {
            if (input == null) {
                throw new IOException("Database configuration file not found");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
