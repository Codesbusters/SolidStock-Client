package fr.codesbusters.solidstock.client.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntChecker {

    public static boolean isValidInput(String text) {
        boolean isValid = text.matches("\\d*\\.?\\d{0,2}");
        return isValid;
    }

    public static boolean isValidIntegerInput(String text) {
        boolean isValid = text.matches("\\d*");
        return isValid;
    }
}
