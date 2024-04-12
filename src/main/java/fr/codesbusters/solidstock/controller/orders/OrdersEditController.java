package fr.codesbusters.solidstock.controller.orders;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.dto.order.GetOrderDto;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.listener.EstimateSelectorListener;
import fr.codesbusters.solidstock.model.order.OrderRowModel;
import fr.codesbusters.solidstock.model.order.OrdersModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import static sun.security.pkcs11.wrapper.Functions.getId;

@Slf4j
@Controller
public class OrdersEditController extends DefaultController implements Initializable, CustomerSelectorListener, EstimateSelectorListener {


    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField orderId;
    @FXML
    public MFXTextField subject;
    @FXML
    public MFXTextField customerId;
    @FXML
    public Label customerName;
    @FXML
    public MFXTextField dueDate;
    @FXML
    public MFXTextField estimateId;
    @FXML
    public MFXTextField statusName;
    @FXML
    public TextArea description;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderId.setText(String.valueOf(getId()));
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/orders/" + getId(), String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                GetOrderDto order = objectMapper.readValue(responseEntity.getBody(), new TypeReference<GetOrderDto>() {
                });
                subject.setText(order.getName());
                description.setText(order.getDescription());
                customerId.setText(String.valueOf(order.getId()));
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
        MFXTableColumn<OrderRowModel> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(OrderRowModel::getID));
        MFXTableColumn<OrderRowModel> productColumn = new MFXTableColumn<>("Nom du produit", true, Comparator.comparing(OrderRowModel::getProductName));
        MFXTableColumn<OrderRowModel> quantityColumn = new MFXTableColumn<>("Quantité", true, Comparator.comparing(OrderRowModel::getQuantity));
        MFXTableColumn<OrderRowModel> priceColumn = new MFXTableColumn<>("Prix unitaire", true, Comparator.comparing(OrderRowModel::getUnitPrice));
        MFXTableColumn<OrderRowModel> totalColumn = new MFXTableColumn<>("Total HT", true, Comparator.comparing(OrderRowModel::getTotalHT));
        MFXTableColumn<OrderRowModel> vatColumn = new MFXTableColumn<>("TVA", true, Comparator.comparing(OrderRowModel::getVat));
        MFXTableColumn<OrderRowModel> totalVatColumn = new MFXTableColumn<>("Total TTC", true, Comparator.comparing(OrderRowModel::getTotalTTC));

        productColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrderRowModel::getProductName));
        quantityColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrderRowModel::getQuantity));
        priceColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrderRowModel::getUnitPrice));
        totalColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrderRowModel::getTotalHT));
        vatColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrderRowModel::getVat));
        totalVatColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrderRowModel::getTotalTTC));

        table.getTableColumns().addAll(productColumn, quantityColumn, priceColumn, totalColumn, vatColumn, totalVatColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("ID", OrderRowModel::getID),
                new StringFilter<>("Nom du produit", OrderRowModel::getProductName),
                new DoubleFilter<>("Quantité", OrderRowModel::getQuantity),
                new DoubleFilter<>("Prix unitaire", OrderRowModel::getUnitPrice),
                new DoubleFilter<>("Total HT", OrderRowModel::getTotalHT),
                new DoubleFilter<>("TVA", OrderRowModel::getVat),
                new DoubleFilter<>("Total TTC", OrderRowModel::getTotalTTC)
        );

        reloadInvoiceRow();
    }

    @FXML
    public void editOrder() throws NumberFormatException, UnsupportedEncodingException {
        String subjectString = subject.getText();
        String descriptionString = description.getText();
        String customerNameString = customerName.getText();
        String dueDadescriptionring = dueDate.getText();
        String statusNameString = statusName.getText();

        // Vérification du sujet
        if (subjectString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez saisir un sujet", DialogType.ERROR, 0);
        }

        // Vérification de la description
        if (descriptionString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez saisir une description", DialogType.ERROR, 0);
        }

        // Vérification du nom du client
        if (customerNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez saisir un nom de client", DialogType.ERROR, 0);
        }

        // Vérification de la date d'échéance
        if (dueDadescriptionring.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez saisir une date d'échéance", DialogType.ERROR, 0);
        }

        // Vérification du nom du statut
        if (statusNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez saisir un nom de statut", DialogType.ERROR, 0);
        }

        // Modification de l'objet Orders
        OrdersModel ordersModel = OrdersModel.ofSplit(
                Integer.parseInt(orderId.getText()),
                subjectString,
                descriptionString,
                Integer.parseInt(customerId.getText()),
                customerNameString,
                dueDadescriptionring,
                Integer.parseInt(estimateId.getText()),
                statusNameString
        );

        log.info("Order to add : {}", ordersModel);

        cancel();

        openDialog(stackPane.getScene(), "Commande " + ordersModel.getSubject() + " modifiée avec succès", DialogType.INFORMATION, 0);
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

    @FXML
    public void selectEstimate() {
        openEstimateSelector(stackPane.getScene(), this);
    }

    @FXML
    public void processEstimateContent(String estimateContent) {
        estimateId.setText(estimateContent);
    }


}