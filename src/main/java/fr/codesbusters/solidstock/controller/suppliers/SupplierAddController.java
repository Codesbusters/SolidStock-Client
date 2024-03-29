package fr.codesbusters.solidstock.controller.suppliers;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.supplier.PostSupplierDto;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import static fr.codesbusters.solidstock.service.RIBChecker.isValidIBAN;

@Slf4j
@Controller
public class SupplierAddController extends DefaultController implements Initializable {

    @FXML
    public StackPane stackPane;
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

    }

    @FXML
    public void addSupplier() throws NumberFormatException {
        String companyNameString = supplierCompanyName.getText();
        String firstNameString = supplierFirstName.getText();
        String lastNameString = supplierLastName.getText();
        String sirenString = supplierSiren.getText();
        String siretString = supplierSiret.getText();
        String ribString = supplierRib.getText();
        int rcsInt;
        if (supplierRcs.getText().isEmpty()) {
            rcsInt = 0;
        } else {
            try {
                rcsInt = Integer.parseInt(supplierRcs.getText());
            } catch (NumberFormatException e) {
                openDialog(stackPane.getScene(), "La valeur du RCS n'est pas valide", DialogType.ERROR, 0);
                return;
            }
        }
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
        PostSupplierDto supplier = new PostSupplierDto();
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


        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/supplier/add", supplier, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Supplier added successfully : {}", supplier);

            if (supplier.getCompanyName().isEmpty()) {
                openDialog(stackPane.getScene(), "Fournisseur " + supplier.getFirstName() + " " + supplier.getLastName() + " créé avec succès.", DialogType.INFORMATION, 0);
            } else {
                openDialog(stackPane.getScene(), "Fournisseur " + supplier.getCompanyName() + " créé avec succès.", DialogType.INFORMATION, 0);
            }

            cancel();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de l'ajout du fournisseur.", DialogType.ERROR, 0);
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

}
