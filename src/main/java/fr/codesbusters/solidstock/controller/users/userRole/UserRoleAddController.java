package fr.codesbusters.solidstock.controller.users.userRole;

import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.listener.RoleSelectorListener;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class UserRoleAddController extends DefaultShowController implements Initializable, RoleSelectorListener {

    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField newRoleName;

    @FXML
    public void cancel(ActionEvent actionEvent) {
    }

    @FXML
    public void addNewRole(ActionEvent actionEvent) {
    }

    @Override
    public void processRoleContent(String roleContent, String roleName) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}