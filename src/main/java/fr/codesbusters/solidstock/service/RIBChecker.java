package fr.codesbusters.solidstock.service;


import java.math.BigInteger;

public class RIBChecker {
    public static boolean isValidIBAN(String iban) {
        // Supprimer les espaces et convertir en majuscules
        iban = iban.replaceAll("\\s", "").toUpperCase();

        // Vérifier que la longueur de l'IBAN est correcte
        if (iban.length() < 4 || iban.length() > 34) {
            return false;
        }

        // Vérifier que les premiers 2 caractères sont des lettres
        if (!iban.substring(0, 2).matches("[A-Z]+")) {
            return false;
        }

        // Vérifier que les caractères restants sont des chiffres ou des lettres
        if (!iban.substring(2).matches("[0-9A-Z]+")) {
            return false;
        }

        // Réarranger l'IBAN
        String rearrangedIban = iban.substring(4) + iban.substring(0, 4);

        // Convertir les lettres en chiffres
        StringBuilder numericIban = new StringBuilder();
        for (int i = 0; i < rearrangedIban.length(); i++) {
            char c = rearrangedIban.charAt(i);
            if (Character.isLetter(c)) {
                numericIban.append(Character.getNumericValue(c));
            } else {
                numericIban.append(c);
            }
        }

        // Convertir en BigInteger
        BigInteger ibanNumber = new BigInteger(numericIban.toString());

        // Vérifier la divisibilité par 97
        return ibanNumber.mod(BigInteger.valueOf(97)).intValue() == 1;
    }
}