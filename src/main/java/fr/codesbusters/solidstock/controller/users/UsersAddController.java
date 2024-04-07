package fr.codesbusters.solidstock.controller.users;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.customer.GetCustomerDto;
import fr.codesbusters.solidstock.dto.product.GetProductDto;
import fr.codesbusters.solidstock.dto.user.PostUserDto;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.service.IntChecker;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class UsersAddController extends DefaultController implements Initializable, CustomerSelectorListener {

    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField userName;
    @FXML
    public MFXTextField userCustomerId;
    @FXML
    public Label customerName;
    @FXML
    public MFXTextField userLogin;
    @FXML
    public MFXTextField userMail;
    @FXML
    public MFXTextField userPassword;
    @FXML
    public MFXTextField userConfirmPassword;
    @FXML
    public MFXTextField userFirstName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public String userLogin() {
        String nameString = userName.getText();
        String firstNameString = userFirstName.getText();

        // Vérification que les champs ne sont pas vides
        if (nameString.isBlank() || nameString.isEmpty() && firstNameString.isBlank() || firstNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un nom et un prénom", DialogType.ERROR, 0);
        } else {
            if (userLogin.getText().isEmpty()) {
                String loginString = nameString.toLowerCase() + "." + firstNameString.toLowerCase();
                userLogin.setText(loginString);
            }
        }
        return nameString;
    }

    @FXML
    public void selectCustomer() {
        openCustomerSelector(stackPane.getScene(), this);
    }

    @FXML
    public void addUser() throws NumberFormatException, UnsupportedEncodingException {
       String userLoginString;
        if (userLogin.getText().isEmpty() || userLogin.getText().isBlank()) {
            userLoginString = userLogin();
        } else {
            userLoginString = userLogin.getText();
        }
        String userMailString = userMail.getText();
        String userPasswordString = userPassword.getText();
        String userConfirmPasswordString = userConfirmPassword.getText();
        String nameString = userName.getText();
        String nameFirstString = userFirstName.getText();

        // Vérification du nom
        if (nameString.isBlank() || nameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un nom", DialogType.ERROR, 0);
            return;
        }

        // Vérification du prénom
        if (nameFirstString.isBlank() || nameFirstString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un prénom", DialogType.ERROR, 0);
            return;
        }


        // Vérification du mail
        if (userMailString.isBlank() || userMailString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un mail", DialogType.ERROR, 0);
            return;
        }


        // Vérification du mot de passe
        if (userPasswordString.isBlank() || userPasswordString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un mot de passe", DialogType.ERROR, 0);
            return;
        }

        // Vérification de la confirmation du mot de passe
        if (userConfirmPasswordString.isBlank() || userConfirmPasswordString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez confirmer le mot de passe", DialogType.ERROR, 0);
            return;
        } else if (!userConfirmPasswordString.equals(userPasswordString)) {
            openDialog(stackPane.getScene(), "Les mots de passe ne correspondent pas", DialogType.ERROR, 0);
            return;
        }

        String customerIdString = userCustomerId.getText();

        // Création de l'objet User
        PostUserDto user = new PostUserDto();
        user.setFirstName(nameString);
        user.setName(nameString);
        user.setLogin(userLoginString);
        user.setEmail(userMailString);
        user.setPassword(userPasswordString);

        // Envoie de la requête
        RequestAPI requestAPI = new RequestAPI();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(user);
        } catch (Exception e) {
            log.error("Error while parsing user list", e);
        }
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("user/add/", user, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("User added successfully : {}", user);
            ObjectMapper mapperResponse = new ObjectMapper();
            GetProductDto userResponse = null;
            try {
                userResponse = mapperResponse.readValue(responseEntity.getBody(), new TypeReference<>() {
                });
            } catch (Exception e) {
                log.error("Error while parsing supplier list", e);
            }
            openDialog(stackPane.getScene(), "Utilisateur " + user.getLogin() + " créé avec succès", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de l'ajout de l'utilisateur " + user.getLogin(), DialogType.ERROR, 0);
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onIdCustomerChanged(KeyEvent event) {
        Object source = event.getSource();
        if (source instanceof MFXTextField textField && textField == userCustomerId) {
            String text = textField.getText();
            if (!text.isEmpty() && IntChecker.isValidIntegerInput(text)) {
                int customerId = Integer.parseInt(text);
                String customerName = getCustomerNameById(customerId);
                this.customerName.setText(customerName);
            } else if (!IntChecker.isValidIntegerInput(text)){
                textField.setText(text.substring(0, text.length() - 1));
            } else {
                this.customerName.setText("");
            }
        }
    }


    private String getCustomerNameById(int customerId) {
        String customer = findCustomerById(customerId);
        this.customerName.setText(Objects.requireNonNullElse(customer, ""));
        return customer;
    }

    private String findCustomerById(int customerId) {
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/customer/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetCustomerDto> customerList = null;
        try {
            customerList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing customer list", e);
        }

        assert customerList != null;
        for (GetCustomerDto customer : customerList) {
            if (customer.getId() == customerId) {
                return customer.getCompanyName();
            }
        }
        return null;
    }

    @Override
    public void processCustomerContent(String customerContent) {
        String[] customer = customerContent.split("-");
        userCustomerId.setText(customer[0]);
        customerName.setText(customer[1]);
    }
}
