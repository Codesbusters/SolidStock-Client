package fr.codesbusters.solidstock.controller.login;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.LoginDto;
import fr.codesbusters.solidstock.service.RequestAPI;
import fr.codesbusters.solidstock.service.SessionManager;
import fr.codesbusters.solidstock.utils.LoginScreen;
import fr.codesbusters.solidstock.utils.TokenManager;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

@Component
@Slf4j
public class LoginController extends DefaultController {

    @FXML
    private MFXCheckbox rememberMe;
    RequestAPI requestAPI = new RequestAPI();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MFXTextField username;

    @FXML
    private MFXTextField password;

    @Setter
    private LoginScreen loginScreen;


    @FXML
    private void initialize() throws IOException {

        anchorPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    handleLoginButtonClick();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        anchorPane.setOnKeyPressed(event -> {
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

        Platform.runLater(() -> {

            if (TokenManager.getRememberMe() && checkLogin(anchorPane.getScene())) {
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
    private boolean isValidLogin() throws JsonProcessingException {
        loginToAPI();
        return checkLogin(anchorPane.getScene());
    }

    @FXML
    public void loginToAPI() {
        LoginDto loginDto = new LoginDto(username.getText(), password.getText());
        try {
            ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/auth/login", loginDto, String.class, false, false);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode actualObj = mapper.readTree(responseEntity.getBody());
                String token = actualObj.get("accessToken").asText();
                String tokenType = actualObj.get("tokenType").asText();
                TokenManager.saveToken(tokenType + " " + token, rememberMe.isSelected());
                log.info("Ajout du token dans la session... ");

            } else {
            }
        } catch (HttpServerErrorException.InternalServerError | HttpClientErrorException.Unauthorized e) {
            // Catching 500 and 401 errors and returning false
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void handleLoginButtonClick() throws IOException {
        boolean isValidLogin = isValidLogin();
        isValidLogin = checkLogin(anchorPane.getScene());
        if (isValidLogin) {
            loginScreen.launchNextScreen();
            loginScreen.hideLogin();
        } else {
            openDialog(anchorPane.getScene(), "Erreur de connexion", DialogType.ERROR, 0);
        }
    }

    public void cancel(ActionEvent actionEvent) {
        loginScreen.hideLogin();
        Platform.exit();
    }

    private boolean checkLogin(Scene scene) {
        DefaultController controller = new DefaultController();
        if (TokenManager.tokenExists()) {
            try {
                SessionManager.getInstance().setAttribute("token", TokenManager.getToken());
                ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/auth/me", String.class, true, false);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode actualObj = mapper.readTree(responseEntity.getBody());
                    SessionManager.getInstance().setAttribute("user", actualObj);
                    return true;
                } else {
                    SessionManager.getInstance().removeAttribute("token");
                    TokenManager.deleteToken();
                    controller.openDialog(scene, "Impossible d'établire la connexion avec vos identifiants.", DialogType.ERROR, 0);
                    return false;
                }
            } catch (HttpServerErrorException.InternalServerError | HttpClientErrorException.Unauthorized |
                     JsonProcessingException e) {
                controller.openDialog(scene, "Erreur de connexion aux serveurs", DialogType.ERROR, 0);
                return false;
            }
        } else {
            return false;
        }
    }
}