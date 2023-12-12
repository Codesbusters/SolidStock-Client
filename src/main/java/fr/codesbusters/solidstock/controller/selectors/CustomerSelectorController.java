package fr.codesbusters.solidstock.controller.selectors;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.model.CustomerModel;
import fr.codesbusters.solidstock.model.SolidStockModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.util.Comparator;

public class CustomerSelectorController extends DefaultController implements Initializable {

    @FXML
    MFXTableView<CustomerModel> table;

    private Stage parentStage;

    private CustomerSelectorListener listener;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    public void setStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setListener(CustomerSelectorListener listener) {
        this.listener = listener;
    }

    public void setupTable() {
        MFXTableColumn<CustomerModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(CustomerModel::getID));
        MFXTableColumn<CustomerModel> nameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(CustomerModel::getName));
        MFXTableColumn<CustomerModel> addressColumn = new MFXTableColumn<>("Adresse", true, Comparator.comparing(CustomerModel::getAddress));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getID));
        nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getName));
        addressColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getAddress));

        table.getTableColumns().addAll(idColumn, nameColumn, addressColumn);
        table.getFilters().addAll(
                new StringFilter<>("Réf.", CustomerModel::getName),
                new StringFilter<>("Libelle", CustomerModel::getName),
                new StringFilter<>("Adresse", CustomerModel::getAddress)
        );
        table.setItems(SolidStockModel.customers);
    }

    @FXML
    private void submitAction(ActionEvent event) {

        // Obtenez l'ID sélectionné de la table
        CustomerModel selectedValue = table.getSelectionModel().getSelectedValue();

        // Vérifiez si l'ID est nul
        if (selectedValue != null) {
            String customerId = String.valueOf(selectedValue.getID());
            if (listener != null) {
                listener.processCustomerContent(customerId);
            } else {
                openDialog(table.getScene(), "Une erreur est survenue, veuillez réessayer", DialogType.ERROR);
            }

            // Fermez la fenêtre pop-up
            parentStage.close();
        } else {
            openDialog(table.getScene(), "Veuillez sélectionner un client", DialogType.ERROR);
        }
    }

    @FXML
    private void cancel() {
        parentStage.close();
    }
}
