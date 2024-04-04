package fr.codesbusters.solidstock.controller.invoices.invoiceRow;

import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.controller.DefaultShowController;
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
public class InvoiceRowAddController extends DefaultShowController implements Initializable {

    @FXML
    public StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void selectProduct(ActionEvent actionEvent) {
    }
}
