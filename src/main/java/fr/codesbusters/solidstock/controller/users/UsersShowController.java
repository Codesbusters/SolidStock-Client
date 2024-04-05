package fr.codesbusters.solidstock.controller.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.user.GetUserDto;
import fr.codesbusters.solidstock.model.RoleModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class UsersShowController extends DefaultShowController implements Initializable {

    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField userId;
    @FXML
    public MFXTextField userName;
    @FXML
    public MFXTextField userCustomerId;
    @FXML
    public Label customerName;
    @FXML
    public MFXTextField userMail;
    @FXML
    public MFXTextField userLogin;
    @FXML
    public MFXComboBox<RoleModel> role;
    @FXML
    public Label roleName;

    private void disableTextFields() {
        userId.setEditable(false);
        userName.setEditable(false);
        userCustomerId.setEditable(false);
        userMail.setEditable(false);
        userLogin.setEditable(false);
        role.setEditable(false);
    }
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        userId.setText(String.valueOf(getId()));
        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/user/" + getId(), String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetUserDto user = null;
        try {
            user = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing user list", e);
        }

        assert user != null;
        userName.setText(user.getName());
        if (user.getCustomer() == null) {
            userCustomerId.setText("");
            customerName.setText("Pas lié à un client");
        } else
        {
            userCustomerId.setText(String.valueOf(user.getCustomer().getId()));
            customerName.setText(user.getCustomer().getCompanyName());
        }
        userMail.setText(user.getEmail());
        userLogin.setText(user.getUserName());
        role.setText(String.valueOf(user.getRole().getId()));
        roleName.setText(user.getRole().getName());
        disableTextFields();
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}
