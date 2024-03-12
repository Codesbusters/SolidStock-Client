package fr.codesbusters.solidstock.controller.orders;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.listener.EstimateSelectorListener;
import fr.codesbusters.solidstock.model.OrdersModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class OrdersEditController extends DefaultController implements Initializable, CustomerSelectorListener, EstimateSelectorListener {


    @FXML
    StackPane stackPane;
    @FXML
    MFXTextField orderId;
    @FXML
    MFXTextField subject;
    @FXML
    MFXTextField customerId;
    @FXML
    MFXTextField customerName;
    @FXML
    MFXTextField dueDate;
    @FXML
    MFXTextField estimateId;
    @FXML
    MFXTextField statusId;
    @FXML
    MFXTextField statusName;
    @FXML
    TextArea description;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    @FXML
    public void editOrder() throws NumberFormatException, UnsupportedEncodingException {
        String subjectString = subject.getText();
        String descriptionString = description.getText();
        String customerNameString = customerName.getText();
        String dueDadescriptionring = dueDate.getText();
        String statusNameString = statusName.getText();

        // Vérification du sujet
        if (subjectString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez saisir un sujet", DialogType.ERROR);
        }

        // Vérification de la description
        if (descriptionString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez saisir une description", DialogType.ERROR);
        }

        // Vérification du nom du client
        if (customerNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez saisir un nom de client", DialogType.ERROR);
        }

        // Vérification de la date d'échéance
        if (dueDadescriptionring.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez saisir une date d'échéance", DialogType.ERROR);
        }

        // Vérification du nom du statut
        if (statusNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez saisir un nom de statut", DialogType.ERROR);
        }

        // Modification de l'objet Orders
        OrdersModel ordersModel = OrdersModel.ofSplit(
                Integer.parseInt(orderId.getText()),
                subjectString,
                descriptionString,
                Integer.parseInt(customerId.getText()),
                customerNameString,
                dueDadescriptionring,
                Integer.parseInt(estimateId.getText()),
                Integer.parseInt(statusId.getText()),
                statusNameString
        );

        log.info("Order to add : {}", ordersModel);

        cancel();

        openDialog(stackPane.getScene(), "Commande " + ordersModel.getSubject() + " modifiée avec succès", DialogType.INFORMATION);
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