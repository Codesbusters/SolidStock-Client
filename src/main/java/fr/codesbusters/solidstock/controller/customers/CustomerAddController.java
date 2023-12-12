package fr.codesbusters.solidstock.controller.customers;

import fr.codesbusters.solidstock.business.Customer;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.listener.ThirdPartySelectorListener;
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
public class CustomerAddController extends DefaultController implements Initializable, ThirdPartySelectorListener {


    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField customerName;
    @FXML
    public MFXTextField customerThirdParty;
    @FXML
    public MFXTextField customerAddress;
    @FXML
    public MFXTextField customerCorporateName;
    @FXML
    public MFXTextField customerSiren;
    @FXML
    public MFXTextField customerSiret;
    @FXML
    public MFXTextField customerRib;

    @FXML
    public MFXTextField customerRcs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addCustomer() throws NumberFormatException, UnsupportedEncodingException {
        String nameString = customerName.getText();
        String thirdPartyString = customerThirdParty.getText();
        String addressString = customerAddress.getText();
        String corporateNameString = customerCorporateName.getText();
        String sirenString = customerSiren.getText();
        String siretString = customerSiret.getText();
        String ribString = customerRib.getText();
        String rcsString = customerRcs.getText();

        // Vérification du nom du produit
        if (nameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom du client", DialogType.ERROR);
            return;
        }

        // Vérification du tier
        if (thirdPartyString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le tier", DialogType.ERROR);
            return;
        }

        // Vérification de l'adresse
        if (addressString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner l'adresse du client'", DialogType.ERROR);
            return;
        }

        // Vérification du nom de l'entreprise
        if (corporateNameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom de l'entreprise", DialogType.ERROR);
            return;
        }

        // Vérification du numéro de siren
        if (sirenString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le numéro de siren", DialogType.ERROR);
            return;
        }

        // Vérification du numéro de siret
        if (siretString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le numéro de siret", DialogType.ERROR);
            return;
        }

        // Vérification du RIB
        if (ribString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le RIB du client", DialogType.ERROR);
            return;
        }

        // Vérification du RCS
        if (rcsString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le RCS du client", DialogType.ERROR);
            return;
        }

        // Création de l'objet Customer
        Customer customer = new Customer();
        customer.setName(nameString);
        customer.setThirdPartyId(Integer.parseInt(thirdPartyString));
        customer.setAddress(addressString);
        customer.setCorporateName(corporateNameString);
        customer.setSiren(Integer.parseInt(sirenString));
        customer.setSiret(Integer.parseInt(siretString));
        customer.setRib(ribString);
        customer.setRcs(Integer.parseInt(rcsString));

        log.info("Customer to add : {}", customer);

        cancel();

        openDialog(stackPane.getScene(), "Client " + customer.getName() + " créer avec succès", DialogType.INFORMATION);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void selectThirdParty() {
        openThirdPartySelector(stackPane.getScene(), this);
    }

    @Override
    public void processThirdPartyContent(String thirdPartyContent) {
        customerThirdParty.setText(thirdPartyContent);
    }
}
