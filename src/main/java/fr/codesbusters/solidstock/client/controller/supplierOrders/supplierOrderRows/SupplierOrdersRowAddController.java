package fr.codesbusters.solidstock.client.controller.supplierOrders.supplierOrderRows;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultController;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.invoice.PostInvoiceRowDto;
import fr.codesbusters.solidstock.client.listener.ProductSelectorListener;
import fr.codesbusters.solidstock.client.listener.SupplierSelectorListener;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class SupplierOrdersRowAddController extends DefaultShowController implements ProductSelectorListener, Initializable {
    @FXML
    public StackPane stackPane;

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

    }

    @FXML
    public void addSupplierOrderRow() {
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


        PostInvoiceRowDto invoiceRow = new PostInvoiceRowDto();
        invoiceRow.setQuantity(Double.parseDouble(productQuantity));
        invoiceRow.setSellPrice(Double.parseDouble(productPrice));
        invoiceRow.setProductId(Integer.parseInt(productId));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(invoiceRow);
        } catch (Exception e) {
            log.error("Error while converting object to json", e);
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/supplier-order/" + getIntermediaryId() + "/row/add", json, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la création de la ligne de facture.", DialogType.ERROR, 0);
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

