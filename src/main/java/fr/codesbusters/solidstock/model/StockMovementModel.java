package fr.codesbusters.solidstock.model;

import javafx.beans.property.*;

public class StockMovementModel {

    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty refProfuct = new SimpleIntegerProperty();
    private final StringProperty productName = new SimpleStringProperty("");
    private final StringProperty quantite = new SimpleStringProperty("");
    private final StringProperty dateAction = new SimpleStringProperty("");
    private final BooleanProperty inOut = new SimpleBooleanProperty();

    public StockMovementModel(int id, int refProfuct, String productName, String quantite, String dateAction, boolean inOut) {
        setID(id);
        setRefProfuct(refProfuct);
        setProductName(productName);
        setQuantite(quantite);
        setDateAction(dateAction);
        setInOut(inOut);
    }

    public static StockMovementModel ofSplit(int id, int refProfuct, String productName, String quantite, String dateAction, boolean inOut) {
        return new StockMovementModel(id, refProfuct, productName, quantite, dateAction, inOut);
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

    public int getRefProfuct() {
        return refProfuct.get();
    }

    public void setRefProfuct(int refProfuct) {
        this.refProfuct.set(refProfuct);
    }

    public IntegerProperty refProfuctProperty() {
        return refProfuct;
    }


    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public String getQuantite() {
        return quantite.get();
    }

    public void setQuantite(String quantite) {
        this.quantite.set(quantite);
    }

    public StringProperty quantiteProperty() {
        return quantite;
    }

    public String getDateAction() {
        return dateAction.get();
    }

    public void setDateAction(String dateAction) {
        this.dateAction.set(dateAction);
    }

    public StringProperty dateActionProperty() {
        return dateAction;
    }

    public boolean getInOut() {
        return inOut.get();
    }

    public void setInOut(boolean inOut) {
        this.inOut.set(inOut);
    }

    public BooleanProperty inOutProperty() {
        return inOut;
    }

}
