package fr.codesbusters.solidstock.business;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.*;

@Getter
@Setter
@Data
@ToString
public class Status {
    private final StringProperty name = new SimpleStringProperty("");

    public Status(String name) {
        setName(name);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }
}
