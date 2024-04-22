package fr.codesbusters.solidstock.client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationPropertiesReader {
    private final Properties properties;

    public ApplicationPropertiesReader() {
        properties = new Properties();
        try {
            // Charge le fichier application.properties depuis les ressources
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
