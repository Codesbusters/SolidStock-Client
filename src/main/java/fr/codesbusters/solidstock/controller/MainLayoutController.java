package fr.codesbusters.solidstock.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    private AnchorPane dashboard;

    @FXML
    private AnchorPane products;

    @FXML
    private AnchorPane inventory;

    @FXML
    private AnchorPane orders;

    @FXML
    private AnchorPane invoices;

    @FXML
    private AnchorPane estimates;

    @FXML
    private AnchorPane customers;

    @FXML
    private AnchorPane suppliers;

    @FXML
    private AnchorPane third_party;

    @FXML
    private AnchorPane direct_sales;

    @FXML
    private AnchorPane settings;

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
        if (!dashboard.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("dashboard/index.fxml");
                resetMenuStyles();
                dashboard.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadProducts() {
        if (!products.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("products/index.fxml");
                resetMenuStyles();
                products.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadInventory() {
        if (!inventory.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("inventory.fxml");
                resetMenuStyles();
                inventory.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void loadInvoices() {
        if (!invoices.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("invoices.fxml");
                resetMenuStyles();
                invoices.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadEstimates() {
        if (!estimates.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("estimates.fxml");
                resetMenuStyles();
                estimates.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadOrders() {
        if (!orders.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("orders.fxml");
                resetMenuStyles();
                orders.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadSuppliers() {
        if (!suppliers.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("suppliers.fxml");
                resetMenuStyles();
                suppliers.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadThirdParty() {
        if (!third_party.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
            loadPage("thirdParty.fxml");
            resetMenuStyles();
            third_party.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
        } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadDirectSales() {
        if (!direct_sales.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("directsSales.fxml");
                resetMenuStyles();
                direct_sales.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadSettings() {
        if (!settings.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
            loadPage("settings.fxml");
            resetMenuStyles();
            settings.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
        } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void loadCustomers() {
        if (!customers.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
            loadPage("customers.fxml");
            resetMenuStyles();
            customers.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
        } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void resetMenuStyles() {
        dashboard.setStyle("");
        customers.setStyle("");
        settings.setStyle("");
        inventory.setStyle("");
        invoices.setStyle("");
        direct_sales.setStyle("");
        third_party.setStyle("");
        suppliers.setStyle("");
        orders.setStyle("");
        estimates.setStyle("");
        products.setStyle("");
    }
}

