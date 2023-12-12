package fr.codesbusters.solidstock.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ThirdPartyModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty firstName = new SimpleStringProperty("");
    private final StringProperty lastName = new SimpleStringProperty("");
    private final StringProperty city = new SimpleStringProperty("");
    private final IntegerProperty zipCode = new SimpleIntegerProperty();
    private final StringProperty address = new SimpleStringProperty("");
    private final IntegerProperty streetNumber = new SimpleIntegerProperty();
    private final StringProperty email = new SimpleStringProperty("");
    private final StringProperty mobilePhone = new SimpleStringProperty("");

    public ThirdPartyModel(int id, String firstName, String lastName, String city, int zipcode, String address, int streetNumber, String email, String mobilePhone) {
        setID(id);
        setFirstName(firstName);
        setLastName(lastName);
        setCity(city);
        setZipCode(zipcode);
        setAddress(address);
        setStreetNumber(streetNumber);
        setEmail(email);
        setMobilePhone(mobilePhone);
    }

    public static ThirdPartyModel ofSplit(int id, String firstName, String lastName, String city, int zipcode, String address, int streetNumber, String email, String mobilePhone) {
        return new ThirdPartyModel(id, firstName, lastName, city, zipcode, address, streetNumber, email, mobilePhone);
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

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
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

    public int getZipCode() {
        return zipCode.get();
    }

    public void setZipCode(int zipCode) {
        this.zipCode.set(zipCode);
    }

    public IntegerProperty zipCodeProperty() {
        return zipCode;
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

    public int getStreetNumber() {
        return streetNumber.get();
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber.set(streetNumber);
    }

    public IntegerProperty streetNumberProperty() {
        return streetNumber;
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

    public String getMobilePhone() {
        return mobilePhone.get();
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone.set(mobilePhone);
    }

    public StringProperty mobilePhoneProperty() {
        return mobilePhone;
    }

}
