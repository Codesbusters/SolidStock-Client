package fr.codesbusters.solidstock.controller.orders;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.order.GetOrderDto;
import fr.codesbusters.solidstock.model.order.OrdersModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;


@Slf4j
@Controller
public class OrdersFormController extends DefaultShowController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<OrdersModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }


    @FXML
    public void addOrder() {
        openPopUp("orders/addPopup.fxml", stackPane.getScene(), "Ajouter un bon de commande");
        reloadOrder();
    }


    private void setupTable() {

        MFXTableColumn<OrdersModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(OrdersModel::getID));
        MFXTableColumn<OrdersModel> subjectColumn = new MFXTableColumn<>("Sujet", true, Comparator.comparing(OrdersModel::getSubject));
        MFXTableColumn<OrdersModel> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(OrdersModel::getDescription));
        MFXTableColumn<OrdersModel> customerIdColumn = new MFXTableColumn<>("ID Client", true, Comparator.comparing(OrdersModel::getCustomerId));
        MFXTableColumn<OrdersModel> customerNameColumn = new MFXTableColumn<>("Nom Client", true, Comparator.comparing(OrdersModel::getCustomerName));
        MFXTableColumn<OrdersModel> dueDateColumn = new MFXTableColumn<>("Date de livraison", true, Comparator.comparing(OrdersModel::getDueDate));
        MFXTableColumn<OrdersModel> statusNameColumn = new MFXTableColumn<>("Statut", true, Comparator.comparing(OrdersModel::getStatusName));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getID));
        subjectColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getSubject));
        descriptionColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getDescription));
        customerIdColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getCustomerId));
        customerNameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getCustomerName));
        dueDateColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getDueDate));
        statusNameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getStatusName));


        table.getTableColumns().addAll(idColumn, subjectColumn, descriptionColumn, customerIdColumn, customerNameColumn, dueDateColumn, statusNameColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", OrdersModel::getID),
                new StringFilter<>("Sujet", OrdersModel::getSubject),
                new IntegerFilter<>("ID Client", OrdersModel::getCustomerId),
                new StringFilter<>("Nom Client", OrdersModel::getCustomerName),
                new StringFilter<>("Date de livraison", OrdersModel::getDueDate),
                new StringFilter<>("Statut", OrdersModel::getStatusName)
        );

        reloadOrder();
    }

    @FXML
    public void editOrder() {
        OrdersModel ordersModel = table.getSelectionModel().getSelectedValue();

        if (ordersModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un bon de commande", DialogType.ERROR, 0);
            return;
        }

        setId(ordersModel.getID());

        openPopUp("orders/editPopup.fxml", stackPane.getScene(), "Modification du bon de commande");
        reloadOrder();
    }

    @FXML
    public void removeOrder() {
        OrdersModel ordersModel = table.getSelectionModel().getSelectedValue();

        if (ordersModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR, 0);
            return;
        }
        boolean result = openDialog(stackPane.getScene(), "Voulez-vous vraiment annuler le bon de commande " + ordersModel.getID() + " ?", DialogType.CONFIRMATION, 0);
        if (!result) {
            return;
        }
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/orders/" + ordersModel.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Bon de commande " + ordersModel.getID() + " annulé avec succès", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression du bon de commande", DialogType.ERROR, 0);
        }
        reloadOrder();
    }

    @FXML
    public void reloadOrder() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/orders/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetOrderDto> orderList = null;
        try {
            orderList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing order list", e);
        }

        ObservableList<OrdersModel> ordersModels = FXCollections.observableArrayList();
        assert orderList != null;
        for (GetOrderDto orderDto : orderList) {
            OrdersModel ordersModel = new OrdersModel();
            ordersModel.setID(orderDto.getId());
            ordersModel.setSubject(orderDto.getName());
            ordersModel.setDescription(orderDto.getDescription());
            String customerName = orderDto.getCustomer().getCompanyName();
            if (customerName == null) {
                customerName = orderDto.getCustomer().getFirstName() + " " + orderDto.getCustomer().getLastName();
            }
            ordersModel.setCustomerName(customerName);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime date = LocalDateTime.parse(orderDto.getEstimateDate(), DateTimeFormatter.ISO_DATE_TIME);
            ordersModel.setDueDate(date.format(formatter));
            ordersModel.setSubject(orderDto.getStatus());

            ordersModels.add(ordersModel);
        }

        table.getItems().addAll(ordersModels);
        table.autosizeColumnsOnInitialization();
    }

    @FXML
    public void downloadOrder(ActionEvent actionEvent) throws IOException {
        OrdersModel orderModel = table.getSelectionModel().getSelectedValue();

        if (orderModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un bon de commande", DialogType.ERROR, 0);
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        File pdfFile = requestAPI.downloadFile("/orders/" + orderModel.getID() + "/pdf", true, true);

        if (pdfFile != null && pdfFile.exists()) {
            openFile(pdfFile);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors du téléchargement du bon de commande", DialogType.ERROR, 0);
        }
    }

    private void openFile(File file) {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux")) {
            // Code pour Linux
            ProcessBuilder pb = new ProcessBuilder("xdg-open", file.getAbsolutePath());
            try {
                pb.start();
            } catch (IOException e) {
                log.error("Error while opening file", e);
            }
        } else {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    log.error("Error while opening file", e);
                }
            } else {
                openDialog(stackPane.getScene(), "Impossible d'ouvrir le fichier", DialogType.ERROR, 0);
            }
        }
    }
}