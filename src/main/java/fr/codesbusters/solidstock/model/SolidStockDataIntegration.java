package fr.codesbusters.solidstock.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SolidStockDataIntegration {

    public static final ObservableList<String> languages;
    public static final ObservableList<UsersModel> users;
    public static final ObservableList<RoleModel> roles;

    static {
        languages = FXCollections.observableArrayList(
                "Français",
                "English"
        );

        users = FXCollections.observableArrayList(
                UsersModel.ofSplit(1, "Jean", "Didier", 3, "bonjouurtoi", 3, "Ouvrier", "Fatiguant", "didier.jean@solidstock.fr", "01 02 03 04 05", "DidiJO"),
                UsersModel.ofSplit(2, "Jean", "Robert", 2, "salut", 6, "comptable", "Chiant à mourir", "robert.jean@solidstock.fr", "01 02 03 04 05", "RobJO"),
                UsersModel.ofSplit(3, "Jean", "Pascal", 1, "hello", 8, "chef", "Je commande", "pascal.jean@solidstock.fr", "01 02 03 04 05", "PascJO"),
                UsersModel.ofSplit(4, "Jean", "Marie", 2, "buenos dias", 9, "patron", "Je fais ce que je veux", "marie.jean@solidstock.fr", "01 02 03 04 05", "MarreJO"),
                UsersModel.ofSplit(5, "Jean", "Pierre", 5, "hey", 2, "actionnaire", "J'aime l'argent", "pierre.jean@solidstock.fr", "01 02 03 04 05", "CaillouxJO"),
                UsersModel.ofSplit(6, "Jean", "François", 6, "yo", 5, "Livreur", "Je vais vite", "françois.jean@solidstock.fr", "01 02 03 04 05", "FRJO")
        );

        roles = FXCollections.observableArrayList(
                RoleModel.ofSplit(1, "Administrateur", "Contrôle tout"),
                RoleModel.ofSplit(2, "Superviseur", "Peut réaliser toute actions non critiques"),
                RoleModel.ofSplit(3, "Gestionnaire", "Peut gérer le stock"),
                RoleModel.ofSplit(4, "Vendeur", "Peut voir le stock et vendre"),
                RoleModel.ofSplit(5, "Visiteur", "Peut accéder uniquement au site web")
        );

    }
}
