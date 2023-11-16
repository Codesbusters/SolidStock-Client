package fr.codesbusters.solidstock.controller.test;

import fr.codesbusters.solidstock.controller.DefaultController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class TestController extends DefaultController implements Initializable {
    @FXML
    private StackPane stackPane;
    @FXML
    private MFXTextField sellPrice;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
