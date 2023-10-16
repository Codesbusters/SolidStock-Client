package fr.codesbusters.solidstock.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class MainLayoutController {
    @FXML
    private VBox leftContent;
    @FXML
    private Pane mainContent;

    public void loadPage(String pageFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + pageFXML));
        Node page = loader.load();
        mainContent.getChildren().setAll(page);
    }

    @FXML
    private void loadPage1() {
        try {
            loadPage("AddBook.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }

    @FXML
    private void loadPage2() {
        try {
            loadPage("test.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }
}