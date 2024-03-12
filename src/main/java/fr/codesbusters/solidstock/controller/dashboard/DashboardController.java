package fr.codesbusters.solidstock.controller.dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class DashboardController implements Initializable {

    @FXML
    public PieChart pieChart;

    @FXML
    public BarChart barChart;
    @FXML
    public CategoryAxis barXAxis;
    @FXML
    public NumberAxis barYAxis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Données pour le graphique circulaire
        pieChart.getData().addAll(
                new PieChart.Data("Jambon", 30),
                new PieChart.Data("Beurre", 25),
                new PieChart.Data("Comté", 20),
                new PieChart.Data("Saucisson", 15),
                new PieChart.Data("Rosette", 10)
        );
        pieChart.setTitle("Répartition des ventes");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("01/2023", 10));
        series.getData().add(new XYChart.Data<>("02/2023", 20));
        series.getData().add(new XYChart.Data<>("03/2023", 30));
        series.getData().add(new XYChart.Data<>("04/2023", 5));
        series.getData().add(new XYChart.Data<>("05/2023", 15));
        series.getData().add(new XYChart.Data<>("06/2023", 25));
        series.getData().add(new XYChart.Data<>("07/2023", 0));

        // Ajout de la série au BarChart
       // barChart.getData().add(series);

        // Optionnel : Configuration des axes et du titre
        barXAxis.setLabel("Mois");
        barYAxis.setLabel("Quantitée Vendue");
        barChart.setTitle("Nombre de produits vendus par mois");
    }
}
