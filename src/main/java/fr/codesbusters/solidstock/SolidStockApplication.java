package fr.codesbusters.solidstock;

import fr.codesbusters.solidstock.service.StartJFX;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "fr.codesbusters")
public class SolidStockApplication {

    public static void main(String[] args) {
        Application.launch(StartJFX.class, args);
    }

}
