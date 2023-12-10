package fr.codesbusters.solidstock.controller.products;

import fr.codesbusters.solidstock.component.SSDoubleField;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.model.QuantityTypeModel;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class ProductShowController extends DefaultShowController implements Initializable {


    @FXML
    public StackPane stackPane;
    @FXML
    public ImageView imageView;
    @FXML
    public MFXTextField productId;
    @FXML
    public MFXTextField productName;
    @FXML
    public TextArea description;
    @FXML
    public SSDoubleField buyPrice;
    @FXML
    public SSDoubleField sellPrice;
    @FXML
    public SSDoubleField vat;
    @FXML
    public MFXTextField supplierID;
    @FXML
    public MFXTextField productFamilyID;
    @FXML
    public MFXComboBox<QuantityTypeModel> quantityType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productId.setText(String.valueOf(getId()));
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }


}
