package fr.codesbusters.solidstock.controller.dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class DashboardAddController implements Initializable {

    @FXML
    public Pane mainContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
