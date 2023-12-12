package fr.codesbusters.solidstock.utils;

import fr.codesbusters.solidstock.controller.login.LoginController;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.scenicview.ScenicView;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class LoginScreen extends Stage {

    private final String currentVersion = new ApplicationPropertiesReader().getProperty("build.version");

    private Scene scene;

    public LoginScreen() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login/index.fxml"));

        try {
            Parent root = loader.load();
            scene = new Scene(root);

            setMinWidth(400);
            setMinHeight(600);

            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

            KeyCombination keyCombination = new KeyCodeCombination(KeyCode.LESS, KeyCombination.CONTROL_DOWN);

            scene.setOnKeyPressed(event -> {
                if (keyCombination.match(event)) {
                    ScenicView.show(scene);
                }
            });
            setTitle("SolidStock - Connexion");

            Image icon = new Image("/img/icon.png");
            getIcons().add(icon);

            setScene(scene);

            LoginController controller = loader.getController();
            controller.setLoginScreen(this);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void showLogin() {
        show();
    }

    public void hideLogin() {
        hide();
    }

    public void launchNextScreen() throws IOException {
        // Implémentez la logique pour lancer le prochain écran ici
        log.info("Lancement de l'écran pricipale...");

        SplashScreen splashScreen = new SplashScreen();
        splashScreen.showSplash();
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/main/MainLayout.fxml")));
                Scene scene = new Scene(root);
                MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
                setScene(scene);
                setMinWidth(1266.0);
                setMinHeight(770.0);
                setMaximized(true);
                setResizable(true);
                setTitle("SolidStock - " + currentVersion);
                Image icon = new Image("/img/icon.png");
                getIcons().add(icon);

                // Définissez la combinaison de touches (Ctrl + <)
                KeyCombination keyCombination = new KeyCodeCombination(KeyCode.LESS, KeyCombination.CONTROL_DOWN);

                // Ajoutez un gestionnaire d'événements pour la combinaison de touches
                scene.setOnKeyPressed(event -> {
                    if (keyCombination.match(event)) {
                        // Déclenchez ScenicView.show(scene)
                        ScenicView.show(scene);
                    }
                });

                show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                // Fermer l'écran de chargement une fois que le reste de l'interface utilisateur est chargé
                splashScreen.hideSplash();
            }
        });


    }
}