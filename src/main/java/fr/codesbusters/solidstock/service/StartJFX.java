package fr.codesbusters.solidstock.service;


import fr.codesbusters.solidstock.SolidStockApplication;
import fr.codesbusters.solidstock.utils.LoginScreen;
import fr.codesbusters.solidstock.utils.SplashScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StartJFX extends Application {


    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(SolidStockApplication.class).run();
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
        applicationContext.close();
        Platform.exit();
    }


}
