package fr.codesbusters.solidstock.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SolidStockDataIntegration {

    public static final ObservableList<String> languages;

    static {
        languages = FXCollections.observableArrayList(
                "Français",
                "English"
        );
    }
}