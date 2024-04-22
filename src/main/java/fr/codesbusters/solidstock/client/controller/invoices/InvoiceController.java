package fr.codesbusters.solidstock.client.controller.invoices;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.invoice.GetInvoiceDto;
import fr.codesbusters.solidstock.client.model.invoice.InvoiceModel;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
public class InvoiceController extends DefaultShowController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<InvoiceModel> table;

    @FXML
    private MFXButton modifyButton;

    @FXML
    private MFXButton deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    @FXML
    public void addInvoice() {
        openPopUp("invoices" +
                "/addPopup.fxml", stackPane.getScene(), "Créer une Facture");
        reloadInvoice();
    }

    private void setupTable() {
        MFXTableColumn<InvoiceModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(InvoiceModel::getID));
        MFXTableColumn<InvoiceModel> dateColumn = new MFXTableColumn<>("Date", true, Comparator.comparing(InvoiceModel::getDate));
        MFXTableColumn<InvoiceModel> NameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(InvoiceModel::getName));
        MFXTableColumn<InvoiceModel> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(InvoiceModel::getDescription));
        MFXTableColumn<InvoiceModel> customerNameColumn = new MFXTableColumn<>("Client", true, Comparator.comparing(InvoiceModel::getCustomerName));
        MFXTableColumn<InvoiceModel> totalHtColumn = new MFXTableColumn<>("Total HT", true, Comparator.comparing(InvoiceModel::getTotalHt));
        MFXTableColumn<InvoiceModel> totalTtcColumn = new MFXTableColumn<>("Total TTC", true, Comparator.comparing(InvoiceModel::getTotalTtc));


        idColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceModel::getID) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        NameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceModel::getName) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        descriptionColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceModel::getDescription) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        customerNameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceModel::getCustomerName) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        dateColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceModel::getDate) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        totalHtColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceModel::getTotalHt) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        totalTtcColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(InvoiceModel::getTotalTtc) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });


        table.getTableColumns().addAll(idColumn, dateColumn, NameColumn, descriptionColumn, customerNameColumn,  totalHtColumn, totalTtcColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", InvoiceModel::getID),
                new StringFilter<>("Date", InvoiceModel::getDate),
                new StringFilter<>("Nom", InvoiceModel::getName),
                new StringFilter<>("Description", InvoiceModel::getDescription),
                new StringFilter<>("Client", InvoiceModel::getCustomerName),
                new DoubleFilter<>("Total HT", InvoiceModel::getTotalHt),
                new DoubleFilter<>("Total TTC", InvoiceModel::getTotalTtc)
        );

        reloadInvoice();
    }


    @FXML
    public void editInvoice() {
        InvoiceModel invoiceModel = table.getSelectionModel().getSelectedValue();

        if (invoiceModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une facture", DialogType.ERROR, 0);
            return;
        }

        setId(invoiceModel.getID());

        openPopUp("invoices/editPopup.fxml", stackPane.getScene(), "Modification de la facture");
        reloadInvoice();

    }

    @FXML
    public void removeInvoice() {
        InvoiceModel invoiceModel = table.getSelectionModel().getSelectedValue();

        if (invoiceModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR, 0);
            return;
        }
        boolean result = openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer la facture " + invoiceModel.getID() + " ?", DialogType.CONFIRMATION, 0);
        if (!result) {
            return;
        }
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/invoice/" + invoiceModel.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Facture " + invoiceModel.getID() + " supprimée avec succès", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression de la facture", DialogType.ERROR, 0);
        }
        reloadInvoice();
    }


    @FXML
    public void reloadInvoice() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/invoice/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetInvoiceDto> invoiceList = null;
        try {
            invoiceList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing invoice list", e);
        }

        ObservableList<InvoiceModel> invoicesModels = FXCollections.observableArrayList();
        assert invoiceList != null;
        for (GetInvoiceDto invoiceDto : invoiceList) {
            InvoiceModel invoiceModel = new InvoiceModel();
            invoiceModel.setID(invoiceDto.getId());
            invoiceModel.setName(invoiceDto.getName());
            invoiceModel.setDescription(invoiceDto.getDescription());

            String customerName = invoiceDto.getCustomer().getCompanyName();
            if (customerName == null) {
                customerName = invoiceDto.getCustomer().getFirstName() + " " + invoiceDto.getCustomer().getLastName();
            }
            invoiceModel.setCustomerName(customerName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime date = LocalDateTime.parse(invoiceDto.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME);
            invoiceModel.setDate(date.format(formatter));
            invoiceModel.setTotalHt(invoiceDto.getTotalHt());
            invoiceModel.setTotalTtc(invoiceDto.getTotalTtc());

            invoicesModels.add(invoiceModel);
        }

        table.getItems().addAll(invoicesModels);
    }

    @FXML
    public void downloadInvoice(ActionEvent actionEvent) throws IOException {
        InvoiceModel invoiceModel = table.getSelectionModel().getSelectedValue();

        if (invoiceModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une facture", DialogType.ERROR, 0);
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        File pdfFile = requestAPI.downloadFile("/invoice/" + invoiceModel.getID() + "/pdf", true, true);

        if (pdfFile != null && pdfFile.exists()) {
            openFile(pdfFile);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors du téléchargement de la facture", DialogType.ERROR, 0);
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
