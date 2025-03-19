package com.example.backend.config;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class Config {
    final static InputStream CONFIG = Config.class.getClassLoader().getResourceAsStream("application.properties");

    public static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(CONFIG);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}