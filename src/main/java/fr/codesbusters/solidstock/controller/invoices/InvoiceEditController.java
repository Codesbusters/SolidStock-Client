package fr.codesbusters.solidstock.controller.invoices;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.invoice.GetInvoiceDto;
import fr.codesbusters.solidstock.dto.invoice.GetInvoiceRowDto;
import fr.codesbusters.solidstock.dto.invoice.PostInvoiceDto;
import fr.codesbusters.solidstock.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.model.ProductModel;
import fr.codesbusters.solidstock.model.invoice.InvoiceModel;
import fr.codesbusters.solidstock.model.invoice.InvoiceRowModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class InvoiceEditController extends DefaultShowController implements Initializable, CustomerSelectorListener {

    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField invoiceId;
    @FXML
    public MFXTextField invoiceName;
    @FXML
    public MFXTextField invoiceDescription;
    @FXML
    public MFXTextField customerId;
    @FXML
    public Label customerName;
    @FXML
    private MFXTableView<InvoiceRowModel> table;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invoiceId.setText(String.valueOf(getId()));
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/invoice/" + getId(), String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                GetInvoiceDto invoice = objectMapper.readValue(responseEntity.getBody(), new TypeReference<GetInvoiceDto>() {
                });
                invoiceName.setText(invoice.getName());
                invoiceDescription.setText(invoice.getDescription());
                customerId.setText(String.valueOf(invoice.getId()));
                if (invoice.getCustomer().getCompanyName() != null && !invoice.getCustomer().getCompanyName().isEmpty()) {
                    customerName.setText(invoice.getCustomer().getCompanyName());
                } else {
                    customerName.setText(invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName());
                }
            } catch (Exception e) {
                log.error("Error while parsing invoice", e);
            }
        }

        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    private void setupTable() {
        MFXTableColumn<InvoiceRowModel> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(InvoiceRowModel::getID));
        MFXTableColumn<InvoiceRowModel> productColumn = new MFXTableColumn<>("Nom du produit", true, Comparator.comparing(InvoiceRowModel::getProductName));
        MFXTableColumn<InvoiceRowModel> quantityColumn = new MFXTableColumn<>("Quantité", true, Comparator.comparing(InvoiceRowModel::getQuantity));
        MFXTableColumn<InvoiceRowModel> priceColumn = new MFXTableColumn<>("Prix unitaire", true, Comparator.comparing(InvoiceRowModel::getUnitPrice));
        MFXTableColumn<InvoiceRowModel> totalColumn = new MFXTableColumn<>("Total HT", true, Comparator.comparing(InvoiceRowModel::getTotalHT));
        MFXTableColumn<InvoiceRowModel> vatColumn = new MFXTableColumn<>("TVA", true, Comparator.comparing(InvoiceRowModel::getVat));
        MFXTableColumn<InvoiceRowModel> totalVatColumn = new MFXTableColumn<>("Total TTC", true, Comparator.comparing(InvoiceRowModel::getTotalTTC));

        productColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceRowModel::getProductName));
        quantityColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceRowModel::getQuantity));
        priceColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceRowModel::getUnitPrice));
        totalColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceRowModel::getTotalHT));
        vatColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceRowModel::getVat));
        totalVatColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceRowModel::getTotalTTC));

        table.getTableColumns().addAll(productColumn, quantityColumn, priceColumn, totalColumn, vatColumn, totalVatColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("ID", InvoiceRowModel::getID),
                new StringFilter<>("Nom du produit", InvoiceRowModel::getProductName),
                new DoubleFilter<>("Quantité", InvoiceRowModel::getQuantity),
                new DoubleFilter<>("Prix unitaire", InvoiceRowModel::getUnitPrice),
                new DoubleFilter<>("Total HT", InvoiceRowModel::getTotalHT),
                new DoubleFilter<>("TVA", InvoiceRowModel::getVat),
                new DoubleFilter<>("Total TTC", InvoiceRowModel::getTotalTTC)
        );

        reloadInvoiceRow();
    }

    @FXML
    public void editInvoice() throws NumberFormatException {

        PostInvoiceDto invoice = new PostInvoiceDto();
        invoice.setName(invoiceName.getText());
        invoice.setDescription(invoiceDescription.getText());
        invoice.setCustomerId(Integer.parseInt(customerId.getText()));

        if (invoice.getName().isEmpty() || invoice.getDescription().isEmpty() || invoice.getCustomerId() == 0) {
            openDialog(stackPane.getScene(), "Veuillez remplir tous les champs.", DialogType.ERROR, 0);
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPutRequest("/invoice/"+getId(), invoice, String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
            openDialog(stackPane.getScene(), "Facture modifiée avec succès.", DialogType.INFORMATION, 0);

        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la modfication de la facture.", DialogType.ERROR, 0);
        }

    }

    @FXML
    public void selectCustomer() {
        openCustomerSelector(stackPane.getScene(), this);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void processCustomerContent(String customerContent) {
        String[] customer = customerContent.split(" - ");
        customerId.setText(customer[0]);
        customerName.setText(customer[1]);
    }


    @FXML
    public void reloadInvoiceRow() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/invoice/" + getId() + "/row/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetInvoiceRowDto> invoiceList = null;
        try {
            invoiceList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing invoice list", e);
        }

        ObservableList<InvoiceRowModel> invoiceRowsModels = FXCollections.observableArrayList();
        assert invoiceList != null;
        for (GetInvoiceRowDto invoiceDto : invoiceList) {
            InvoiceRowModel invoiceModel = new InvoiceRowModel();
            invoiceModel.setID(invoiceDto.getId());
            invoiceModel.setProductName(invoiceDto.getProduct().getName());
            invoiceModel.setQuantity(invoiceDto.getQuantity());
            invoiceModel.setUnitPrice(invoiceDto.getSellPrice());
            invoiceModel.setTotalHT(invoiceDto.getQuantity() * invoiceDto.getSellPrice());
            invoiceModel.setVat(invoiceDto.getProduct().getVat().getRate());
            invoiceModel.setTotalTTC(invoiceDto.getQuantity() * invoiceDto.getSellPrice() * (1 + invoiceDto.getProduct().getVat().getRate() / 100));

            invoiceRowsModels.add(invoiceModel);
        }

        table.getItems().addAll(invoiceRowsModels);
    }

    @FXML
    public void addInvoiceRow(ActionEvent actionEvent) {
        openPopUp("/invoices/invoicesRows/addRowPopup.fxml", stackPane.getScene(),"Ajouter une ligne de facture");
        reloadInvoiceRow();

    }

    @FXML
    public void editInvoiceRow(ActionEvent actionEvent) {
        InvoiceRowModel invoiceRowModel =  table.getSelectionModel().getSelectedValues().getFirst();
        if (invoiceRowModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une facture.", DialogType.ERROR, 0);
            return;
        }


        setIntermediaryId(invoiceRowModel.getID());
        openPopUp("/invoices/invoicesRows/editRowPopup.fxml", stackPane.getScene(),"Modifier une ligne de facture");
        reloadInvoiceRow();
    }

    @FXML
    public void removeInvoiceRow(ActionEvent actionEvent) {
       InvoiceRowModel invoiceRowModel =  table.getSelectionModel().getSelectedValues().getFirst();

         RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/invoice/" + getId() + "/row/" + invoiceRowModel.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Ligne de facture supprimée avec succès.", DialogType.INFORMATION, 0);
            reloadInvoiceRow();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression de la ligne de facture.", DialogType.ERROR, 0);
        }

    }
}