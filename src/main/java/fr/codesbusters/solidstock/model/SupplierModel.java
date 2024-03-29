package fr.codesbusters.solidstock.model;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SupplierModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty zipCode = new SimpleStringProperty("");
    private final StringProperty city = new SimpleStringProperty("");
    private final StringProperty country = new SimpleStringProperty("");
    private final StringProperty workPhone = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");
    private final BooleanProperty isDisabled = new SimpleBooleanProperty(false);

    public SupplierModel(int id, String name, String zipCode, String city, String workHome, String email, String country, boolean isDisabled) {
        setID(id);
        setName(name);
        setZipCode(zipCode);
        setCity(city);
        setCountry(country);
        setWorkPhone(workHome);
        setEmail(email);
        setIsDisabled(isDisabled);
    }

    public static SupplierModel ofSplit(int id, String name, String zipCode, String city, String workPhone, String email, String country, boolean isDisabled) {
        return new SupplierModel(id, name, zipCode, city, workPhone, email, country, isDisabled);
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

    public String getZipCode() {
        return zipCode.get();
    }

    public void setZipCode(String zipCode) {
        this.zipCode.set(zipCode);
    }

    public StringProperty zipCodeProperty() {
        return zipCode;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getWorkPhone() {
        return workPhone.get();
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone.set(workPhone);
    }

    public StringProperty workPhoneProperty() {
        return workPhone;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public StringProperty countryProperty() {
        return country;
    }

    public boolean getIsDisabled() {
        return isDisabled.get();
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled.set(isDisabled);
    }

    public BooleanProperty isDisabledProperty() {
        return isDisabled;
    }


}