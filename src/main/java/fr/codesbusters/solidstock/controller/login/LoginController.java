package fr.codesbusters.solidstock.controller.login;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.LoginDto;
import fr.codesbusters.solidstock.service.RequestAPI;
import fr.codesbusters.solidstock.service.SessionManager;
import fr.codesbusters.solidstock.utils.LoginScreen;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

@Component
@Slf4j
public class LoginController extends DefaultController {
    RequestAPI requestAPI = new RequestAPI();

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
                    username.setText("dorian5");
                    password.setText("test");
                    handleLoginButtonClick();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private boolean isValidLogin() throws JsonProcessingException {
        LoginDto loginDto = new LoginDto(username.getText(), password.getText());
        try {
            ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/auth/login", loginDto, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                log.info(responseEntity.getBody());
                JsonNode actualObj = mapper.readTree(responseEntity.getBody());
                String token = actualObj.get("accessToken").asText();
                String tokenType = actualObj.get("tokenType").asText();
                SessionManager.getInstance().setAttribute("token", tokenType + " " + token);
                log.info("Ajout du token dans la session : " + token);
                return true;
            } else {
                return false;
            }
        } catch (HttpServerErrorException.InternalServerError | HttpClientErrorException.Unauthorized e) {
            // Catching 500 and 401 errors and returning false
            return false;
        }
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
