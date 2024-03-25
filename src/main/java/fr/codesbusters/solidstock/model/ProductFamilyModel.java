package fr.codesbusters.solidstock.model;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductFamilyModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");

    private final BooleanProperty isDisabled = new SimpleBooleanProperty(false);


    public ProductFamilyModel(int id, String name, String description, boolean isDisabled) {
        setID(id);
        setName(name);
        setDescription(description);
        setIsDisabled(isDisabled);
    }

    public static ProductFamilyModel ofSplit(int id, String name, String description, boolean isDisabled) {
        return new ProductFamilyModel(id, name, description, isDisabled);
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

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public boolean getIsDisabled() {
        return isDisabled.get();
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled.set(isDisabled);
    }

    public BooleanProperty isDisabledProperty() {
        return  isDisabled;
    }

}
