package fr.codesbusters.solidstock.utils;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class SplashScreen extends Stage {

    public SplashScreen() {
        initStyle(StageStyle.UNDECORATED);

        // Charger l'image du splash screen
        Image splashImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/splash.png")));
        ImageView imageView = new ImageView(splashImage);

        // Créer une mise en page avec l'image au centre
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imageView);

        // Créer la scène
        Scene scene = new Scene(stackPane, splashImage.getWidth(), splashImage.getHeight());

        // Définir le curseur sur "WAIT" (loading) au survol
        scene.setOnMouseEntered(event -> scene.setCursor(Cursor.WAIT));

        // Revenir au curseur par défaut lorsque la souris quitte la zone du splash screen
        scene.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/splash.png")));
        getIcons().add(icon);

        // Appliquer la scène à la fenêtre
        setScene(scene);
    }

    public void showSplash() {
        show();
    }

    public void hideSplash() {
        hide();
    }
}