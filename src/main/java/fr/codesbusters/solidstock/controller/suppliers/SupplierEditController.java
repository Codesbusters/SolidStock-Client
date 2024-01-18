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

import java.io.UnsupportedEncodingException;
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
    public void editSupplier() throws NumberFormatException, UnsupportedEncodingException {
        int idInteger = Integer.parseInt(supplierId.getText());
        String nameString = supplierName.getText();
        String addressString = supplierAddress.getText();

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

        // Création de l'objet Supplier
        Supplier supplier = new Supplier();
        supplier.setId(idInteger);
        supplier.setName(nameString);
        supplier.setAddress(addressString);

        log.info("Supplier to edit : {}", supplier);

        cancel();

        openDialog(stackPane.getScene(), "Fournisseur " + supplier.getName() + " modifié avec succès", DialogType.INFORMATION);
    }
}
