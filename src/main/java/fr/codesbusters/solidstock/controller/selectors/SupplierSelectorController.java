package fr.codesbusters.solidstock.controller.selectors;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.listener.SupplierSelectorListener;
import fr.codesbusters.solidstock.model.SolidStockModel;
import fr.codesbusters.solidstock.model.SupplierModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.Comparator;

@Slf4j
@Controller
public class SupplierSelectorController extends DefaultController implements Initializable {

    @FXML
    MFXTableView<SupplierModel> table;

    private Stage parentStage;

    private SupplierSelectorListener listener;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    public void setStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setListener(SupplierSelectorListener listener) {
        this.listener = listener;
    }

    private void setupTable() {
        MFXTableColumn<SupplierModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(SupplierModel::getID));
        MFXTableColumn<SupplierModel> nameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(SupplierModel::getName));
        MFXTableColumn<SupplierModel> addressColumn = new MFXTableColumn<>("Adresse", true, Comparator.comparing(SupplierModel::getAddress));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getID));
        nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getName));
        addressColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getAddress));

        table.getTableColumns().addAll(idColumn, nameColumn, addressColumn);
        table.getFilters().addAll(
                new StringFilter<>("Réf.", SupplierModel::getName),
                new StringFilter<>("Libelle", SupplierModel::getName),
                new StringFilter<>("Adresse", SupplierModel::getAddress)
        );
        table.setItems(SolidStockModel.suppliers);


    }

    @FXML
    private void submitAction(ActionEvent event) {

        // Obtenez l'ID sélectionné de la table
        SupplierModel selectedValue = table.getSelectionModel().getSelectedValue();

        // Vérifiez si l'ID est null
        if (selectedValue != null) {
            String supplierId = String.valueOf(selectedValue.getID());
            if (listener != null) {
                listener.processSupplierContent(supplierId);
            } else {
                openDialog(table.getScene(), "Une erreur est survenue, veuillez réessayer.", DialogType.ERROR);
            }

            // Fermez la fenêtre pop-up
            parentStage.close();
        } else {
            openDialog(table.getScene(), "Veuillez séléctionner un fournisseur", DialogType.ERROR);
        }
    }

    @FXML
    private void cancel() {
        parentStage.close();
    }
}
