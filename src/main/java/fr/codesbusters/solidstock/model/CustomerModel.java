package fr.codesbusters.solidstock.model;

import javafx.beans.property.*;

public class CustomerModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");

    private final IntegerProperty thirdPartyId = new SimpleIntegerProperty();

    private final StringProperty address = new SimpleStringProperty("");

    private final BooleanProperty corporation = new SimpleBooleanProperty();

    private final StringProperty corporateName = new SimpleStringProperty("");

    private final IntegerProperty siren = new SimpleIntegerProperty();

    private final IntegerProperty siret = new SimpleIntegerProperty();

    private final StringProperty rib = new SimpleStringProperty("");

    private final IntegerProperty rcs = new SimpleIntegerProperty();

    public CustomerModel(int id, String name, int thirdPartyId, String address, boolean corporation, String corporateName, int siren, int siret, String rib, int rcs) {
        setID(id);
        setName(name);
        setThirdPartyId(thirdPartyId);
        setAddress(address);
        setCorporation(corporation);
        setCorporateName(corporateName);
        setSiren(siren);
        setSiret(siret);
        setRib(rib);
        setRcs(rcs);
    }

    public static CustomerModel ofSplit(int id, String name, int thirdPartyId, String address, boolean corporation, String corporateName, int siren, int siret, String rib, int rcs) {
        return new CustomerModel(id, name, thirdPartyId, address, corporation, corporateName, siren, siret, rib, rcs);
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

    public int getThirdPartyId() {
        return thirdPartyId.get();
    }

    public void setThirdPartyId(int thirdPartyId) {
        this.thirdPartyId.set(thirdPartyId);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public boolean getCorporation() {
        return corporation.get();
    }

    public void setCorporation(boolean corporation) {
        this.corporation.set(corporation);
    }

    public String getCorporateName() {
        return corporateName.get();
    }

    public void setCorporateName(String corporateName) {
        this.corporateName.set(corporateName);
    }

    public int getSiren() {
        return siren.get();
    }

    public void setSiren(int siren) {
        this.siren.set(siren);
    }

    public int getSiret() {
        return siret.get();
    }

    public void setSiret(int siret) {
        this.siret.set(siret);
    }

    public String getRib() {
        return rib.get();
    }

    public void setRib(String rib) {
        this.rib.set(rib);
    }

    public int getRcs() {
        return rcs.get();
    }

    public void setRcs(int rcs) {
        this.rcs.set(rcs);
    }
}
