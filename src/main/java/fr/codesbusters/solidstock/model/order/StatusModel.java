package fr.codesbusters.solidstock.model.order;

import fr.codesbusters.solidstock.business.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

public class StatusModel {
    public static final ObservableList<Status> status;

    static {
        status = FXCollections.observableArrayList(
                new Status("En attente de validation"),
                new Status("Annulé"),
                new Status("Validé")
        );
    }

    public static ObservableList<Status> getFilteredStatus() {
        ObservableList<Status> filteredStatus = FXCollections.observableArrayList();
        for (Status s : status) {
            if ("En attente de validation".equals(s.getName()) ||
                    "Annulé".equals(s.getName()) ||
                    "Validé".equals(s.getName())) {
                filteredStatus.add(s);
            }
        }
        return filteredStatus;
    }

    public static StringConverter<Status> getStatusStringConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(Status status) {
                if (status != null) {
                    return status.getName();
                } else {
                    return "";
                }
            }

            @Override
            public Status fromString(String string) {
                return null;
            }
        };
    }
}
