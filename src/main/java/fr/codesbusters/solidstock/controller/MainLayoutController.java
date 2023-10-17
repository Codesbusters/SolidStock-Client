package fr.codesbusters.solidstock.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class MainLayoutController {
    @FXML
    private Pane mainContent;

    public void loadPage(String pageFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + pageFXML));
        Node page = loader.load();
        mainContent.getChildren().setAll(page);
    }

    @FXML
    private void loadCustomer() {
        try {
            loadPage("customers.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }

    @FXML
    private void loadDashboard() {
        try {
            loadPage("dashboard.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }

    @FXML
    private void loadProducts() {
        try {
            loadPage("products.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }

    @FXML
    private void loadInventory() {
        try {
            loadPage("inventory.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }


    @FXML
    private void loadOrders() {
        try {
            loadPage("orders.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }

    @FXML
    private void loadSuppliers() {
        try {
            loadPage("suppliers.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }

    @FXML
    private void loadThirdParty() {
        try {
            loadPage("thirdParty.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }

    @FXML
    private void loadDirectSales() {
        try {
            loadPage("directsSales.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }

    @FXML
    private void loadSettings() {
        try {
            loadPage("settings.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }


    @FXML
    private void loadCustomers() {
        try {
            loadPage("customers.fxml");
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la page
        }
    }
}
