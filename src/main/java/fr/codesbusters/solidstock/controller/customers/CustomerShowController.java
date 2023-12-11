package fr.codesbusters.solidstock.controller.customers;

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
public class CustomerShowController extends DefaultShowController implements Initializable {


    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField customerName;
    @FXML
    public MFXTextField customerThirdParty;
    @FXML
    public MFXTextField customerAddress;
    @FXML
    public MFXTextField customerCorporateName;
    @FXML
    public MFXTextField customerSiren;
    @FXML
    public MFXTextField customerSiret;
    @FXML
    public MFXTextField customerRib;

    @FXML
    public MFXTextField customerRcs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerName.setText(String.valueOf(getId()));
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }


}
