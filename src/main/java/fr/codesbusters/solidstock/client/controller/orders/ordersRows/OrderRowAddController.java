package fr.codesbusters.solidstock.client.controller.orders.ordersRows;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.invoice.PostInvoiceRowDto;
import fr.codesbusters.solidstock.client.dto.order.PostOrderRowDto;
import fr.codesbusters.solidstock.client.listener.ProductSelectorListener;
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
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class OrderRowAddController extends DefaultShowController implements Initializable, ProductSelectorListener {


    @FXML
    public StackPane stackPane;
    @FXML
    public Label productName;
    @FXML
    public MFXTextField productId;
    @FXML
    public MFXTextField productQuantity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void selectProduct(ActionEvent actionEvent) {
        openProductSelector(stackPane.getScene(), this);
    }

    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    public void addOrderRow() {
        String productId = this.productId.getText();
        String productName = this.productName.getText();
        String productQuantity = this.productQuantity.getText();

        if (productId.isEmpty() || productName.isEmpty() || productQuantity.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez remplir tous les champs.", DialogType.ERROR, 0);
            return;
        }

        try {
            Double.parseDouble(productQuantity);
        } catch (NumberFormatException e) {
            openDialog(stackPane.getScene(), "La quantité doit être un nombre.", DialogType.ERROR, 0);
            return;
        }


        PostOrderRowDto orderRow = new PostOrderRowDto();
        orderRow.setQuantity(Double.parseDouble(productQuantity));
        orderRow.setProductId(Integer.parseInt(productId));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(orderRow);
        } catch (Exception e) {
            log.error("Error while converting object to json", e);
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/orders/" + getId() + "/row/add", json, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la création de la ligne de la commande.", DialogType.ERROR, 0);
        }
    }

    @Override
    public void processProductContent(String productContent, String productName, String productPrice) {
        productId.setText(productContent);
        this.productName.setText(productName);
        this.productQuantity.requestFocus();
    }
}