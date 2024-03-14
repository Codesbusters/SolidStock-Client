package fr.codesbusters.solidstock.controller.selectors;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.model.CustomerModel;
import fr.codesbusters.solidstock.model.SolidStockDataIntegration;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
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
public class CustomerSelectorController extends DefaultShowController implements Initializable {

    @FXML
    AnchorPane anchorPane;

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
        MFXTableColumn<CustomerModel> corporationNameColumn = new MFXTableColumn<>("Entreprise", true, Comparator.comparing(CustomerModel::getCorporateName));

        idColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(CustomerModel::getID));
        nameColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(CustomerModel::getName));
        addressColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(CustomerModel::getAddress));
        corporationNameColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(CustomerModel::getCorporateName));

        table.getTableColumns().addAll(idColumn, nameColumn, addressColumn, corporationNameColumn);
        table.getFilters().addAll(
                new StringFilter<>("Réf.", CustomerModel::getName),
                new StringFilter<>("Libelle", CustomerModel::getName),
                new StringFilter<>("Adresse", CustomerModel::getAddress),
                new StringFilter<>("Entreprise", CustomerModel::getCorporateName)
        );
        table.setItems(SolidStockDataIntegration.customers);
    }

    @FXML
    private void submitAction(ActionEvent event) {

        // Obtenez l'ID sélectionné de la table
        CustomerModel selectedValue = table.getSelectionModel().getSelectedValue();

        // Vérifiez si l'ID est nul
        if (selectedValue != null) {
            String customerId = String.valueOf(selectedValue.getID());
            String customerName = selectedValue.getName();
            if (listener != null) {
                listener.processCustomerContent(customerId + " - " + customerName);
            } else {
                openDialog(table.getScene(), "Une erreur est survenue, veuillez réessayer", DialogType.ERROR, 0);
            }

            // Fermez la fenêtre pop-up
            parentStage.close();
        } else {
            openDialog(table.getScene(), "Veuillez sélectionner un client", DialogType.ERROR, 0);
        }
    }

    @FXML
    public void addCustomer() {
        openPopUp("customers/addPopup.fxml", anchorPane.getScene(), "Ajouter un client");
    }

    @FXML
    public void showCustomer() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(anchorPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR, 0);
            return;
        }

        setId(customer.getID());

        openPopUp("customers/showPopup.fxml", anchorPane.getScene(), "Détails du client");
    }

    @FXML
    public void editCustomer() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(anchorPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR, 0);
            return;
        }

        setId(customer.getID());

        openPopUp("customers/editPopup.fxml", anchorPane.getScene(), "Modifier le client");
    }

    @FXML
    public void deleteCustomer() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(anchorPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR, 0);
            return;
        }

        openDialog(anchorPane.getScene(), "Êtes-vous sûr de vouloir supprimer le client " + customer.getName() + " ?", DialogType.CONFIRMATION, 0);
    }

    @FXML
    private void cancel() {
        parentStage.close();
    }
}
