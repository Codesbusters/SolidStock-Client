package fr.codesbusters.solidstock.controller.suppliers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class SupplierShowController extends DefaultShowController implements Initializable {

    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField supplierId;

    @FXML
    public MFXTextField supplierCompanyName;

    @FXML
    public MFXTextField supplierFirstName;

    @FXML
    public MFXTextField supplierLastName;
    @FXML
    public MFXTextField supplierAddress;

    @FXML
    public MFXTextField supplierStreetNumber;

    @FXML
    public MFXTextField supplierZipCode;

    @FXML
    public MFXTextField supplierCity;

    @FXML
    public MFXTextField supplierMobilePhone;

    @FXML
    public MFXTextField supplierWorkPhone;

    @FXML
    public MFXTextField supplierHomePhone;

    @FXML
    public MFXTextField supplierEmail;

    @FXML
    public MFXTextField supplierSiret;

    @FXML
    public MFXTextField supplierSiren;

    @FXML
    public MFXTextField supplierRib;

    @FXML
    public MFXTextField supplierRcs;

    @FXML
    public MFXTextField supplierWebsite;

    @FXML
    public MFXTextField supplierCountry;

    @FXML
    public MFXTextField supplierFax;

    @FXML
    public MFXTextField supplierNote;

    private void disableTextFields() {
        supplierId.setEditable(false);
        supplierCompanyName.setEditable(false);
        supplierFirstName.setEditable(false);
        supplierLastName.setEditable(false);
        supplierAddress.setEditable(false);
        supplierStreetNumber.setEditable(false);
        supplierZipCode.setEditable(false);
        supplierCity.setEditable(false);
        supplierMobilePhone.setEditable(false);
        supplierHomePhone.setEditable(false);
        supplierWorkPhone.setEditable(false);
        supplierEmail.setEditable(false);
        supplierSiren.setEditable(false);
        supplierSiret.setEditable(false);
        supplierRib.setEditable(false);
        supplierRcs.setEditable(false);
        supplierWebsite.setEditable(false);
        supplierCountry.setEditable(false);
        supplierFax.setEditable(false);
        supplierNote.setEditable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplierId.setText(String.valueOf(getId()));
        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/supplier/" + getId(), String.class, true);
        ObjectMapper mapper = new ObjectMapper();
        GetSupplierDto supplier = null;
        try {
            supplier = mapper.readValue(responseEntity.getBody(), new TypeReference<GetSupplierDto>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }

        assert supplier != null;
        supplierCompanyName.setText(supplier.getCompanyName());
        supplierFirstName.setText(supplier.getFirstName());
        supplierLastName.setText(supplier.getLastName());
        supplierAddress.setText(supplier.getAddress());
        supplierStreetNumber.setText(supplier.getStreetNumber());
        supplierZipCode.setText(supplier.getZipCode());
        supplierCity.setText(supplier.getCity());
        supplierSiren.setText(supplier.getSiren());
        supplierSiret.setText(supplier.getSiret());
        supplierRib.setText(supplier.getRib());
        supplierRcs.setText(String.valueOf(supplier.getRcs()));
        supplierMobilePhone.setText(supplier.getMobilePhone());
        supplierHomePhone.setText(supplier.getHomePhone());
        supplierWorkPhone.setText(supplier.getWorkPhone());
        supplierEmail.setText(supplier.getEmail());
        supplierWebsite.setText(supplier.getWebsite());
        supplierCountry.setText(supplier.getCountry());
        supplierFax.setText(supplier.getFax());
        supplierNote.setText(supplier.getNote());

        disableTextFields();
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}