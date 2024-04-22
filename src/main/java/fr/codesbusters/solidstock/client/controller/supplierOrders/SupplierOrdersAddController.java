package fr.codesbusters.solidstock.client.controller.supplierOrders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.supplierOrder.GetSupplierOrderDto;
import fr.codesbusters.solidstock.client.model.supplierOrder.SupplierOrderModel;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.AnchorPane;
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
public class SupplierOrdersAddController extends DefaultShowController implements Initializable {

    @FXML
    public AnchorPane anchorPane;

    @FXML
    private MFXTableView<SupplierOrderModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
    }

    private void setupTable() {
        MFXTableColumn<SupplierOrderModel> orderNumberColumn = new MFXTableColumn<>("Numéro de commande");
        MFXTableColumn<SupplierOrderModel> orderDateColumn = new MFXTableColumn<>("Date de commande");
        MFXTableColumn<SupplierOrderModel> deliveryDateColumn = new MFXTableColumn<>("Date de livraison");
        MFXTableColumn<SupplierOrderModel> statusColumn = new MFXTableColumn<>("Statut");


        orderNumberColumn.setRowCellFactory(supplierOrderModel -> new MFXTableRowCell<>(SupplierOrderModel::getOrderNumber) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
            if (supplierOrderModel != null && supplierOrderModel.getIsDisabled()) {
                setStyle("-fx-opacity: 0.5;");
            }
        }});

        orderDateColumn.setRowCellFactory(supplierOrderModel -> new MFXTableRowCell<>(SupplierOrderModel::getOrderDate) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
            if (supplierOrderModel != null && supplierOrderModel.getIsDisabled()) {
                setStyle("-fx-opacity: 0.5;");
            }
        }});

        deliveryDateColumn.setRowCellFactory(supplierOrderModel -> new MFXTableRowCell<>(SupplierOrderModel::getDeliveryDate) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
            if (supplierOrderModel != null && supplierOrderModel.getIsDisabled()) {
                setStyle("-fx-opacity: 0.5;");
            }
        }});

        statusColumn.setRowCellFactory(supplierOrderModel -> new MFXTableRowCell<>(SupplierOrderModel::getStatus) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
            if (supplierOrderModel != null && supplierOrderModel.getIsDisabled()) {
                setStyle("-fx-opacity: 0.5;");
            }
        }});

        table.getTableColumns().addAll(orderNumberColumn, orderDateColumn, deliveryDateColumn, statusColumn);
        table.getFilters().addAll(
                new StringFilter<>("Numéro de commande", SupplierOrderModel::getOrderNumber),
                new StringFilter<>("Date de commande", SupplierOrderModel::getOrderDate),
                new StringFilter<>("Date de livraison", SupplierOrderModel::getDeliveryDate),
                new StringFilter<>("Statut", SupplierOrderModel::getStatus)
        );


        reloadStockMovement();

    }

    @FXML
    public void openConfirmRemove() {

       boolean isOk = openDialog(anchorPane.getScene(),"Êtes-vous sûr de vouloir supprimer cette commande ?", DialogType.CONFIRMATION,  0);
        if (!isOk) {
            return;
        }
        SupplierOrderModel supplierOrderModel = table.getSelectionModel().getSelectedValue();
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/supplier-order/" + supplierOrderModel.getID(), String.class, true);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(anchorPane.getScene(), "Le mouvement de stock a été supprimé avec succès", DialogType.INFORMATION, 0);
            reloadStockMovement();
        } else {
            openDialog(anchorPane.getScene(), "Erreur lors de la suppression du mouvement de stock", DialogType.ERROR, 0);
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void reloadStockMovement() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/supplier-order/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetSupplierOrderDto> supplierOrderList = null;
        try {
            supplierOrderList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing supplier order", e);
        }
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ObservableList<SupplierOrderModel> supplierOrderModels = FXCollections.observableArrayList();
        assert supplierOrderList != null;
        for (GetSupplierOrderDto supplierOrderDto : supplierOrderList) {
            SupplierOrderModel supplierOrderModel = new SupplierOrderModel();
            supplierOrderModel.setID((int) supplierOrderDto.getId());
            supplierOrderModel.setOrderNumber(supplierOrderDto.getOrderNumber());
            supplierOrderModel.setOrderDate(LocalDate.parse(supplierOrderDto.getOrderDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")).format(formatters));
            supplierOrderModel.setDeliveryDate(LocalDate.parse(supplierOrderDto.getDeliveryDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")).format(formatters));
            supplierOrderModel.setStatus(supplierOrderDto.getStatus());
            supplierOrderModels.add(supplierOrderModel);
        }



        supplierOrderModels.sort(Comparator.comparingLong(SupplierOrderModel::getID));
        table.getItems().addAll(supplierOrderModels);
        table.autosizeColumnsOnInitialization();
    }
}
