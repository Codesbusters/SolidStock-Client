package fr.codesbusters.solidstock.controller.customers;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.model.CustomerModel;
import fr.codesbusters.solidstock.model.SolidStockModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class CustomerController extends DefaultShowController implements Initializable {
    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<CustomerModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }


    @FXML
    public void addCustomer() {
        openPopUp("customers/addPopup.fxml", stackPane.getScene(), "Ajouter un client");
    }


    private void setupTable() {
        MFXTableColumn<CustomerModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(CustomerModel::getID));
        MFXTableColumn<CustomerModel> nameColumn = new MFXTableColumn<>("Libelle", true, Comparator.comparing(CustomerModel::getName));
        MFXTableColumn<CustomerModel> thirdPartyColumn = new MFXTableColumn<>("Tier", true, Comparator.comparing(CustomerModel::getThirdPartyId));
        MFXTableColumn<CustomerModel> addressColumn = new MFXTableColumn<>("Adresse", true, Comparator.comparing(CustomerModel::getAddress));
        MFXTableColumn<CustomerModel> corporationColumn = new MFXTableColumn<>("Professionnel", true, Comparator.comparing(CustomerModel::getCorporation));
        MFXTableColumn<CustomerModel> corporationNameColumn = new MFXTableColumn<>("Nom de l'entreprise", true, Comparator.comparing(CustomerModel::getCorporateName));
        MFXTableColumn<CustomerModel> sirenColumn = new MFXTableColumn<>("Siren", true, Comparator.comparing(CustomerModel::getSiren));
        MFXTableColumn<CustomerModel> siretColumn = new MFXTableColumn<>("Siret", true, Comparator.comparing(CustomerModel::getSiret));
        MFXTableColumn<CustomerModel> ribColumn = new MFXTableColumn<>("RIB", true, Comparator.comparing(CustomerModel::getRib));
        MFXTableColumn<CustomerModel> rcsColumn = new MFXTableColumn<>("RCS", true, Comparator.comparing(CustomerModel::getRcs));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getID));
        nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getName));
        thirdPartyColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getThirdPartyId));
        addressColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getAddress) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});

        corporationColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getCorporation) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});

        corporationNameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getCorporateName) {{
            setAlignment(Pos.CENTER_RIGHT);
            setGraphicTextGap(5);
        }});

        sirenColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getSiren) {{
            setAlignment(Pos.CENTER_RIGHT);
            setGraphicTextGap(5);
        }});

        siretColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getSiret) {{
            setAlignment(Pos.CENTER_RIGHT);
            setGraphicTextGap(5);
        }});

        ribColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getRib) {{
            setAlignment(Pos.CENTER_RIGHT);
            setGraphicTextGap(5);
        }});

        rcsColumn.setRowCellFactory(product -> new MFXTableRowCell<>(CustomerModel::getRcs) {{
            setAlignment(Pos.CENTER_RIGHT);
            setGraphicTextGap(5);
        }});

        table.getTableColumns().addAll(idColumn, nameColumn, thirdPartyColumn, addressColumn, corporationColumn, corporationNameColumn, sirenColumn, siretColumn, ribColumn, rcsColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", CustomerModel::getID),
                new StringFilter<>("Libelle", CustomerModel::getName),
                new IntegerFilter<>("Tier", CustomerModel::getThirdPartyId)
        );
        table.setItems(SolidStockModel.customers);


    }

    @FXML
    public void showCustomer() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR);
            return;
        }

        setId(customer.getID());

        openPopUp("customers/showPopup.fxml", stackPane.getScene(), "Détails du client");

    }

    @FXML
    public void editCustomer() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR);
            return;
        }

        setId(customer.getID());

        openPopUp("customers/editPopup.fxml", stackPane.getScene(), "Modification du client");

    }

    @FXML
    public void removeCustomer() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR);
            return;
        }

        openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer le client " + customer.getName() + " ?", DialogType.CONFIRMATION);

    }


    //on double click on a row
    @FXML
    public void showCustomerDetails() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR);
            return;
        }
        openDialog(stackPane.getScene(), table.getSelectionModel().getSelectedValue().getName(), DialogType.CONFIRMATION);
    }
}
