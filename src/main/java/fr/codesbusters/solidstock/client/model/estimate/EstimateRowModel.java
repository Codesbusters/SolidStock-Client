package fr.codesbusters.solidstock.client.model.estimate;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EstimateRowModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty productName = new SimpleStringProperty();
    private final DoubleProperty quantity = new SimpleDoubleProperty();
    private final StringProperty unitType = new SimpleStringProperty();
    private final DoubleProperty unitPrice = new SimpleDoubleProperty();
    private final DoubleProperty totalHT = new SimpleDoubleProperty();
    private final DoubleProperty vat = new SimpleDoubleProperty();
    private final DoubleProperty totalTTC = new SimpleDoubleProperty();

    public EstimateRowModel(int id, String productName, double quantity, String unitType, double unitPrice, double totalHT, Double vat, double totalTTC) {
        setID(id);
        setProductName(productName);
        setQuantity(quantity);
        setUnitType(unitType);
        setUnitPrice(unitPrice);
        setTotalHT(totalHT);
        setVat(vat);
        setTotalTTC(totalTTC);
    }

    public static EstimateRowModel ofSplit(int id, String productName, double quantity, String unitType, double unitPrice, double totalHT, Double vat, double totalTTC) {
        return new EstimateRowModel(id, productName, quantity, unitType, unitPrice, totalHT, vat, totalTTC);
    }

    public int getID() {
        return id.get();
    }

    public void setID(int id) {
        this.id.set(id);
    }

    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public double getQuantity() {
        return quantity.get();
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    public String getUnitType() {
        return unitType.get();
    }

    public void setUnitType(String unitType) {
        this.unitType.set(unitType);
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public double getTotalHT() {
        return totalHT.get();
    }

    public void setTotalHT(double totalHT) {
        this.totalHT.set(totalHT);
    }

    public Double getVat() {
        return vat.get();
    }

    public void setVat(Double vat) {
        this.vat.set(vat);
    }

    public double getTotalTTC() {
        return totalTTC.get();
    }

    public void setTotalTTC(double totalTTC) {
        this.totalTTC.set(totalTTC);
    }
}
