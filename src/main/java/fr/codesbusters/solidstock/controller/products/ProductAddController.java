package fr.codesbusters.solidstock.controller.products;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.Product;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.listener.ProductFamilySelectorListener;
import fr.codesbusters.solidstock.listener.SupplierSelectorListener;
import fr.codesbusters.solidstock.model.QuantityTypeModel;
import fr.codesbusters.solidstock.model.SolidStockDataIntegration;
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
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    public TextArea description;
    @FXML
    public MFXTextField buyPrice;
    @FXML
    public MFXTextField sellPrice;
    @FXML
    public MFXTextField vat;
    @FXML
    public MFXTextField supplierID;
    @FXML
    public MFXTextField productFamilyID;
    @FXML
    public MFXComboBox<QuantityTypeModel> quantityType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sellPrice.setTextLimit(8);

        ObservableList<QuantityTypeModel> quantityTypes = SolidStockDataIntegration.quantityType;

        quantityType.setItems(quantityTypes);
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
        String descriptionString = description.getText();
        String supplierIdString = supplierID.getText();
        String productFamily = productFamilyID.getText();
        String quantityTypeString = null;
        QuantityTypeModel selectedItem = quantityType.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            quantityTypeString = String.valueOf(selectedItem.getID());
        }

        String buyPriceDouble = buyPrice.getText();
        String sellPriceDouble = sellPrice.getText();
        String vatDouble = vat.getText();

        // Vérification du nom du produit
        if (nameString.isBlank()) {
            System.out.println(com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());

            openDialog(stackPane.getScene(), "Veuillez renseigner le nom du produit", DialogType.ERROR, 0);
            return;
        }

        // Vérification de la famille de produit
        if (productFamily.isBlank() || !productFamily.matches("\\d+")) {
            openDialog(stackPane.getScene(), "Veuillez renseigner une famille de produit", DialogType.ERROR, 0);
            return;
        }

        // Vérification du fournisseur
        if (supplierIdString.isBlank() || !supplierIdString.matches("\\d+")) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un fournisseur valide", DialogType.ERROR, 0);
            return;
        }


        // Vérification du type de quantité
        if (quantityTypeString == null || quantityTypeString.trim().isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un type de quantité", DialogType.ERROR, 0);
            return;
        }

        // Validation de l'image
        String imageBase64 = validateImage();

        // Création de l'objet Product
        Product product = new Product();
        product.setName(nameString);
        product.setDescription(descriptionString);
        product.setSupplierId(Integer.parseInt(supplierIdString));
        product.setProductFamilyId(Integer.parseInt(productFamily));
        product.setQuantityTypeId(Integer.parseInt(quantityTypeString));
        product.setBuyPrice(Double.parseDouble(buyPriceDouble));
        product.setSellPrice(Double.parseDouble(sellPriceDouble));
        product.setVat(Double.parseDouble(vatDouble));
        product.setImage(imageBase64);

        log.info("Product to add : {}", product);

        cancel();

        openDialog(stackPane.getScene(), "Produit " + product.getName() + " créer avec succès", DialogType.INFORMATION, 0);
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

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void processSupplierContent(String supplierContent) {
        supplierID.setText(supplierContent);
    }


    @Override
    public void processProductFamilyContent(String productFamilyContent) {
        productFamilyID.setText(productFamilyContent);
    }


}
