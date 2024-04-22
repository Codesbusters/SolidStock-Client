package fr.codesbusters.solidstock.client.controller.suppliers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import static fr.codesbusters.solidstock.client.service.RIBChecker.isValidIBAN;

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
    public TextArea supplierNote;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplierId.setEditable(false);
        supplierId.setText(String.valueOf(getId()));

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/supplier/" + getId(), String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetSupplierDto supplier = null;
        try {
            supplier = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }

        assert supplier != null;
        supplierCompanyName.setText(supplier.getCompanyName());
        supplierFirstName.setText(supplier.getFirstName());
        supplierLastName.setText(supplier.getLastName());
        supplierAddress.setText(supplier.getAddress());
        supplierStreetNumber.setText(supplier.getStreetNumber());
        supplierZipCode.setText(supplier.getZipCode());
        supplierCity.setText(supplier.getCity());
        supplierSiren.setText(supplier.getSiren());
        supplierSiret.setText(supplier.getSiret());
        supplierRib.setText(supplier.getRib());
        supplierRcs.setText(String.valueOf(supplier.getRcs()));
        supplierMobilePhone.setText(supplier.getMobilePhone());
        supplierHomePhone.setText(supplier.getHomePhone());
        supplierWorkPhone.setText(supplier.getWorkPhone());
        supplierEmail.setText(supplier.getEmail());
        supplierWebsite.setText(supplier.getWebsite());
        supplierCountry.setText(supplier.getCountry());
        supplierFax.setText(supplier.getFax());
        supplierNote.setText(supplier.getNote());
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

        // Vérification du nom du fournisseur ou du prénom ou nom de société
        if (companyNameString.isEmpty() && firstNameString.isEmpty() && lastNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un des champs suivants : (Nom / Prénom / Nom société)", DialogType.ERROR, 0);
            return;
        }
        // Vérification du RIB du fournisseur
        if (!ribString.isEmpty() && !isValidIBAN(ribString)) {
            boolean result = openDialog(stackPane.getScene(), "Rib non valide, souhaitez vous continuer", DialogType.CONFIRMATION, 0);
            if (!result) {
                return;
            }
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
        if (supplier.getCompanyName().isEmpty()) {
            openDialog(stackPane.getScene(), "Fournisseur " + supplier.getFirstName() + " " + supplier.getLastName() + " modifié avec succès.", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Fournisseur " + supplier.getCompanyName() + " modifié avec succès.", DialogType.INFORMATION, 0);
        }
    }
}