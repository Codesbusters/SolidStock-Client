package fr.codesbusters.solidstock.model;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty country = new SimpleStringProperty("");
    private final StringProperty cityCode = new SimpleStringProperty("");
    private final StringProperty phone = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");

    private final BooleanProperty isDisabled = new SimpleBooleanProperty(false);

    public CustomerModel(int id, String name, String country, String cityCode, String phone, String email) {
        setID(id);
        setName(name);
        setCountry(country);
        setCityCode(cityCode);
        setPhone(phone);
        setEmail(email);
    }

    public static CustomerModel ofSplit(int id, String name, String country, String cityCode, String phone, String email) {
        return new CustomerModel(id, name, country, cityCode, phone, email);
    }

    public int getID() {
        return id.get();
    }

    public void setID(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getCityCode() {
        return cityCode.get();
    }

    public void setCityCode(String cityCode) {
        this.cityCode.set(cityCode);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public boolean getIsDisabled() {
        return isDisabled.get();
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled.set(isDisabled);
    }

}
