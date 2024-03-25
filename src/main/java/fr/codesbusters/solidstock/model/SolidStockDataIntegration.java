package fr.codesbusters.solidstock.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SolidStockDataIntegration {

    public static final ObservableList<String> languages;
    public static final ObservableList<UsersModel> users;
    public static final ObservableList<QuantityTypeModel> quantityType;
    public static final ObservableList<RoleModel> roles;

    public static final ObservableList<CustomerModel> customers;
    public static final ObservableList<OrdersModel> orders;

    public static final ObservableList<EstimateModel> estimates;

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

        quantityType = FXCollections.observableArrayList(
                QuantityTypeModel.ofSplit(1, "Gramme", "g", "Unitée de mesure de masse"),
                QuantityTypeModel.ofSplit(2, "Kilogramme", "kg", "Unitée de mesure de masse"),
                QuantityTypeModel.ofSplit(3, "Litre", "L", "Unitée de mesure de volume"),
                QuantityTypeModel.ofSplit(4, "Millilitre", "mL", "Unitée de mesure de volume"),
                QuantityTypeModel.ofSplit(5, "Piece", "pc", "Unitée de mesure de quantité"),
                QuantityTypeModel.ofSplit(6, "Mètre", "m", "Unitée de mesure de longueur"),
                QuantityTypeModel.ofSplit(7, "Centimètre", "cm", "Unitée de mesure de longueur"),
                QuantityTypeModel.ofSplit(8, "Millimètre", "mm", "Unitée de mesure de longueur"),
                QuantityTypeModel.ofSplit(9, "Kilomètre", "km", "Unitée de mesure de longueur")
        );

        roles = FXCollections.observableArrayList(
                RoleModel.ofSplit(1, "Administrateur", "Contrôle tout"),
                RoleModel.ofSplit(2, "Superviseur", "Peut réaliser toute actions non critiques"),
                RoleModel.ofSplit(3, "Gestionnaire", "Peut gérer le stock"),
                RoleModel.ofSplit(4, "Vendeur", "Peut voir le stock et vendre"),
                RoleModel.ofSplit(5, "Visiteur", "Peut accéder uniquement au site web")
        );

        customers = FXCollections.observableArrayList(
                CustomerModel.ofSplit(1, "LAURENT Michel", 5, "Rue du pont, 69696", false, "", 1, 1, "rib", 1),
                CustomerModel.ofSplit(2, "CORNADO Thibault", 5, "Rue du pont, 69696", false, "", 1, 1, "rib", 1),
                CustomerModel.ofSplit(3, "TACOS Renault", 5, "Rue du pont, 69696", false, "", 1, 1, "rib", 1),
                CustomerModel.ofSplit(4, "DELENNE Xavier", 5, "Rue du pont, 69696", false, "", 1, 1, "rib", 1)
        );


        orders = FXCollections.observableArrayList(
                OrdersModel.ofSplit(1, "Commande 1", "Description 1", 1, "LAURENT Michel", "01/01/2021", 1, 1, "En cours"),
                OrdersModel.ofSplit(2, "Commande 2", "Description 2", 2, "CORNADO Thibault", "01/01/2021", 2, 2, "Refusée"),
                OrdersModel.ofSplit(3, "Commande 3", "Description 3", 3, "TACOS Renault", "01/01/2021", 3, 3, "En cours"),
                OrdersModel.ofSplit(4, "Commande 4", "Description 4", 4, "DELENNE Xavier", "01/01/2021", 4, 4, "Validée"),
                OrdersModel.ofSplit(5, "Commande 5", "Description 5", 1, "LAURENT Michel", "01/01/2021", 5, 5, "Refusée")
        );

        estimates = FXCollections.observableArrayList(
                EstimateModel.ofSplit(1, "Devis 1", "Description 1", 1, "LAURENT Michel", "01/01/2021"),
                EstimateModel.ofSplit(2, "Devis 2", "Description 2", 2, "CORNADO Thibault", "01/01/2021"),
                EstimateModel.ofSplit(3, "Devis 3", "Description 3", 3, "TACOS Renault", "01/01/2021"),
                EstimateModel.ofSplit(4, "Devis 4", "Description 4", 4, "DELENNE Xavier", "01/01/2021"),
                EstimateModel.ofSplit(5, "Devis 5", "Description 5", 1, "LAURENT Michel", "01/01/2021")
        );
    }
}