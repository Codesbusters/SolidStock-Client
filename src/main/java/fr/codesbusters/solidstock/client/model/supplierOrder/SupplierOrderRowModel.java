package fr.codesbusters.solidstock.client.model.supplierOrder;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SupplierOrderRowModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty productName = new SimpleStringProperty();
    private final DoubleProperty quantity = new SimpleDoubleProperty();
    private final StringProperty unitType = new SimpleStringProperty();
    private final DoubleProperty unitPrice = new SimpleDoubleProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();

    public SupplierOrderRowModel(int id, String productName, double quantity, String unitType, double unitPrice, double totalHT) {
        setID(id);
        setProductName(productName);
        setQuantity(quantity);
        setUnitType(unitType);
        setUnitPrice(unitPrice);
        setTotal(totalHT);
    }

    public static SupplierOrderRowModel ofSplit(int id, String productName, double quantity, String unitType, double unitPrice, double totalHT) {
        return new SupplierOrderRowModel(id, productName, quantity, unitType, unitPrice, totalHT);
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

    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public DoubleProperty quantityProperty() {
        return quantity;
    }

    public StringProperty unitTypeProperty() {
        return unitType;
    }

    public DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    public DoubleProperty totalProperty() {
        return total;
    }



}
