package fr.codesbusters.solidstock.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VatModel {

    private final IntegerProperty id = new SimpleIntegerProperty();

    private final StringProperty rate = new SimpleStringProperty("");

    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty percentage = new SimpleStringProperty("");

    public VatModel(int id, String rate, String description, String percentage) {
        setId(id);
        setRate(rate);
        setDescription(description);
        setPercentage(percentage);
    }



    public static VatModel ofSplit(int id, String rate, String description, String percentage) {
        return new VatModel(id, rate, description, percentage);
    }

    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getPercentage() {
        return percentage.get();
    }

    public void setPercentage(String percentage) {
        this.percentage.set(percentage);
    }

    public StringProperty percentageProperty() {
        return percentage;
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

    public String getRate() {
        return rate.get();
    }

    public void setRate(String rate) {
        this.rate.set(rate);
    }

    public StringProperty rateProperty() {
        return rate;
    }
}
