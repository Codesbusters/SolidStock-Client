package fr.codesbusters.solidstock.controller.dashboard;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class DashboardController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void openAddPopUp() {
        try {
            // Chargez le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard/addPopUp.fxml"));
            Parent root = loader.load();

            // Créez une nouvelle scène
            Scene scene = new Scene(root);

            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);

            // Créez une nouvelle fenêtre modale pour la pop-up
            Stage popupStage = new Stage();
            popupStage.setTitle("Ajouter un produit - SolidStock");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(popupStage.getOwner());

            // Définissez la scène de la fenêtre pop-up
            popupStage.setScene(scene);

            // Affichez la fenêtre pop-up et attendez qu'elle soit fermée avant de revenir à la fenêtre principale
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
