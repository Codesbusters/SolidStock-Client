package fr.codesbusters.solidstock.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class AddBookController {


    @FXML
    private TextField title;
    @FXML
    private TextField author;
    @FXML
    private DatePicker parution_date;

    @FXML
    private Button button;

    @FXML
    private void initialize() {
        button.setOnAction(event -> saveBook());
    }

    private void saveBook() {
    }


}