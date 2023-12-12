package fr.codesbusters.solidstock.controller.orders;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.OrderForm;
import fr.codesbusters.solidstock.controller.DefaultShowController;
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
public class OrdersAddController extends DefaultShowController implements Initializable {

    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField subject;
    @FXML
    public TextArea description;
    @FXML
    public MFXTextField customerId;
    @FXML
    public MFXTextField customerName;
    @FXML
    public MFXTextField dueDate;
    @FXML
    public MFXTextField estimateId;
    @FXML
    public MFXTextField statusId;
    @FXML
    public MFXTextField statusName;

    @FXML
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        customerId.setText(String.valueOf(getId()));
        estimateId.setText(String.valueOf(getId()));
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addOrders() throws NumberFormatException, UnsupportedEncodingException {
        String subjectString = subject.getText();
        String descriptionString = description.getText();
        int customerIdInteger = Integer.parseInt(customerId.getText());
        String customerNameString = customerName.getText();
        DateTime dueDateString = DateTime.parse(dueDate.getText());
        int estimateIdInteger = Integer.parseInt(estimateId.getText());
        int statusIdIntegrr = Integer.parseInt(statusId.getText());
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
        order.setCustomerId(customerIdInteger);
        order.setCustomerName(customerNameString);
        order.setDueDate(dueDateString);
        order.setEstimateId(estimateIdInteger);
        order.setStatusId(statusIdIntegrr);
        order.setStatusName(statusNameString);

        log.info("Order to add : {}", order);

        cancel();

        openDialog(stackPane.getScene(), "Commande " + order.getSubject() + " créée avec succès", DialogType.INFORMATION);
    }

    @FXML
    public void selectCustomer() {
        openCustomerSelector(stackPane.getScene(), new CustomerSelectorListener() {
            @Override
            public void processCustomerContent(String customerContent) {
                log.info("Customer content : {}", customerContent);
            }

            @Override
            public void onCustomerSelected(int customerId, String customerName) {
                OrdersAddController.this.customerId.setText(String.valueOf(customerId));
                OrdersAddController.this.customerName.setText(customerName);
            }
        });
    }

    @FXML
    public void selectEstimate() {
        openEstimateSelector(stackPane.getScene(), new EstimateSelectorListener() {
            @Override
            public void processEstimateContent(String estimateContent) {
                log.info("Estimate content : {}", estimateContent);
            }

            @Override
            public void onEstimateSelected(int estimateId, String estimateName) {
                OrdersAddController.this.estimateId.setText(String.valueOf(estimateId));
                OrdersAddController.this.subject.setText(subject.getText());
            }
        });
    }

}
