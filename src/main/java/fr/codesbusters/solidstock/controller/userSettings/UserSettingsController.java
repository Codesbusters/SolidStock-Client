package fr.codesbusters.solidstock.controller.userSettings;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.UserSettings;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.role.GetRoleDto;
import fr.codesbusters.solidstock.dto.user.GetUserDto;
import fr.codesbusters.solidstock.model.SolidStockDataIntegration;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

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
    MFXComboBox<String> defaultLoadingPageField;
    @FXML
    MFXComboBox<String> langageField;
    @FXML
    MFXListView<String> roleList;
    @FXML
    MFXTextField lastConnectionField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadUserPreferences();
    }

    public void saveAction() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || emailField.getText().isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez remplir tous les champs", DialogType.ERROR, 0);
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
            openDialog(stackPane.getScene(), "Modifications sauvegardée", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Les mots de passe ne correspondent pas", DialogType.ERROR, 0);
        }
    }

    @FXML
    public void reloadUserPreferences() {
        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/auth/me", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetUserDto user = null;
        try {
            user = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing user preferences", e);
        }

        assert user != null;
        lastNameField.setText(user.getLastName());
        firstNameField.setText(user.getFirstName());
        emailField.setText(user.getEmail());
        ObservableList<String> roles = FXCollections.observableArrayList();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (GetRoleDto roleDto : user.getRoles()) {
                roles.add(roleDto.getName());
            }
        } else {
            roles.add("Aucun rôle attribué");
        }

        roleList.setItems(roles);
        ObservableList<String> pages = SolidStockDataIntegration.pages;
        defaultLoadingPageField.setItems(pages);

        ObservableList<String> languages = SolidStockDataIntegration.languages;
        langageField.setItems(languages);
    }
}
