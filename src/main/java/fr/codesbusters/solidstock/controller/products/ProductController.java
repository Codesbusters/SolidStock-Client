package fr.codesbusters.solidstock.controller.products;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.model.ProductModel;
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
public class ProductController extends DefaultShowController implements Initializable {
    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<ProductModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }


    @FXML
    public void addProduct() {
        openPopUp("products/addPopup.fxml", stackPane.getScene(), "Ajouter un produit");
    }


    private void setupTable() {
        MFXTableColumn<ProductModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(ProductModel::getID));
        MFXTableColumn<ProductModel> nameColumn = new MFXTableColumn<>("Libelle", true, Comparator.comparing(ProductModel::getName));
        MFXTableColumn<ProductModel> productFamilyColumn = new MFXTableColumn<>("Famille", true, Comparator.comparing(ProductModel::getProductFamily));
        MFXTableColumn<ProductModel> inStockColumn = new MFXTableColumn<>("En Stock", true, Comparator.comparing(ProductModel::getInStock));
        MFXTableColumn<ProductModel> selledColumn = new MFXTableColumn<>("Vendus", true, Comparator.comparing(ProductModel::getSelled));
        MFXTableColumn<ProductModel> sellPrice = new MFXTableColumn<>("Prix de Vente", true, Comparator.comparing(ProductModel::getSellPrice));
        MFXTableColumn<ProductModel> buyPrice = new MFXTableColumn<>("Prix d'achat", true, Comparator.comparing(ProductModel::getBuyPrice));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getID));
        nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getName));
        productFamilyColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getProductFamily));
        inStockColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getInStock) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});

        selledColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getSelled) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});

        sellPrice.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getSellPrice) {{
            setAlignment(Pos.CENTER_RIGHT);
            setGraphicTextGap(5);
        }});

        buyPrice.setRowCellFactory(product -> new MFXTableRowCell<>(ProductModel::getBuyPrice) {{
            setAlignment(Pos.CENTER_RIGHT);
            setGraphicTextGap(5);
        }});

        table.getTableColumns().addAll(idColumn, nameColumn, productFamilyColumn, inStockColumn, selledColumn, sellPrice, buyPrice);
        table.getFilters().addAll(
                new StringFilter<>("Réf.", ProductModel::getName),
                new StringFilter<>("Libelle", ProductModel::getName),
                new StringFilter<>("Famille", ProductModel::getProductFamily),
                new IntegerFilter<>("Nombre en stock", ProductModel::getInStock),
                new IntegerFilter<>("Nombre vendu", ProductModel::getSelled)
        );
        table.setItems(SolidStockModel.products);


    }

    @FXML
    public void showProduct() {
        ProductModel product = table.getSelectionModel().getSelectedValue();

        if (product == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un produit", DialogType.ERROR);
            return;
        }

        setId(product.getID());

        openPopUp("products/showPopup.fxml", stackPane.getScene(), "Détails du produit");

    }

    @FXML
    public void editProduct() {
        ProductModel product = table.getSelectionModel().getSelectedValue();

        if (product == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un produit", DialogType.ERROR);
            return;
        }

        setId(product.getID());

        openPopUp("products/editPopup.fxml", stackPane.getScene(), "Modification du produit");

    }

    @FXML
    public void removeProduct() {
        ProductModel product = table.getSelectionModel().getSelectedValue();

        if (product == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un produit", DialogType.ERROR);
            return;
        }

        openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer le produit " + product.getName() + " ?", DialogType.CONFIRMATION);

    }


    //on double click on a row
    @FXML
    public void showProductDetails() {
        ProductModel product = table.getSelectionModel().getSelectedValue();

        if (product == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un produit", DialogType.ERROR);
            return;
        }
        openDialog(stackPane.getScene(), table.getSelectionModel().getSelectedValue().getName(), DialogType.CONFIRMATION);
    }


}
