package fr.codesbusters.solidstock.controller.suppliers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.model.SupplierModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
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

    @FXML
    private MFXButton modifyButton;

    @FXML
    private MFXButton deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();

        table.setOnMouseClicked(event -> {
            SupplierModel supplier = table.getSelectionModel().getSelectedValue();

            if (supplier != null) {
                if (supplier.getIsDisabled()) {
                    modifyButton.setDisable(true);
                    deleteButton.setDisable(true);
                } else {
                    modifyButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            }
        });
    }

    @FXML
    public void addSupplier() {
        openPopUp("suppliers/addPopup.fxml", stackPane.getScene(), "Ajouter un fournisseur");
        reloadSupplier();
    }

    private void setupTable() {
        MFXTableColumn<SupplierModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(SupplierModel::getID));
        MFXTableColumn<SupplierModel> nameColumn = new MFXTableColumn<>("Raison Sociale", true, Comparator.comparing(SupplierModel::getName));
        MFXTableColumn<SupplierModel> zipCodeColumn = new MFXTableColumn<>("Code postal", true, Comparator.comparing(SupplierModel::getZipCode));
        MFXTableColumn<SupplierModel> cityColumn = new MFXTableColumn<>("Ville", true, Comparator.comparing(SupplierModel::getCity));
        MFXTableColumn<SupplierModel> countryColumn = new MFXTableColumn<>("Pays", true, Comparator.comparing(SupplierModel::getCountry));
        MFXTableColumn<SupplierModel> emailColumn = new MFXTableColumn<>("Émail", true, Comparator.comparing(SupplierModel::getEmail));
        MFXTableColumn<SupplierModel> workPhoneColumn = new MFXTableColumn<>("Téléphone travail", true, Comparator.comparing(SupplierModel::getWorkPhone));

        idColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(SupplierModel::getID) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });

        nameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(SupplierModel::getName) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });

        emailColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(SupplierModel::getEmail) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });
        zipCodeColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(SupplierModel::getZipCode) {{
            setAlignment(Pos.CENTER_LEFT);
            if (rowCell != null && rowCell.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
        }});
        cityColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(SupplierModel::getCity) {{
            setAlignment(Pos.CENTER_LEFT);
            if (rowCell != null && rowCell.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
        }});
        workPhoneColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(SupplierModel::getWorkPhone) {{
            setAlignment(Pos.CENTER_LEFT);
            if (rowCell != null && rowCell.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
        }});
        countryColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(SupplierModel::getCountry) {{
            setAlignment(Pos.CENTER_LEFT);
            if (rowCell != null && rowCell.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
        }});

        table.getTableColumns().addAll(idColumn, nameColumn, emailColumn, zipCodeColumn, cityColumn, countryColumn, workPhoneColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", SupplierModel::getID),
                new StringFilter<>("Nom entreprise", SupplierModel::getName),
                new StringFilter<>("Code postal", SupplierModel::getZipCode),
                new StringFilter<>("Ville", SupplierModel::getCity),
                new StringFilter<>("Pays", SupplierModel::getCountry),
                new StringFilter<>("E-mail", SupplierModel::getEmail)
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
        boolean result = openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer le fournisseur " + supplier.getName() + " ? \nCela entraînera également la suppression de ces produits", DialogType.CONFIRMATION, 0);
        if (!result) {
            return;
        }
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/supplier/" + supplier.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Supplier removed successfully : {}", supplier);
            openDialog(stackPane.getScene(), "Fournisseur " + supplier.getName() + " supprimé avec succès", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression du fournisseur", DialogType.ERROR, 0);
        }
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
        openDialog(stackPane.getScene(), table.getSelectionModel().getSelectedValue().getName(), DialogType.CONFIRMATION, 0);
    }

    @FXML
    public void reloadSupplier() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/supplier/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetSupplierDto> supplierList = null;
        try {
            supplierList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing supplier list", e);
        }

        ObservableList<SupplierModel> supplierModels = FXCollections.observableArrayList();
        assert supplierList != null;
        for (GetSupplierDto supplier : supplierList) {
            SupplierModel supplierModel = new SupplierModel();
            supplierModel.setID(supplier.getId());

            if (supplier.getCompanyName() != null && !supplier.getCompanyName().isEmpty()) {
                supplierModel.setName(supplier.getCompanyName());
            } else {
                supplierModel.setName(supplier.getFirstName() + " " + supplier.getLastName());
            }

            if (supplier.getZipCode() == null || supplier.getZipCode().isEmpty()) {
                supplierModel.setZipCode("");
            } else {
                supplierModel.setZipCode(supplier.getZipCode());
            }

            if (supplier.getCity() == null || supplier.getCity().isEmpty()) {
                supplierModel.setCity("");
            } else {
                supplierModel.setCity(supplier.getCity());
            }

            if (supplier.getCountry() == null || supplier.getCountry().isEmpty()) {
                supplierModel.setCountry("");
            } else {
                supplierModel.setCountry(supplier.getCountry());
            }

            if (supplier.getWorkPhone() == null || supplier.getWorkPhone().isEmpty()) {
                supplierModel.setWorkPhone("");
            } else {
                supplierModel.setWorkPhone(supplier.getWorkPhone());
            }

            if (supplier.getEmail() == null || supplier.getEmail().isEmpty()) {
                supplierModel.setEmail("");
            } else {
                supplierModel.setEmail(supplier.getEmail());
            }
            supplierModel.setIsDisabled(supplier.isDeleted());
            supplierModels.add(supplierModel);
        }
        supplierModels.sort(Comparator.comparingInt(SupplierModel::getID));
        table.getItems().addAll(supplierModels);
    }
}