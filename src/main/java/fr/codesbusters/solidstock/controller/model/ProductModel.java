package fr.codesbusters.solidstock.controller.model;

import io.github.palexdev.materialfx.demo.model.Device;
import io.github.palexdev.materialfx.utils.RandomUtils;
import javafx.beans.property.*;

public class ProductModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty ip = new SimpleStringProperty("");
    private final StringProperty owner = new SimpleStringProperty("");
    private final ObjectProperty<Device.State> state = new SimpleObjectProperty<>();

    public ProductModel(int id, String name, String ip, String owner, Device.State state) {
        setID(id);
        setName(name);
        setIP(ip);
        setOwner(owner);
        setState(state);
    }

    public static int randomID() {
        return RandomUtils.random.nextInt(100000, 1000000);
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

    public String getIP() {
        return ip.get();
    }

    public void setIP(String ip) {
        this.ip.set(ip);
    }

    public StringProperty ipProperty() {
        return ip;
    }

    public String getOwner() {
        return owner.get();
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public StringProperty ownerProperty() {
        return owner;
    }

    public Device.State getState() {
        return state.get();
    }

    public void setState(Device.State state) {
        this.state.set(state);
    }

    public ObjectProperty<Device.State> stateProperty() {
        return state;
    }

    public enum State {
        ONLINE, OFFLINE
    }
}
