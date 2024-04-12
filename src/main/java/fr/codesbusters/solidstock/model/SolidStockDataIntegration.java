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
    }
}