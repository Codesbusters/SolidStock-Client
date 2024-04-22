package fr.codesbusters.solidstock.model;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StockMovementModel {
    private final StringProperty type = new SimpleStringProperty("");
    private final StringProperty dateAction = new SimpleStringProperty("");
    private final LongProperty id = new SimpleLongProperty();
    private final IntegerProperty refProfuct = new SimpleIntegerProperty();
    private final StringProperty productName = new SimpleStringProperty("");
    private final DoubleProperty quantite = new SimpleDoubleProperty();
    private final StringProperty expiredDate = new SimpleStringProperty("");
    private final BooleanProperty inOut = new SimpleBooleanProperty();
    private final StringProperty motif = new SimpleStringProperty("");
    private final StringProperty batchNumber = new SimpleStringProperty("");
    private final BooleanProperty isDisabled = new SimpleBooleanProperty();

private final StringProperty note = new SimpleStringProperty("");

    public StockMovementModel (String type, String dateAction, long id, int refProfuct, String productName, double quantite, String expiredDate, boolean inOut, String motif, String batchNumber, String note, boolean isDisabled) {
        this.type.set(type);
        this.dateAction.set(dateAction);
        this.id.set(id);
        this.refProfuct.set(refProfuct);
        this.productName.set(productName);
        this.quantite.set(quantite);
        this.expiredDate.set(expiredDate);
        this.inOut.set(inOut);
        this.motif.set(motif);
        this.batchNumber.set(batchNumber);
        this.note.set(note);
        this.isDisabled.set(isDisabled);
    }

    public static StockMovementModel ofSplit(String type, String dateAction, long id, int refProfuct, String productName, double quantite, String expiredDate, boolean inOut, String motif, String batchNumber, String note, boolean booleanProperty) {
        return new StockMovementModel(type, dateAction, id, refProfuct, productName, quantite, expiredDate, inOut, motif, batchNumber, note, booleanProperty);
    }

    public long getID() {
        return id.get();
    }

    public void setID(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
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

    public double getQuantite() {
        return quantite.get();
    }

    public void setQuantite(double quantite) {
        this.quantite.set(quantite);
    }

    public DoubleProperty quantiteProperty() {
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

    public String getMotif() {
        return motif.get();
    }

    public void setMotif(String motif) {
        this.motif.set(motif);
    }

    public StringProperty motifProperty() {
        return motif;
    }

    public String getExpiredDate() {
        return expiredDate.get();
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate.set(expiredDate);
    }

    public StringProperty expiredDateProperty() {
        return expiredDate;
    }

    public String getBatchNumber() {
        return batchNumber.get();
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber.set(batchNumber);
    }

    public StringProperty batchNumberProperty() {
        return batchNumber;
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

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
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
