package fr.codesbusters.solidstock.controller.login;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.utils.LoginScreen;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import lombok.Setter;

import java.io.IOException;

public class LoginController extends DefaultController {

    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTextField username;

    @FXML
    private MFXTextField password;

    @Setter
    private LoginScreen loginScreen;

    @FXML
    private void initialize() throws IOException {

        stackPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    handleLoginButtonClick();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        stackPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                try {
                    loginScreen.launchNextScreen();
                    loginScreen.hideLogin();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
            openDialog(username.getScene(), "Le nom d'utilisateur ou le mot de passe est incorrect. Veuillez réessayer.", DialogType.ERROR, 0);
        }
    }

    public void cancel(ActionEvent actionEvent) {
        loginScreen.hideLogin();
        Platform.exit();
    }
}
