package fr.codesbusters.solidstock.controller.customers;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.customer.PostCustomerDto;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

import static fr.codesbusters.solidstock.service.RIBChecker.isValidIBAN;

@Slf4j
@Controller
public class CustomerAddController extends DefaultController implements Initializable {


    @FXML
    public StackPane stackPane;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addCustomer() throws NumberFormatException, UnsupportedEncodingException {
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
        int rcsInt;
        if (customerRcs.getText().isEmpty()) {
            rcsInt = 0;
        } else {
            try {
                rcsInt = Integer.parseInt(customerRcs.getText());
            } catch (NumberFormatException e) {
                openDialog(stackPane.getScene(), "La valeur du RCS n'est pas valide", DialogType.ERROR, 0);
                return;
            }
        }
        String faxString = customerFax.getText();

        // Vérification du nom du client
        if (firstNameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le prénom du client", DialogType.ERROR, 0);
            return;
        }

        // Vérification du nom
        if (lastNameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom du client", DialogType.ERROR, 0);
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
        PostCustomerDto customer = new PostCustomerDto();
        customer.setCompanyName(companyNameString);
        customer.setFirstName(firstNameString);
        customer.setLastName(lastNameString);
        customer.setCountry(countryString);
        customer.setCity(cityString);
        customer.setZipCode(zipCodeString);
        customer.setStreetNumber(streetNumberString);
        customer.setAddress(addressString);
        customer.setEmail(emailString);
        customer.setMobilePhone(mobilePhoneString);
        customer.setHomePhone(homePhoneString);
        customer.setWorkPhone(workPhoneString);
        customer.setWebsite(webSiteString);
        customer.setSiren(sirenString);
        customer.setSiret(siretString);
        customer.setRib(ribString);
        customer.setRcs(rcsInt);
        customer.setFax(faxString);

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/customer/add", customer, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Customer to add : {}", customer);

            if (customer.getCompanyName().isEmpty()) {
                openDialog(stackPane.getScene(), "Client " + customer.getFirstName() + " " + customer.getLastName() + " créé avec succès.", DialogType.INFORMATION, 0);
            } else {
                openDialog(stackPane.getScene(), "Client " + customer.getCompanyName() + " créé avec succès.", DialogType.INFORMATION, 0);
            }

            cancel();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de l'ajout du client.", DialogType.ERROR, 0);
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

}
