package fr.codesbusters.solidstock.controller.estimate.estimateRows;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.estimates.GetEstimateRowDto;
import fr.codesbusters.solidstock.dto.estimates.PostEstimateRowDto;
import fr.codesbusters.solidstock.listener.ProductSelectorListener;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class EstimateRowEditController extends DefaultShowController implements Initializable, ProductSelectorListener {
    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField estimateRowId;

    @FXML
    public MFXTextField productId;

    @FXML
    public Label productName;
    @FXML
    public MFXTextField productQuantity;
    @FXML
    public MFXTextField productPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/estimate/" + getId() + "/row/" + getIntermediaryId(), String.class, true, true);

        ObjectMapper objectMapper = new ObjectMapper();
        GetEstimateRowDto getEstimateRowDto = null;
        try {
            getEstimateRowDto = objectMapper.readValue(responseEntity.getBody(), GetEstimateRowDto.class);
        } catch (Exception e) {
            log.error("Error while converting json to object", e);
        }

        estimateRowId.setText(String.valueOf(getEstimateRowDto.getId()));
        productId.setText(String.valueOf(getEstimateRowDto.getProduct().getId()));
        productName.setText(getEstimateRowDto.getProduct().getName());
        productQuantity.setText(String.valueOf(getEstimateRowDto.getQuantity()));
        productPrice.setText(String.valueOf(getEstimateRowDto.getSellPrice()));


    }

    @FXML
    public void editEstimateRow() {
        String productId = this.productId.getText();
        String productName = this.productName.getText();
        String productQuantity = this.productQuantity.getText();
        String productPrice = this.productPrice.getText();

        if (productId.isEmpty() || productName.isEmpty() || productQuantity.isEmpty() || productPrice.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez remplir tous les champs.", DialogType.ERROR, 0);
            return;
        }

        try {
            Double.parseDouble(productQuantity);
        } catch (NumberFormatException e) {
            openDialog(stackPane.getScene(), "La quantité doit être un nombre.", DialogType.ERROR, 0);
            return;
        }

        try {
            Double.parseDouble(productPrice);
        } catch (NumberFormatException e) {
            openDialog(stackPane.getScene(), "Le prix doit être un nombre.", DialogType.ERROR, 0);
            return;
        }


        PostEstimateRowDto estimateRow = new PostEstimateRowDto();
        estimateRow.setQuantity(Double.parseDouble(productQuantity));
        estimateRow.setSellPrice(Double.parseDouble(productPrice));
        estimateRow.setProductId(Integer.parseInt(productId));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(estimateRow);
        } catch (Exception e) {
            log.error("Error while converting object to json", e);
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPutRequest("/estimate/" + getId() + "/row/" + getIntermediaryId(), json, String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la création de la ligne de devis.", DialogType.ERROR, 0);
        }

    }

    public void selectProduct(ActionEvent actionEvent) {
        openProductSelector(stackPane.getScene(), this);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void processProductContent(String productContent, String productName, String productPrice) {
        productId.setText(productContent);
        this.productName.setText(productName);
        this.productPrice.setText(productPrice);
        this.productQuantity.requestFocus();
    }
}