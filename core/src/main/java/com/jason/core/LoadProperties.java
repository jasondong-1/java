package com.jason.core;

import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(LoadProperties.class.getClassLoader().getResourceAsStream("aa.properties"));
        System.out.println(properties.getProperty("name"));
    }
}
