package fr.codesbusters.solidstock.client.controller.main;


import fr.codesbusters.solidstock.client.utils.TokenManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MainLayoutController implements Initializable {

    @FXML
    public AnchorPane leftPane;

    @FXML
    private Pane mainContent;

    @FXML
    private GridPane dashboard;

    @FXML
    private GridPane products;

    @FXML
    private GridPane inventory;

    @FXML
    private GridPane orders;

    @FXML
    private GridPane invoices;

    @FXML
    private GridPane estimates;

    @FXML
    private GridPane customers;

    @FXML
    private GridPane suppliers;

    @FXML
    private GridPane third_party;

    @FXML
    private GridPane disconnect;

    @FXML
    private GridPane userSettings;

    @FXML
    private GridPane adminSettings;

    @FXML
    private GridPane users;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDashboard();
    }

    public void loadPage(String pageFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + pageFXML));
        Node newPage = loader.load();

        if (!mainContent.getChildren().isEmpty() && mainContent.getChildren().get(0).equals(newPage)) {
            return;
        }

        FadeTransition fadeOut = new FadeTransition(Duration.millis(100), mainContent);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> {
            mainContent.getChildren().clear();

            StackPane stackPane = new StackPane(newPage);
            stackPane.prefWidthProperty().bind(mainContent.widthProperty());
            stackPane.prefHeightProperty().bind(mainContent.heightProperty());
            mainContent.getChildren().add(stackPane);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(250), mainContent);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }


    @FXML
    private void toggleMenu() {
        if (leftPane.isVisible()) {
            leftPane.setVisible(false);
            leftPane.setManaged(false); // Ne pas gérer l'espace
        } else {
            leftPane.setVisible(true);
            leftPane.setManaged(true); // Gérer l'espace
        }
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
    private void loadStockMovement() {
        if (!inventory.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("stockMovement/index.fxml");
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
                loadPage("invoices/index.fxml");
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
                loadPage("estimates/index.fxml");
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
                loadPage("orders/index.fxml");
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
                loadPage("suppliers/index.fxml");
                resetMenuStyles();
                suppliers.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void loadDirectSales() {
        if (!disconnect.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("directsSales/index.fxml");
                resetMenuStyles();
                disconnect.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadUserSettings() {
        if (!userSettings.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("userSettings/index.fxml");
                resetMenuStyles();
                userSettings.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadAdminSettings() {
        if (!adminSettings.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("adminSettings/index.fxml");
                resetMenuStyles();
                adminSettings.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void loadCustomers() {
        if (!customers.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("customers/index.fxml");
                resetMenuStyles();
                customers.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadUsers() {
        if (!users.getStyle().contains("-fx-background-color: #000; -fx-background-radius: 20")) {
            try {
                loadPage("users/index.fxml");
                resetMenuStyles();
                users.setStyle("-fx-background-color: #000; -fx-background-radius: 20");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void resetMenuStyles() {
        dashboard.setStyle("");
        customers.setStyle("");
        userSettings.setStyle("");
        adminSettings.setStyle("");
        inventory.setStyle("");
        invoices.setStyle("");
        disconnect.setStyle("");
        suppliers.setStyle("");
        orders.setStyle("");
        estimates.setStyle("");
        products.setStyle("");
        users.setStyle("");
    }

    @FXML
    public void disconnect(MouseEvent mouseEvent) {
        TokenManager.deleteToken();


        System.exit(0);

    }


}
