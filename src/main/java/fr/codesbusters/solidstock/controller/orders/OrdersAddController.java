package fr.codesbusters.solidstock.controller.orders;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.order.PostOrderDto;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
public class OrdersAddController extends DefaultController implements Initializable, CustomerSelectorListener {


    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField subject;
    @FXML
    public TextArea description;
    @FXML
    public Label customerName;
    @FXML
    public MFXTextField customerId;
    @FXML
    public MFXDatePicker dueDate;
    @FXML
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
    }


    @FXML
    public void addOrders() throws NumberFormatException {
        // Création de l'objet Orders
        PostOrderDto order = new PostOrderDto();
        order.setName(subject.getText());
        order.setDescription(description.getText());
        if (dueDate.getValue() != null) {
            order.setEstimateDate(dueDate.getValue().toString());
        }
        order.setCustomerId(Integer.parseInt(customerId.getText()));

        if (order.getName().isEmpty() || order.getDescription().isEmpty() || order.getCustomerId() == 0) {
            openDialog(stackPane.getScene(), "Veuillez remplir tous les champs.", DialogType.ERROR, 0);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = dueDate.getValue().format(formatter);
        order.setEstimateDate(formattedDate);

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/orders/add", order, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
            openDialog(stackPane.getScene(), "Commande créée avec succès.", DialogType.INFORMATION, 0);

        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la création de la commande " + subject.getText() + ".", DialogType.ERROR, 0);
        }
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
        String[] customer = customerContent.split(" - ");
        customerId.setText(customer[0]);
        customerName.setText(customer[1]);    }

}