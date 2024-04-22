package fr.codesbusters.solidstock.client.model;

import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleModel {

    public final IntegerProperty id = new SimpleIntegerProperty();

    public final StringProperty roleName = new SimpleStringProperty("");

    public final BooleanProperty isDisabled = new SimpleBooleanProperty(false);

    public RoleModel(int id, String roleName, boolean isDisabled) {
        setID(id);
        setRoleName(roleName);
        setIsDisabled(isDisabled);
    }

    public static RoleModel ofSplit(int id, String roleName, boolean isDisabled) {
        return new RoleModel(id, roleName, isDisabled);
    }

    // Getter
    public int getID() {
        return id.get();
    }

    public String getRoleName() {
        return roleName.get();
    }

    public boolean getIsDisabled() {
        return isDisabled.get();
    }
    // Setter

    public void setID(int id) {
        this.id.set(id);
    }

    public void setRoleName(String roleName) {
        this.roleName.set(roleName);
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled.set(isDisabled);
    }
    // Section Property
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty roleNameProperty() {
        return roleName;
    }
    public BooleanProperty isDisabledProperty() {
        return isDisabled;
    }
}
