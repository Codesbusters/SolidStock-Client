package fr.codesbusters.solidstock.controller.orders;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.order.GetOrderDto;
import fr.codesbusters.solidstock.model.StockMovementModel;
import fr.codesbusters.solidstock.model.SupplierModel;
import fr.codesbusters.solidstock.model.order.OrdersModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


@Slf4j
@Controller
public class OrdersFormController extends DefaultShowController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    public MFXButton modifyButton;
    @FXML
    public MFXButton cancelButton;

    @FXML
    private MFXTableView<OrdersModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
        table.setOnMouseClicked(event -> {
            OrdersModel order = table.getSelectionModel().getSelectedValue();

            if (order != null ) {
                if (Objects.equals(order.getStatusName(), "Annulé")) {
                    modifyButton.setDisable(true);
                    cancelButton.setDisable(true);
                } else {
                    modifyButton.setDisable(false);
                    cancelButton.setDisable(false);
                }
            }
        });
    }


    @FXML
    public void addOrder() {
        openPopUp("orders/addPopup.fxml", stackPane.getScene(), "Ajouter un bon de commande");
        reloadOrder();
    }


    private void setupTable() {

        MFXTableColumn<OrdersModel> icon = new MFXTableColumn<>("", true, Comparator.comparing(OrdersModel::getStatusName));
        MFXTableColumn<OrdersModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(OrdersModel::getID));
        MFXTableColumn<OrdersModel> subjectColumn = new MFXTableColumn<>("Sujet", true, Comparator.comparing(OrdersModel::getSubject));
        MFXTableColumn<OrdersModel> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(OrdersModel::getDescription));
        MFXTableColumn<OrdersModel> customerIdColumn = new MFXTableColumn<>("ID Client", true, Comparator.comparing(OrdersModel::getCustomerId));
        MFXTableColumn<OrdersModel> customerNameColumn = new MFXTableColumn<>("Nom Client", true, Comparator.comparing(OrdersModel::getCustomerName));
        MFXTableColumn<OrdersModel> dueDateColumn = new MFXTableColumn<>("Date de livraison", true, Comparator.comparing(OrdersModel::getDueDate));
        MFXTableColumn<OrdersModel> statusNameColumn = new MFXTableColumn<>("Statut", true, Comparator.comparing(OrdersModel::getStatusName));


        icon.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrdersModel::getStatusName) {{
            MFXFontIcon icon;

            if (rowCell != null) {
                String statusName = rowCell.getStatusName();

                if (Objects.equals(statusName, "Annulé")) {
                    icon = new MFXFontIcon("fas-x");
                    icon.setColor(Color.rgb(255, 0, 0));
                } else if (Objects.equals(statusName, "En attente de validation")) {
                    icon = new MFXFontIcon("fas-hourglass-half");
                    icon.setColor(Color.rgb(255, 130, 20));
                } else {
                    icon = new MFXFontIcon("fas-circle-check");
                    icon.setColor(Color.rgb(0, 255, 0));
                }

                icon.setSize(20);
                setGraphic(icon);
            }

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setAlignment(Pos.CENTER);
        }});

        idColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrdersModel::getID) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && Objects.equals(rowCell.getStatusName(), "Annulé")) {
                    setStyle("-fx-opacity: 0.5");
                }
            }
        });
        subjectColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrdersModel::getSubject) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && Objects.equals(rowCell.getStatusName(), "Annulé")) {
                    setStyle("-fx-opacity: 0.5");
                }
            }
        });
        descriptionColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrdersModel::getDescription) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && Objects.equals(rowCell.getStatusName(), "Annulé")) {
                    setStyle("-fx-opacity: 0.5");
                }
            }
        });
        customerIdColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrdersModel::getCustomerId) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && Objects.equals(rowCell.getStatusName(), "Annulé")) {
                    setStyle("-fx-opacity: 0.5");
                }
            }
        });
        customerNameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrdersModel::getCustomerName) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && Objects.equals(rowCell.getStatusName(), "Annulé")) {
                    setStyle("-fx-opacity: 0.5");
                }
            }
        });
        dueDateColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrdersModel::getDueDate) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && Objects.equals(rowCell.getStatusName(), "Annulé")) {
                    setStyle("-fx-opacity: 0.5");
                }
            }
        });
        statusNameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(OrdersModel::getStatusName) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && Objects.equals(rowCell.getStatusName(), "Annulé")) {
                    setStyle("-fx-opacity: 0.5");
                }
            }
        });


        table.getTableColumns().addAll(icon ,idColumn, subjectColumn, descriptionColumn, customerIdColumn, customerNameColumn, dueDateColumn, statusNameColumn);
        table.getFilters().addAll(
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
            ordersModel.setDueDate(orderDto.getEstimateDate() == null ? "" : LocalDate.parse(orderDto.getEstimateDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(formatter));
            ordersModel.setSubject(orderDto.getName());
            ordersModel.setStatusName(orderDto.getStatus());

            ordersModels.add(ordersModel);
        }
        log.info("Liste des commandes récupérées depuis l'API : {}", ordersModels);

        table.getItems().addAll(ordersModels);
        table.autosizeColumns();
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