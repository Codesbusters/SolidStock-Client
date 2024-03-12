package fr.codesbusters.solidstock.controller.suppliers;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.Supplier;
import fr.codesbusters.solidstock.controller.DefaultController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class SupplierAddController extends DefaultController implements Initializable {

    @FXML
    public StackPane stackPane;
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
    public MFXTextField supplierThirdParty;

    @FXML
    public MFXTextField supplierCountry;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addSupplier() throws NumberFormatException, UnsupportedEncodingException {
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
        if (nameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom du fournisseur", DialogType.ERROR);
            return;
        }

        // Vérification de l'adresse
        if (addressString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner l'adresse du fournisseur", DialogType.ERROR);
            return;
        }

        // Vérification du complément d'adresse
        if (additionalAddressString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner les informations complémentaires", DialogType.ERROR);
            return;
        }

        // Vérification du code postal
        if (zipCodeString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le code postal du fournisseur", DialogType.ERROR);
            return;
        }

        // Vérification de la ville
        if (cityString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner la ville du fournisseur", DialogType.ERROR);
            return;
        }

        // Vérification du pays
        if (countryString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le pays du fournisseur", DialogType.ERROR);
            return;
        }

        // Vérification du téléphone
        if (phoneString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le numéro de téléphone du fournisseur", DialogType.ERROR);
            return;
        }

        // Vérification de l'email
        if (emailString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner l'adresse mail du fournisseur", DialogType.ERROR);
            return;
        }

        // Vérification du site web
        if (webSiteString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le site web du fournisseur", DialogType.ERROR);
            return;
        }

        // Création de l'objet Supplier
        Supplier supplier = new Supplier();
        supplier.setName(nameString);
        supplier.setAddress(addressString);
        supplier.setAdditionnalAddress(additionalAddressString);
        supplier.setZipCode(zipCodeString);
        supplier.setCity(cityString);
        supplier.setCountry(countryString);
        supplier.setPhone(phoneString);
        supplier.setEmail(emailString);
        supplier.setWebsite(webSiteString);

        log.info("Supplier to add : {}", supplier);

        cancel();

        openDialog(stackPane.getScene(), "Fournisseur " + supplier.getName() + " créé avec succès", DialogType.INFORMATION);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

}
