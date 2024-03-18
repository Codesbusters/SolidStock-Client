package fr.codesbusters.solidstock.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TokenManager {
    static ApplicationPropertiesReader applicationPropertiesReader = new ApplicationPropertiesReader();
    private static final String TOKEN_FILE = System.getenv("APPDATA") + "\\ " + applicationPropertiesReader.getProperty("spring.application.name")  + "\\settings.ini";

    public static String getToken() {
        try {
            File file = new File(TOKEN_FILE);
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                return bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveToken(String token) {
        try {
            File file = new File(TOKEN_FILE);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            java.io.FileWriter fileWriter = new java.io.FileWriter(file);
            fileWriter.write(token);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteToken() {
        File file = new File(TOKEN_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean tokenExists() {
        File file = new File(TOKEN_FILE);
        return file.exists();
    }

}
