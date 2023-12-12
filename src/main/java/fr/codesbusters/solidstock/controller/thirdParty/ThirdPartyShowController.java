package fr.codesbusters.solidstock.controller.thirdParty;

import fr.codesbusters.solidstock.controller.DefaultShowController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class ThirdPartyShowController extends DefaultShowController implements Initializable {


    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField thirdPartyId;
    @FXML
    public MFXTextField thirdPartyFirstName;
    @FXML
    public MFXTextField thirdPartyLastName;
    @FXML
    public MFXTextField thirdPartyCity;
    @FXML
    public MFXTextField thirdPartyZipCode;
    @FXML
    public MFXTextField thirdPartyAddress;
    @FXML
    public MFXTextField thirdPartyStreetNumber;
    @FXML
    public MFXTextField thirdPartyEmail;
    @FXML
    public MFXTextField thirdPartyMobilePhone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        thirdPartyId.setText(String.valueOf(getId()));
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }


}
