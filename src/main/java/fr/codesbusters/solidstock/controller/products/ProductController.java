package fr.codesbusters.solidstock.controller.products;

import fr.codesbusters.solidstock.model.ProductModel;
import fr.codesbusters.solidstock.model.SolidStockModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.scenicview.ScenicView;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class ProductController implements Initializable {
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
        try {
            // Récupérez la fenêtre principale (parent)
            Stage primaryStage = (Stage) stackPane.getScene().getWindow();


            // Chargez le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/products/addPopup.fxml"));
            Parent root = loader.load();

            // Créez une nouvelle scène
            Scene scene = new Scene(root);

            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);

            // Créez une nouvelle fenêtre modale pour la pop-up
            Stage popupStage = new Stage();
            popupStage.setResizable(false);
            popupStage.setTitle("Ajouter un produit - SolidStock");
            ScenicView.show(scene);

            // Utilisez Modality.WINDOW_MODAL pour lier la fenêtre pop-up au propriétaire (parent)
            popupStage.initModality(Modality.WINDOW_MODAL);

            // Définissez le propriétaire de la fenêtre pop-up
            popupStage.initOwner(primaryStage);

            // Définissez la scène de la fenêtre pop-up
            popupStage.setScene(scene);

            // Ajoutez un écouteur pour détecter la fermeture de la fenêtre pop-up
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
