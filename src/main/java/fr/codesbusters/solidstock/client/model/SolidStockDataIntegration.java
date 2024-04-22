package fr.codesbusters.solidstock.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SolidStockDataIntegration {

    public static final ObservableList<String> languages;

    public static final ObservableList<String> pages;

    static {
        languages = FXCollections.observableArrayList(
                "Français",
                "English"
        );

        pages = FXCollections.observableArrayList(
                "Tableau de bord",
                "Produits",
                "Clients",
                "Fournisseurs",
                "Mouvement de stock",
                "Devis",
                "Factures",
                "Commandes"
        );
    }
}
