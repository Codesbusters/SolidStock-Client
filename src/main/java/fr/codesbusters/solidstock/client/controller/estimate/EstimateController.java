package fr.codesbusters.solidstock.client.controller.estimate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.estimates.GetEstimateDto;
import fr.codesbusters.solidstock.client.model.estimate.EstimateModel;
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
public class EstimateController extends DefaultShowController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<EstimateModel> table;

    @FXML
    private MFXButton modifyButton;

    @FXML
    private MFXButton deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
    }

    @FXML
    public void addEstimate() {
        openPopUp("estimates" + "/addPopup.fxml", stackPane.getScene(), "Créer un devis");
        reloadEstimate();
    }

    private void setupTable() {
        MFXTableColumn<EstimateModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(EstimateModel::getID));
        MFXTableColumn<EstimateModel> dateColumn = new MFXTableColumn<>("Date", true, Comparator.comparing(EstimateModel::getDate));
        MFXTableColumn<EstimateModel> NameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(EstimateModel::getName));
        MFXTableColumn<EstimateModel> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(EstimateModel::getDescription));
        MFXTableColumn<EstimateModel> customerNameColumn = new MFXTableColumn<>("Client", true, Comparator.comparing(EstimateModel::getCustomerName));
        MFXTableColumn<EstimateModel> totalHtColumn = new MFXTableColumn<>("Total HT", true, Comparator.comparing(EstimateModel::getTotalHt));
        MFXTableColumn<EstimateModel> totalTtcColumn = new MFXTableColumn<>("Total TTC", true, Comparator.comparing(EstimateModel::getTotalTtc));

        idColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateModel::getID) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        NameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateModel::getName) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        descriptionColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateModel::getDescription) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        customerNameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateModel::getCustomerName) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        dateColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateModel::getDate) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        totalHtColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateModel::getTotalHt) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        totalTtcColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateModel::getTotalTtc) {
            {
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        table.getTableColumns().addAll(idColumn, NameColumn, descriptionColumn, customerNameColumn, dateColumn, totalHtColumn, totalTtcColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", EstimateModel::getID),
                new StringFilter<>("Nom", EstimateModel::getName),
                new StringFilter<>("Description", EstimateModel::getDescription),
                new StringFilter<>("Client", EstimateModel::getCustomerName),
                new StringFilter<>("Date", EstimateModel::getDate),
                new DoubleFilter<>("Total HT", EstimateModel::getTotalHt),
                new DoubleFilter<>("Total TTC", EstimateModel::getTotalTtc)
        );

        reloadEstimate();
    }

    @FXML
    public void editEstimate() {
        EstimateModel estimateModel = table.getSelectionModel().getSelectedValue();

        if (estimateModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un devis", DialogType.ERROR, 0);
            return;
        }

        setId(estimateModel.getID());

        openPopUp("estimates/editPopup.fxml", stackPane.getScene(), "Modification du devis");
        reloadEstimate();

    }

    @FXML
    public void removeEstimate() {
        EstimateModel estimateModel = table.getSelectionModel().getSelectedValue();

        if (estimateModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un devis", DialogType.ERROR, 0);
            return;
        }
        boolean result = openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer le devis " + estimateModel.getID() + " ?", DialogType.CONFIRMATION, 0);
        if (!result) {
            return;
        }
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/estimate/" + estimateModel.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Devis " + estimateModel.getID() + " supprimé avec succès", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression du devis", DialogType.ERROR, 0);
        }
        reloadEstimate();
    }

    @FXML
    public void reloadEstimate() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/estimate/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetEstimateDto> estimateList = null;
        try {
            estimateList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing estimate list", e);
        }

        ObservableList<EstimateModel> estimatesModels = FXCollections.observableArrayList();
        assert estimateList != null;
        for (GetEstimateDto estimateDto : estimateList) {
            EstimateModel estimateModel = new EstimateModel();
            estimateModel.setID(estimateDto.getId());
            estimateModel.setName(estimateDto.getName());
            estimateModel.setDescription(estimateDto.getDescription());

            String customerName = estimateDto.getCustomer().getCompanyName();
            if (customerName == null) {
                customerName = estimateDto.getCustomer().getFirstName() + " " + estimateDto.getCustomer().getLastName();
            }
            estimateModel.setCustomerName(customerName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime date = LocalDateTime.parse(estimateDto.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME);
            estimateModel.setDate(date.format(formatter));
            estimateModel.setTotalHt(estimateDto.getTotalHt());
            estimateModel.setTotalTtc(estimateDto.getTotalTtc());

            estimatesModels.add(estimateModel);
        }

        table.getItems().addAll(estimatesModels);
        table.autosizeColumnsOnInitialization();
    }

    @FXML
    public void downloadEstimate() throws IOException {
        EstimateModel estimateModel = table.getSelectionModel().getSelectedValue();

        if (estimateModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un devis", DialogType.ERROR, 0);
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        File pdfFile = requestAPI.downloadFile("/estimate/" + estimateModel.getID() + "/pdf", true, true);

        if (pdfFile != null && pdfFile.exists()) {
            openFile(pdfFile);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors du téléchargement du devis", DialogType.ERROR, 0);
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
