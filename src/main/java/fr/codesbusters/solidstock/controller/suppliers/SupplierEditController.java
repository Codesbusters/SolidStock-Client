package fr.codesbusters.solidstock.controller.suppliers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class SupplierEditController extends DefaultShowController implements Initializable {

    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField supplierId;

    @FXML
    public MFXTextField supplierCompanyName;

    @FXML
    public MFXTextField supplierFirstName;

    @FXML
    public MFXTextField supplierLastName;
    @FXML
    public MFXTextField supplierAddress;

    @FXML
    public MFXTextField supplierStreetNumber;

    @FXML
    public MFXTextField supplierZipCode;

    @FXML
    public MFXTextField supplierCity;

    @FXML
    public MFXTextField supplierMobilePhone;

    @FXML
    public MFXTextField supplierWorkPhone;

    @FXML
    public MFXTextField supplierHomePhone;

    @FXML
    public MFXTextField supplierEmail;

    @FXML
    public MFXTextField supplierSiret;

    @FXML
    public MFXTextField supplierSiren;

    @FXML
    public MFXTextField supplierRib;

    @FXML
    public MFXTextField supplierRcs;

    @FXML
    public MFXTextField supplierWebsite;

    @FXML
    public MFXTextField supplierCountry;

    @FXML
    public MFXTextField supplierFax;

    @FXML
    public MFXTextField supplierNote;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplierId.setText(String.valueOf(getId()));

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/supplier/" + getId(), String.class, true);
        ObjectMapper mapper = new ObjectMapper();
        GetSupplierDto supplier = null;
        try {
            supplier = mapper.readValue(responseEntity.getBody(), new TypeReference<GetSupplierDto>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }

        supplierCompanyName.setText(supplier.getCompanyName());
        supplierAddress.setText(supplier.getAddress());
        supplierStreetNumber.setText("");
        supplierZipCode.setText(supplier.getZipCode());
        supplierCity.setText(supplier.getCity());
        supplierMobilePhone.setText(supplier.getHomePhone());
        supplierEmail.setText(supplier.getEmail());
        supplierWebsite.setText(supplier.getWebsite());
        supplierCountry.setText(supplier.getCountry());
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void editSupplier() throws NumberFormatException {
        int idInteger = Integer.parseInt(supplierId.getText());
        String companyNameString = supplierCompanyName.getText();
        String firstNameString = supplierFirstName.getText();
        String lastNameString = supplierLastName.getText();
        String sirenString = supplierSiren.getText();
        String siretString = supplierSiret.getText();
        String ribString = supplierRib.getText();
        int rcsInt = Integer.parseInt(supplierRcs.getText());
        String addressString = supplierAddress.getText();
        String streetNumberString = supplierStreetNumber.getText();
        String zipCodeString = supplierZipCode.getText();
        String cityString = supplierCity.getText();
        String countryString = supplierCountry.getText();
        String mobilePhoneString = supplierMobilePhone.getText();
        String homePhoneString = supplierHomePhone.getText();
        String workPhoneString = supplierWorkPhone.getText();
        String emailString = supplierEmail.getText();
        String webSiteString = supplierWebsite.getText();
        String faxString = supplierFax.getText();
        String noteString = supplierNote.getText();

        // Vérification du nom du fournisseur
        if (companyNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom de la société.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du prénom du fournisseur
        if (firstNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le prénom du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du nom du fournisseur
        if (lastNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom du fournisseur", DialogType.ERROR, 0);
            return;
        }

        // Vérification de l'adresse
        if (addressString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner l'adresse du fournisseur", DialogType.ERROR, 0);
            return;
        }

        // Vérification du code postal
        if (zipCodeString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le code postal du fournisseur", DialogType.ERROR, 0);
            return;
        }

        // Vérification de la ville
        if (cityString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner la ville du fournisseur", DialogType.ERROR, 0);
            return;
        }

        // Vérification du pays
        if (countryString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le pays du fournisseur", DialogType.ERROR, 0);
            return;
        }

        // Vérification du téléphone
        if (mobilePhoneString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le numéro de téléphone du fournisseur", DialogType.ERROR, 0);
            return;
        }

        // Vérification de l'email
        if (emailString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner l'adresse mail du fournisseur", DialogType.ERROR, 0);
            return;
        }

        // Création de l'objet Supplier
        GetSupplierDto supplier = new GetSupplierDto();
        supplier.setId(idInteger);
        supplier.setCompanyName(companyNameString);
        supplier.setFirstName(firstNameString);
        supplier.setLastName(lastNameString);
        supplier.setAddress(addressString);
        supplier.setSiren(sirenString);
        supplier.setSiret(siretString);
        supplier.setRib(ribString);
        supplier.setRcs(rcsInt);
        supplier.setStreetNumber(streetNumberString);
        supplier.setZipCode(zipCodeString);
        supplier.setCity(cityString);
        supplier.setCountry(countryString);
        supplier.setMobilePhone(mobilePhoneString);
        supplier.setHomePhone(homePhoneString);
        supplier.setWorkPhone(workPhoneString);
        supplier.setEmail(emailString);
        supplier.setWebsite(webSiteString);
        supplier.setFax(faxString);
        supplier.setNote(noteString);

        // Envoi de la requête
        RequestAPI requestAPI = new RequestAPI();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(supplier);
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }
        requestAPI.sendPutRequest("/supplier/" + idInteger, json, String.class, true);

        cancel();
        openDialog(stackPane.getScene(), "Fournisseur " + supplier.getCompanyName() + " modifié avec succès", DialogType.INFORMATION, 0);
    }
}