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
    public final StringProperty name = new SimpleStringProperty("");
    public final StringProperty firstName = new SimpleStringProperty("");
    public final StringProperty password = new SimpleStringProperty("");
    public final IntegerProperty customerId = new SimpleIntegerProperty();
    public final IntegerProperty roleId = new SimpleIntegerProperty();
    public final StringProperty roleName = new SimpleStringProperty("");
    public final StringProperty email = new SimpleStringProperty("");
    public final StringProperty userLoginName = new SimpleStringProperty("");
    public final BooleanProperty isDisabled = new SimpleBooleanProperty(false);


    public UsersModel(int id, String name, String firstName, int customerId, String password, int roleId, String roleName, String email, String userLoginName, boolean isDisabled){
        setID(id);
        setName(name);
        setFirstName(firstName);
        setCustomerId(customerId);
        setPassword(password);
        setRoleId(roleId);
        setRoleName(roleName);
        setEmail(email);
        setUserLoginName(userLoginName);
        setIsDisabled(isDisabled);
    }

    public static UsersModel ofSplit(int id, String name, String firstName, int customerId, String password, int roleId, String roleName, String email, String userLoginName, boolean isDisabled){
        return new UsersModel(id, name, firstName, customerId, password, roleId, roleName, email, userLoginName, isDisabled);
    }

    // Section Setter
    public void setRoleId(int roleId) {
        this.roleId.set(roleId);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public void setName(String name) {
        this.name.set(name);
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public void setID(int id) {
        this.id.set(id);
    }

    public void setRoleName(String roleName){
        this.roleName.set(roleName);
    }

    public void setEmail(String email){this.email.set(email);}
    public void setUserLoginName(String userLoginName){this.userLoginName.set(userLoginName);}
    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled.set(isDisabled);
    }



    // Section Getter
    public int getID(){
        return id.get();
    }
    public String getPassword(){
        return password.get();
    }
    public int getCustomerId(){
        return customerId.get();
    }
    public String getName(){
        return name.get();
    }
    public String getFirstName(){
        return firstName.get();
    }
    public int getRoleId(){
        return roleId.get();
    }
    public String getRoleName(){
        return roleName.get();
    }
    public String getEmail(){
        return email.get();
    }
    public String getUserLoginName(){
        return userLoginName.get();
    }

    public boolean getIsDisabled() {
        return isDisabled.get();
    }



    // Section property
    public IntegerProperty idProperty(){
        return id;
    }
    public StringProperty passwordProperty(){
        return password;
    }
    public IntegerProperty customerIdProperty(){
        return customerId;
    }
    public StringProperty nameProperty(){
        return name;
    }
    public StringProperty firstNameProperty(){
        return firstName;
    }
    public IntegerProperty roleIdProperty(){
        return roleId;
    }
    public StringProperty roleNameProperty(){
        return roleName;
    }
    public StringProperty emailProperty(){
        return email;
    }
    public StringProperty userLoginNameProperty(){
        return userLoginName;
    }
    public BooleanProperty isDisabledProperty() {
        return isDisabled;
    }

}
