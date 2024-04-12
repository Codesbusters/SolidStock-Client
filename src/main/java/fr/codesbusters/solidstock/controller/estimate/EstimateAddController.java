package fr.codesbusters.solidstock.controller.estimate;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.estimates.PostEstimateDto;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class EstimateAddController extends DefaultController implements Initializable, CustomerSelectorListener {
    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField estimateName;

    @FXML
    public MFXTextField estimateDescription;

    @FXML
    public MFXTextField customerId;

    @FXML
    public Label customerName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addEstimate() throws NumberFormatException {

        PostEstimateDto estimate = new PostEstimateDto();
        estimate.setName(estimateName.getText());
        estimate.setDescription(estimateDescription.getText());
        estimate.setCustomerId(Integer.parseInt(customerId.getText()));

        if (estimate.getName().isEmpty() || estimate.getDescription().isEmpty() || estimate.getCustomerId() == 0) {
            openDialog(stackPane.getScene(), "Veuillez remplir tous les champs.", DialogType.ERROR, 0);
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/estimate/add", estimate, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
            openDialog(stackPane.getScene(), "Devis créé avec succès.", DialogType.INFORMATION, 0);

        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la création du devis.", DialogType.ERROR, 0);
        }

    }

    @FXML
    public void selectCustomer() {
        openCustomerSelector(stackPane.getScene(), this);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void processCustomerContent(String customerContent) {
        String[] customer = customerContent.split(" - ");
        customerId.setText(customer[0]);
        customerName.setText(customer[1]);
    }
}
