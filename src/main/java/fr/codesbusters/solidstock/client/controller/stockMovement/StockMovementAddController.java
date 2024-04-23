package fr.codesbusters.solidstock.client.controller.stockMovement;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultController;
import fr.codesbusters.solidstock.client.dto.stockMovement.PostStockMovementDto;
import fr.codesbusters.solidstock.client.listener.ProductSelectorListener;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class StockMovementAddController extends DefaultController implements Initializable, ProductSelectorListener {

    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField productId;

    @FXML
    public MFXTextField productQuantity;

    @FXML
    public MFXTextField productBatchNumber;

    @FXML
    public MFXDatePicker expiryDate;

    @FXML
    public Label productName;

    @FXML
    public MFXComboBox<String> statusType;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("IN_DELIVERY");
        types.add("IN_RETURN");
        types.add("IN_ADJUSTMENT");
        types.add("OUT_SALE");
        types.add("OUT_LOSS");
        types.add("OUT_BREAKAGE");
        types.add("OUT_EXPIRATION");
        types.add("OUT_STOLEN");
        types.add("OUT_SALES_EVENT");
        types.add("OUT_TRANSFER");
        types.add("OUT_ADJUSTMENT");

        statusType.getItems().addAll(types);

    }

    @FXML
    public void addMovement() {
        String productQuantity = this.productQuantity.getText();
        String productBatchNumber = this.productBatchNumber.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String productExpiryDate = this.expiryDate.getValue().format(formatter);

        PostStockMovementDto postStockMovementDto = new PostStockMovementDto();
        postStockMovementDto.setProductId(Integer.parseInt(productId.getText()));
        postStockMovementDto.setLocationId(1);
        postStockMovementDto.setQuantity(Double.parseDouble(productQuantity));
        postStockMovementDto.setBatchNumber(productBatchNumber);
        postStockMovementDto.setExpiredDate(productExpiryDate);
        postStockMovementDto.setType(statusType.getValue());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(postStockMovementDto);
        } catch (Exception e) {
            log.error("Error while converting object to json", e);
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/stock-movement/add", json, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de l'enregistrement du mouvement de stock.", DialogType.ERROR, 0);
        }
    }

    public void selectProduct(ActionEvent actionEvent) {
        openProductSelector(stackPane.getScene(), this);
    }

    @Override
    public void processProductContent(String productContent, String productName, String productPrice) {
        productId.setText(productContent);
        this.productName.setText(productName);
        this.productQuantity.requestFocus();
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}
