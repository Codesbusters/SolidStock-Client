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
public class UsersModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty surName = new SimpleStringProperty("");
    private final StringProperty firstName = new SimpleStringProperty("");
    private final IntegerProperty thirdPartyId = new SimpleIntegerProperty();
    private final StringProperty password = new SimpleStringProperty("");
    private final IntegerProperty roleId = new SimpleIntegerProperty();
    private final StringProperty roleName = new SimpleStringProperty("");
    private final StringProperty roleDescription = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");
    private final StringProperty phoneNumber = new SimpleStringProperty("");
    private final StringProperty userLoginName = new SimpleStringProperty("");


    public UsersModel(int id, String surName, String firstName, int thirdPartyId, String password, int roleId, String roleName, String roleDescription, String email, String phoneNumber, String userLoginName){
        setID(id);
        setSurName(surName);
        setFirstName(firstName);
        setThirdPartyId(thirdPartyId);
        setPassword(password);
        setRoleId(roleId);
        setRoleName(roleName);
        setRoleDescription(roleDescription);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setUserLoginName(userLoginName);
    }

    public static UsersModel ofSplit(int id, String surName, String firstName, int thirdPartyId, String password, int roleId, String roleName, String roleDescription, String email, String phoneNumber, String userLoginName){
        return new UsersModel(id, surName, firstName, thirdPartyId, password, roleId, roleName, roleDescription, email, phoneNumber, userLoginName);
    }

    // Section Setter
    private void setRoleId(int roleId) {
        this.roleId.set(roleId);
    }

    private void setPassword(String password) {
        this.password.set(password);
    }

    private void setThirdPartyId(int thirdPartyId) {
        this.thirdPartyId.set(thirdPartyId);
    }

    private void setSurName(String surName) {
        this.surName.set(surName);
    }
    private void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    private void setID(int id) {
        this.id.set(id);
    }

    private void setRoleName(String roleName){
        this.roleName.set(roleName);
    }

    private void setRoleDescription(String roleDescription){this.roleDescription.set(roleDescription);}
    private void setEmail(String email){this.email.set(email);}
    private void setPhoneNumber(String phoneNumber){this.phoneNumber.set(phoneNumber);}
    private void setUserLoginName(String userLoginName){this.userLoginName.set(userLoginName);}

    // Section Getter
    public int getID(){
        return id.get();
    }
    public String getPassword(){
        return password.get();
    }
    public int getThirdPartyId(){
        return thirdPartyId.get();
    }
    public String getSurName(){
        return surName.get();
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
    public String getRoleDescription(){
        return roleDescription.get();
    }
    public String getEmail(){
        return email.get();
    }
    public String getPhoneNumber(){
        return phoneNumber.get();
    }
    public String getUserLoginName(){
        return userLoginName.get();
    }


    // Section property
    public IntegerProperty idProperty(){
        return id;
    }
    public StringProperty passwordProperty(){
        return password;
    }
    public IntegerProperty thirdPartyIdProperty(){
        return thirdPartyId;
    }
    public StringProperty surNameProperty(){
        return surName;
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
    public StringProperty roleDescriptionProperty(){
        return roleDescription;
    }
    public StringProperty emailProperty(){
        return email;
    }
    public StringProperty phoneNumberProperty(){
        return phoneNumber;
    }
    public StringProperty userLoginNameProperty(){
        return userLoginName;
    }
}
