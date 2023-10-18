package fr.codesbusters.solidstock.service;


import fr.codesbusters.solidstock.SolidStockApplication;
import fr.codesbusters.solidstock.utils.ApplicationPropertiesReader;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.scenicview.ScenicView;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class StartJFX extends Application {

    private final String currentVersion = new ApplicationPropertiesReader().getProperty("build.version");
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(SolidStockApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/main/MainLayout.fxml")));
        Scene scene = new Scene(root);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1320.0);
        primaryStage.setMinHeight(720.0);
        primaryStage.setTitle("SolidStock - " + currentVersion);
        Image icon = new Image("icon.png");
        primaryStage.getIcons().add(icon);

        primaryStage.show();
        ScenicView.show(scene);
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }


}
