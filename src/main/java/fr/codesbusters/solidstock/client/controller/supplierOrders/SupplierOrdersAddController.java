package fr.codesbusters.solidstock.client.controller.supplierOrders;


import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.supplierOrder.PostSupplierOrderDto;
import fr.codesbusters.solidstock.client.listener.SupplierSelectorListener;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class SupplierOrdersAddController extends DefaultShowController implements Initializable, SupplierSelectorListener {

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public MFXTextField supplierId;
    @FXML
    public Label supplierName;
    @FXML
    public MFXTextField orderNumber;
    @FXML
    public MFXDatePicker orderDate;
    @FXML
    public MFXDatePicker deliveryDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void selectSupplier() {
            openSupplierSelector(anchorPane.getScene(), this);
    }

    @Override
    public void processSupplierContent(String supplierContent, String supplierName) {
        supplierId.setText(supplierContent);
        this.supplierName.setText(supplierName);
    }

    @FXML
    public void save() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PostSupplierOrderDto postSupplierOrderDto = new PostSupplierOrderDto();
        postSupplierOrderDto.setSupplierId(supplierId.getText());
        postSupplierOrderDto.setOrderNumber(orderNumber.getText());
        postSupplierOrderDto.setOrderDate(orderDate.getValue().format(formatter));
        postSupplierOrderDto.setDeliveryDate(deliveryDate.getValue().format(formatter));
        postSupplierOrderDto.setStatus("TO_BE_VALIDATED");

        if (postSupplierOrderDto.getSupplierId().isEmpty() || postSupplierOrderDto.getOrderNumber().isEmpty() || postSupplierOrderDto.getOrderDate().isEmpty() || postSupplierOrderDto.getDeliveryDate().isEmpty()) {
            openDialog(anchorPane.getScene(), "Veuillez remplir tous les champs.", DialogType.ERROR, 0);
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/supplier-order/add", postSupplierOrderDto, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
            openDialog(anchorPane.getScene(), "Commande fournisseur créée avec succès.", DialogType.INFORMATION, 0);
        } else {
            openDialog(anchorPane.getScene(), "Erreur lors de la création de la commande fournisseur", DialogType.ERROR, 0);
        }
    }
}
