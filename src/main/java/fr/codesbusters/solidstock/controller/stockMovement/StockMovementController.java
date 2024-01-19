package fr.codesbusters.solidstock.controller.stockMovement;

import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.model.SolidStockDataIntegration;
import fr.codesbusters.solidstock.model.StockMovementModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.BooleanFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class StockMovementController extends DefaultShowController implements Initializable {
    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<StockMovementModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    private void setupTable() {
        MFXTableColumn<StockMovementModel> icon = new MFXTableColumn<>("", true, Comparator.comparing(StockMovementModel::getInOut));
        MFXTableColumn<StockMovementModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(StockMovementModel::getID));
        MFXTableColumn<StockMovementModel> refProductColumn = new MFXTableColumn<>("Réf. Produit", true, Comparator.comparing(StockMovementModel::getRefProfuct));
        MFXTableColumn<StockMovementModel> productNameColumn = new MFXTableColumn<>("Nom Produit", true, Comparator.comparing(StockMovementModel::getProductName));
        MFXTableColumn<StockMovementModel> quantityColumn = new MFXTableColumn<>("Quantité", true, Comparator.comparing(StockMovementModel::getQuantite));
        MFXTableColumn<StockMovementModel> dateActionColumn = new MFXTableColumn<>("Date", true, Comparator.comparing(StockMovementModel::getDateAction));
        MFXTableColumn<StockMovementModel> motifColumn = new MFXTableColumn<>("Motif", true, Comparator.comparing(StockMovementModel::getMotif));
        icon.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getInOut) {{
            MFXFontIcon iconPlus = new MFXFontIcon("fas-plus");
            iconPlus.setColor(Color.rgb(0, 255, 0));
            iconPlus.setSize(20);

            MFXFontIcon iconMinus = new MFXFontIcon("fas-minus");
            iconMinus.setColor(Color.rgb(255, 0, 0));
            iconMinus.setSize(20);

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            setAlignment(Pos.CENTER);
            setGraphic(stockMovementModel.getInOut() ? iconPlus : iconMinus);


        }});

        idColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getID));
        refProductColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getRefProfuct));
        productNameColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getProductName));
        quantityColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getQuantite) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        dateActionColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getDateAction) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        motifColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getMotif));


        table.getTableColumns().addAll(icon, idColumn, refProductColumn, productNameColumn, quantityColumn, dateActionColumn, motifColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", StockMovementModel::getID),
                new IntegerFilter<>("Réf. Produit", StockMovementModel::getRefProfuct),
                new StringFilter<>("Nom Produit", StockMovementModel::getProductName),
                new StringFilter<>("Quantité", StockMovementModel::getQuantite),
                new StringFilter<>("Date", StockMovementModel::getDateAction),
                new BooleanFilter<>("Type", StockMovementModel::getInOut),
                new StringFilter<>("Motif", StockMovementModel::getMotif)

        );
        table.setItems(SolidStockDataIntegration.stockMovements);


    }

    @FXML
    public void openConfirmRemove() {

        //get the row selected
        Optional<ButtonType> result = openDialog(stackPane.getScene(), "Êtes-vous sûr de vouloir annuler le mouvement de stock n° " + table.getSelectionModel().getSelectedValue().getID() + " ?", Alert.AlertType.CONFIRMATION);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Set the movement on canceled
        }

    }

    @FXML
    public void openDeliveryPopup() {
        openPopUp("stockMovement/deliveryPopup.fxml", stackPane.getScene(), "Nouvelle livraison");
    }
}
