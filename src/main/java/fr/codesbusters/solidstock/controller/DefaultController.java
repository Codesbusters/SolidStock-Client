package fr.codesbusters.solidstock.controller;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

import java.io.File;
import java.io.IOException;

public class DefaultController {

    public void openPopUp(String fxmlPath, Scene scene, String title) {
        try {
            // Récupérez la fenêtre principale (parent)
            Stage primaryStage = (Stage) scene.getWindow();


            // Chargez le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + File.separator + fxmlPath));
            Parent root = loader.load();

            // Créez une nouvelle scène
            Scene newScene = new Scene(root);

            MFXThemeManager.addOn(newScene, Themes.DEFAULT, Themes.LEGACY);

            // Créez une nouvelle fenêtre modale pour la pop-up
            Stage popupStage = new Stage();
            popupStage.setResizable(false);
            popupStage.setTitle(title);
            Image icon = new Image("/img/icon.png");
            popupStage.getIcons().add(icon);

            // Définissez la combinaison de touches (Ctrl + <)
            KeyCombination keyCombination = new KeyCodeCombination(KeyCode.LESS, KeyCombination.CONTROL_DOWN);

            // Ajoutez un gestionnaire d'événements pour la combinaison de touches
            newScene.setOnKeyPressed(event -> {
                if (keyCombination.match(event)) {
                    // Déclenchez ScenicView.show(scene)
                    ScenicView.show(newScene);
                }
            });

            // Utilisez Modality.WINDOW_MODAL pour lier la fenêtre pop-up au propriétaire (parent)
            popupStage.initModality(Modality.WINDOW_MODAL);

            // Définissez le propriétaire de la fenêtre pop-up
            popupStage.initOwner(primaryStage);

            // Définissez la scène de la fenêtre pop-up
            popupStage.setScene(newScene);

            // Ajoutez un écouteur pour détecter la fermeture de la fenêtre pop-up
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
