package fr.codesbusters.solidstock.controller.estimate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.estimates.GetEstimateDto;
import fr.codesbusters.solidstock.dto.estimates.GetEstimateRowDto;
import fr.codesbusters.solidstock.dto.estimates.PostEstimateDto;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.model.estimate.EstimateRowModel;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class EstimateEditController extends DefaultShowController implements Initializable, CustomerSelectorListener {

    @FXML
    public StackPane stackPane;
    @FXML
    public MFXTextField estimateId;
    @FXML
    public MFXTextField estimateName;
    @FXML
    public MFXTextField estimateDescription;
    @FXML
    public MFXTextField customerId;
    @FXML
    public Label customerName;
    @FXML
    private MFXTableView<EstimateRowModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estimateId.setText(String.valueOf(getId()));
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/estimate/" + getId(), String.class, true, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                GetEstimateDto estimate = objectMapper.readValue(responseEntity.getBody(), new TypeReference<GetEstimateDto>() {
                });
                estimateName.setText(estimate.getName());
                estimateDescription.setText(estimate.getDescription());
                customerId.setText(String.valueOf(estimate.getId()));
                if (estimate.getCustomer().getCompanyName() != null && !estimate.getCustomer().getCompanyName().isEmpty()) {
                    customerName.setText(estimate.getCustomer().getCompanyName());
                } else {
                    customerName.setText(estimate.getCustomer().getFirstName() + " " + estimate.getCustomer().getLastName());
                }
            } catch (Exception e) {
                log.error("Error while parsing estimate", e);
            }
        }

        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    private void setupTable() {
        MFXTableColumn<EstimateRowModel> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(EstimateRowModel::getID));
        MFXTableColumn<EstimateRowModel> productColumn = new MFXTableColumn<>("Nom du produit", true, Comparator.comparing(EstimateRowModel::getProductName));
        MFXTableColumn<EstimateRowModel> quantityColumn = new MFXTableColumn<>("Quantité", true, Comparator.comparing(EstimateRowModel::getQuantity));
        MFXTableColumn<EstimateRowModel> priceColumn = new MFXTableColumn<>("Prix unitaire", true, Comparator.comparing(EstimateRowModel::getUnitPrice));
        MFXTableColumn<EstimateRowModel> totalColumn = new MFXTableColumn<>("Total HT", true, Comparator.comparing(EstimateRowModel::getTotalHT));
        MFXTableColumn<EstimateRowModel> vatColumn = new MFXTableColumn<>("TVA", true, Comparator.comparing(EstimateRowModel::getVat));
        MFXTableColumn<EstimateRowModel> totalVatColumn = new MFXTableColumn<>("Total TTC", true, Comparator.comparing(EstimateRowModel::getTotalTTC));

        productColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateRowModel::getProductName));
        quantityColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateRowModel::getQuantity));
        priceColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateRowModel::getUnitPrice));
        totalColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateRowModel::getTotalHT));
        vatColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateRowModel::getVat));
        totalVatColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(EstimateRowModel::getTotalTTC));

        table.getTableColumns().addAll(productColumn, quantityColumn, priceColumn, totalColumn, vatColumn, totalVatColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("ID", EstimateRowModel::getID),
                new StringFilter<>("Nom du produit", EstimateRowModel::getProductName),
                new DoubleFilter<>("Quantité", EstimateRowModel::getQuantity),
                new DoubleFilter<>("Prix unitaire", EstimateRowModel::getUnitPrice),
                new DoubleFilter<>("Total HT", EstimateRowModel::getTotalHT),
                new DoubleFilter<>("TVA", EstimateRowModel::getVat),
                new DoubleFilter<>("Total TTC", EstimateRowModel::getTotalTTC)
        );

        reloadEstimateRow();
    }

    @FXML
    public void editEstimate() throws NumberFormatException {

        PostEstimateDto estimate = new PostEstimateDto();
        estimate.setName(estimateName.getText());
        estimate.setDescription(estimateDescription.getText());
        estimate.setCustomerId(Integer.parseInt(customerId.getText()));

        if (estimate.getName().isEmpty() || estimate.getDescription().isEmpty() || estimate.getCustomerId() == 0) {
            openDialog(stackPane.getScene(), "Veuillez remplir tous les champs.", DialogType.ERROR, 0);
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendPutRequest("/estimate/" + getId(), estimate, String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            cancel();
            openDialog(stackPane.getScene(), "Devis modifié avec succès.", DialogType.INFORMATION, 0);

        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la modification du devis.", DialogType.ERROR, 0);
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
    public void reloadEstimateRow() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/estimate/" + getId() + "/row/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetEstimateRowDto> estimateList = null;
        try {
            estimateList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing estimate list", e);
        }

        ObservableList<EstimateRowModel> estimateRowsModels = FXCollections.observableArrayList();
        assert estimateList != null;
        for (GetEstimateRowDto estimateRowDto : estimateList) {
            EstimateRowModel estimateRowModel = new EstimateRowModel();
            estimateRowModel.setID(estimateRowDto.getId());
            estimateRowModel.setProductName(estimateRowDto.getProduct().getName());
            estimateRowModel.setQuantity(estimateRowDto.getQuantity());
            estimateRowModel.setUnitPrice(estimateRowDto.getSellPrice());
            estimateRowModel.setTotalHT(estimateRowDto.getQuantity() * estimateRowDto.getSellPrice());
            estimateRowModel.setVat(estimateRowDto.getProduct().getVat().getRate());
            estimateRowModel.setTotalTTC(estimateRowDto.getQuantity() * estimateRowDto.getSellPrice() * (1 + estimateRowDto.getProduct().getVat().getRate() / 100));

            estimateRowsModels.add(estimateRowModel);
        }

        table.getItems().addAll(estimateRowsModels);
    }

    @FXML
    public void addEstimateRow(ActionEvent actionEvent) {
        openPopUp("/estimates/estimatesRows/addRowPopup.fxml", stackPane.getScene(), "Ajouter une ligne au devis");
        reloadEstimateRow();
    }

    @FXML
    public void editEstimateRow(ActionEvent actionEvent) {
        EstimateRowModel estimateRowModel = table.getSelectionModel().getSelectedValue();
        if (estimateRowModel == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une ligne du devis.", DialogType.ERROR, 0);
            return;
        }


        setIntermediaryId(estimateRowModel.getID());
        openPopUp("/estimates/estimatesRows/editRowPopup.fxml", stackPane.getScene(), "Modifier une ligne du devis");
        reloadEstimateRow();
    }

    @FXML
    public void removeEstimateRow(ActionEvent actionEvent) {
        EstimateRowModel estimateRowModel = table.getSelectionModel().getSelectedValue();

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/estimate/" + getId() + "/row/" + estimateRowModel.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Ligne du devis supprimée avec succès.", DialogType.INFORMATION, 0);
            reloadEstimateRow();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression de la ligne du devis.", DialogType.ERROR, 0);
        }

    }
}
