package fr.codesbusters.solidstock.controller.selectors;

import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.listener.ProductFamilySelectorListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ProductFamilySelectorController extends DefaultController implements Initializable {


    @FXML
    TextField supplierTextField;

    private Stage parentStage;

    private ProductFamilySelectorListener listener;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

    }

    public void setStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setListener(ProductFamilySelectorListener listener) {
        this.listener = listener;
    }

    @FXML
    private void submitAction(ActionEvent event) {
        String supplierContent = supplierTextField.getText();

        // Appelez la méthode du parent via l'interface
        if (listener != null) {
            log.info(supplierContent);
            listener.processProductFamilyContent(supplierContent);
        }

        // Fermez la fenêtre pop-up
        parentStage.close();
    }
}
