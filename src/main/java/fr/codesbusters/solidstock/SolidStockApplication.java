package fr.codesbusters.solidstock;

import fr.codesbusters.solidstock.client.utils.LoginScreen;
import fr.codesbusters.solidstock.client.utils.SplashScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class SolidStockApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.showSplash();
        Platform.runLater(() -> {
            try {
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.showLogin();


            } finally {
                // Fermer l'écran de chargement une fois que le reste de l'interface utilisateur est chargé
                splashScreen.hideSplash();
            }
        });
    }



    @Override
    public void stop() {
        Platform.exit();
    }
}
