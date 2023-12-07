package fr.codesbusters.solidstock.business;

import javafx.scene.control.Alert;

public class DialogType {

    public static final Alert.AlertType ERROR = Alert.AlertType.ERROR;
    public static final Alert.AlertType INFORMATION = Alert.AlertType.INFORMATION;
    public static final Alert.AlertType WARNING = Alert.AlertType.WARNING;
    public static final Alert.AlertType CONFIRMATION = Alert.AlertType.CONFIRMATION;
    public static final Alert.AlertType NONE = Alert.AlertType.NONE;
    private final Alert.AlertType alertType;
    public DialogType(Alert.AlertType alertType) {
        this.alertType = alertType;
    }

    public Alert.AlertType getAlertType() {
        return alertType;
    }


}
