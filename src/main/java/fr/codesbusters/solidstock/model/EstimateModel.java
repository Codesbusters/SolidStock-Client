package fr.codesbusters.solidstock.model;

import lombok.Getter;

public class EstimateModel {

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

    public EstimateModel(int id, String subject, String description, int customerId, String customerName, String dueDate) {
        setID(id);
        setSubject(subject);
        setDescription(description);
        setCustomerId(customerId);
        setCustomerName(customerName);
        setDueDate(dueDate);
    }

    public static EstimateModel ofSplit(int id, String subject, String description, int customerId, String customerName, String dueDate) {
        return new EstimateModel(id, subject, description, customerId, customerName, dueDate);
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

}
