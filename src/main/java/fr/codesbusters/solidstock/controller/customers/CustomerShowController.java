package fr.codesbusters.solidstock.controller.customers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.customer.GetCustomerDto;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class CustomerShowController extends DefaultShowController implements Initializable {


    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField customerId;
    @FXML
    public MFXTextField customerCompanyName;
    @FXML
    public MFXTextField customerFirstName;
    @FXML
    public MFXTextField customerLastName;
    @FXML
    public MFXTextField customerCountry;
    @FXML
    public MFXTextField customerCity;
    @FXML
    public MFXTextField customerZipCode;
    @FXML
    public MFXTextField customerStreetNumber;
    @FXML
    public MFXTextField customerAddress;
    @FXML
    public MFXTextField customerEmail;

    @FXML
    public MFXTextField customerMobilePhone;

    @FXML
    public MFXTextField customerHomePhone;

    @FXML
    public MFXTextField customerWorkPhone;

    @FXML
    public MFXTextField customerWebsite;

    @FXML
    public MFXTextField customerSiren;

    @FXML
    public MFXTextField customerSiret;

    @FXML
    public MFXTextField customerRib;

    @FXML
    public MFXTextField customerRcs;

    @FXML
    public MFXTextField customerFax;

    @FXML
    public TextArea customerNote;

    @FXML
    public MFXButton enable;

    private void disableTextFields() {
        customerId.setEditable(false);
        customerCompanyName.setEditable(false);
        customerFirstName.setEditable(false);
        customerLastName.setEditable(false);
        customerAddress.setEditable(false);
        customerStreetNumber.setEditable(false);
        customerZipCode.setEditable(false);
        customerCity.setEditable(false);
        customerMobilePhone.setEditable(false);
        customerHomePhone.setEditable(false);
        customerWorkPhone.setEditable(false);
        customerEmail.setEditable(false);
        customerSiren.setEditable(false);
        customerSiret.setEditable(false);
        customerRib.setEditable(false);
        customerRcs.setEditable(false);
        customerWebsite.setEditable(false);
        customerCountry.setEditable(false);
        customerFax.setEditable(false);
        customerNote.setEditable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerId.setText(String.valueOf(getId()));
        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/customer/" + getId(), String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetCustomerDto customer = null;

        try {
            customer = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing customers list", e);
        }

        assert customer != null;
        customerCompanyName.setText(customer.getCompanyName());
        customerFirstName.setText(customer.getFirstName());
        customerLastName.setText(customer.getLastName());
        customerCountry.setText(customer.getCountry());
        customerCity.setText(customer.getCity());
        customerZipCode.setText(customer.getZipCode());
        customerStreetNumber.setText(customer.getStreetNumber());
        customerAddress.setText(customer.getAddress());
        customerMobilePhone.setText(customer.getMobilePhone());
        customerHomePhone.setText(customer.getHomePhone());
        customerWorkPhone.setText(customer.getWorkPhone());
        customerEmail.setText(customer.getEmail());
        customerWebsite.setText(customer.getWebsite());
        customerSiren.setText(customer.getSiren());
        customerSiret.setText(customer.getSiret());
        customerRib.setText(customer.getRib());
        customerRcs.setText(String.valueOf(customer.getRcs()));
        customerFax.setText(customer.getFax());
        customerNote.setText(customer.getNote());
        disableTextFields();
        enable.setVisible(customer.isDeleted());
    }

    @FXML
    public void enable() {
        int idInteger = Integer.parseInt(customerId.getText());
        String companyNameString = customerCompanyName.getText();
        String firstNameString = customerFirstName.getText();
        String lastNameString = customerLastName.getText();

        GetCustomerDto customer = new GetCustomerDto();
        customer.setId(idInteger);
        customer.setCompanyName(companyNameString);
        customer.setFirstName(firstNameString);
        customer.setLastName(lastNameString);
        RequestAPI requestAPI = new RequestAPI();

        requestAPI.sendPostRequest("/customer/" + idInteger, null, String.class, true, true);

        cancel();
        if (customer.getCompanyName().isEmpty()) {
            openDialog(stackPane.getScene(), "Client " + customer.getFirstName() + " " + customer.getLastName() + " réactivé avec succès.", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Client " + customer.getCompanyName() + " réactivé avec succès.", DialogType.INFORMATION, 0);
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}
