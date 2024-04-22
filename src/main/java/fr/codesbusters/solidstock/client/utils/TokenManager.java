package fr.codesbusters.solidstock.client.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TokenManager {
    static ApplicationPropertiesReader applicationPropertiesReader = new ApplicationPropertiesReader();
    private static final String TOKEN_FILE;

    static {
        String appDataPath = System.getenv("APPDATA");
        if (appDataPath != null) {
            // Système Windows
            TOKEN_FILE = appDataPath + File.separator + applicationPropertiesReader.getProperty("spring.application.name") + File.separator + "settings.ini";
        } else {
            // Système Unix/Linux
            TOKEN_FILE = System.getProperty("user.home") + File.separator + "." + applicationPropertiesReader.getProperty("spring.application.name") + File.separator + "settings.ini";
        }
    }

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

    public static boolean getRememberMe() {
        try {
            File file = new File(TOKEN_FILE);
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                bufferedReader.readLine();
                return Boolean.parseBoolean(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveToken(String token, boolean rememberMe) {
        try {
            File file = new File(TOKEN_FILE);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            java.io.FileWriter fileWriter = new java.io.FileWriter(file);
            fileWriter.write(token);
            fileWriter.write("\n");
            fileWriter.write(rememberMe + "");
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

    private String getTempDirectory() {
        return System.getProperty("java.io.tmpdir");
    }

}
