package fr.codesbusters.solidstock.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleModel {

    private final IntegerProperty id = new SimpleIntegerProperty();

    private final StringProperty roleName = new SimpleStringProperty("");

    private final StringProperty roleDescription = new SimpleStringProperty("");

    public RoleModel(int id, String roleName, String roleDescription) {
        setID(id);
        setRoleName(roleName);
        setRoleDescription(roleDescription);
    }

    public static RoleModel ofSplit(int id, String roleName, String roleDescription) {
        return new RoleModel(id, roleName, roleDescription);
    }

    // Getter
    public int getID() {
        return id.get();
    }

    // Setter
    private void setID(int id) {
        this.id.set(id);
    }

    public String getRoleName() {
        return roleName.get();
    }

    private void setRoleName(String roleName) {
        this.roleName.set(roleName);
    }

    public String getRoleDescription() {
        return roleDescription.get();
    }

    private void setRoleDescription(String roleDescription) {
        this.roleDescription.set(roleDescription);
    }

    // Section Property
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty roleNameProperty() {
        return roleName;
    }

    public StringProperty roleDescriptionProperty() {
        return roleDescription;
    }

    @Override
    public String toString() {
        return getID() + " - " + getRoleName() + " - " + getRoleDescription();
    }

}
