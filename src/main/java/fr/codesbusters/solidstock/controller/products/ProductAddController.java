package fr.codesbusters.solidstock.controller.products;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class ProductAddController implements Initializable {

    public StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void addProduct() {
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}
