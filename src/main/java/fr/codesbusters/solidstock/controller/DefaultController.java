package fr.codesbusters.solidstock.controller;

import fr.codesbusters.solidstock.controller.selectors.ProductFamilySelectorController;
import fr.codesbusters.solidstock.controller.selectors.SupplierSelectorController;
import fr.codesbusters.solidstock.listener.ProductFamilySelectorListener;
import fr.codesbusters.solidstock.listener.SupplierSelectorListener;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
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

    private MFXGenericDialog dialogContent;

    private MFXStageDialog dialog;

    public void openErrorDialog(Scene scene, String message) {

        Stage primaryStage = (Stage) scene.getWindow();

        this.dialogContent = MFXGenericDialogBuilder.build()
                .setContentText(message)
                .makeScrollable(true)
                .get();
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(primaryStage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("Une erreur est survenue")
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialogContent.setMaxSize(400, 200);
        dialog.showDialog();
    }

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

    public void openSupplierSelector(Scene scene, SupplierSelectorListener listener) {
        try {
            Stage primaryStage = (Stage) scene.getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/supplierSelector/supplierSelector.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            MFXThemeManager.addOn(newScene, Themes.DEFAULT, Themes.LEGACY);

            Stage popupStage = new Stage();
            popupStage.setResizable(false);
            popupStage.setTitle("Sélectionner un fournisseur");
            Image icon = new Image("/img/icon.png");
            popupStage.getIcons().add(icon);

            KeyCombination keyCombination = new KeyCodeCombination(KeyCode.LESS, KeyCombination.CONTROL_DOWN);
            newScene.setOnKeyPressed(event -> {
                if (keyCombination.match(event)) {
                    ScenicView.show(newScene);
                }
            });

            SupplierSelectorController controller = loader.getController();
            controller.setStage(popupStage);
            controller.setListener(listener);

            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(primaryStage);
            popupStage.setScene(newScene);
            popupStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openProductFamilySelector(Scene scene, ProductFamilySelectorListener listener) {
        try {
            Stage primaryStage = (Stage) scene.getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/supplierSelector/supplierSelector.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            MFXThemeManager.addOn(newScene, Themes.DEFAULT, Themes.LEGACY);

            Stage popupStage = new Stage();
            popupStage.setResizable(false);
            popupStage.setTitle("Sélectionner un fournisseur");
            Image icon = new Image("/img/icon.png");
            popupStage.getIcons().add(icon);

            KeyCombination keyCombination = new KeyCodeCombination(KeyCode.LESS, KeyCombination.CONTROL_DOWN);
            newScene.setOnKeyPressed(event -> {
                if (keyCombination.match(event)) {
                    ScenicView.show(newScene);
                }
            });

            ProductFamilySelectorController controller = loader.getController();
            controller.setStage(popupStage);
            controller.setListener(listener);

            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(primaryStage);
            popupStage.setScene(newScene);
            popupStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
