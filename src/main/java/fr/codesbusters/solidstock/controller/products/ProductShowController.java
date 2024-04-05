package fr.codesbusters.solidstock.controller.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.product.GetProductDto;
import fr.codesbusters.solidstock.model.QuantityTypeModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class ProductShowController extends DefaultShowController implements Initializable {


    @FXML
    public StackPane stackPane;
    @FXML
    public ImageView imageView;
    @FXML
    public MFXTextField productId;
    @FXML
    public MFXTextField productName;
    @FXML
    public TextArea productDescription;
    @FXML
    public MFXTextField productBuyPrice;
    @FXML
    public MFXTextField productSellPrice;
    @FXML
    public MFXTextField productVat;
    @FXML
    public MFXTextField productSupplierID;

    @FXML
    public Label supplierName;
    @FXML
    public MFXTextField productFamilyID;

    @FXML
    public MFXTextField productMinimumStock;

    @FXML
    public Label productFamilyName;
    @FXML
    public MFXComboBox<QuantityTypeModel> productQuantityType;

    private void disableTextFields() {
        productId.setEditable(false);
        productName.setEditable(false);
        productDescription.setEditable(false);
        productBuyPrice.setEditable(false);
        productSellPrice.setEditable(false);
        productVat.setEditable(false);
        productSupplierID.setEditable(false);
        productFamilyID.setEditable(false);
        productQuantityType.setEditable(false);
        productMinimumStock.setEditable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productId.setText(String.valueOf(getId()));
        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/product/" + getId(), String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetProductDto product = null;
        try {
            product = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing product list", e);
        }

        assert  product != null;
        productName.setText(product.getName());
        productQuantityType.setText(String.valueOf(product.getQuantityType().getId()));
        productFamilyID.setText(String.valueOf(product.getProductFamily().getId()));
        productFamilyName.setText(product.getProductFamily().getName());
        productSupplierID.setText(String.valueOf(product.getSupplier().getId()));
        supplierName.setText(product.getSupplier().getCompanyName());
        productBuyPrice.setText(String.valueOf(product.getBuyPrice()));
        productSellPrice.setText(String.valueOf(product.getSellPrice()));
        productVat.setText(String.valueOf(product.getVat().getRate()));
        productMinimumStock.setText(String.valueOf(product.getMinimumStockQuantity()));
        productDescription.setText(product.getDescription());
        disableTextFields();
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }


}
