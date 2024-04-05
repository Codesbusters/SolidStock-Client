package fr.codesbusters.solidstock.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SolidStockDataIntegration {

    public static final ObservableList<String> languages;
        public static final ObservableList<RoleModel> roles;

    public static final ObservableList<OrdersModel> orders;

    public static final ObservableList<EstimateModel> estimates;

    static {
        languages = FXCollections.observableArrayList(
                "Français",
                "English"
        );

        roles = FXCollections.observableArrayList(
                RoleModel.ofSplit(1, "Administrateur", "Contrôle tout"),
                RoleModel.ofSplit(2, "Superviseur", "Peut réaliser toute actions non critiques"),
                RoleModel.ofSplit(3, "Gestionnaire", "Peut gérer le stock"),
                RoleModel.ofSplit(4, "Vendeur", "Peut voir le stock et vendre"),
                RoleModel.ofSplit(5, "Visiteur", "Peut accéder uniquement au site web")
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
