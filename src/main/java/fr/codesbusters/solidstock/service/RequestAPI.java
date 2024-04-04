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
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.io.IOException;
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
        log.info("Sending GET request to: " + url);
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

    public <T> ResponseEntity<T> sendPutRequestWithFile(String url, MultiValueMap<String, Object> requestBody, Class<T> responseType, boolean needLogin) {
        isTokenValid();
        url = apiUrl + url;
        log.info("Sending PUT request to: " + url + " with body: " + requestBody);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        if (needLogin) {
            String token = SessionManager.getInstance().getAttribute("token").toString();
            headers.set("Authorization", token);
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType);
    }

    public File getImage(String url, boolean needLogin) throws IOException {
        isTokenValid();
        url = apiUrl + url;
        log.info("Sending GET request to: " + url);

        File image = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (needLogin) {
            String token = SessionManager.getInstance().getAttribute("token").toString();
            headers.set("Authorization", token);
        }
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), byte[].class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String tempPath = getTempDirectory() + File.separator + "SolidStock" + File.separator + "Images" + File.separator;
            byte[] imageBytes = response.getBody();
            if (imageBytes != null) {
                File imageDir = new File(tempPath);
                if (!imageDir.exists()) {
                    imageDir.mkdirs();
                }
                Path imagePath = Paths.get(tempPath + "productImage.png");
                Files.write(imagePath, imageBytes);
                log.info("Image récupérée avec succès et enregistrée à : " + imagePath);
                image = new File(imagePath.toString());
            } else {
                log.error("Impossible de récupérer l'image.");
            }
        } else {
            log.error("Erreur lors de la récupération de l'image : " + response.getStatusCode());
        }
        return image;
    }

    private String getTempDirectory() {
        return System.getProperty("java.io.tmpdir");
    }
    //delete
    public <T> ResponseEntity<T> sendDeleteRequest(String url, Class<T> responseType, boolean needLogin) {
        isTokenValid();
        url = apiUrl + url;
        log.info("Sending DELETE request to: " + url);
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

    public File downloadFile(String url, boolean needLogin, boolean needCheckToken) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Vérifier si le token est valide si nécessaire
        if (needCheckToken) {
            isTokenValid();
        }

        // Ajouter le token aux en-têtes si besoin de connexion
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (needLogin) {
            String token = SessionManager.getInstance().getAttribute("token").toString();
            headers.set("Authorization", token);
        }
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        // Construire l'URL
        String fullUrl = apiUrl + url;
        log.info("Downloading file from: " + fullUrl);

        // Effectuer la requête GET
        ResponseEntity<byte[]> response = restTemplate.exchange(fullUrl, HttpMethod.GET, requestEntity, byte[].class);
        byte[] fileBytes = response.getBody();

        // Récupérer le nom du fichier
        String fileName = Objects.requireNonNull(response.getHeaders().getContentDisposition()).getFilename();
        File tempFile = File.createTempFile("temp_", fileName);

        // Écrire les données du fichier dans le fichier temporaire
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(fileBytes);
        }

        // Retourner le fichier temporaire
        return tempFile;
    }


}