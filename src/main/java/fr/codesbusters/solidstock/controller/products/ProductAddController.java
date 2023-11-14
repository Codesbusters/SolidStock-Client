package fr.codesbusters.solidstock.controller.products;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class ProductAddController implements Initializable {

    public StackPane stackPane;
    public ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
}
