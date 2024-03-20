package fr.codesbusters.solidstock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.SolidStockApplication;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.controller.login.LoginController;
import fr.codesbusters.solidstock.utils.ApplicationPropertiesReader;
import fr.codesbusters.solidstock.utils.LoginScreen;
import fr.codesbusters.solidstock.utils.TokenManager;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Slf4j
@Service
public class RequestAPI {
    private static final RestTemplate restTemplate = new RestTemplate();
    ApplicationPropertiesReader applicationPropertiesReader = new ApplicationPropertiesReader();
    String apiUrl = applicationPropertiesReader.getProperty("fr.codesbusters.solidstock.api.url");

    public <T> ResponseEntity<T> sendPostRequest(String url, Object requestBody, Class<T> responseType, boolean needLogin, boolean needCheckToken) {
        if (needCheckToken) {
            isTokenValid();
        }
        url = apiUrl + url;
        log.info("Sending POST request to: " + url + " with body: " + requestBody);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (needLogin) {
            String token = SessionManager.getInstance().getAttribute("token").toString();
            headers.set("Authorization", token);
        }
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> sendGetRequest(String url, Class<T> responseType, boolean needLogin, boolean needCheckToken) {
        if (needCheckToken) {
            isTokenValid();
        }
        url = apiUrl + url;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (needLogin) {
            String token = SessionManager.getInstance().getAttribute("token").toString();
            headers.set("Authorization", token);
        }
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
    }

    //update
    public <T> ResponseEntity<T> sendPutRequest(String url, Object requestBody, Class<T> responseType, boolean needLogin) {
        isTokenValid();
        url = apiUrl + url;
        log.info("Sending PUT request to: " + url + " with body: " + requestBody);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (needLogin) {
            String token = SessionManager.getInstance().getAttribute("token").toString();
            headers.set("Authorization", token);
        }
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType);
    }

    //delete
    public <T> ResponseEntity<T> sendDeleteRequest(String url, Class<T> responseType, boolean needLogin) {
        isTokenValid();
        url = apiUrl + url;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (needLogin) {
            String token = SessionManager.getInstance().getAttribute("token").toString();
            headers.set("Authorization", token);
        }
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType);
    }


    //check if token is valid
    public void isTokenValid() {
        DefaultController defaultController = new DefaultController();
        String token = SessionManager.getInstance().getAttribute("token").toString();
        String url = apiUrl + "/auth/me";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                log.error("Token is not valid");
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.showLogin();
                    defaultController.openDialog(loginScreen.getScene(), "Votre session a expiré, veuillez vous reconnecter", DialogType.ERROR, 0);

            }
        } catch (HttpServerErrorException e) {
            log.error("Error while checking token", e);
        }

    }


}