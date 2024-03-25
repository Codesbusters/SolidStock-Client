package fr.codesbusters.solidstock.controller.selectors.productFamily;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.productFamily.GetProductFamilyDto;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class ProductFamilyAddController extends DefaultController implements Initializable {

    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField productFamilyName;

    @FXML
    public TextArea productFamilyDescription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addProductFamily() throws NumberFormatException {
        String nameString = productFamilyName.getText();
        String descriptionString = productFamilyDescription.getText();

        // Vérification du nom de famille du produit
        if (nameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un libelle.", DialogType.ERROR, 0);
            return;
        }

        // Vérification de la description de famille du produit
        if (descriptionString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner une description.", DialogType.ERROR, 0);
            return;
        }

        // Création de l'objet ProductFamily
        GetProductFamilyDto productFamily = new GetProductFamilyDto();
        productFamily.setName(nameString);
        productFamily.setDescription(descriptionString);

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/product-family/add", productFamily, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("ProductFamily added successfully : {}", productFamily);
            openDialog(stackPane.getScene(), "Famille de produit " + productFamily.getName() + " créé avec succès.", DialogType.INFORMATION, 0);
            cancel();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de l'ajout d'une famille de produit.", DialogType.ERROR, 0);
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}
