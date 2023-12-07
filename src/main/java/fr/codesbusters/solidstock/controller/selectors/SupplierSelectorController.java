package fr.codesbusters.solidstock.controller.selectors;

import fr.codesbusters.solidstock.business.ProductDetail;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.listener.SupplierSelectorListener;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class SupplierSelectorController extends DefaultController implements Initializable {


    @FXML
    TextField supplierTextField;

    @FXML
    MFXTableView<ProductDetail> table;

    private Stage parentStage;

    private SupplierSelectorListener listener;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

    }

    public void setStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setListener(SupplierSelectorListener listener) {
        this.listener = listener;
    }

    @FXML
    private void submitAction(ActionEvent event) {



       /* String supplierContent = supplierTextField.getText();

        // Appelez la méthode du parent via l'interface
        if (listener != null) {
            log.info(supplierContent);
            listener.processSupplierContent(supplierContent);
        }

        // Fermez la fenêtre pop-up
        parentStage.close();*/
    }

    @FXML
    private void cancel() {
        parentStage.close();
    }
}
