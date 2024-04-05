package fr.codesbusters.solidstock.controller.adminSettings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.admin.GetAdminSettingsDto;
import fr.codesbusters.solidstock.dto.admin.PostAdminSettingsDto;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class AdminSettingsController extends DefaultController implements Initializable {


    @FXML
    StackPane stackPane;

    @FXML
    MFXTextField ownerCompanyName;

    @FXML
    MFXTextField ownerCompanySiret;

    @FXML
    MFXTextField ownerCompanyRcs;

    @FXML
    MFXTextField ownerCompanyOwnerName;

    @FXML
    MFXTextField ownerCompanySiren;

    @FXML
    MFXTextField ownerCompanyStreetNumber;

    @FXML
    MFXTextField ownerCompanyStreetName;

    @FXML
    MFXTextField ownerCompanyZipCode;

    @FXML
    MFXTextField ownerCompanyCity;

    @FXML
    MFXTextField ownerCompanyCountry;

    @FXML
    MFXTextField ownerCompanyEmail;

    @FXML
    MFXTextField ownerCompanyPhone;

    @FXML
    MFXTextField ownerCompanyIban;

    @FXML
    MFXTextField urlApi;

    @FXML
    MFXTextField urlWeb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadAdminSettings();
    }

    @FXML
    public void saveAdminSettings() {

        PostAdminSettingsDto ownerCompany = new PostAdminSettingsDto();
        ownerCompany.setCompanyName(ownerCompanyName.getText());
        ownerCompany.setSiret(ownerCompanySiret.getText());
        ownerCompany.setOwnerName(ownerCompanyOwnerName.getText());
        ownerCompany.setSiren(ownerCompanySiren.getText());
        ownerCompany.setRcs(Integer.parseInt(ownerCompanyRcs.getText()));
        ownerCompany.setStreetNumber(ownerCompanyStreetNumber.getText());
        ownerCompany.setStreetName(ownerCompanyStreetName.getText());
        ownerCompany.setZipCode(ownerCompanyZipCode.getText());
        ownerCompany.setCity(ownerCompanyCity.getText());
        ownerCompany.setCountry(ownerCompanyCountry.getText());
        ownerCompany.setEmail(ownerCompanyEmail.getText());
        ownerCompany.setPhone(ownerCompanyPhone.getText());
        ownerCompany.setIban(ownerCompanyIban.getText());

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> ownerCompanyResponse = requestAPI.sendPutRequest("/owner-company", ownerCompany, String.class, true);
        if (ownerCompanyResponse.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Paramètres administrateurs sauvegardés avec succès", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Une erreur est survenue lors de la sauvegarde de vos paramètres", DialogType.ERROR, 0);
        }
    }

    @FXML
    public void reloadAdminSettings() {
        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/owner-company", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetAdminSettingsDto adminSettings = null;
        try {
            adminSettings = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing company", e);
        }

        assert adminSettings != null;
        ownerCompanyName.setText(adminSettings.getCompanyName());
        ownerCompanySiret.setText(adminSettings.getSiret());
        ownerCompanyRcs.setText(String.valueOf(adminSettings.getRcs()));
        ownerCompanyOwnerName.setText(adminSettings.getOwnerName());
        ownerCompanySiren.setText(adminSettings.getSiren());
        ownerCompanyStreetNumber.setText(adminSettings.getStreetNumber());
        ownerCompanyStreetName.setText(adminSettings.getStreetName());
        ownerCompanyZipCode.setText(adminSettings.getZipCode());
        ownerCompanyCity.setText(adminSettings.getCity());
        ownerCompanyCountry.setText(adminSettings.getCountry());
        ownerCompanyEmail.setText(adminSettings.getEmail());
        ownerCompanyPhone.setText(adminSettings.getPhone());
        ownerCompanyIban.setText(adminSettings.getIban());
    }
}
