package fr.codesbusters.solidstock.controller.products;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.product.GetProductDto;
import fr.codesbusters.solidstock.model.ProductModel;
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
public class ProductController extends DefaultShowController implements Initializable {
    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<ProductModel> table;

    @FXML
    private MFXButton modifyButton;

    @FXML
    private MFXButton deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();

        table.setOnMouseClicked(event -> {
            ProductModel product = table.getSelectionModel().getSelectedValue();

            if (product != null) {
                if (product.getIsDisabled()) {
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
                setAlignment(Pos.CENTER_LEFT);
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

        table.getTableColumns().addAll(idColumn, nameColumn, inStockColumn, selledColumn, sellPrice, buyPrice);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", ProductModel::getID),
                new StringFilter<>("Libelle", ProductModel::getName),
                new StringFilter<>("Famille", ProductModel::getProductFamily)
        );
        reloadProduct();
    }

    @FXML
    public void showProduct() {
        ProductModel product = table.getSelectionModel().getSelectedValue();

        if (product == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un produit", DialogType.ERROR, 0);
            return;
        }

        setId(product.getID());
        openPopUp("products/showPopup.fxml", stackPane.getScene(), "Détails du produit");
        reloadProduct();
    }

    @FXML
    public void editProduct() {
        ProductModel product = table.getSelectionModel().getSelectedValue();

        if (product == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un produit", DialogType.ERROR, 0);
            return;
        }

        setId(product.getID());
        openPopUp("products/editPopup.fxml", stackPane.getScene(), "Modification du produit");
        reloadProduct();

    }

    @FXML
    public void removeProduct() {
        ProductModel product = table.getSelectionModel().getSelectedValue();

        if (product == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un produit", DialogType.ERROR, 0);
            return;
        }

        boolean isCanceled = openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer le produit " + product.getName() + " ?", DialogType.CONFIRMATION, 0);
        if (!isCanceled) {
            return;
        }

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/product/" + product.getID(), String.class, true);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Le produit a été supprimé avec succès", DialogType.INFORMATION, 0);

        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression du produit", DialogType.ERROR, 0);
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
            productModel.setInStock(product.getInStock());
            productModel.setSelled(product.getSelled());

            productModels.add(productModel);
        }
        productModels.sort(Comparator.comparingInt(ProductModel::getID));
        table.getItems().addAll(productModels);
    }


}