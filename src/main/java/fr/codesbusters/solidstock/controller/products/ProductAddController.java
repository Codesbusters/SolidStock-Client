package fr.codesbusters.solidstock.controller.products;

import fr.codesbusters.solidstock.component.SSDoubleField;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.listener.SupplierSelectorListener;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class ProductAddController extends DefaultController implements Initializable, SupplierSelectorListener {


    @FXML
    public StackPane stackPane;
    @FXML
    public ImageView imageView;
    @FXML
    public SSDoubleField sellPrice;
    @FXML
    public MFXTextField supplierID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sellPrice.setTextLimit(8);

    }

    @FXML
    public void selectSupplier() {
        openSupplierSelector(stackPane.getScene(), this);
    }


    @FXML
    public void addProduct() {
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
}
