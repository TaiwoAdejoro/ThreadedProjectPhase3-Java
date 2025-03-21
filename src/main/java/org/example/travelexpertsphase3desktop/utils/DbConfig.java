package org.example.travelexpertsphase3desktop.utils;

import java.io.InputStream;
import java.util.Properties;

public class DbConfig {

    private static Properties prop = new Properties();
        static {
            try (InputStream in = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (in != null) {
                    throw new RuntimeException("db.properties not found");
                }
                prop.load(in);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error loading properties", e);
            }

        }


    public static String getProperty(String key){
        return prop.getProperty(key);
    }
}
