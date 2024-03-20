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
        MFXTableColumn<SupplierModel> NameColumn = new MFXTableColumn<>("Nom Société", true, Comparator.comparing(SupplierModel::getName));
        MFXTableColumn<SupplierModel> zipCodeColumn = new MFXTableColumn<>("Code postal", true, Comparator.comparing(SupplierModel::getZipCode));
        MFXTableColumn<SupplierModel> cityColumn = new MFXTableColumn<>("Ville", true, Comparator.comparing(SupplierModel::getCity));
        MFXTableColumn<SupplierModel> countryColumn = new MFXTableColumn<>("Pays", true, Comparator.comparing(SupplierModel::getCountry));
        MFXTableColumn<SupplierModel> emailColumn = new MFXTableColumn<>("Émail", true, Comparator.comparing(SupplierModel::getEmail));
        MFXTableColumn<SupplierModel> workPhoneColumn = new MFXTableColumn<>("Téléphone travail", true, Comparator.comparing(SupplierModel::getWorkPhone));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getID) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (product != null && product.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });

        NameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getName) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (product != null && product.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });

        emailColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getEmail) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (product != null && product.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });
        zipCodeColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getZipCode) {{
            setAlignment(Pos.CENTER_LEFT);
            if (product != null && product.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
        }});
        cityColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getCity) {{
            setAlignment(Pos.CENTER_LEFT);
            if (product != null && product.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
        }});
        workPhoneColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getWorkPhone) {{
            setAlignment(Pos.CENTER_LEFT);
            if (product != null && product.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
        }});
        countryColumn.setRowCellFactory(product -> new MFXTableRowCell<>(SupplierModel::getCountry) {{
            setAlignment(Pos.CENTER_LEFT);
            if (product != null && product.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
        }});

        table.getTableColumns().addAll(idColumn, NameColumn, emailColumn, zipCodeColumn, cityColumn, countryColumn, workPhoneColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", SupplierModel::getID),
                new StringFilter<>("Nom entreprise", SupplierModel::getName),
                new StringFilter<>("Code postal", SupplierModel::getZipCode),
                new StringFilter<>("Ville", SupplierModel::getCity),
                new StringFilter<>("Pays", SupplierModel::getCountry),
                new StringFilter<>("Email", SupplierModel::getEmail)
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
            log.info("Supplier remove successfully : {}", supplier);
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

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/supplier/all", String.class, true);
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

            if (supplier.getAddress() == null || supplier.getAddress().isEmpty()) {
                supplierModel.setAddress("");
            } else {
                supplierModel.setAddress(supplier.getAddress());
            }

            if (supplier.getStreetNumber() == null || supplier.getStreetNumber().isEmpty()) {
                supplierModel.setStreetNumber("");
            } else {
                supplierModel.setStreetNumber(supplier.getStreetNumber());
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

            if (supplier.getMobilePhone() == null || supplier.getMobilePhone().isEmpty()) {
                supplierModel.setMobilePhone("");
            } else {
                supplierModel.setMobilePhone(supplier.getMobilePhone());
            }

            if (supplier.getHomePhone() == null || supplier.getHomePhone().isEmpty()) {
                supplierModel.setHomePhone("");
            } else {
                supplierModel.setHomePhone(supplier.getHomePhone());
            }

            if (supplier.getWorkPhone() == null || supplier.getWorkPhone().isEmpty()) {
                supplierModel.setWorkPhone("");
            } else {
                supplierModel.setWorkPhone(supplier.getWorkPhone());
            }

            if (supplier.getSiren() == null || supplier.getSiren().isEmpty()) {
                supplierModel.setSiren("");
            } else {
                supplierModel.setSiren(supplier.getSiren());
            }

            if (supplier.getSiret() == null || supplier.getSiret().isEmpty()) {
                supplierModel.setSiret("");
            } else {
                supplierModel.setSiret(supplier.getSiret());
            }

            if (supplier.getRib() == null || supplier.getRib().isEmpty()) {
                supplierModel.setRib("");
            } else {
                supplierModel.setRib(supplier.getRib());
            }

            if (supplier.getEmail() == null || supplier.getEmail().isEmpty()) {
                supplierModel.setEmail("");
            } else {
                supplierModel.setEmail(supplier.getEmail());
            }

            if (supplier.getWebsite() == null || supplier.getWebsite().isEmpty()) {
                supplierModel.setWebsite("");
            } else {
                supplierModel.setWebsite(supplier.getWebsite());
            }

            if (supplier.getFax() == null || supplier.getFax().isEmpty()) {
                supplierModel.setFax("");
            } else {
                supplierModel.setFax(supplier.getFax());
            }

            if (supplier.getNote() == null || supplier.getNote().isEmpty()) {
                supplierModel.setNote("");
            } else {
                supplierModel.setNote(supplier.getNote());
            }

            supplierModel.setRcs(supplier.getRcs());
            supplierModel.setIsDisabled(supplier.isDeleted());
            supplierModels.add(supplierModel);
        }
        supplierModels.sort(Comparator.comparingInt(SupplierModel::getID));
        table.getItems().addAll(supplierModels);
    }
}