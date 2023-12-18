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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addSupplier() throws NumberFormatException, UnsupportedEncodingException {
        String nameString = supplierName.getText();
        String addressString = supplierAddress.getText();

        // Vérification du nom du fournisseur
        if (nameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom du fournisseur", DialogType.ERROR);
            return;
        }

        // Vérification de l'adresse
        if (addressString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner l'adresse du fournisseur'", DialogType.ERROR);
            return;
        }

        // Création de l'objet Customer
        Supplier supplier = new Supplier();
        supplier.setName(nameString);
        supplier.setAddress(addressString);

        log.info("Supplier to add : {}", supplier);

        cancel();

        openDialog(stackPane.getScene(), "Fournisseur " + supplier.getName() + " créer avec succès", DialogType.INFORMATION);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

}
