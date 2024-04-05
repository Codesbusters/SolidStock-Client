package fr.codesbusters.solidstock.controller.users;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.User;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.model.RoleModel;
import fr.codesbusters.solidstock.model.SolidStockDataIntegration;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    public MFXTextField userName;
    @FXML
    public MFXTextField userCustomerId;
    @FXML
    public Label customerName;
    @FXML
    public MFXTextField userLogin;
    @FXML
    public MFXTextField userMail;
    @FXML
    public MFXTextField userPassword;
    @FXML
    public MFXTextField userConfirmPassword;
    @FXML
    public MFXComboBox<RoleModel> role;
    @FXML
    public Label roleName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<RoleModel> roles = SolidStockDataIntegration.roles;

        role.setItems(roles);
    }


    @FXML
    public void userLogin() {
        String nameString = userName.getText();

        // Vérification que les champs ne sont pas vides
        if (nameString.isBlank() || nameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un nom", DialogType.ERROR, 0);
        } else {
            if (userLogin.getText().isEmpty()) {
                String loginString = nameString.toLowerCase();
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
        String nameString = userName.getText();
        String roleString = null;
        RoleModel selectedItem = role.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            roleString = selectedItem.getRoleName();
        }

        // Vérification du nom
        if (nameString.isBlank() || nameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un nom", DialogType.ERROR, 0);
            return;
        }


        // Vérification du login
        if (userLoginString.isBlank() || userLoginString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un identifiant", DialogType.ERROR, 0);
            return;
        }

        // Vérification du mail
        if (userMailString.isBlank() || userMailString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un mail", DialogType.ERROR, 0);
            return;
        }


        // Vérification du mot de passe
        if (userPasswordString.isBlank() || userPasswordString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un mot de passe", DialogType.ERROR, 0);
            return;
        }

        // Vérification de la confirmation du mot de passe
        if (userConfirmPasswordString.isBlank() || userConfirmPasswordString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez confirmer le mot de passe", DialogType.ERROR, 0);
            return;
        } else if (!userConfirmPasswordString.equals(userPasswordString)) {
            openDialog(stackPane.getScene(), "Les mots de passe ne correspondent pas", DialogType.ERROR, 0);
            return;
        }

        // Vérification du rôle
        if (role.getText().isBlank() || role.getText().isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un rôle", DialogType.ERROR, 0);
            return;
        }

        // Création de l'objet User
        User user = new User();
        user.setFirstName(nameString);
        user.setLastName(nameString);
        user.setLogin(userLoginString);
        user.setRoleId(selectedItem.getID());
        user.setMail(userMailString);
        user.setPassword(userPasswordString);
        user.setRoleName(roleString);
        cancel();

        openDialog(stackPane.getScene(), "Utilisateur " + user.getLogin() + " créé avec succès", DialogType.INFORMATION, 0);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}
