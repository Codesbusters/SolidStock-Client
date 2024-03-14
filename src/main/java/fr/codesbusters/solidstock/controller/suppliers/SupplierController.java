package fr.codesbusters.solidstock.controller.suppliers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.model.SupplierModel;
import fr.codesbusters.solidstock.service.RequestAPI;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
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
        MFXTableColumn<SupplierModel> companyNameColumn = new MFXTableColumn<>("Nom Entreprise", true, Comparator.comparing(SupplierModel::getCompanyName));
        MFXTableColumn<SupplierModel> firstNameColumn = new MFXTableColumn<>("Prénom", true, Comparator.comparing(SupplierModel::getFirstName));
        MFXTableColumn<SupplierModel> lastNameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(SupplierModel::getLastName));
        MFXTableColumn<SupplierModel> zipCodeColumn = new MFXTableColumn<>("Code postal", true, Comparator.comparing(SupplierModel::getZipCode));
        MFXTableColumn<SupplierModel> cityColumn = new MFXTableColumn<>("Ville", true, Comparator.comparing(SupplierModel::getCity));
        MFXTableColumn<SupplierModel> countryColumn = new MFXTableColumn<>("Pays", true, Comparator.comparing(SupplierModel::getCountry));
        MFXTableColumn<SupplierModel> emailColumn = new MFXTableColumn<>("Émail", true, Comparator.comparing(SupplierModel::getEmail));
        MFXTableColumn<SupplierModel> workPhoneColumn = new MFXTableColumn<>("Téléphone travail", true, Comparator.comparing(SupplierModel::getWorkPhone));
        MFXTableColumn<SupplierModel> websiteColumn = new MFXTableColumn<>("Site web", true, Comparator.comparing(SupplierModel::getWebsite));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getID));

        companyNameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getCompanyName) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        firstNameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getFirstName) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        lastNameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getLastName) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        emailColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getEmail) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        zipCodeColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getZipCode) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        cityColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getCity) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        workPhoneColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getWorkPhone) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        websiteColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getWebsite) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        countryColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getCountry) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});

        table.getTableColumns().addAll(idColumn, companyNameColumn, firstNameColumn, lastNameColumn, emailColumn, zipCodeColumn, cityColumn, countryColumn, workPhoneColumn, websiteColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", SupplierModel::getID),
                new StringFilter<>("Nom entreprise", SupplierModel::getCompanyName),
                new StringFilter<>("Nom", SupplierModel::getLastName),
                new StringFilter<>("Prénom", SupplierModel::getFirstName),
                new StringFilter<>("Code postal", SupplierModel::getZipCode),
                new StringFilter<>("Ville", SupplierModel::getCity),
                new StringFilter<>("Pays", SupplierModel::getCountry),
                new StringFilter<>("Email", SupplierModel::getEmail),
                new StringFilter<>("Site web", SupplierModel::getWebsite)
        );

        reloadSupplier();
    }


    @FXML
    public void showSupplier() {
        SupplierModel supplier = table.getSelectionModel().getSelectedValue();

        if (supplier == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR, 0);
            return;
        }

        setId(supplier.getID());

        openPopUp("suppliers/showPopup.fxml", stackPane.getScene(), "Détails du fournisseur");
        reloadSupplier();
    }

    @FXML
    public void editSupplier() {
        SupplierModel supplier = table.getSelectionModel().getSelectedValue();

        if (supplier == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR, 0);
            return;
        }

        setId(supplier.getID());

        openPopUp("suppliers/editPopup.fxml", stackPane.getScene(), "Modification du fournisseur");
        reloadSupplier();

    }

    @FXML
    public void removeSupplier() {
        SupplierModel supplier = table.getSelectionModel().getSelectedValue();

        if (supplier == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR, 0);
            return;
        }

        openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer le fournisseur " + supplier.getCompanyName() + " ?", DialogType.CONFIRMATION, 0);
        reloadSupplier();
    }

    //on double click on a row
    @FXML
    public void showSupplierDetails() {
        SupplierModel supplier = table.getSelectionModel().getSelectedValue();

        if (supplier == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR, 0);
            return;
        }
        openDialog(stackPane.getScene(), table.getSelectionModel().getSelectedValue().getCompanyName(), DialogType.CONFIRMATION, 0);
    }

    @FXML
    public void reloadSupplier() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/supplier/all", String.class, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetSupplierDto> supplierList = null;
        try {
            supplierList = mapper.readValue(responseEntity.getBody(), new TypeReference<List<GetSupplierDto>>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }

        for (GetSupplierDto supplier : supplierList) {
            SupplierModel supplierModel = new SupplierModel();
            supplierModel.setID(supplier.getId());
            table.getItems().add(supplierModel);
        }
    }
}