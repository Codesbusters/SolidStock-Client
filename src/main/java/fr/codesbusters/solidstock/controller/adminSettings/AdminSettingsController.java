package fr.codesbusters.solidstock.controller.adminSettings;

import fr.codesbusters.solidstock.business.AdminSettings;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class AdminSettingsController extends DefaultController implements Initializable {


    @FXML
    StackPane stackPane;

    @FXML
    MFXTextField urlApi;

    @FXML
    MFXTextField urlWeb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void saveSettings() {
        if (urlApi.getText().isEmpty() || urlWeb.getText().isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez remplir tous les champs...", DialogType.ERROR);
            return;
        }

        AdminSettings adminSettings = new AdminSettings();

        adminSettings.setUrlApi(urlApi.getText());
        adminSettings.setUrlWeb(urlWeb.getText());

        log.info(adminSettings.toString());
    }
}
