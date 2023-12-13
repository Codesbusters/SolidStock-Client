package fr.codesbusters.solidstock.controller.orders;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.OrderForm;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.listener.EstimateSelectorListener;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;

@Slf4j
@Controller
public class OrdersAddController extends DefaultController implements Initializable, CustomerSelectorListener, EstimateSelectorListener {

    @FXML
    StackPane stackPane;
    @FXML
    MFXTextField subject;
    @FXML
    TextArea description;
    @FXML
    MFXTextField customerId;
    @FXML
    MFXTextField customerName;
    @FXML
    MFXTextField dueDate;
    @FXML
    MFXTextField estimateId;
    @FXML
    MFXTextField statusName;

    @FXML
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
    }


    @FXML
    public void addOrders() throws NumberFormatException, UnsupportedEncodingException {
        String subjectString = subject.getText();
        String descriptionString = description.getText();
        String customerNameString = customerName.getText();
        DateTime dueDateString = DateTime.parse(dueDate.getText());
        String statusNameString = statusName.getText();

        // Vérification du sujet
        if (subjectString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le sujet est vide");
            alert.setContentText("Veuillez saisir un sujet");
            alert.showAndWait();
        }

        // Vérification de la description
        if (descriptionString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La description est vide");
            alert.setContentText("Veuillez saisir une description");
            alert.showAndWait();
        }

        // Vérification du nom du client
        if (customerNameString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le nom du client est vide");
            alert.setContentText("Veuillez saisir un nom de client");
            alert.showAndWait();
        }

        // Vérification de la date d'échéance
        if (dueDateString == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La date d'échéance est vide");
            alert.setContentText("Veuillez saisir une date d'échéance");
            alert.showAndWait();
        }

        // Vérification du nom du statut
        if (statusNameString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le nom du statut est vide");
            alert.setContentText("Veuillez saisir un nom de statut");
            alert.showAndWait();
        }

        // Création de l'objet Orders
        OrderForm order = new OrderForm();
        order.setSubject(subjectString);
        order.setDescription(descriptionString);
        order.setCustomerName(customerNameString);
        order.setDueDate(dueDateString);
        order.setStatusName(statusNameString);

        log.info("Order to add : {}", order);

        cancel();

        openDialog(stackPane.getScene(), "Commande " + order.getSubject() + " créée avec succès", DialogType.INFORMATION);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void selectCustomer() {
        openCustomerSelector(stackPane.getScene(), this);
    }

    @FXML
    public void processCustomerContent(String customerContent) {
        customerName.setText(customerContent);
    }

    @FXML
    public void selectEstimate() {
        openEstimateSelector(stackPane.getScene(), this);
    }

    @FXML
    public void processEstimateContent(String estimateContent) {
        estimateId.setText(estimateContent);
    }
}
