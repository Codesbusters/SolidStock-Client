package fr.codesbusters.solidstock.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MainLayoutController implements Initializable {
    @FXML
    private Pane mainContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDashboard();
    }

    public void loadPage(String pageFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + pageFXML));
        Node page = loader.load();

        // Set constraints to make the StackPane fill the Pane
        StackPane stackPane = new StackPane(page);
        stackPane.prefWidthProperty().bind(mainContent.widthProperty());
        stackPane.prefHeightProperty().bind(mainContent.heightProperty());

        mainContent.getChildren().setAll(stackPane);
    }

    @FXML
    private void loadDashboard() {
        try {
            loadPage("dashboard/index.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadProducts() {
        try {
            loadPage("products/index.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadInventory() {
        try {
            loadPage("inventory.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void loadOrders() {
        try {
            loadPage("orders.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadSuppliers() {
        try {
            loadPage("suppliers.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadThirdParty() {
        try {
            loadPage("thirdParty.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadDirectSales() {
        try {
            loadPage("directsSales.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadSettings() {
        try {
            loadPage("settings.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void loadCustomers() {
        try {
            loadPage("customers.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

