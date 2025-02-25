package org.example.travelexpertsphase3desktop.Utils;

import java.io.InputStream;
import java.util.Properties;

public class DbConfig {
    private static Properties prop = new Properties();

    static {
        //reading the db.properties file
        try(InputStream in = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if(in == null) {
                throw new RuntimeException("db.properties file not found");
            }
            //loads the input stream from the .properties file into the Properties object
            prop.load(in);
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading properties", e);
        }
    }

    //read from properties object
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

}
