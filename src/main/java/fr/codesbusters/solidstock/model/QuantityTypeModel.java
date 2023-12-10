package fr.codesbusters.solidstock.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class QuantityTypeModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty unit = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");

    public QuantityTypeModel(int id, String name, String unit, String description) {
        setID(id);
        setName(name);
        setUnit(unit);
        setDescription(description);
    }

    public static QuantityTypeModel ofSplit(int id, String name, String unit, String description) {
        return new QuantityTypeModel(id, name, unit, description);
    }

    public int getID() {
        return id.get();
    }

    public void setID(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
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

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    @Override
    public String toString() {
        return getID() + " - " + getName() + " - " + getUnit();
    }
}