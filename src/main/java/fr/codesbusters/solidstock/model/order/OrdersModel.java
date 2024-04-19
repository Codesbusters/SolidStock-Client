package fr.codesbusters.solidstock.model.order;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrdersModel {

    private final LongProperty id = new SimpleLongProperty();

    private final StringProperty subject = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty customerName = new SimpleStringProperty("");
    private final StringProperty dueDate = new SimpleStringProperty("");
    private final StringProperty statusName = new SimpleStringProperty("");
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    public OrdersModel(long id, String subject, String description, int customerId, String customerName, String dueDate, String statusName) {
        this.id.set(id);
        this.subject.set(subject);
        this.description.set(description);
        this.customerId.set(customerId);
        this.customerName.set(customerName);
        this.dueDate.set(dueDate);
        this.statusName.set(statusName);
    }

    public static OrdersModel ofSplit(long id, String subject, String description, int customerId, String customerName, String dueDate, String statusName) {
        return new OrdersModel(id, subject, description, customerId, customerName, dueDate, statusName);
    }

    public int getID() {
        return (int) id.get();
    }

    public void setID(long id) {
        this.id.set(id);
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getDescription() {
        return description.get();
    }
    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getCustomerId() {
        return customerId.get();
    }
    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getCustomerName() {
        return customerName.get();
    }
    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getDueDate() {
        return dueDate.get();
    }
    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
    }
    public String getStatusName() {
        return statusName.get();
    }
    public void setStatusName(String statusName) {
        this.statusName.set(statusName);
    }

}