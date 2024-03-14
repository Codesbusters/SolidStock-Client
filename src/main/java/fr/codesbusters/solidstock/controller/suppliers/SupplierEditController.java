package fr.codesbusters.solidstock.controller.suppliers;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.Supplier;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
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
        if (companyNameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom de la société.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du prénom du fournisseur
        if (firstNameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le prénom du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du nom du fournisseur
        if (lastNameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du siren du fournisseur
        if (sirenString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le siren de la société.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du siret du fournisseur
        if (siretString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le siret de la société.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du rib du fournisseur
        if (ribString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le rib de la société.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du rcs du fournisseur
        if (rcsInt == 0) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le rcs de la société.", DialogType.ERROR, 0);
            return;
        }

        // Vérification de l'adresse
        if (addressString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner l'adresse du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification de le numéro de rue
        if (streetNumberString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le numéro de rue du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du code postal
        if (zipCodeString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le code postal du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification de la ville
        if (cityString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner la ville du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du pays
        if (countryString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le pays du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du téléphone personnel
        if (mobilePhoneString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le numéro de téléphone personnel du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du téléphone domicile
        if (homePhoneString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le numéro de téléphone domicile du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du téléphone professionnel
        if (workPhoneString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le numéro de téléphone professionnel du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification de l'email
        if (emailString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner l'adresse mail du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du site web
        if (webSiteString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le site web du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Vérification du fax
        if (faxString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le fax du fournisseur.", DialogType.ERROR, 0);
            return;
        }

        // Création de l'objet Supplier
        Supplier supplier = new Supplier();
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

        log.info("Supplier to edit : {}", supplier);

        cancel();

        openDialog(stackPane.getScene(), "Fournisseur " + supplier.getLastName() + " " + supplier.getFirstName() + " de la société " + supplier.getCompanyName() + " modifié avec succès", DialogType.INFORMATION, 0);
    }
}