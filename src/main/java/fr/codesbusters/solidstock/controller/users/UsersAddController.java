package fr.codesbusters.solidstock.controller.users;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.User;
import fr.codesbusters.solidstock.controller.DefaultController;
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
public class UsersAddController extends DefaultController implements Initializable {

    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField userFirstName;
    @FXML
    public MFXTextField userLastName;
    @FXML
    public MFXTextField userLogin;
    @FXML
    public MFXTextField userMail;
    @FXML
    public MFXTextField userPassword;
    @FXML
    public MFXTextField userConfirmPassword;
    @FXML
    public MFXTextField userMobilePhone;
    @FXML
    public MFXTextField roleName;
    @FXML
    public MFXTextField roleDescriptionTextField;
    @FXML
    public MFXComboBox<RoleModel> role;

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
    public void addUser() throws NumberFormatException, UnsupportedEncodingException {
        String userLoginString = userLogin.getText();
        String userMailString = userMail.getText();
        String userPasswordString = userPassword.getText();
        String userConfirmPasswordString = userConfirmPassword.getText();
        String userMobilePhoneString = userMobilePhone.getText();
        String lastNameString = userLastName.getText();
        String firstNameString = userFirstName.getText();
        String nameString = roleName.getText();
        RoleModel selectedItem = role.getSelectionModel().getSelectedItem();
        String roleString = null;
        String descriptionString = roleDescriptionTextField.getText();
        if (selectedItem != null) {
            roleString = String.valueOf(selectedItem.getID());
        }

        // Vérification du nom
        if (lastNameString.isBlank() || lastNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un nom", DialogType.ERROR);
        }

        // Vérification du prénom
        if (firstNameString.isBlank() || firstNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un prénom", DialogType.ERROR);
        }

        // Vérification du login
        if (userLoginString.isBlank() || userLoginString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un identifiant", DialogType.ERROR);
        }

        // Vérification du mail
        if (userMailString.isBlank() || userMailString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un mail", DialogType.ERROR);
        }

        // Vérification du mot de passe
        if (userPasswordString.isBlank() || userPasswordString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un mot de passe", DialogType.ERROR);
        }

        // Vérification de la confirmation du mot de passe
        if (userConfirmPasswordString.isBlank() || userConfirmPasswordString.isEmpty() || !userConfirmPasswordString.equals(userPasswordString)) {
            openDialog(stackPane.getScene(), "Veuillez confirmer le mot de passe", DialogType.ERROR);
        }

        // Vérification du numéro de téléphone
        if (userMobilePhoneString.isBlank() || userMobilePhoneString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un numéro de téléphone", DialogType.ERROR);
        }

        // Vérification du rôle
        if (nameString.isBlank() || nameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un nom", DialogType.ERROR);
        }

        // Vérfication du rôle
        if (roleString == null || roleString.trim().isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un rôle", DialogType.ERROR);
        }

        // Vérification de la description
        if (descriptionString.isBlank() || descriptionString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner une description", DialogType.ERROR);
        }

        // Création de l'objet User
        User user = new User();
        user.setFirstName(firstNameString);
        user.setLastName(lastNameString);
        user.setLogin(userLoginString);
        assert selectedItem != null;
        user.setRoleId(selectedItem.getID());
        user.setMail(userMailString);
        user.setPassword(userPasswordString);
        user.setMobileNumber(userMobilePhoneString);
        user.setRoleName(roleString);

        log.info("User to add : {}", user);

        cancel();

        openDialog(stackPane.getScene(), "Utilisateur " + user.getFirstName() + " " + user.getLastName() + " créer avec succès", DialogType.INFORMATION);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<RoleModel> roles = SolidStockModel.roles;

        role.setItems(roles);
    }
}
