package fr.codesbusters.solidstock.controller.users;

import fr.codesbusters.solidstock.controller.DefaultShowController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class UsersShowController extends DefaultShowController implements Initializable {

    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField userId;
    @FXML
    public MFXTextField userLastName;

    @FXML
    public MFXTextField userFirstName;

    @FXML
    public MFXTextField userMail;
    @FXML
    public MFXTextField userLogin;
    @FXML
    public MFXTextField userMobilePhone;

    @FXML
    public MFXPasswordField userPassword;

    @FXML
    public MFXPasswordField userConfirmPassword;

    @FXML
    public MFXComboBox role;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        userId.setText(String.valueOf(getId()));
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}
