package fr.codesbusters.solidstock.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class ProductController implements Initializable {
    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*        Pane parentPane = (Pane) stackPane.getParent();
        stackPane.prefWidthProperty().bind(parentPane.widthProperty());
        stackPane.prefHeightProperty().bind(parentPane.heightProperty());*/
    }

}
