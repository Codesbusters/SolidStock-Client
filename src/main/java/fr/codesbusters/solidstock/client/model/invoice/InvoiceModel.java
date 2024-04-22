package fr.codesbusters.solidstock.client.model.invoice;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvoiceModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty customerName = new SimpleStringProperty("");
    private final DoubleProperty totalHt = new SimpleDoubleProperty();
    private final DoubleProperty totalTtc = new SimpleDoubleProperty();



    public InvoiceModel(int id, String name, String description, String customerName, String date, double totalHt, double totalTtc) {
        this.id.set(id);
        this.name.set(name);
        this.description.set(description);
        this.customerName.set(customerName);
        this.date.set(date);
        this.totalHt.set(totalHt);
        this.totalTtc.set(totalTtc);
    }

    public static InvoiceModel ofSplit(int id, String name, String description, String customerName, String date, double totalHt, double totalTtc) {
        return new InvoiceModel(id, name, description, customerName, date, totalHt, totalTtc);
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


    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public double getTotalHt() {
        return totalHt.get();
    }

    public void setTotalHt(double totalHt) {
        this.totalHt.set(totalHt);
    }

    public double getTotalTtc() {
        return totalTtc.get();
    }

    public void setTotalTtc(double totalTtc) {
        this.totalTtc.set(totalTtc);
    }

    public DoubleProperty totalHtProperty() {
        return totalHt;
    }

    public DoubleProperty totalTtcProperty() {
        return totalTtc;
    }




}
