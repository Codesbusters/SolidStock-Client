package fr.codesbusters.solidstock.controller.products;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.product.GetProductDto;
import fr.codesbusters.solidstock.dto.productFamily.GetProductFamilyDto;
import fr.codesbusters.solidstock.dto.quantityType.GetQuantityTypeDto;
import fr.codesbusters.solidstock.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.dto.vat.GetVatDto;
import fr.codesbusters.solidstock.listener.ProductFamilySelectorListener;
import fr.codesbusters.solidstock.listener.SupplierSelectorListener;
import fr.codesbusters.solidstock.model.QuantityTypeModel;
import fr.codesbusters.solidstock.model.SolidStockDataIntegration;
import fr.codesbusters.solidstock.service.RequestAPI;
import fr.codesbusters.solidstock.utils.Base64Converter;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class ProductEditController extends DefaultShowController implements Initializable, SupplierSelectorListener, ProductFamilySelectorListener {

    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField productId;
    @FXML
    public ImageView imageView;
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
    public MFXTextField productSupplierId;

    @FXML
    public MFXTextField productFamilyID;
    @FXML
    public MFXComboBox<QuantityTypeModel> quantityType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productSellPrice.setTextLimit(8);


        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/product/" + getId(), String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetProductDto product = null;
        try {
            product = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }

        assert  product != null;
        productName.setText(product.getName());
        productId.setText(String.valueOf(product.getId()));
        productFamilyID.setText(String.valueOf(product.getProductFamily().getId()));
        productSupplierId.setText(String.valueOf(product.getSupplier().getId()));
        productBuyPrice.setPromptText(String.valueOf(product.getBuyPrice()));
        productSellPrice.setPromptText(String.valueOf(product.getSellPrice()));
        productVat.setPromptText(String.valueOf(product.getVat().getId()));
        productDescription.setText(product.getDescription());
        ObservableList<QuantityTypeModel> quantityTypes = SolidStockDataIntegration.quantityType;
        quantityType.setItems(quantityTypes);
        quantityType.setText(String.valueOf(product.getQuantityType().getDescription()));
    }

    @FXML
    public void selectSupplier() {
        openSupplierSelector(stackPane.getScene(), this);
    }

    @FXML
    public void selectProductFamily() {
        openProductFamilySelector(stackPane.getScene(), this);
    }


    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void editProduct() throws NumberFormatException {
        int idInteger = Integer.parseInt(productId.getText());
        String nameString = productName.getText();
        String descriptionString = productDescription.getText();
        String supplierIdString = productSupplierId.getText();
        String productIdFamily = productFamilyID.getText();
        String quantityTypeString = null;
        QuantityTypeModel selectedItem = quantityType.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            quantityTypeString = String.valueOf(selectedItem.getID());
        }

        String buyPriceString = productBuyPrice.getText();
        String sellPriceString = productSellPrice.getText();
        String vat = productVat.getText();

        // Vérification du nom du produit
        if (nameString.isBlank()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner le nom du produit", DialogType.ERROR, 0);
            return;
        }

        // Vérification de la famille de produit
        if (productIdFamily.isBlank() || !productIdFamily.matches("\\d+")) {
            openDialog(stackPane.getScene(), "Veuillez renseigner une famille de produit", DialogType.ERROR, 0);
            return;
        }

        // Vérification du fournisseur
        if (supplierIdString.isBlank() || !supplierIdString.matches("\\d+")) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un fournisseur valide", DialogType.ERROR, 0);
            return;
        }


        // Validation de l'image
        String imageBase64 = validateImage();

        int supplierId = Integer.parseInt(supplierIdString);
        int productFamilyId = Integer.parseInt(productIdFamily);
        int quantityTypeId = Integer.parseInt(quantityTypeString);
        int vatId = Integer.parseInt(vat);

        // Création de l'objet Product
        GetProductDto product = new GetProductDto();

        GetSupplierDto supplier = new GetSupplierDto();
        GetSupplierDto supplierDtoId = GetSupplierDto.fromId(supplierId);

        GetProductFamilyDto productFamily = new GetProductFamilyDto();
        GetProductFamilyDto productFamilyDtoId = GetProductFamilyDto.fromId(productFamilyId);

        GetQuantityTypeDto quantityType = new GetQuantityTypeDto();
        GetQuantityTypeDto quantityTypeId2 = GetQuantityTypeDto.fromId(quantityTypeId);

        GetVatDto vatDto = GetVatDto.fromId(vatId);

        supplier.setId(supplierDtoId.getId());
        productFamily.setId(productFamilyDtoId.getId());
        quantityType.setId(quantityTypeId2.getId());
        vatDto.setId(vatId);

        product.setId(idInteger);
        product.setName(nameString);
        product.setDescription(descriptionString);
        product.setSupplier(supplier);
        product.setProductFamily(productFamily);
        product.setQuantityType(quantityType);
        product.setBuyPrice(buyPriceString);
        product.setSellPrice(sellPriceString);
        product.setVat(vatDto);
//        product.setImage(imageBase64);

        log.info("Product to modify : {}", product);
        // Envoie de la requête
        RequestAPI requestAPI = new RequestAPI();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(product);
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }
//        requestAPI.sendPutRequest("/product/" + idI)

        cancel();

        openDialog(stackPane.getScene(), "Produit " + product.getName() + " modifié avec succès", DialogType.INFORMATION, 0);
    }

    private String validateImage() {
        String imageBase64 = null;

        try {
            String imageUrl = URLDecoder.decode(imageView.getImage().getUrl().substring(6), StandardCharsets.UTF_8);
            File imageFile = new File(imageUrl);
            if (!imageFile.getPath().endsWith("\\img\\addImage.png")) {
                if (!imageFile.exists()) {
                    openDialog(stackPane.getScene(), "Veuillez renseigner une image valide", DialogType.ERROR, 0);
                } else {
                    imageBase64 = Base64Converter.convertImageToBase64(imageFile);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Error decoding image URL", e);
            openDialog(stackPane.getScene(), "Erreur lors de la récupération de l'image", DialogType.ERROR, 0);
        }

        return imageBase64;
    }

    @FXML
    public void imageSelect() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Afficher la boîte de dialogue et obtenir le fichier sélectionné
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    @Override
    public void processSupplierContent(String supplierContent) {
        productSupplierId.setText(supplierContent);
    }


    @Override
    public void processProductFamilyContent(String productFamilyContent) {
        productFamilyID.setText(productFamilyContent);
    }


}
