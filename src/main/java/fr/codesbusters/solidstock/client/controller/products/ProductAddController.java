package fr.codesbusters.solidstock.client.controller.products;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultController;
import fr.codesbusters.solidstock.client.dto.product.GetProductDto;
import fr.codesbusters.solidstock.client.dto.product.PostProductDto;
import fr.codesbusters.solidstock.client.dto.productFamily.GetProductFamilyDto;
import fr.codesbusters.solidstock.client.dto.quantityType.GetQuantityTypeDto;
import fr.codesbusters.solidstock.client.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.client.dto.vat.GetVatDto;
import fr.codesbusters.solidstock.client.listener.ProductFamilySelectorListener;
import fr.codesbusters.solidstock.client.listener.SupplierSelectorListener;
import fr.codesbusters.solidstock.client.service.IntChecker;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class ProductAddController extends DefaultController implements Initializable, SupplierSelectorListener, ProductFamilySelectorListener {


    @FXML
    public StackPane stackPane;
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
    public MFXTextField productFamilyID;
    @FXML
    public MFXTextField productBarCode;

    @FXML
    public Label productFamilyName;
    @FXML
    public MFXComboBox<String> productQuantityType;

    private File imageSelected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productSellPrice.setTextLimit(8);

        RequestAPI requestAPIVat = new RequestAPI();

        ResponseEntity<String> responseVatList = requestAPIVat.sendGetRequest("/vat/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();

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
            log.error("Error while parsing quantityType list", e);
        }

        ObservableList<String> vatDisplays = FXCollections.observableArrayList();
        if (allVats != null) {
            for (GetVatDto vatDto : allVats) {
                String vatDisplay = vatDto.getId() + " - " + vatDto.getPercentage();
                vatDisplays.add(vatDisplay);
            }
        }
        productVat.setItems(vatDisplays);

        ObservableList<String> quantityTypesDisplays = FXCollections.observableArrayList();
        if (allQuantityTypes != null) {
            for (GetQuantityTypeDto quantityTypeDto : allQuantityTypes) {
                String quantityTypeDisplay = quantityTypeDto.getUnit() + " - " + quantityTypeDto.getName();
                quantityTypesDisplays.add(quantityTypeDisplay);
            }
        }
        productQuantityType.setItems(quantityTypesDisplays);
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
    public void addProduct() throws NumberFormatException, UnsupportedEncodingException {
        String nameString = productName.getText();
        String descriptionString = productDescription.getText();
        String supplierIdString = productSupplierId.getText();
        String productIdFamily = productFamilyID.getText();
        String productBarCodeString = productBarCode.getText();
        if (!isValidBarCode(productBarCodeString)) {
            openDialog(stackPane.getScene(), "Veuillez saisir un barCode valide : 13 chiffres", DialogType.ERROR,0);
            return;
        }
        productBarCodeString = formatBarCode(productBarCodeString);

        String buyPriceString = productBuyPrice.getText();
        if (!isValidPrice(buyPriceString)) {
            openDialog(stackPane.getScene(), "Veuillez saisir un prix d'achat valide", DialogType.ERROR, 0);
            return;
        }
        String sellPriceString = productSellPrice.getText();
        if (!isValidPrice(sellPriceString)) {
            openDialog(stackPane.getScene(), "Veuillez saisir un prix de vente valide", DialogType.ERROR, 0);
            return;
        }
        String minimumStockString = productMinimumStock.getText();
        if (!isValidQuantity(minimumStockString)) {
            openDialog(stackPane.getScene(), "Veuillez saisir une quantitée de stock valide", DialogType.ERROR, 0);
            return;
        }
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
        String quantityTypeUnit = (quantityType.split(" - ")[0]);
        int vatId = Integer.parseInt(vat.split(" - ")[0]);

        // Création de l'objet Product
        PostProductDto product = new PostProductDto();

        product.setName(nameString);
        product.setDescription(descriptionString);
        product.setBarCode(productBarCodeString);
        product.setSupplierId(supplierId);
        product.setProductFamilyId(productFamilyId);
        product.setQuantityTypeUnit(quantityTypeUnit);
        product.setMinimumStockQuantity(Integer.parseInt(minimumStockString));
        product.setBuyPrice(buyPriceString);
        product.setSellPrice(sellPriceString);
        product.setVatId(vatId);

        log.info("Product to add : {}", product);
        // Envoie de la requête
        RequestAPI requestAPI = new RequestAPI();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(product);
        } catch (Exception e) {
            log.error("Error while parsing product list", e);
        }
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/product/add", product, String.class, true, true);
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
            if (imageSelected == null) {
                cancel();
                openDialog(stackPane.getScene(), "Produit " + product.getName() + " ajouté avec succès", DialogType.INFORMATION, 0);
                return;
            }
            requestBody.add("file", new FileSystemResource(imageSelected));
            assert productResponse != null;
            ResponseEntity<String> responseEntity2 = requestAPI.sendPutRequestWithFile("/product/" + productResponse.getId() + "/image", requestBody, String.class, true);
            if (responseEntity2.getStatusCode().is2xxSuccessful()) {
                cancel();
                openDialog(stackPane.getScene(), "Produit " + product.getName() + " ajouté avec succès", DialogType.INFORMATION, 0);
            } else {
                openDialog(stackPane.getScene(), "Erreur lors de l'ajout de l'image",DialogType.ERROR,0);
            }

        } else {
            openDialog(stackPane.getScene(), "Erreur lors de l'ajout du produit", DialogType.ERROR, 0);
        }

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

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
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

    @FXML
    public void onIdSupplierChanged(KeyEvent event) {
        Object source = event.getSource();
        if (source instanceof MFXTextField textField && textField == productSupplierId) {
            String text = textField.getText();
            if (!text.isEmpty() && IntChecker.isValidIntegerInput(text)) {
                int supplierId = Integer.parseInt(text);
                String supplierName = getSupplierNameById(supplierId);
                this.supplierName.setText(supplierName);
            } else if (!IntChecker.isValidIntegerInput(text)){
                textField.setText(text.substring(0, text.length() - 1));
            } else {
                this.supplierName.setText("");
            }
        }
    }

    private String getSupplierNameById(int supplierId) {
        String supplier = findSupplierById(supplierId);
        this.supplierName.setText(Objects.requireNonNullElse(supplier, ""));
        return supplier;
    }

    private String findSupplierById(int supplierId) {
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/supplier/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetSupplierDto> supplierList = null;
        try {
            supplierList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }

        assert supplierList != null;
        for (GetSupplierDto supplier : supplierList) {
            if (supplier.getId() == supplierId) {
                return supplier.getCompanyName();
            }
        }
        return null;
    }

    @FXML
    public void onIdProductFamilyChanged(KeyEvent event) {
        Object source = event.getSource();
        if (source instanceof MFXTextField textField && textField == productFamilyID) {
            String text = textField.getText();
            if (!text.isEmpty() && IntChecker.isValidIntegerInput(text)) {
                int productFamilyId = Integer.parseInt(text);
                String productFamilyName = getProductFamilyNameById(productFamilyId);
                this.productFamilyName.setText(productFamilyName);
            } else if (!IntChecker.isValidIntegerInput(text)){
                textField.setText(text.substring(0, text.length() - 1));
            } else {
                this.productFamilyName.setText("");
            }
        }
    }

    private String getProductFamilyNameById(int productFamilyId) {
        String productFamily = findProductFamilyById(productFamilyId);
        this.productFamilyName.setText(Objects.requireNonNullElse(productFamily, ""));
        return productFamily;
    }

    private String findProductFamilyById(int productFamilyId) {
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/product-family/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetProductFamilyDto> productFamilyList = null;
        try {
            productFamilyList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }

        assert productFamilyList != null;
        for (GetProductFamilyDto productFamily : productFamilyList) {
            if (productFamily.getId() == productFamilyId) {
                return productFamily.getName();
            }
        }
        return null;
    }

    public boolean isValidPrice(String price) {
        String regex = "^(\\d+|\\d*\\.\\d{1,2})$";
        return price.matches(regex);
    }

    public String formatBarCode(String barCode) {
        if (barCode.length() < 13) {
            int zerosToAdd = 13 - barCode.length();
            StringBuilder zeros = new StringBuilder();
            for (int i = 0; i < zerosToAdd; i++) {
                zeros.append("0");
            }
            return  barCode + zeros.toString();
        } else {
            return barCode;
        }
    }
    public boolean isValidBarCode(String barCode) {
        String formattedBarCode = formatBarCode(barCode);
        String regex = "^\\d{13}$";
        return formattedBarCode.matches(regex);
    }



    public boolean isValidQuantity(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    public void onTextChangedDouble(KeyEvent event) {
        Object source = event.getSource();
        if (source instanceof MFXTextField textField) {
            String text = textField.getText();
            if (!text.isEmpty() && !IntChecker.isValidInput(text)) {
                textField.setText(text.substring(0, text.length() - 1));
            }
        }
    }

    @FXML
    public void onTextChangedInteger(KeyEvent event) {
        Object source = event.getSource();
        if (source instanceof MFXTextField textField) {
            String text = textField.getText();
            if (!text.isEmpty() && !IntChecker.isValidIntegerInput(text)) {
                textField.setText(text.substring(0, text.length() - 1));
            }
        }
    }
}
