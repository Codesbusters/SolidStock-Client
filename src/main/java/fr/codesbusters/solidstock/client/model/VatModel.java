package fr.codesbusters.solidstock.client.model;

import javafx.beans.property.*;

public class VatModel {

    private final IntegerProperty id = new SimpleIntegerProperty();

    private final DoubleProperty rate = new SimpleDoubleProperty();

    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty percentage = new SimpleStringProperty("");

    public VatModel(int id, double rate, String description, String percentage) {
        setId(id);
        setRate(rate);
        setDescription(description);
        setPercentage(percentage);
    }

    public static VatModel ofSplit(int id, double rate, String description, String percentage) {
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

    public double getRate() {
        return rate.get();
    }

    public void setRate(double rate) {
        this.rate.set(rate);
    }

    public DoubleProperty rateProperty() {
        return rate;
    }

    public String getDisplay() {
        return getId() + " - " + getPercentage();
    }
}
