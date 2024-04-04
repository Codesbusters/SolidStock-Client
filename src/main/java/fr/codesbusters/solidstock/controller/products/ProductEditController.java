package fr.codesbusters.solidstock.controller.products;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.product.GetProductDto;
import fr.codesbusters.solidstock.dto.product.PostProductDto;
import fr.codesbusters.solidstock.dto.quantityType.GetQuantityTypeDto;
import fr.codesbusters.solidstock.dto.vat.GetVatDto;
import fr.codesbusters.solidstock.listener.ProductFamilySelectorListener;
import fr.codesbusters.solidstock.listener.SupplierSelectorListener;
import fr.codesbusters.solidstock.service.RequestAPI;
import fr.codesbusters.solidstock.utils.Base64Converter;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
    public MFXComboBox<String> productVat;
    @FXML
    public MFXTextField productSupplierId;
    @FXML
    public Label supplierName;
    @FXML
    public MFXTextField productMinimumStock;

    @FXML
    public Label productFamilyName;
    @FXML
    public MFXTextField productFamilyID;
    @FXML
    public MFXComboBox<String> productQuantityType;

    private File imageSelected;


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

        RequestAPI requestAPIVat = new RequestAPI();

        ResponseEntity<String> responseVatList = requestAPIVat.sendGetRequest("/vat/all", String.class, true, true);
        List<GetVatDto> allVats = null;
        try {
            allVats = mapper.readValue(responseVatList.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            log.error("Error while parsing VAT list", e);
        }

        RequestAPI requestAPIQuantityType = new RequestAPI();

        ResponseEntity<String> responseQuantityTypeList = requestAPIQuantityType.sendGetRequest("/quantity-type/all", String.class, true, true);
        List<GetQuantityTypeDto> allQuantityTypes = null;
        try {
            allQuantityTypes = mapper.readValue(responseQuantityTypeList.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            log.error("Error while parsing VAT list", e);
        }

        assert  product != null;
        productName.setText(product.getName());
        productId.setText(String.valueOf(product.getId()));
        productFamilyID.setText(String.valueOf(product.getProductFamily().getId()));
        productFamilyName.setText(product.getProductFamily().getName());
        productSupplierId.setText(String.valueOf(product.getSupplier().getId()));
        supplierName.setText(product.getSupplier().getCompanyName());
        productBuyPrice.setText(String.valueOf(product.getBuyPrice()));
        productSellPrice.setText(String.valueOf(product.getSellPrice()));

        ObservableList<String> vatDisplays = FXCollections.observableArrayList();
        if (allVats != null) {
            for (GetVatDto vatDto : allVats) {
                String vatDisplay = vatDto.getId() + " - " + vatDto.getPercentage();
                vatDisplays.add(vatDisplay);
            }
        }
        productVat.setItems(vatDisplays);
        productVat.setText(product.getVat().getDescription());
        productMinimumStock.setText(String.valueOf(product.getMinimumStockQuantity()));
        productDescription.setText(product.getDescription());

        ObservableList<String> quantityTypesDisplays = FXCollections.observableArrayList();
        if (allQuantityTypes != null) {
            for (GetQuantityTypeDto quantityTypeDto : allQuantityTypes) {
                String quantityTypeDisplay = quantityTypeDto.getId() + " - " + quantityTypeDto.getUnit();
                quantityTypesDisplays.add(quantityTypeDisplay);
            }
        }
        productQuantityType.setItems(quantityTypesDisplays);
        productQuantityType.setText(product.getQuantityType().getName());

        File productImage = null;
        try {
            productImage = requestAPI.getImage("/product/" + getId() + "/image", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (productImage != null) {
            log.info("Image found");
            Image image = new Image(productImage.toURI().toString());
            imageView.setImage(image);
        }
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

        String buyPriceString = productBuyPrice.getText();
        String sellPriceString = productSellPrice.getText();
        String minimumStockString = productMinimumStock.getText();
        String vat = productVat.getText();
        String quantityType = productQuantityType.getText();

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

        int supplierId = Integer.parseInt(supplierIdString);
        int productFamilyId = Integer.parseInt(productIdFamily);
        int quantityTypeId = Integer.parseInt(quantityType.split(" - ")[0]);
        int vatId = Integer.parseInt(vat.split(" - ")[0]);

        // Création de l'objet Product
        PostProductDto product = new PostProductDto();

        product.setName(nameString);
        product.setDescription(descriptionString);
        product.setSupplierId(supplierId);
        product.setProductFamilyId(productFamilyId);
        product.setQuantityTypeId(quantityTypeId);
        product.setMinimumStockQuantity(Double.parseDouble(minimumStockString));
        product.setBuyPrice(buyPriceString);
        product.setSellPrice(sellPriceString);
        product.setVatId(vatId);

        log.info("Product to modify : {}", product);
        // Envoie de la requête
        RequestAPI requestAPI = new RequestAPI();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(product);
        } catch (Exception e) {
            log.error("Error while parsing product list", e);
        }
        ResponseEntity<String> responseEntity = requestAPI.sendPutRequest("/product/" + idInteger, json, String.class, true);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Product added successfully : {}", product);
            ObjectMapper mapperResponse = new ObjectMapper();
            GetProductDto productResponse = null;
            try {
                productResponse = mapperResponse.readValue(responseEntity.getBody(), new TypeReference<>() {
                });
            } catch (Exception e) {
                log.error("Error while parsing supplier list", e);
            }
            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("file", new FileSystemResource(imageSelected));
            ResponseEntity<String> responseEntity2 = requestAPI.sendPutRequestWithFile("/product/" + productResponse.getId() + "/image", requestBody, String.class, true);
            if (responseEntity2.getStatusCode().is2xxSuccessful()) {
                cancel();
                openDialog(stackPane.getScene(), "Produit " + product.getName() + " modifié avec succès", DialogType.INFORMATION, 0);
            } else {
                openDialog(stackPane.getScene(), "Erreur lors de la modification de l'image",DialogType.ERROR,0);
            }

        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la modification du produit " + product.getName(), DialogType.ERROR, 0);
        }
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
            imageSelected = selectedFile;
        }
    }

    @Override
    public void processSupplierContent(String supplierContent, String supplierName) {
        productSupplierId.setText(supplierContent);
        this.supplierName.setText(supplierName);
    }


    @Override
    public void processProductFamilyContent(String productFamilyContent, String productFamilyName) {
        productFamilyID.setText(productFamilyContent);
        this.productFamilyName.setText(productFamilyName);
    }

}
