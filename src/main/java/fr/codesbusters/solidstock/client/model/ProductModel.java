package fr.codesbusters.solidstock.client.model;

import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final DoubleProperty sellPrice = new SimpleDoubleProperty();

    private final DoubleProperty buyPrice = new SimpleDoubleProperty();

    private final IntegerProperty inStock = new SimpleIntegerProperty();
    private final StringProperty productFamily = new SimpleStringProperty("");

    private final DoubleProperty minimumStockQuantity = new SimpleDoubleProperty();

    private final IntegerProperty selled = new SimpleIntegerProperty();
    private final BooleanProperty isDisabled = new SimpleBooleanProperty(false);

    public ProductModel(int id, String name, String description, String productFamily, double sellPrice, double buyPrice, int inStock, int selled, boolean isDisabled) {
        setID(id);
        setName(name);
        setDescription(description);
        setSellPrice(sellPrice);
        setBuyPrice(buyPrice);
        setProductFamily(productFamily);
        setInStock(inStock);
        setSelled(selled);
        setIsDisabled(isDisabled);
    }

    public static ProductModel ofSplit(int id, String name, String description, String productFamily, double sellPrice, double buyPrice, int inStock, int selled, boolean isDisabled) {
        return new ProductModel(id, name, description, productFamily, sellPrice, buyPrice, inStock, selled, isDisabled);
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

    public String getProductFamily() {
        return productFamily.get();
    }

    public void setProductFamily(String productFamily) {
        this.productFamily.set(productFamily);
    }

    public StringProperty productFamilyProperty() {
        return productFamily;
    }

    public double getSellPrice() {
        return sellPrice.get();
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice.set(sellPrice);
    }

    public DoubleProperty sellPriceProperty() {
        return sellPrice;
    }

    public double getBuyPrice() {
        return buyPrice.get();
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice.set(buyPrice);
    }

    public DoubleProperty buyPriceProperty() {
        return buyPrice;
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

    public boolean getIsDisabled() {
        return isDisabled.get();
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled.set(isDisabled);
    }

    public BooleanProperty isDisabledProperty() {
        return isDisabled;
    }

    public double getMinimumStockQuantity() {
        return minimumStockQuantity.get();
    }

    public void setMinimumStockQuantity(double minimumStockQuantity) {
        this.minimumStockQuantity.set(minimumStockQuantity);
    }

    public DoubleProperty minimumStockQuantityProperty() {
        return minimumStockQuantity;
    }

}
