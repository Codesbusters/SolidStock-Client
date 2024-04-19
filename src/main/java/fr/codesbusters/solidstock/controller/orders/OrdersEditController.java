package fr.codesbusters.solidstock.controller.orders;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.business.Status;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.order.GetOrderDto;
import fr.codesbusters.solidstock.dto.order.GetOrderRowDto;
import fr.codesbusters.solidstock.dto.order.PostOrderDto;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.model.order.OrderRowModel;
import fr.codesbusters.solidstock.model.order.StatusModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;


@Slf4j
@Controller
public class OrdersEditController extends DefaultShowController implements Initializable, CustomerSelectorListener {


    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField orderId;
    @FXML
    public MFXTextField customerId;
    @FXML
    public Label customerName;
    @FXML
    public MFXTextField orderName;
    @FXML
    public MFXTextField orderDescription;
    @FXML
    public MFXComboBox<Status> orderStatus;
    @FXML
    public MFXDatePicker dueDate;
    @FXML
    private MFXTableView<OrderRowModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderId.setText(String.valueOf(getId()));
        ObservableList<Status> filteredStatuts = StatusModel.getFilteredStatus();
        orderStatus.setConverter(StatusModel.getStatusStringConverter());
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/orders/" + getId(), String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                GetOrderDto order = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
                });
                orderName.setText(order.getName());
                orderDescription.setText(order.getDescription());
                orderStatus.setText(order.getStatus());
                dueDate.setText(order.getEstimateDate());
                customerId.setText(String.valueOf(order.getCustomer().getId()));
                orderStatus.setItems(filteredStatuts);
                if (order.getCustomer().getCompanyName() != null && !order.getCustomer().getCompanyName().isEmpty()) {
                    customerName.setText(order.getCustomer().getCompanyName());
                } else {
                    customerName.setText(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
                }
            } catch (Exception e) {
                log.error("Error while parsing order", e);
            }
        }

        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    private void setupTable() {
        MFXTableColumn<OrderRowModel> productColumn = new MFXTableColumn<>("Nom du produit", true, Comparator.comparing(OrderRowModel::getProductName));
        MFXTableColumn<OrderRowModel> quantityColumn = new MFXTableColumn<>("Quantité", true, Comparator.comparing(OrderRowModel::getQuantity));

        productColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrderRowModel::getProductName));
        quantityColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrderRowModel::getQuantity));

        table.getTableColumns().addAll(productColumn, quantityColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("ID", OrderRowModel::getID),
                new StringFilter<>("Nom du produit", OrderRowModel::getProductName)
        );

        reloadOrderRow();
    }

    @FXML
    public void editOrder() throws NumberFormatException {
        PostOrderDto order = new PostOrderDto();
        order.setName(orderName.getText());
        order.setDescription(orderDescription.getText());
        order.setCustomerId(Integer.parseInt(customerId.getText()));
        order.setStatus(orderStatus.getText());

        if (dueDate.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = dueDate.getValue().format(formatter);
            order.setEstimateDate(formattedDate);
        } else {
            RequestAPI requestAPI = new RequestAPI();
            ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/orders/" + getId(), String.class, true, true);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    GetOrderDto orderBis = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
                    });
                    dueDate.setText(orderBis.getEstimateDate());
                    LocalDate estimateDate = LocalDate.parse(orderBis.getEstimateDate());
                    dueDate.setValue(estimateDate);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDate = dueDate.getValue().format(formatter);
                    order.setEstimateDate(formattedDate);
                } catch (Exception e) {
                    log.error("Error while parsing order", e);
                }
            }
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPutRequest("/orders/" + getId(), order, String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
            openDialog(stackPane.getScene(), "Commande " + orderName.getText() + " modifiée avec succès.", DialogType.INFORMATION, 0);

        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la modfication de la commande " + orderName.getText() + ".", DialogType.ERROR, 0);
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void selectCustomer() {
        openCustomerSelector(stackPane.getScene(), this);
    }

    @FXML
    public void processCustomerContent(String customerContent) {
        customerName.setText(customerContent);
    }


    public void reloadOrderRow() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/orders/" + getId() + "/row/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetOrderRowDto> orderList = null;
        try {
            orderList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing order row list", e);
        }

        ObservableList<OrderRowModel> orderRowModels = FXCollections.observableArrayList();
        assert orderList != null;
        for (GetOrderRowDto orderRowDto : orderList) {
            OrderRowModel orderRowModel = new OrderRowModel();
            orderRowModel.setID(orderRowDto.getId());
            orderRowModel.setProductName(orderRowDto.getProduct().getName());
            orderRowModel.setQuantity(orderRowDto.getQuantity());

            orderRowModels.add(orderRowModel);
        }

        table.getItems().addAll(orderRowModels);
    }

    public void addOrderRow(ActionEvent actionEvent) {
        openPopUp("/orders/ordersRows/addRowPopup.fxml", stackPane.getScene(), "Ajouter une ligne à la commande");
        reloadOrderRow();
    }

    public void editOrderRow(ActionEvent actionEvent) {
        OrderRowModel orderRowModel = table.getSelectionModel().getSelectedValues().getFirst();
        if (orderRowModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une commande.", DialogType.ERROR, 0);
            return;
        }

        setIntermediaryId(orderRowModel.getID());
        openPopUp("/orders/ordersRows/editRowPopup.fxml", stackPane.getScene(), "Modifier une ligne de commande");
        reloadOrderRow();
    }

    public void removeOrderRow(ActionEvent actionEvent) {
        OrderRowModel orderRowModel = table.getSelectionModel().getSelectedValues().getFirst();

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/orders/" + getId() + "/row/" + orderRowModel.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Ligne de commande supprimée avec succès.", DialogType.INFORMATION, 0);
            reloadOrderRow();
        } else {
             openDialog(stackPane.getScene(), "Erreur lors de la suppression de la ligne de commande.", DialogType.INFORMATION, 0);
        }
    }
}