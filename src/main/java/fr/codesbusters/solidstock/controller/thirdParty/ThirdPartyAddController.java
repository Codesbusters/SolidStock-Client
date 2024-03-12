package fr.codesbusters.solidstock.controller.thirdParty;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.ThirdParty;
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
public class ThirdPartyAddController extends DefaultController implements Initializable {


    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField thirdPartyFirstName;
    @FXML
    public MFXTextField thirdPartyLastName;
    @FXML
    public MFXTextField thirdPartyCity;
    @FXML
    public MFXTextField thirdPartyZipCode;
    @FXML
    public MFXTextField thirdPartyAddress;
    @FXML
    public MFXTextField thirdPartyStreetNumber;
    @FXML
    public MFXTextField thirdPartyEmail;
    @FXML
    public MFXTextField thirdPartyMobilePhone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addThirdParty() throws NumberFormatException, UnsupportedEncodingException {
        String FirstNameString = thirdPartyFirstName.getText();
        String LastNameString = thirdPartyLastName.getText();
        String addressString = thirdPartyAddress.getText();
        String cityNameString = thirdPartyCity.getText();
        String zipCodeString = thirdPartyZipCode.getText();
        String streetNumberString = thirdPartyStreetNumber.getText();
        String emailString = thirdPartyEmail.getText();
        String mobilePhoneString = thirdPartyMobilePhone.getText();

        // Vérification du nom du tiers
        if (FirstNameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le prénom", DialogType.ERROR, 0);
            return;
        }

        // Vérification du tiers
        if (LastNameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom", DialogType.ERROR, 0);
            return;
        }

        // Vérification de l'adresse
        if (addressString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner l'adresse'", DialogType.ERROR, 0);
            return;
        }

        // Vérification de la ville
        if (cityNameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner la ville", DialogType.ERROR, 0);
            return;
        }

        // Vérification du code postal
        if (zipCodeString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le code postal", DialogType.ERROR, 0);
            return;
        }

        // Vérification du numéro de rue
        if (streetNumberString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le numéro de rue", DialogType.ERROR, 0);
            return;
        }

        // Vérification de l'email
        if (emailString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner l'email", DialogType.ERROR, 0);
            return;
        }

        // Vérification du numéro de téléphone
        if (mobilePhoneString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le numéro de téléphone", DialogType.ERROR, 0);
            return;
        }

        // Création de l'objet ThirdParty
        ThirdParty thirdParty = new ThirdParty();
        thirdParty.setFirstName(FirstNameString);
        thirdParty.setLastName(LastNameString);
        thirdParty.setAddress(addressString);
        thirdParty.setCity(cityNameString);
        thirdParty.setZipCode(Integer.parseInt(zipCodeString));
        thirdParty.setStreetNumber(Integer.parseInt(streetNumberString));
        thirdParty.setEmail(emailString);
        thirdParty.setMobilePhone(mobilePhoneString);

        log.info("ThirdParty to add : {}", thirdParty);

        cancel();

        openDialog(stackPane.getScene(), "Tiers " + thirdParty.getLastName() + " " + thirdParty.getFirstName() + " créé avec succès", DialogType.INFORMATION, 0);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

}
