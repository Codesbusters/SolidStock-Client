package fr.codesbusters.solidstock.controller.suppliers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.Supplier;
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
    public MFXTextField supplierName;
    @FXML
    public MFXTextField supplierAddress;

    @FXML
    public MFXTextField supplierAdditionalAddress;

    @FXML
    public MFXTextField supplierZipCode;

    @FXML
    public MFXTextField supplierCity;

    @FXML
    public MFXTextField supplierPhone;

    @FXML
    public MFXTextField supplierEmail;

    @FXML
    public MFXTextField supplierWebsite;

    @FXML
    public MFXTextField supplierCountry;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplierId.setText(String.valueOf(getId()));

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/supplier/"+String.valueOf(getId()), String.class, true);
        ObjectMapper mapper = new ObjectMapper();
        GetSupplierDto supplier = null;
        try {
            supplier = mapper.readValue(responseEntity.getBody(), new TypeReference<GetSupplierDto>() {});
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }

        supplierName.setText(supplier.getCompanyName());
        supplierAddress.setText(supplier.getAddress());
        supplierAdditionalAddress.setText("");
        supplierZipCode.setText(supplier.getZipCode());
        supplierCity.setText(supplier.getCity());
        supplierPhone.setText(supplier.getHomePhone());
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
        String nameString = supplierName.getText();
        String addressString = supplierAddress.getText();
        String additionalAddressString = supplierAdditionalAddress.getText();
        String zipCodeString = supplierZipCode.getText();
        String cityString = supplierCity.getText();
        String countryString = supplierCountry.getText();
        String phoneString = supplierPhone.getText();
        String emailString = supplierEmail.getText();
        String webSiteString = supplierWebsite.getText();

        // Vérification du nom du fournisseur
        if (nameString.isEmpty()) {
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
        if (phoneString.isEmpty()) {
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
        supplier.setCompanyName(nameString);
        supplier.setAddress(addressString);
        supplier.setZipCode(zipCodeString);
        supplier.setCity(cityString);
        supplier.setCountry(countryString);
        supplier.setHomePhone(phoneString);
        supplier.setEmail(emailString);
        supplier.setWebsite(webSiteString);

        // Envoi de la requête
        RequestAPI requestAPI = new RequestAPI();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(supplier);
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }
        requestAPI.sendPutRequest("/supplier/"+String.valueOf(idInteger), json, String.class, true);

        cancel();

        openDialog(stackPane.getScene(), "Fournisseur " + supplier.getCompanyName() + " modifié avec succès", DialogType.INFORMATION, 0);
    }
}
