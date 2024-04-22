package fr.codesbusters.solidstock.client.controller.selectors.productFamily;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.productFamily.GetProductFamilyDto;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXButton;
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

@Slf4j
@Controller
public class ProductFamilyShowController extends DefaultShowController implements Initializable {

    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField productFamilyId;
    @FXML
    public MFXTextField productFamilyName;

    @FXML
    public TextArea productFamilyDescription;

    @FXML
    private MFXButton enable;

    private void disableTextFields() {
        productFamilyId.setEditable(false);
        productFamilyName.setEditable(false);
        productFamilyDescription.setEditable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productFamilyId.setText(String.valueOf(getId()));
        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/product-family/" + getId(), String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetProductFamilyDto productFamily = null;
        try {
            productFamily = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }

        assert productFamily != null;
        productFamilyName.setText(productFamily.getName());
        productFamilyDescription.setText(productFamily.getDescription());
        disableTextFields();
        enable.setVisible(productFamily.isDeleted());
    }

    @FXML
    public void enable() {
        int idInteger = Integer.parseInt(productFamilyId.getText());
        String nameString = productFamilyName.getText();
        String descriptionString = productFamilyDescription.getText();

        // Création de l'objet ProductFamily
        GetProductFamilyDto productFamiy = new GetProductFamilyDto();
        productFamiy.setId(idInteger);
        productFamiy.setName(nameString);
        productFamiy.setName(descriptionString);

        // Envoie de la requête
        RequestAPI requestAPI = new RequestAPI();

        requestAPI.sendPostRequest("/product-family/" + idInteger, null,  String.class, true, true);

        cancel();
        openDialog(stackPane.getScene(), "Famille de produit " + productFamiy.getName() + " réactivée avec succès.", DialogType.INFORMATION, 0);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

}
