package fr.codesbusters.solidstock.client.model.order;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderRowModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty productName = new SimpleStringProperty();
    private final DoubleProperty quantity = new SimpleDoubleProperty();
    private final StringProperty unitType = new SimpleStringProperty();

    public OrderRowModel(int id, String productName, double quantity, String unitType) {
        setID(id);
        setProductName(productName);
        setQuantity(quantity);
        setUnitType(unitType);
    }

    public static OrderRowModel ofSplit(int id, String productName, double quantity, String unitType) {
        return new OrderRowModel(id, productName, quantity, unitType);
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

}