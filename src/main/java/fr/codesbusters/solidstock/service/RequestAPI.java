package fr.codesbusters.solidstock.service;

import fr.codesbusters.solidstock.utils.ApplicationPropertiesReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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
            log.info("Token: " + token);
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
            log.info("Token: " + token);
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
            log.info("Token: " + token);
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
            log.info("Token: " + token);
            headers.set("Authorization", token);
        }
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType);
    }


}