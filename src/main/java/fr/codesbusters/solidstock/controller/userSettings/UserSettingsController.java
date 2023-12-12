package fr.codesbusters.solidstock.controller.userSettings;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.UserSettings;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.model.SolidStockDataIntegration;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class UserSettingsController extends DefaultController implements Initializable {


    @FXML
    public StackPane stackPane;

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
    MFXComboBox<String> langageField;

    @FXML
    MFXTextField lastConnectionField;

    public void saveAction() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty() || langageField.getText().isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez remplir tous les champs", DialogType.ERROR);
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
        } else {
            openDialog(stackPane.getScene(), "Les mots de passe ne correspondent pas", DialogType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> languages = SolidStockDataIntegration.languages;

        langageField.setItems(languages);

    }
}
