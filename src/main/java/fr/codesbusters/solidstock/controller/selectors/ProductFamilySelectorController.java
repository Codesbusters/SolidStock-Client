package fr.codesbusters.solidstock.controller.selectors;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultController;
import fr.codesbusters.solidstock.listener.ProductFamilySelectorListener;
import fr.codesbusters.solidstock.model.ProductFamilyModel;
import fr.codesbusters.solidstock.model.SolidStockModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.Comparator;

@Slf4j
@Controller
public class ProductFamilySelectorController extends DefaultController implements Initializable {

    @FXML
    MFXTableView<ProductFamilyModel> table;

    private Stage parentStage;

    private ProductFamilySelectorListener listener;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
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

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductFamilyModel::getID));
        nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductFamilyModel::getName));
        descriptionColumn.setRowCellFactory(product -> new MFXTableRowCell<>(ProductFamilyModel::getDescription));

        table.getTableColumns().addAll(idColumn, nameColumn, descriptionColumn);
        table.getFilters().addAll(
                new StringFilter<>("Réf.", ProductFamilyModel::getName),
                new StringFilter<>("Libelle", ProductFamilyModel::getName),
                new StringFilter<>("Description", ProductFamilyModel::getDescription)
        );
        table.setItems(SolidStockModel.productFamily);


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
                openDialog(table.getScene(), "Une erreur est survenue, veuillez réessayer.", DialogType.ERROR);
            }

            // Fermez la fenêtre pop-up
            parentStage.close();
        } else {
            openDialog(table.getScene(), "Veuillez séléctionner une famille de produit", DialogType.ERROR);
        }
    }

    @FXML
    private void cancel() {
        parentStage.close();
    }
}
