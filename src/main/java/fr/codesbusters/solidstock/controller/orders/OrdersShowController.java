package fr.codesbusters.solidstock.controller.orders;

import fr.codesbusters.solidstock.controller.DefaultShowController;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class OrdersShowController extends DefaultShowController implements Initializable {


    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField subject;
    @FXML
    public TextArea description;
    @FXML
    public MFXTextField customerId;
    @FXML
    public MFXTextField customerName;
    @FXML
    public MFXDatePicker dueDate;
    @FXML
    public MFXTextField estimateId;
    @FXML
    public MFXTextField statusId;
    @FXML
    public MFXTextField statusName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerId.setText(String.valueOf(getId()));
        estimateId.setText(String.valueOf(getId()));
        statusId.setText(String.valueOf(getId()));
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }


}
