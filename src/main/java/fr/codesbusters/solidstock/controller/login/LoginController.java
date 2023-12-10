package fr.codesbusters.solidstock.controller.login;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.utils.LoginScreen;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lombok.Setter;

import java.io.IOException;

public class LoginController extends DefaultController {

    @FXML
    private MFXTextField username;

    @FXML
    private MFXTextField password;

    @Setter
    private LoginScreen loginScreen;

    @FXML
    private void initialize() {
        // Vous pouvez ajouter des initialisations ici si nécessaire
    }

    @FXML
    private boolean isValidLogin() {
        return !username.getText().isEmpty() && !password.getText().isEmpty();
    }

    @FXML
    private void handleLoginButtonClick() throws IOException {
        boolean isValidLogin = isValidLogin();
        if (isValidLogin) {
            loginScreen.launchNextScreen();
            loginScreen.hideLogin();
        } else {
            openDialog(username.getScene(), "Le nom d'utilisateur ou le mot de passe est incorrect. Veuillez réessayer.", DialogType.ERROR);
        }
    }

    public void cancel(ActionEvent actionEvent) {
        loginScreen.hideLogin();
        Platform.exit();
    }
}
