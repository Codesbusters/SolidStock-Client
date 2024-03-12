package fr.codesbusters.solidstock.controller.selectors;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.listener.EstimateSelectorListener;
import fr.codesbusters.solidstock.model.EstimateModel;
import fr.codesbusters.solidstock.model.SolidStockModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.Comparator;

@Slf4j
@Controller
public class EstimateSelectorController extends DefaultShowController implements Initializable {

    @FXML
    AnchorPane anchorPane;
    @FXML
    MFXTableView<EstimateModel> table;

    private Stage parentStage;

    private EstimateSelectorListener listener;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    public void setStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setListener(EstimateSelectorListener listener) {
        this.listener = listener;
    }

    public void setupTable() {
        MFXTableColumn<EstimateModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(EstimateModel::getID));
        MFXTableColumn<EstimateModel> subjectColumn = new MFXTableColumn<>("Sujet", true, Comparator.comparing(EstimateModel::getSubject));
        MFXTableColumn<EstimateModel> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(EstimateModel::getDescription));
        MFXTableColumn<EstimateModel> customerIdColumn = new MFXTableColumn<>("ID Client", true, Comparator.comparing(EstimateModel::getCustomerId));
        MFXTableColumn<EstimateModel> customersubjectColumn = new MFXTableColumn<>("Nom Client", true, Comparator.comparing(EstimateModel::getCustomerName));
        MFXTableColumn<EstimateModel> dueDateColumn = new MFXTableColumn<>("Date de livraison", true, Comparator.comparing(EstimateModel::getDueDate));

        idColumn.setRowCellFactory(estimate -> new MFXTableRowCell<>(EstimateModel::getID));
        subjectColumn.setRowCellFactory(estimate -> new MFXTableRowCell<>(EstimateModel::getSubject));
        descriptionColumn.setRowCellFactory(estimate -> new MFXTableRowCell<>(EstimateModel::getDescription));
        customerIdColumn.setRowCellFactory(estimate -> new MFXTableRowCell<>(EstimateModel::getCustomerId));
        customersubjectColumn.setRowCellFactory(estimate -> new MFXTableRowCell<>(EstimateModel::getCustomerName));
        dueDateColumn.setRowCellFactory(estimate -> new MFXTableRowCell<>(EstimateModel::getDueDate));

        table.getTableColumns().addAll(idColumn, subjectColumn, descriptionColumn, customerIdColumn, customersubjectColumn, dueDateColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", EstimateModel::getID),
                new StringFilter<>("Sujet", EstimateModel::getSubject),
                new StringFilter<>("Description", EstimateModel::getDescription),
                new IntegerFilter<>("ID Client", EstimateModel::getCustomerId),
                new StringFilter<>("Nom Client", EstimateModel::getCustomerName)
        );
        table.setItems(SolidStockModel.estimates);
    }

    @FXML
    private void submitAction(ActionEvent event) {

        // Obtenez l'ID sélectionné de la table
        EstimateModel selectedValue = table.getSelectionModel().getSelectedValue();

        // Vérifiez si l'ID est nul
        if (selectedValue != null) {
            String estimateId = String.valueOf(selectedValue.getID());
            if (listener != null) {
                listener.processEstimateContent(estimateId);
            } else {
                openDialog(table.getScene(), "Une erreur est survenue, veuillez réessayer", DialogType.ERROR);
            }

            // Fermez la fenêtre pop-up
            parentStage.close();
        } else {
            openDialog(table.getScene(), "Veuillez sélectionner un devis", DialogType.ERROR);
        }
    }

    @FXML
    private void cancel() {
        parentStage.close();
    }
}
