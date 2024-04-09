package fr.codesbusters.solidstock.controller.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.role.GetRoleDto;
import fr.codesbusters.solidstock.dto.user.GetUserDto;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public MFXTextField lastName;
    @FXML
    public MFXTextField userCustomerId;
    @FXML
    public Label customerName;
    @FXML
    public MFXTextField userMail;
    @FXML
    public MFXListView<String> role;
    @FXML
    public Label roleName;
    @FXML
    public MFXTextField firstName;

    private void disableTextFields() {
        userId.setDisable(true);
        lastName.setEditable(false);
        firstName.setEditable(false);
        userCustomerId.setEditable(false);
        userMail.setEditable(false);
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
        lastName.setText(user.getLastName());
        firstName.setText(user.getFirstName());
        if (user.getCustomer() == null) {
            userCustomerId.setText("");
            customerName.setText("Non lié à un client");
        } else {
            userCustomerId.setText(String.valueOf(user.getCustomer().getId()));
            customerName.setText(user.getCustomer().getCompanyName());
        }
        userMail.setText(user.getEmail());

        ObservableList<String> roleList = FXCollections.observableArrayList();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (GetRoleDto roleDto : user.getRoles()) {
                roleList.add(roleDto.getName());
            }
        } else {
            roleList.add("Aucun rôle attribué");
        }

        role.setItems(roleList);
        int roleModelsCount = roleList.size();
        if (roleModelsCount == 1) {
            roleName.setText("Rôle");
        } else {
            roleName.setText("Rôles");
        }
        disableTextFields();
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}