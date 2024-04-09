package fr.codesbusters.solidstock.model;

import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsersModel {

    public final IntegerProperty id = new SimpleIntegerProperty();
    public final StringProperty lastName = new SimpleStringProperty("");
    public final StringProperty firstName = new SimpleStringProperty("");
    public final StringProperty password = new SimpleStringProperty("");
    public final IntegerProperty customerId = new SimpleIntegerProperty();
    public final IntegerProperty roleId = new SimpleIntegerProperty();
    public final StringProperty roleName = new SimpleStringProperty("");
    public final StringProperty email = new SimpleStringProperty("");
    public final BooleanProperty isDisabled = new SimpleBooleanProperty(false);


    public UsersModel(int id, String name, String firstName, int customerId, String password, int roleId, String roleName, String email, boolean isDisabled) {
        setID(id);
        setLastName(name);
        setFirstName(firstName);
        setCustomerId(customerId);
        setPassword(password);
        setRoleId(roleId);
        setRoleName(roleName);
        setEmail(email);
        setIsDisabled(isDisabled);
    }

    public static UsersModel ofSplit(int id, String name, String firstName, int customerId, String password, int roleId, String roleName, String email, boolean isDisabled) {
        return new UsersModel(id, name, firstName, customerId, password, roleId, roleName, email, isDisabled);
    }

    // Section Getter
    public int getID() {
        return id.get();
    }

    public void setID(int id) {
        this.id.set(id);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    // Section Setter
    public void setRoleId(int roleId) {
        this.roleId.set(roleId);
    }

    public String getRoleName() {
        return roleName.get();
    }

    public void setRoleName(String roleName) {
        this.roleName.set(roleName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public boolean getIsDisabled() {
        return isDisabled.get();
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled.set(isDisabled);
    }

    // Section property
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public IntegerProperty roleIdProperty() {
        return roleId;
    }

    public StringProperty roleNameProperty() {
        return roleName;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public BooleanProperty isDisabledProperty() {
        return isDisabled;
    }

}