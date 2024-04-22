package fr.codesbusters.solidstock.client.controller.customers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.customer.GetCustomerDto;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

import static fr.codesbusters.solidstock.client.service.RIBChecker.isValidIBAN;

@Slf4j
@Controller
public class CustomerEditController extends DefaultShowController implements Initializable {


    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField customerId;
    @FXML
    public MFXTextField customerCompanyName;
    @FXML
    public MFXTextField customerFirstName;
    @FXML
    public MFXTextField customerLastName;
    @FXML
    public MFXTextField customerCountry;
    @FXML
    public MFXTextField customerCity;
    @FXML
    public MFXTextField customerZipCode;
    @FXML
    public MFXTextField customerStreetNumber;
    @FXML
    public MFXTextField customerAddress;
    @FXML
    public MFXTextField customerEmail;

    @FXML
    public MFXTextField customerMobilePhone;

    @FXML
    public MFXTextField customerHomePhone;

    @FXML
    public MFXTextField customerWorkPhone;

    @FXML
    public MFXTextField customerWebsite;

    @FXML
    public MFXTextField customerSiren;

    @FXML
    public MFXTextField customerSiret;

    @FXML
    public MFXTextField customerRib;

    @FXML
    public MFXTextField customerRcs;

    @FXML
    public MFXTextField customerFax;

    @FXML
    public TextArea customerNote;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerId.setEditable(false);
        customerId.setText(String.valueOf(getId()));

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/customer/" + getId(), String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetCustomerDto customer = null;

        try {
            customer = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing customers list", e);
        }

        assert customer != null;
        customerCompanyName.setText(customer.getCompanyName());
        customerFirstName.setText(customer.getFirstName());
        customerLastName.setText(customer.getLastName());
        customerCountry.setText(customer.getCountry());
        customerCity.setText(customer.getCity());
        customerZipCode.setText(customer.getZipCode());
        customerStreetNumber.setText(customer.getStreetNumber());
        customerAddress.setText(customer.getAddress());
        customerMobilePhone.setText(customer.getMobilePhone());
        customerHomePhone.setText(customer.getHomePhone());
        customerWorkPhone.setText(customer.getWorkPhone());
        customerEmail.setText(customer.getEmail());
        customerWebsite.setText(customer.getWebsite());
        customerSiren.setText(customer.getSiren());
        customerSiret.setText(customer.getSiret());
        customerRib.setText(customer.getRib());
        customerRcs.setText(String.valueOf(customer.getRcs()));
        customerRib.setText(customer.getFax());
        customerNote.setText(customer.getNote());
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void editCustomer() throws NumberFormatException, UnsupportedEncodingException {
        int idInteger = Integer.parseInt(customerId.getText());
        String companyNameString = customerCompanyName.getText();
        String firstNameString = customerFirstName.getText();
        String lastNameString = customerLastName.getText();
        String countryString = customerCountry.getText();
        String cityString = customerCity.getText();
        String zipCodeString = customerZipCode.getText();
        String streetNumberString = customerStreetNumber.getText();
        String addressString = customerAddress.getText();
        String mobilePhoneString = customerMobilePhone.getText();
        String homePhoneString = customerHomePhone.getText();
        String workPhoneString = customerWorkPhone.getText();
        String emailString = customerEmail.getText();
        String webSiteString = customerWebsite.getText();
        String sirenString = customerSiren.getText();
        String siretString = customerSiret.getText();
        String ribString = customerRib.getText();
        int rcsInt = Integer.parseInt(customerRcs.getText());
        String faxString = customerFax.getText();
        String noteString = customerNote.getText();

        // Vérification du nom du client
        if (companyNameString.isEmpty() && firstNameString.isEmpty() && lastNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un des champs suivants : (Nom / Prénom / Raison sociale)", DialogType.ERROR, 0);
            return;
        }

        // Vérification du RIB du client
        if (!ribString.isEmpty() && !isValidIBAN(ribString)) {
            boolean result = openDialog(stackPane.getScene(), "Rib non valide, souhaitez vous continuer", DialogType.CONFIRMATION, 0);
            if (!result) {
                return;
            }
        }

        // Création de l'objet Customer
        GetCustomerDto customer = new GetCustomerDto();
        customer.setId(idInteger);
        customer.setCompanyName(companyNameString);
        customer.setFirstName(firstNameString);
        customer.setLastName(lastNameString);
        customer.setCountry(countryString);
        customer.setCity(cityString);
        customer.setZipCode(zipCodeString);
        customer.setStreetNumber(streetNumberString);
        customer.setAddress(addressString);
        if (mobilePhoneString != null) {
            customer.setMobilePhone(mobilePhoneString.replace(" ", ""));
        }
        if (homePhoneString != null) {
            customer.setHomePhone(homePhoneString.replace(" ", ""));
        }
        if (workPhoneString != null) {
            customer.setWorkPhone(workPhoneString.replace(" ", ""));
        }
        customer.setEmail(emailString);
        customer.setWebsite(webSiteString);
        if (sirenString != null) {
            customer.setSiren(sirenString.replace(" ", ""));
        }
        if (siretString != null) {
            customer.setSiret(siretString.replace(" ", ""));
        }
        customer.setRib(ribString.replace(" ", ""));
        customer.setRcs(rcsInt);
        if (faxString != null) {
            customer.setFax(faxString.replace(" ", ""));
        }
        customer.setNote(noteString);

        // Envoi de la requête
        RequestAPI requestAPI = new RequestAPI();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(customer);
        } catch (Exception e) {
            log.error("Error while parsing customers list", e);
        }
        requestAPI.sendPutRequest("/customer/" + idInteger, json, String.class, true);

        cancel();
        if (customer.getCompanyName().isEmpty()) {
            openDialog(stackPane.getScene(), "Client " + customer.getFirstName() + " " + customer.getLastName() + " modifié avec succès.", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Client " + customer.getCompanyName() + " modifié avec succès.", DialogType.INFORMATION, 0);
        }
    }
}
