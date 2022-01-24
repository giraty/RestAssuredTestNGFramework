package org.selenium.pom.utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    //constructor private
    // only this class should be able to create it's own instance
    private ConfigLoader(){
        properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
        // directly using the class
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }
    public String getBaseUrl(){
        String prop = properties.getProperty("baseUrl");
        if(prop != null){
            return prop;
        }
        else throw new RuntimeException("property baseUrl is not specified in config properties file");
    }
    public String getUsername(){
        String prop = properties.getProperty("username");
        if(prop != null){
            return prop;
        }
        else throw new RuntimeException("property username is not specified in config properties file");
    }
    public String getPassword(){
        String prop = properties.getProperty("password");
        if(prop != null){
            return prop;
        }
        else throw new RuntimeException("property password is not specified in config properties file");
    }

}
