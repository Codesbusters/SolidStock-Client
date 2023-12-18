package fr.codesbusters.solidstock.controller.suppliers;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.model.SolidStockModel;
import fr.codesbusters.solidstock.model.SupplierModel;
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
public class SupplierController extends DefaultShowController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<SupplierModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    @FXML
    public void addSupplier() {
        openPopUp("suppliers/addPopup.fxml", stackPane.getScene(), "Ajouter un fournisseur");
    }

    private void setupTable() {
        MFXTableColumn<SupplierModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(SupplierModel::getID));
        MFXTableColumn<SupplierModel> nameColumn = new MFXTableColumn<>("Raison sociale", true, Comparator.comparing(SupplierModel::getName));
        MFXTableColumn<SupplierModel> addressColumn = new MFXTableColumn<>("Adresse", true, Comparator.comparing(SupplierModel::getAddress));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getID));
        nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getName));
        addressColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getAddress) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});

        table.getTableColumns().addAll(idColumn, nameColumn, addressColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", SupplierModel::getID),
                new StringFilter<>("Raison sociale", SupplierModel::getName),
                new StringFilter<>("Adresse", SupplierModel::getAddress)
        );
        table.setItems(SolidStockModel.suppliers);
    }

    @FXML
    public void showSupplier() {
        SupplierModel supplier = table.getSelectionModel().getSelectedValue();

        if (supplier == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR);
            return;
        }

        setId(supplier.getID());

        openPopUp("suppliers/showPopup.fxml", stackPane.getScene(), "Détails du fournisseur");
    }

    @FXML
    public void editSupplier() {
        SupplierModel supplier = table.getSelectionModel().getSelectedValue();

        if (supplier == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR);
            return;
        }

        setId(supplier.getID());

        openPopUp("suppliers/editPopup.fxml", stackPane.getScene(), "Modification du fournisseur");

    }

    @FXML
    public void removeSupplier() {
        SupplierModel supplier = table.getSelectionModel().getSelectedValue();

        if (supplier == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR);
            return;
        }

        openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer le fournisseur " + supplier.getName() + " ?", DialogType.CONFIRMATION);

    }

    //on double click on a row
    @FXML
    public void showSupplierDetails() {
        SupplierModel supplier = table.getSelectionModel().getSelectedValue();

        if (supplier == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR);
            return;
        }
        openDialog(stackPane.getScene(), table.getSelectionModel().getSelectedValue().getName(), DialogType.CONFIRMATION);
    }
}
