package fr.codesbusters.solidstock.controller.selectors.productFamily;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.productFamily.GetProductFamilyDto;
import fr.codesbusters.solidstock.listener.ProductFamilySelectorListener;
import fr.codesbusters.solidstock.model.ProductFamilyModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Controller
public class ProductFamilySelectorController extends DefaultShowController implements Initializable {

    @FXML
    MFXTableView<ProductFamilyModel> table;

    @FXML
    private AnchorPane stackPane;

    private Stage parentStage;

    private ProductFamilySelectorListener listener;

    @FXML
    private MFXButton modifyButton;

    @FXML
    private MFXButton deleteButton;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();

        table.setOnMouseClicked(event -> {
            ProductFamilyModel productFamily = table.getSelectionModel().getSelectedValue();

            if (productFamily!= null) {
                if(productFamily.getIsDisabled()) {
                    modifyButton.setDisable(true);
                    deleteButton.setDisable(true);
                } else {
                    modifyButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            }
        });
    }

    public void setStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setListener(ProductFamilySelectorListener listener) {
        this.listener = listener;
    }

    private void setupTable() {
        MFXTableColumn<ProductFamilyModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(ProductFamilyModel::getID));
        MFXTableColumn<ProductFamilyModel> nameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(ProductFamilyModel::getName));
        MFXTableColumn<ProductFamilyModel> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(ProductFamilyModel::getDescription));

        idColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(ProductFamilyModel::getID) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });
        nameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(ProductFamilyModel::getName) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });
        descriptionColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(ProductFamilyModel::getDescription) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });

        table.getTableColumns().addAll(idColumn, nameColumn, descriptionColumn);
        table.getFilters().addAll(
                new StringFilter<>("Réf.", ProductFamilyModel::getName),
                new StringFilter<>("Libelle", ProductFamilyModel::getName),
                new StringFilter<>("Description", ProductFamilyModel::getDescription)
        );
        reloadProductFamily();
    }

    @FXML
    public void addProductFamily() {
        openPopUp("selector/productFamilySelector/addPopup.fxml", stackPane.getScene(), "Ajouter une famille de produit");
        reloadProductFamily();
    }

    @FXML
    public void showProductFamily() {
        ProductFamilyModel productFamily = table.getSelectionModel().getSelectedValue();

        if (productFamily == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une famille de produit.", DialogType.ERROR, 0);
            return;
        }

        setId(productFamily.getID());
        openPopUp("selector/productFamilySelector/showPopup.fxml", stackPane.getScene(), "Détails de la famille du produit");
        reloadProductFamily();
    }

    @FXML
    public void editProductFamily() {
        ProductFamilyModel productFamily = table.getSelectionModel().getSelectedValue();

        if (productFamily == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une famille de produit.", DialogType.ERROR, 0);
            return;
        }

        setId(productFamily.getID());

        openPopUp("selector/productFamilySelector/editPopup.fxml", stackPane.getScene(),"Modification de la famille de produit");
        reloadProductFamily();
    }

    @FXML
    public void removeProductFamily() {
        ProductFamilyModel productFamily = table.getSelectionModel().getSelectedValue();

        if (productFamily == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une famille de produit.", DialogType.ERROR, 0);
            return;
        }

        boolean result = openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer la famille de produit " + productFamily.getName() + " ?", DialogType.CONFIRMATION, 0);
        if (!result) {
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/product-family/" + productFamily.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Family product removed successfully : {}", productFamily);
            openDialog(stackPane.getScene(), "Famille de produit " + productFamily.getName() + " supprimée avec succès.", DialogType.INFORMATION,0);
        } else {
            openDialog(stackPane.getScene(),"Erreur lors de la suppression de la famille de produit", DialogType.ERROR,0);
        }
        reloadProductFamily();
    }

    @FXML
    private void submitAction(ActionEvent event) {

        // Obtenez l'ID sélectionné de la table
        ProductFamilyModel selectedValue = table.getSelectionModel().getSelectedValue();

        // Vérifiez si l'ID est null
        if (selectedValue != null) {
            String productFamilyId = String.valueOf(selectedValue.getID());
            if (listener != null) {
                listener.processProductFamilyContent(productFamilyId);
            } else {
                openDialog(table.getScene(), "Une erreur est survenue, veuillez réessayer.", DialogType.ERROR, 0);
            }

            // Fermez la fenêtre pop-up
            parentStage.close();
        } else {
            openDialog(table.getScene(), "Veuillez séléctionner une famille de produit", DialogType.ERROR, 0);
        }
    }

    @FXML
    private void cancel() {
        parentStage.close();
    }

    @FXML
    public void reloadProductFamily() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity =requestAPI.sendGetRequest("/product-family/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetProductFamilyDto> productFamilyList = null;
        try {
            productFamilyList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing product List", e);
        }

        ObservableList<ProductFamilyModel> productFamilyModels = FXCollections.observableArrayList();
        assert productFamilyList != null;
        for (GetProductFamilyDto productFamily : productFamilyList) {
            ProductFamilyModel productFamilyModel = new ProductFamilyModel();
            productFamilyModel.setID(productFamily.getId());
            productFamilyModel.setDescription(productFamily.getDescription());
            if (productFamily.getName() == null || productFamily.getName().isEmpty()) {
                productFamilyModel.setName("");
            } else {
                productFamilyModel.setName(productFamily.getName());
            }
            productFamilyModel.setIsDisabled(productFamily.isDeleted());
            productFamilyModels.add(productFamilyModel);
        }
        productFamilyModels.sort(Comparator.comparingInt(ProductFamilyModel::getID));
        table.getItems().addAll(productFamilyModels);
    }
}
