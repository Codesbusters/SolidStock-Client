package fr.codesbusters.solidstock.model.supplierOrder;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class SupplierOrderModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty orderNumber = new SimpleStringProperty("");
    private final StringProperty orderDate = new SimpleStringProperty("");
    private final StringProperty deliveryDate = new SimpleStringProperty("");
    private final StringProperty status = new SimpleStringProperty("");

    private final BooleanProperty isDisabled = new SimpleBooleanProperty(false);

    public SupplierOrderModel(int id, String orderNumber, String orderDate, String deliveryDate, String status) {
        setID(id);
        setOrderNumber(orderNumber);
        setOrderDate(orderDate);
        setDeliveryDate(deliveryDate);
        setStatus(status);
    }

    public static SupplierOrderModel ofSplit(int id, String orderNumber, String orderDate, String deliveryDate, String status) {
        return new SupplierOrderModel(id, orderNumber, orderDate, deliveryDate, status);
    }

    public int getID() {
        return id.get();
    }

    public void setID(int id) {
        this.id.set(id);
    }

    public String getOrderNumber() {
        return orderNumber.get();
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber.set(orderNumber);
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }

    public String getDeliveryDate() {
        return deliveryDate.get();
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate.set(deliveryDate);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public boolean getIsDisabled() {
        return isDisabled.get();
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled.set(isDisabled);
    }

    public StringProperty orderNumberProperty() {
        return orderNumber;
    }

    public StringProperty orderDateProperty() {
        return orderDate;
    }

    public StringProperty deliveryDateProperty() {
        return deliveryDate;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public BooleanProperty isDisabledProperty() {
        return isDisabled;
    }

    public IntegerProperty idProperty() {
        return id;
    }
}
