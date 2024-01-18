package fr.codesbusters.solidstock.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SupplierModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");

    private final StringProperty address = new SimpleStringProperty("");
    private final StringProperty additionnalAddress = new SimpleStringProperty("");
    private final StringProperty zipCode = new SimpleStringProperty("");
    private final StringProperty city = new SimpleStringProperty("");
    private final StringProperty country = new SimpleStringProperty("");
    private final StringProperty phone = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");
    private final StringProperty website = new SimpleStringProperty("");

    public SupplierModel(int id, String name, String address, String additionnalAddress, String zipCode, String city, String phone, String email, String website, String country) {
        setID(id);
        setName(name);
        setAddress(address);
        setAdditionnalAddress(additionnalAddress);
        setZipCode(zipCode);
        setCity(city);
        setCountry(country);
        setPhone(phone);
        setEmail(email);
        setWebsite(website);
    }

    public static SupplierModel ofSplit(int id, String name, String address, String additionnalAddress, String zipCode, String city, String phone, String email, String website, String country) {
        return new SupplierModel(id, name, address, additionnalAddress, zipCode, city, phone, email, website, country);
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

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getAdditionnalAddress() {
        return additionnalAddress.get();
    }

    public void setAdditionnalAddress(String additionnalAddress) {
        this.additionnalAddress.set(additionnalAddress);
    }

    public StringProperty additionnalAddressProperty() {
        return additionnalAddress;
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

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
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

    public String getWebsite() {
        return website.get();
    }

    public void setWebsite(String website) {
        this.website.set(website);
    }

    public StringProperty websiteProperty() {
        return website;
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
}
