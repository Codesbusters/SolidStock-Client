package fr.codesbusters.solidstock.controller.users;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.User;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.model.RoleModel;
import fr.codesbusters.solidstock.model.SolidStockModel;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class UsersEditController extends DefaultShowController implements Initializable {
    @FXML
    StackPane stackPane;

    @FXML
    MFXTextField userId;
    @FXML
    MFXTextField userFirstName;
    @FXML
    MFXTextField userLastName;
    @FXML
    MFXTextField userLogin;
    @FXML
    MFXTextField userMail;
    @FXML
    MFXTextField userPassword;
    @FXML
    MFXTextField userConfirmPassword;
    @FXML
    MFXTextField userMobilePhone;
    @FXML
    MFXComboBox<RoleModel> role;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId.setText(String.valueOf(getId()));

        ObservableList<RoleModel> roles = SolidStockModel.roles;

        role.setItems(roles);
    }


    @FXML
    public void userLogin() {
        String firstNameString = userFirstName.getText();
        String lastNameString = userLastName.getText();

        // Vérification que les champs ne sont pas vides
        if (firstNameString.isBlank() || firstNameString.isEmpty() || lastNameString.isBlank() || lastNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un nom et un prénom", DialogType.ERROR);
        } else {
            if (userLogin.getText().isEmpty()) {
                String loginString = firstNameString.toLowerCase() + "." + lastNameString.toLowerCase();
                userLogin.setText(loginString);
            }
        }
    }

    @FXML
    public void editUser() throws NumberFormatException, UnsupportedEncodingException {
        String userLoginString = userLogin.getText();
        String userMailString = userMail.getText();
        String userPasswordString = userPassword.getText();
        String userConfirmPasswordString = userConfirmPassword.getText();
        String userMobilePhoneString = userMobilePhone.getText();
        String lastNameString = userLastName.getText();
        String firstNameString = userFirstName.getText();
        String roleString = null;
        RoleModel selectedItem = role.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            roleString = selectedItem.getRoleName();
        }

        // Vérification du nom
        if (lastNameString.isBlank() || lastNameString.isEmpty()) {
            log.info("lastNameString : {}", lastNameString);
            openDialog(stackPane.getScene(), "Veuillez renseigner un nom", DialogType.ERROR);
            return;
        } else {
            log.info("lastNameString : {}", lastNameString);
        }

        // Vérification du prénom
        if (firstNameString.isBlank() || firstNameString.isEmpty()) {
            log.info("firstNameString : {}", firstNameString);
            openDialog(stackPane.getScene(), "Veuillez renseigner un prénom", DialogType.ERROR);
            return;
        } else {
            log.info("firstNameString : {}", firstNameString);
        }

        // Vérification du login
        if (userLoginString.isBlank() || userLoginString.isEmpty()) {
            log.info("userLoginString : {}", userLoginString);
            openDialog(stackPane.getScene(), "Veuillez renseigner un identifiant", DialogType.ERROR);
            return;
        } else {
            log.info("userLoginString : {}", userLoginString);
        }

        // Vérification du mail
        if (userMailString.isBlank() || userMailString.isEmpty()) {
            log.info("userMailString : {}", userMailString);
            openDialog(stackPane.getScene(), "Veuillez renseigner un mail", DialogType.ERROR);
            return;
        } else {
            log.info("userMailString : {}", userMailString);
        }

        // Vérification du numéro de téléphone
        if (userMobilePhoneString.isBlank() || userMobilePhoneString.isEmpty()) {
            log.info("userMobilePhoneString : {}", userMobilePhoneString);
            openDialog(stackPane.getScene(), "Veuillez renseigner un numéro de téléphone", DialogType.ERROR);
            return;
        } else {
            log.info("userMobilePhoneString : {}", userMobilePhoneString);
        }

        // Vérification du mot de passe
        if (userPasswordString.isBlank() || userPasswordString.isEmpty()) {
            log.info("userPasswordString : {}", userPasswordString);
            openDialog(stackPane.getScene(), "Veuillez renseigner un mot de passe", DialogType.ERROR);
            return;
        } else {
            log.info("userPasswordString : {}", userPasswordString);
        }

        // Vérification de la confirmation du mot de passe
        if (userConfirmPasswordString.isBlank() || userConfirmPasswordString.isEmpty()) {
            log.info("userConfirmPasswordString : {}", userConfirmPasswordString);
            openDialog(stackPane.getScene(), "Veuillez confirmer le mot de passe", DialogType.ERROR);
            return;
        } else if (!userConfirmPasswordString.equals(userPasswordString)) {
            log.info("userConfirmPasswordString : {}", userConfirmPasswordString);
            openDialog(stackPane.getScene(), "Les mots de passe ne correspondent pas", DialogType.ERROR);
            return;
        }

        // Vérification du rôle
        if (role.getText().isBlank() || role.getText().isEmpty()) {
            log.info("roleString : {}", roleString);
            openDialog(stackPane.getScene(), "Veuillez renseigner un rôle", DialogType.ERROR);
            return;
        } else {
            log.info("roleString : {}", roleString);
        }

        // Création de l'objet User
        User user = new User();
        user.setFirstName(firstNameString);
        user.setLastName(lastNameString);
        user.setLogin(userLoginString);
        user.setRoleId(selectedItem.getID());
        user.setMail(userMailString);
        user.setPassword(userPasswordString);
        user.setMobileNumber(userMobilePhoneString);
        user.setRoleName(roleString);

        log.info("Utilisateur à modifier : {}", user);

        cancel();

        openDialog(stackPane.getScene(), "Utilisateur " + user.getLogin() + " modifié avec succès", DialogType.INFORMATION);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}
