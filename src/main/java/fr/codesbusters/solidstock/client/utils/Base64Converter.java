package fr.codesbusters.solidstock.client.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Slf4j
public class Base64Converter {


    public static String convertImageToBase64(File file) throws Exception {
        if (file.exists()) {
            log.info("File found : " + file.getPath());
            byte[] fileContent = Files.readAllBytes(Path.of(file.getPath()));
            return Base64.getEncoder().encodeToString(fileContent);
        } else {
            throw new Exception("File not found");
        }
    }
}
