package fr.codesbusters.solidstock.model.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrdersModel {

    private int id;

    @Getter
    private String subject;

    @Getter
    private String description;

    @Getter
    private int customerId;

    @Getter
    private String customerName;

    @Getter
    private String dueDate;

    @Getter
    private int statusId;

    @Getter
    private String statusName;

    public OrdersModel(int id, String subject, String description, int customerId, String customerName, String dueDate, int statusId, String statusName) {
        setID(id);
        setSubject(subject);
        setDescription(description);
        setCustomerId(customerId);
        setCustomerName(customerName);
        setDueDate(dueDate);
        setStatusId(statusId);
        setStatusName(statusName);
    }

    public static OrdersModel ofSplit(int id, String subject, String description, int customerId, String customerName, String dueDate, int statusId, String statusName) {
        return new OrdersModel(id, subject, description, customerId, customerName, dueDate, statusId, statusName);
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }


    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}