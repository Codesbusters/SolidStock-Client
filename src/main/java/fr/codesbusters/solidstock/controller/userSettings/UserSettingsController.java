package fr.codesbusters.solidstock.controller.userSettings;

import fr.codesbusters.solidstock.buisness.UserSettings;
import fr.codesbusters.solidstock.controller.DefaultController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class UserSettingsController extends DefaultController implements Initializable {

    @FXML
    MFXTextField lastNameField;

    @FXML
    MFXTextField firstNameField;

    @FXML
    MFXTextField emailField;

    @FXML
    MFXTextField passwordField;

    @FXML
    MFXTextField confirmPasswordField;

    @FXML
    MFXTextField defaultLoadingPageField;

    @FXML
    MFXTextField userRoleField;

    @FXML
    MFXTextField langageField;

    @FXML
    MFXTextField lastConnectionField;

    public void saveAction() {
        if (firstNameField.getText().equals("") || lastNameField.getText().equals("") || emailField.getText().equals("") || passwordField.getText().equals("") || confirmPasswordField.getText().equals("") || langageField.getText().equals("") || userRoleField.getText().equals("") || lastConnectionField.getText().equals("") || defaultLoadingPageField.getText().equals("")) {
            openErrorDialog(lastNameField.getScene(), "Veuillez remplir tous les champs");
            return;
        }
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
            UserSettings userSettings = new UserSettings();
            userSettings.setFirstName(firstNameField.getText());
            userSettings.setLastName(lastNameField.getText());
            userSettings.setEmail(emailField.getText());
            userSettings.setPassword(passwordField.getText());
            userSettings.setLangage(langageField.getText());
            log.info(userSettings.toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}