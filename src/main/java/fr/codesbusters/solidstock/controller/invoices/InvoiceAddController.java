package fr.codesbusters.solidstock.controller.invoices;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.invoice.PostInvoiceDto;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.service.RequestAPI;
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

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class InvoiceAddController extends DefaultController implements Initializable, CustomerSelectorListener {

    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField invoiceName;

    @FXML
    public MFXTextField invoiceDescription;

    @FXML
    public MFXTextField customerId;

    @FXML
    public Label customerName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addInvoice() throws NumberFormatException {

        PostInvoiceDto invoice = new PostInvoiceDto();
        invoice.setName(invoiceName.getText());
        invoice.setDescription(invoiceDescription.getText());
        invoice.setCustomerId(Integer.parseInt(customerId.getText()));

        if (invoice.getName().isEmpty() || invoice.getDescription().isEmpty() || invoice.getCustomerId() == 0) {
            openDialog(stackPane.getScene(), "Veuillez remplir tous les champs.", DialogType.ERROR, 0);
return;
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/invoice/add", invoice, String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {

            openDialog(stackPane.getScene(), "Erreur lors de l'ajout du fournisseur.", DialogType.ERROR, 0);

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
