package fr.codesbusters.solidstock.controller.userSettings;

import fr.codesbusters.solidstock.buisness.UserSettings;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.model.SolidStockModel;
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
            openErrorDialog(stackPane.getScene(), "Veuillez remplir tous les champs");
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
            openErrorDialog(stackPane.getScene(), "Les mots de passe ne correspondent pas");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> languages = SolidStockModel.languages;

        langageField.setItems(languages);

    }
}