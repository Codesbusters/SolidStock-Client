package fr.codesbusters.solidstock.client.controller.supplierOrders;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.supplierOrder.GetSupplierOrderDto;
import fr.codesbusters.solidstock.client.dto.supplierOrder.GetSupplierOrderRowDto;
import fr.codesbusters.solidstock.client.dto.supplierOrder.PostSupplierOrderDto;
import fr.codesbusters.solidstock.client.listener.SupplierSelectorListener;
import fr.codesbusters.solidstock.client.model.supplierOrder.SupplierOrderModel;
import fr.codesbusters.solidstock.client.model.supplierOrder.SupplierOrderRowModel;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class SupplierOrdersEditController extends DefaultShowController implements Initializable, SupplierSelectorListener {

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public MFXTextField supplierId;
    @FXML
    public Label supplierName;
    @FXML
    public MFXTextField orderNumber;
    @FXML
    public MFXDatePicker orderDate;
    @FXML
    public MFXDatePicker deliveryDate;
    @FXML
    public MFXTextField supplierOrderId;
    @FXML
    public MFXTableView<SupplierOrderRowModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<GetSupplierOrderDto> responseEntity = requestAPI.sendGetRequest("/supplier-order/"+getId(), GetSupplierOrderDto.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {

            ObjectMapper objectMapper = new ObjectMapper();
            GetSupplierOrderDto getSupplierOrderDto = objectMapper.convertValue(responseEntity.getBody(), new TypeReference<GetSupplierOrderDto>() {});
            supplierOrderId.setText(String.valueOf(getId()));
            supplierId.setText(String.valueOf(getSupplierOrderDto.getSupplier().getId()));
            supplierName.setText(getSupplierOrderDto.getSupplier().getCompanyName());
            orderNumber.setText(getSupplierOrderDto.getOrderNumber());
            orderDate.setValue(LocalDate.from(LocalDateTime.parse(getSupplierOrderDto.getOrderDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))));
            deliveryDate.setValue(LocalDate.from(LocalDateTime.parse(getSupplierOrderDto.getDeliveryDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))));

            setupTable();
        } else {
            openDialog(anchorPane.getScene(), "Erreur lors de la récupération de la commande fournisseur", DialogType.ERROR, 0);
        }

    }

    @FXML
    public void removeSupplierOrderRow()  {
        SupplierOrderRowModel supplierOrderRowModel = table.getSelectionModel().getSelectedValue();
        if (supplierOrderRowModel == null) {
            openDialog(anchorPane.getScene(), "Veuillez sélectionner une ligne à supprimer.", DialogType.ERROR, 0);
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/supplier-order/"+getId()+"/row/"+supplierOrderRowModel.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(anchorPane.getScene(), "Ligne supprimée avec succès.", DialogType.INFORMATION, 0);
            reloadSupplierOrder();
        } else {
            openDialog(anchorPane.getScene(), "Erreur lors de la suppression de la ligne", DialogType.ERROR, 0);
        }
    }


    private void setupTable() {
        MFXTableColumn<SupplierOrderRowModel> idColumn = new MFXTableColumn<>("ID");
        MFXTableColumn<SupplierOrderRowModel> productNameColumn = new MFXTableColumn<>("Nom du produit");
        MFXTableColumn<SupplierOrderRowModel> quantityColumn = new MFXTableColumn<>("Quantité");
        MFXTableColumn<SupplierOrderRowModel> unitTypeColumn = new MFXTableColumn<>("Unité");
        MFXTableColumn<SupplierOrderRowModel> unitPriceColumn = new MFXTableColumn<>("Prix d'achat");
        MFXTableColumn<SupplierOrderRowModel> totalColumn = new MFXTableColumn<>("Total");


        idColumn.setRowCellFactory(supplierOrderRowModel -> new MFXTableRowCell<>(SupplierOrderRowModel::getID) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        productNameColumn.setRowCellFactory(supplierOrderRowModel -> new MFXTableRowCell<>(SupplierOrderRowModel::getProductName) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        quantityColumn.setRowCellFactory(supplierOrderRowModel -> new MFXTableRowCell<>(SupplierOrderRowModel::getQuantity) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        unitTypeColumn.setRowCellFactory(supplierOrderRowModel -> new MFXTableRowCell<>(SupplierOrderRowModel::getUnitType) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        unitPriceColumn.setRowCellFactory(supplierOrderRowModel -> new MFXTableRowCell<>(SupplierOrderRowModel::getUnitPrice) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        totalColumn.setRowCellFactory(supplierOrderRowModel -> new MFXTableRowCell<>(SupplierOrderRowModel::getTotal) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        table.getTableColumns().addAll(idColumn, productNameColumn, quantityColumn, unitTypeColumn, unitPriceColumn, totalColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("ID", SupplierOrderRowModel::getID),
                new StringFilter<>("Nom du produit", SupplierOrderRowModel::getProductName),
                new DoubleFilter<>("Quantité", SupplierOrderRowModel::getQuantity),
                new StringFilter<>("Unité", SupplierOrderRowModel::getUnitType),
                new DoubleFilter<>("Prix d'achat", SupplierOrderRowModel::getUnitPrice),
                new DoubleFilter<>("Total", SupplierOrderRowModel::getTotal)
        );



        reloadSupplierOrder();
        table.autosizeColumnsOnInitialization();

    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void selectSupplier() {
            openSupplierSelector(anchorPane.getScene(), this);
    }

    @Override
    public void processSupplierContent(String supplierContent, String supplierName) {
        supplierId.setText(supplierContent);
        this.supplierName.setText(supplierName);
    }

    @FXML
    public void edit() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PostSupplierOrderDto postSupplierOrderDto = new PostSupplierOrderDto();
        postSupplierOrderDto.setSupplierId(supplierId.getText());
        postSupplierOrderDto.setOrderNumber(orderNumber.getText());
        postSupplierOrderDto.setOrderDate(orderDate.getValue().format(formatter));
        postSupplierOrderDto.setDeliveryDate(deliveryDate.getValue().format(formatter));
        postSupplierOrderDto.setStatus("TO_BE_VALIDATED");

        if (postSupplierOrderDto.getSupplierId().isEmpty() || postSupplierOrderDto.getOrderNumber().isEmpty() || postSupplierOrderDto.getOrderDate().isEmpty() || postSupplierOrderDto.getDeliveryDate().isEmpty()) {
            openDialog(anchorPane.getScene(), "Veuillez remplir tous les champs.", DialogType.ERROR, 0);
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPutRequest("/supplier-order/"+ getId(), postSupplierOrderDto, String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
            openDialog(anchorPane.getScene(), "Commande fournisseur modifiée avec succès.", DialogType.INFORMATION, 0);
        } else {
            openDialog(anchorPane.getScene(), "Erreur lors de la création de la commande fournisseur", DialogType.ERROR, 0);
        }
    }

    @FXML
    public void reloadSupplierOrder() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/supplier-order/"+getId()+"/row/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetSupplierOrderRowDto> supplierOrderList;
        try {
            supplierOrderList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ObservableList<SupplierOrderRowModel> supplierOrderModels = FXCollections.observableArrayList();
        assert supplierOrderList != null;
        for (GetSupplierOrderRowDto supplierOrderDto : supplierOrderList) {
            SupplierOrderRowModel supplierOrderModel = new SupplierOrderRowModel();
            supplierOrderModel.setID((int) supplierOrderDto.getId());
            supplierOrderModel.setProductName(supplierOrderDto.getProduct().getName());
            supplierOrderModel.setQuantity(supplierOrderDto.getQuantity());
            supplierOrderModel.setUnitType(supplierOrderDto.getProduct().getQuantityType().getUnit());
            supplierOrderModel.setUnitPrice(supplierOrderDto.getBuyPrice());
            supplierOrderModel.setTotal(supplierOrderDto.getQuantity() * supplierOrderDto.getBuyPrice());
            supplierOrderModels.add(supplierOrderModel);
        }

        supplierOrderModels.sort(Comparator.comparingLong(SupplierOrderRowModel::getID));
        table.getItems().addAll(supplierOrderModels);

    }

    @FXML
    public void addRow() {
        setIntermediaryId(Integer.parseInt(supplierOrderId.getText()));
        openPopUp("supplierOrders/supplierOrdersRow/addRowPopup.fxml", anchorPane.getScene(), "Ajouter une ligne de commande fournisseur");
        reloadSupplierOrder();
    }

}
