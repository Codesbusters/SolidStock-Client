package fr.codesbusters.solidstock.controller;


import fr.codesbusters.solidstock.controller.selectors.CustomerSelectorController;
import fr.codesbusters.solidstock.controller.selectors.EstimateSelectorController;
import fr.codesbusters.solidstock.controller.selectors.productFamily.ProductFamilySelectorController;
import fr.codesbusters.solidstock.controller.selectors.SupplierSelectorController;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.listener.EstimateSelectorListener;
import fr.codesbusters.solidstock.listener.ProductFamilySelectorListener;
import fr.codesbusters.solidstock.listener.SupplierSelectorListener;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class DefaultController {

    private MFXGenericDialog dialogContent;

    private MFXStageDialog dialog;

    public boolean openDialog(Scene scene, String message, Alert.AlertType dialogType, int width) {
        AtomicBoolean finalIsCanceled = new AtomicBoolean(true);
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        if (dialogType == Alert.AlertType.ERROR) {
            dialog.setTitle("Erreur");
        } else if (dialogType == Alert.AlertType.WARNING) {
            dialog.setTitle("Attention");
        } else if (dialogType == Alert.AlertType.INFORMATION) {
            dialog.setTitle("Information");
        } else if (dialogType == Alert.AlertType.CONFIRMATION) {
            dialog.setTitle("Confirmation");
        }
        dialog.getIcons().add(new Image("/img/icon.png"));
        if (width == 0) {
            width = 600;
        }

        dialog.setMinWidth(width);
        dialog.setWidth(width);


        dialog.initOwner(scene.getWindow());
        dialog.setResizable(false);
        //label break line if message is too long
        Label label = new Label(message);
        label.setWrapText(true);

        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> dialog.close());

        okButton.setStyle(" -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 5px;");

        if (dialogType == Alert.AlertType.INFORMATION) {
            okButton.setStyle(okButton.getStyle() + "-fx-background-color: #3f84ff; -fx-text-fill: #ffffff;");
        }
        if (dialogType == Alert.AlertType.ERROR) {
            okButton.setStyle(okButton.getStyle() + "-fx-background-color: #ff4242; -fx-text-fill: #ffffff;");
        }
        if (dialogType == Alert.AlertType.WARNING) {
            okButton.setStyle(okButton.getStyle() + "-fx-background-color: #ffab41; -fx-text-fill: #ffffff;");
        }
        if (dialogType == Alert.AlertType.CONFIRMATION) {
            okButton.setStyle(okButton.getStyle() + "-fx-background-color: #5dff5d; -fx-text-fill: #ffffff;");
        }
        okButton.setStyle(okButton.getStyle());

        //if Confirmation display Cancel button and when clic stop code in the calling method
        if (dialogType == Alert.AlertType.CONFIRMATION) {
            Button cancelButton = new Button("Annuler");

            cancelButton.setOnAction(e -> {
                dialog.close();
                finalIsCanceled.set(false);
            });
            cancelButton.setStyle("-fx-background-color: #656565; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 5px;");

            cancelButton.setTranslateX(10);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.getChildren().addAll(okButton, cancelButton);
            hBox.setPadding(new Insets(0, 20, 20, 0));
            VBox vBox = new VBox(10);
            vBox.getChildren().add(label);
            vBox.setAlignment(Pos.CENTER_LEFT);
            vBox.setPadding(new Insets(20, 20, 20, 20));
            VBox dialogVBox = new VBox();
            dialogVBox.getChildren().addAll(vBox, hBox);
            Scene dialogScene = new Scene(dialogVBox);
            dialog.setScene(dialogScene);
            dialog.onCloseRequestProperty().set(e -> finalIsCanceled.set(false));
            dialog.showAndWait();
        } else {
            VBox vBox = new VBox(10);
            vBox.getChildren().add(label);
            vBox.setAlignment(Pos.CENTER_LEFT);
            vBox.setPadding(new Insets(20, 20, 20, 20));
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.getChildren().add(okButton);
            hBox.setPadding(new Insets(0, 20, 20, 0));
            VBox dialogVBox = new VBox();
            dialogVBox.getChildren().addAll(vBox, hBox);
            Scene dialogScene = new Scene(dialogVBox);
            dialog.setScene(dialogScene);
            dialog.show();
        }
        return finalIsCanceled.get();
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/selector/supplierSelector.fxml"));
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

    public void openCustomerSelector(Scene scene, CustomerSelectorListener listener) {
        try {
            Stage primaryStage = (Stage) scene.getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/selector/customerSelector.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            MFXThemeManager.addOn(newScene, Themes.DEFAULT, Themes.LEGACY);

            Stage popupStage = new Stage();
            popupStage.setResizable(false);
            popupStage.setTitle("Sélectionner un client");
            Image icon = new Image("/img/icon.png");
            popupStage.getIcons().add(icon);

            KeyCombination keyCombination = new KeyCodeCombination(KeyCode.LESS, KeyCombination.CONTROL_DOWN);
            newScene.setOnKeyPressed(event -> {
                if (keyCombination.match(event)) {
                    ScenicView.show(newScene);
                }
            });

            CustomerSelectorController controller = loader.getController();
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

    public void openEstimateSelector(Scene scene, EstimateSelectorListener listener) {
        try {
            Stage primaryStage = (Stage) scene.getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/selector/estimateSelector.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            MFXThemeManager.addOn(newScene, Themes.DEFAULT, Themes.LEGACY);

            Stage popupStage = new Stage();
            popupStage.setResizable(false);
            popupStage.setTitle("Sélectionner un");
            Image icon = new Image("/img/icon.png");
            popupStage.getIcons().add(icon);

            KeyCombination keyCombination = new KeyCodeCombination(KeyCode.LESS, KeyCombination.CONTROL_DOWN);
            newScene.setOnKeyPressed(event -> {
                if (keyCombination.match(event)) {
                    ScenicView.show(newScene);
                }
            });

            EstimateSelectorController controller = loader.getController();
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/selector/productFamilySelector/productFamillySelector.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            MFXThemeManager.addOn(newScene, Themes.DEFAULT, Themes.LEGACY);

            Stage popupStage = new Stage();
            popupStage.setResizable(false);
            popupStage.setTitle("Sélectionner une famille de produit");
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