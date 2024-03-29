package fr.codesbusters.solidstock.model;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SupplierModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty address = new SimpleStringProperty("");
    private final StringProperty streetNumber = new SimpleStringProperty("");
    private final StringProperty zipCode = new SimpleStringProperty("");
    private final StringProperty city = new SimpleStringProperty("");
    private final StringProperty country = new SimpleStringProperty("");
    private final StringProperty mobilePhone = new SimpleStringProperty("");
    private final StringProperty homePhone = new SimpleStringProperty("");
    private final StringProperty workPhone = new SimpleStringProperty("");
    private final StringProperty siren = new SimpleStringProperty("");
    private final StringProperty siret = new SimpleStringProperty("");
    private final StringProperty rib = new SimpleStringProperty("");
    private final IntegerProperty rcs = new SimpleIntegerProperty();
    private final StringProperty email = new SimpleStringProperty("");
    private final StringProperty website = new SimpleStringProperty("");
    private final StringProperty fax = new SimpleStringProperty("");
    private final StringProperty note = new SimpleStringProperty("");
    private final BooleanProperty isDisabled = new SimpleBooleanProperty(false);

    public SupplierModel(int id, String name, String address, String streetNumber, String zipCode, String city, String mobilePhone, String homePhone, String workHome, String siret, String siren, String rib, int rcs, String email, String website, String country, String fax, String note, boolean isDisabled) {
        setID(id);
        setName(name);
        setAddress(address);
        setStreetNumber(streetNumber);
        setZipCode(zipCode);
        setCity(city);
        setCountry(country);
        setMobilePhone(mobilePhone);
        setHomePhone(homePhone);
        setWorkPhone(workHome);
        setSiren(siren);
        setSiret(siret);
        setRib(rib);
        setRcs(rcs);
        setEmail(email);
        setWebsite(website);
        setFax(fax);
        setNote(note);
        setIsDisabled(isDisabled);
    }

    public static SupplierModel ofSplit(int id, String name, String address, String streetNumber, String zipCode, String city, String mobilePhone, String homePhone, String workPhone, String siret, String siren, String rib, Integer rcs, String email, String website, String country, String fax, String note, boolean isDisabled) {
        return new SupplierModel(id, name, address, streetNumber, zipCode, city, mobilePhone, homePhone, workPhone, siret, siren, rib, rcs, email, website, country, fax, note, isDisabled);
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

    public String getStreetNumber() {
        return streetNumber.get();
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber.set(streetNumber);
    }

    public StringProperty streetNumberProperty() {
        return streetNumber;
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

    public String getMobilePhone() {
        return mobilePhone.get();
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone.set(mobilePhone);
    }

    public StringProperty mobilePhoneProperty() {
        return mobilePhone;
    }

    public String getHomePhone() {
        return homePhone.get();
    }

    public void setHomePhone(String homePhone) {
        this.homePhone.set(homePhone);
    }

    public StringProperty homePhoneProperty() {
        return homePhone;
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

    public String getSiren() {
        return siren.get();
    }

    public void setSiren(String siren) {
        this.siren.set(siren);
    }

    public StringProperty sirenProperty() {
        return siren;
    }


    public String getSiret() {
        return siret.get();
    }

    public void setSiret(String siret) {
        this.siret.set(siret);
    }

    public StringProperty siretProperty() {
        return siret;
    }

    public String getRib() {
        return rib.get();
    }

    public void setRib(String rib) {
        this.rib.set(rib);
    }

    public StringProperty ribProperty() {
        return rib;
    }

    public int getRcs() {
        return rcs.get();
    }

    public void setRcs(int rcs) {
        this.rcs.set(rcs);
    }

    public IntegerProperty rscProperty() {
        return rcs;
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

    public String getFax() {
        return fax.get();
    }

    public void setFax(String fax) {
        this.fax.set(fax);
    }

    public StringProperty faxProperty() {
        return fax;
    }

    public String getNote() {
        return note.get();
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public StringProperty noteProperty() {
        return note;
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