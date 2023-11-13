package fr.codesbusters.solidstock.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");

    private final StringProperty productFamily = new SimpleStringProperty("");

    private final StringProperty barCode = new SimpleStringProperty("");
    private final IntegerProperty sellPrice = new SimpleIntegerProperty();

    private final IntegerProperty buyPrice = new SimpleIntegerProperty();

    private final IntegerProperty inStock = new SimpleIntegerProperty();

    private final IntegerProperty selled = new SimpleIntegerProperty();

    public ProductModel(int id, String name, String description, String productFamily, String barCode, int sellPrice, int buyPrice, int inStock, int selled) {
        setID(id);
        setName(name);
        setDescription(description);
        setBarCode(barCode);
        setSellPrice(sellPrice);
        setBuyPrice(buyPrice);
        setProductFamily(productFamily);
        setInStock(inStock);
        setSelled(selled);
    }

    public static ProductModel ofSplit(int id, String name, String description, String productFamily, String barCode, int sellPrice, int buyPrice, int inStock, int selled) {
        return new ProductModel(id, name, description, productFamily, barCode, sellPrice, buyPrice, inStock, selled);
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

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getBarCode() {
        return barCode.get();
    }

    public void setBarCode(String barCode) {
        this.barCode.set(barCode);
    }

    public StringProperty barCodeProperty() {
        return barCode;
    }

    public int getSellPrice() {
        return sellPrice.get();
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice.set(sellPrice);
    }

    public IntegerProperty sellPriceProperty() {
        return sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice.get();
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice.set(buyPrice);
    }

    public IntegerProperty buyPriceProperty() {
        return buyPrice;
    }

    public String getProductFamily() {
        return productFamily.get();
    }

    public void setProductFamily(String productFamily) {
        this.productFamily.set(productFamily);
    }

    public StringProperty productFamilyProperty() {
        return productFamily;
    }

    public int getInStock() {
        return inStock.get();
    }

    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public IntegerProperty inStockProperty() {
        return inStock;
    }

    public int getSelled() {
        return selled.get();
    }

    public void setSelled(int selled) {
        this.selled.set(selled);
    }

    public IntegerProperty selledProperty() {
        return selled;
    }

}
