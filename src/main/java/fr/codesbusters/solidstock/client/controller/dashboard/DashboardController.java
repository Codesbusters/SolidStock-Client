package fr.codesbusters.solidstock.client.controller.dashboard;

import fr.codesbusters.solidstock.client.service.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
    @FXML
    public Label stockValue;
    @FXML
    public Label negativeStockItems;
    @FXML
    public Label numberOfActiveItems;
    @FXML
    public Label itemsUnderMinimumQuantity;
    @FXML
    public Label suppliersOrdersInProgress;
    @FXML
    public Label customersOrdersInProgress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequestAPI requestAPIStockValue = new RequestAPI();

        ResponseEntity<String> responseEntityStockValue = requestAPIStockValue.sendGetRequest("/product/all", String.class, true, true);
        ObjectMapper mapperStockValue = new ObjectMapper();
        List<GetProductDto> productList = null;
        try {
            productList = mapperStockValue.readValue(responseEntityStockValue.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing product list", e);
        }

        double totalStockValue = 0;

        if (productList != null) {
            for (GetProductDto product : productList) {
                double stockValue = Double.parseDouble(product.getSellPrice()) * product.getInStock();
                totalStockValue += stockValue;
            }
            totalStockValue = Math.ceil(totalStockValue);

            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.FRANCE);
            decimalFormat.applyPattern("#,##0");
            String formattedStockValue = decimalFormat.format(totalStockValue) + " €";
            stockValue.setText(formattedStockValue);
        }

        int totalItemActive = 0;

        if (productList != null) {
            for (GetProductDto product : productList) {
                if (!product.isDeleted()) {
                    totalItemActive += 1;
                }
            }
            numberOfActiveItems.setText(String.valueOf(totalItemActive));
        }

        int totalItemUnderStockMinimum = 0;

        if (productList != null) {
            for (GetProductDto productDto : productList) {
                if (productDto.getInStock() < productDto.getMinimumStockQuantity()) {
                    totalItemUnderStockMinimum += 1;
                }
            }
            itemsUnderMinimumQuantity.setText(String.valueOf(totalItemUnderStockMinimum));
        }

        int totalItemsOutOfStock = 0;

        if (productList != null) {
            for (GetProductDto productDto : productList) {
                if (productDto.getInStock() < productDto.getMinimumStockQuantity()) {
                    totalItemsOutOfStock += 1;
                }
            }
            negativeStockItems.setText(String.valueOf(totalItemsOutOfStock));
        }

        RequestAPI requestAPISupplierOrders = new RequestAPI();
        ResponseEntity<String> responseEntitySupplierOrders = requestAPISupplierOrders.sendGetRequest("/supplier-order/all", String.class, true, true);
        ObjectMapper mapperSupplierOrders = new ObjectMapper();
        List<GetSupplierOrderDto> orderSupplierList = null;
        try {
            orderSupplierList = mapperSupplierOrders.readValue(responseEntitySupplierOrders.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing orderSupplier list", e);
        }

        int totalSupplierOrdersInProgress = 0;

        if (orderSupplierList != null) {
            for (GetSupplierOrderDto supplierOrderDto : orderSupplierList) {
                if (supplierOrderDto.getStatus().matches("TO_BE_VALIDATED")) {
                    totalSupplierOrdersInProgress += 1;
                }
            }
            suppliersOrdersInProgress.setText(String.valueOf(totalSupplierOrdersInProgress));
        }

        RequestAPI requestAPIUserOrders = new RequestAPI();

        ResponseEntity<String> responseEntityUserOrders = requestAPIUserOrders.sendGetRequest("/orders/all", String.class, true, true);
        ObjectMapper mapperUserOrders = new ObjectMapper();
        List<GetOrderDto> orderCustomerList = null;
        try {
            orderCustomerList = mapperUserOrders.readValue(responseEntityUserOrders.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing order list", e);
        }

        int totalCustomerOrdersInProgress = 0;

        if (orderCustomerList != null) {
            for (GetOrderDto orderDto : orderCustomerList) {
                if (orderDto.getCustomer() != null && orderDto.getStatus().matches("En attente de validation")) {
                    totalCustomerOrdersInProgress += 1;
                }
            }
            customersOrdersInProgress.setText(String.valueOf(totalCustomerOrdersInProgress));
        }


        RequestAPI requestAPIProductFamily = new RequestAPI();

        ResponseEntity<String> responseEntityProductFamily = requestAPIProductFamily.sendGetRequest("/product-family/all", String.class, true, true);
        ObjectMapper mapperProductFamily = new ObjectMapper();
        List<GetProductFamilyDto> productFamilyList = null;
        try {
            productFamilyList = mapperProductFamily.readValue(responseEntityProductFamily.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing productFamily List", e);
        }

        RequestAPI requestAPIChartSelled = new RequestAPI();

        ResponseEntity<String> responseEntityChartSelled = requestAPIChartSelled.sendGetRequest("/stock-movement/all", String.class, true, true);
        ObjectMapper mapperChartSelled = new ObjectMapper();
        List<GetStockMovementDto> stockMovementChartSelledList = null;
        try {
            stockMovementChartSelledList = mapperChartSelled.readValue(responseEntityChartSelled.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing stockMovement List", e);
        }

        if (productFamilyList != null && stockMovementChartSelledList != null) {
            LocalDate today = LocalDate.now();
            LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            LocalDate endOfWeek = startOfWeek.plusDays(6);

            List<GetStockMovementDto> currentWeekStockMovements = stockMovementChartSelledList.stream()
                    .filter(stockMovement -> {
                        LocalDate movementDate = LocalDate.parse(stockMovement.getExpiredDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        return !movementDate.isBefore(startOfWeek) && !movementDate.isAfter(endOfWeek);
                    })
                    .collect(Collectors.toList());

            double totalSales = currentWeekStockMovements.stream()
                    .mapToDouble(GetStockMovementDto::getQuantity)
                    .sum();

            Map<Integer, Double> salesByFamily = new HashMap<>();
            Map<Integer, String> familyNameMap = new HashMap<>();
            for (GetProductFamilyDto productFamily : productFamilyList) {
                familyNameMap.put(productFamily.getId(), productFamily.getName());
                double familySales = currentWeekStockMovements.stream()
                        .filter(stockMovement -> stockMovement.getProduct().getProductFamily().getId() == productFamily.getId())
                        .mapToDouble(GetStockMovementDto::getQuantity)
                        .sum();
                if (familySales > 0) {
                    salesByFamily.put(productFamily.getId(), familySales);
                }
            }

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<Integer, Double> entry : salesByFamily.entrySet()) {
                double percentage = (entry.getValue() / totalSales) * 100;
                pieChartData.add(new PieChart.Data(familyNameMap.get(entry.getKey()), percentage));
            }

            pieChart.getData().addAll(pieChartData);
        }



        pieChart.setTitle("Répartition des ventes de la semaine");

        if (productFamilyList != null) {
            for (GetProductFamilyDto productFamily : productFamilyList) {
                boolean hasSales = false;

                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(productFamily.getName());
                LocalDate today = LocalDate.now();

                List<String> lastFourMonths = new ArrayList<>();
                for (int i = 0; i <= 3; i++) {
                    lastFourMonths.add(today.minusMonths(i).format(DateTimeFormatter.ofPattern("MM/yyyy")));
                }

                Map<String, Integer> soldQuantitiesByMonth = new HashMap<>();
                for (String month : lastFourMonths) {
                    soldQuantitiesByMonth.put(month, 0);
                }
                Collections.reverse(lastFourMonths);

                assert stockMovementChartSelledList != null;
                for (GetStockMovementDto stockMovement : stockMovementChartSelledList) {
                    if (stockMovement.getProduct().getProductFamily().getId() == productFamily.getId()) {
                        LocalDate movementDate = LocalDate.parse(stockMovement.getExpiredDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        String monthYear = movementDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));
                        if (soldQuantitiesByMonth.containsKey(monthYear)) {
                            soldQuantitiesByMonth.put(monthYear, (int) (soldQuantitiesByMonth.get(monthYear) + stockMovement.getQuantity()));
                            hasSales = true;
                        }
                    }
                }

                if (hasSales) {
                    for (String month : lastFourMonths) {
                        series.getData().add(new XYChart.Data<>(month, soldQuantitiesByMonth.get(month)));
                    }
                    barChart.getData().add(series);
                }
            }
        }


        // Optionnel : Configuration des axes et du titre
        barXAxis.setLabel("Mois");
        barYAxis.setLabel("Quantitée Vendue");
        barChart.setTitle("Quantité de produits vendus par mois");


    }
}
