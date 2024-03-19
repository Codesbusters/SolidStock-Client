package fr.codesbusters.solidstock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.utils.ApplicationPropertiesReader;
import fr.codesbusters.solidstock.utils.TokenManager;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class RequestAPI {
    private static final RestTemplate restTemplate = new RestTemplate();
    ApplicationPropertiesReader applicationPropertiesReader = new ApplicationPropertiesReader();
    String apiUrl = applicationPropertiesReader.getProperty("fr.codesbusters.solidstock.api.url");

    public <T> ResponseEntity<T> sendPostRequest(String url, Object requestBody, Class<T> responseType, boolean needLogin) {
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

    public <T> ResponseEntity<T> sendGetRequest(String url, Class<T> responseType, boolean needLogin) {
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

    public boolean checkLogin(Scene scene) {
        DefaultController controller = new DefaultController();
        if (TokenManager.tokenExists()) {
            try {
                SessionManager.getInstance().setAttribute("token", TokenManager.getToken());
                ResponseEntity<String> responseEntity = sendGetRequest("/auth/me", String.class, true);
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