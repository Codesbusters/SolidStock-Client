package fr.codesbusters.solidstock.controller.selectors;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.product.GetProductDto;
import fr.codesbusters.solidstock.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.listener.ProductSelectorListener;
import fr.codesbusters.solidstock.listener.SupplierSelectorListener;
import fr.codesbusters.solidstock.model.ProductModel;
import fr.codesbusters.solidstock.model.SupplierModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
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
public class ProductSelectorController extends DefaultShowController implements Initializable {

    @FXML
    private AnchorPane stackPane;

    @FXML
    MFXTableView<ProductModel> table;

    private Stage parentStage;

    private ProductSelectorListener listener;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    public void setStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setListener(ProductSelectorListener listener) {
        this.listener = listener;
    }

    @FXML
    public void addProduct() {
        openPopUp("products/addPopup.fxml", stackPane.getScene(), "Ajouter un produit");
        reloadProduct();
    }

    private void setupTable() {
        MFXTableColumn<ProductModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(ProductModel::getID));
        MFXTableColumn<ProductModel> nameColumn = new MFXTableColumn<>("Libelle", true, Comparator.comparing(ProductModel::getName));
        MFXTableColumn<ProductModel> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(ProductModel::getDescription));
        MFXTableColumn<ProductModel> inStockColumn = new MFXTableColumn<>("En Stock", true, Comparator.comparing(ProductModel::getInStock));
        MFXTableColumn<ProductModel> selledColumn = new MFXTableColumn<>("Vendus", true, Comparator.comparing(ProductModel::getSelled));
        MFXTableColumn<ProductModel> sellPrice = new MFXTableColumn<>("Prix de Vente", true, Comparator.comparing(ProductModel::getSellPrice));
        MFXTableColumn<ProductModel> buyPrice = new MFXTableColumn<>("Prix d'achat", true, Comparator.comparing(ProductModel::getBuyPrice));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getID) {
            {
                setAlignment(Pos.CENTER_RIGHT);
                if (product != null && product.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });
        nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getName) {
            {
                setAlignment(Pos.CENTER_RIGHT);
                if (product != null && product.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });
        descriptionColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getDescription) {
            {
                setAlignment(Pos.CENTER_RIGHT);
                if (product != null && product.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });
        inStockColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getInStock) {
            {
                setAlignment(Pos.CENTER_RIGHT);
                if (product != null && product.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });

        selledColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getSelled) {
            {
                setAlignment(Pos.CENTER_RIGHT);
                if (product != null && product.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });

        sellPrice.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getSellPrice) {
            {
                setAlignment(Pos.CENTER_RIGHT);
                if (product != null && product.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });

        buyPrice.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getBuyPrice) {
            {
                setAlignment(Pos.CENTER_RIGHT);
                if (product != null && product.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });

        table.getTableColumns().addAll(idColumn, nameColumn, descriptionColumn, inStockColumn, selledColumn, sellPrice, buyPrice);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", ProductModel::getID),
                new StringFilter<>("Libelle", ProductModel::getName),
                new StringFilter<>("Famille", ProductModel::getProductFamily)
        );
        reloadProduct();
    }

    @FXML
    public void showSupplier() {
        ProductModel supplier = table.getSelectionModel().getSelectedValue();

        if (supplier == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR, 0);
            return;
        }

        setId(supplier.getID());
        openPopUp("suppliers/showPopup.fxml", stackPane.getScene(), "Détails du fournisseur");
        reloadProduct();
    }

    @FXML
    public void editProduct() {
        ProductModel supplier = table.getSelectionModel().getSelectedValue();

        if (supplier == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un fournisseur", DialogType.ERROR, 0);
            return;
        }

        setId(supplier.getID());

        openPopUp("suppliers/editPopup.fxml", stackPane.getScene(), "Modification du fournisseur");
        reloadProduct();

    }

    @FXML
    public void removeProduct() {
        ProductModel supplier = table.getSelectionModel().getSelectedValue();

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
        reloadProduct();
    }

    @FXML
    public void reloadProduct() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/product/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetProductDto> productList = null;
        try {
            productList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing product list", e);
        }

        ObservableList<ProductModel> productModels = FXCollections.observableArrayList();
        assert productList != null;
        for (GetProductDto product : productList) {
            ProductModel productModel = new ProductModel();
            productModel.setID(product.getId());

            if (product.getName() == null || product.getName().isEmpty()) {
                productModel.setName("");
            } else {
                productModel.setName(product.getName());
            }

            if (product.getDescription() == null || product.getDescription().isEmpty()) {
                productModel.setDescription("");
            } else {
                productModel.setDescription(product.getDescription());
            }

            productModel.setSellPrice(Double.parseDouble(product.getSellPrice()));
            productModel.setBuyPrice(Double.parseDouble(product.getBuyPrice()));
            productModel.setMinimumStockQuantity(product.getMinimumStockQuantity());
            productModel.setIsDisabled(product.isDeleted());
            productModels.add(productModel);
        }
        productModels.sort(Comparator.comparingInt(ProductModel::getID));
        table.getItems().addAll(productModels);
    }

    @FXML
    private void submitAction(ActionEvent event) {

        // Obtenez l'ID sélectionné de la table
        ProductModel selectedValue = table.getSelectionModel().getSelectedValue();

        // Vérifiez si l'ID est null
        if (selectedValue != null) {
            String productId = String.valueOf(selectedValue.getID());
            String productName = selectedValue.getName();
            String productPrice = String.valueOf(selectedValue.getSellPrice());
            if (listener != null) {
                listener.processProductContent(productId, productName, productPrice);
            } else {
                openDialog(table.getScene(), "Une erreur est survenue, veuillez réessayer.", DialogType.ERROR, 0);
            }

            // Fermez la fenêtre pop-up
            parentStage.close();
        } else {
            openDialog(table.getScene(), "Veuillez séléctionner un fournisseur", DialogType.ERROR, 0);
        }
    }

    @FXML
    private void cancel() {
        parentStage.close();
    }
}