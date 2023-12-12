package fr.codesbusters.solidstock.controller.orders;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.model.OrdersModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import static fr.codesbusters.solidstock.model.SolidStockModel.orders;

@Slf4j
@Controller
public class OrdersController extends DefaultShowController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<OrdersModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }


    @FXML
    public void addOrder() {
        openPopUp("orders/addPopup.fxml", stackPane.getScene(), "Ajouter une commande");
    }


    private void setupTable() {

        MFXTableColumn<OrdersModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(OrdersModel::getID));
        MFXTableColumn<OrdersModel> subjectColumn = new MFXTableColumn<>("Sujet", true, Comparator.comparing(OrdersModel::getSubject));
        MFXTableColumn<OrdersModel> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(OrdersModel::getDescription));
        MFXTableColumn<OrdersModel> customerIdColumn = new MFXTableColumn<>("ID Client", true, Comparator.comparing(OrdersModel::getCustomerId));
        MFXTableColumn<OrdersModel> customerNameColumn = new MFXTableColumn<>("Nom Client", true, Comparator.comparing(OrdersModel::getCustomerName));
        MFXTableColumn<OrdersModel> dueDateColumn = new MFXTableColumn<>("Date de livraison", true, Comparator.comparing(OrdersModel::getDueDate));
        MFXTableColumn<OrdersModel> estimateIdColumn = new MFXTableColumn<>("Devis", true, Comparator.comparing(OrdersModel::getEstimateId));
        MFXTableColumn<OrdersModel> statusIdColumn = new MFXTableColumn<>("Statut Id", true, Comparator.comparing(OrdersModel::getStatusId));
        MFXTableColumn<OrdersModel> statusNameColumn = new MFXTableColumn<>("Statut", true, Comparator.comparing(OrdersModel::getStatusName));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getID));
        subjectColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getSubject));
        descriptionColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getDescription));
        customerIdColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getCustomerId));
        customerNameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getCustomerName));
        dueDateColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getDueDate));
        estimateIdColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getEstimateId));
        statusIdColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getStatusId));
        statusNameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(OrdersModel::getStatusName));


        table.getTableColumns().addAll(idColumn, subjectColumn, descriptionColumn, customerIdColumn, customerNameColumn, dueDateColumn, estimateIdColumn, statusNameColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", OrdersModel::getID),
                new StringFilter<>("Sujet", OrdersModel::getSubject),
                new IntegerFilter<>("ID Client", OrdersModel::getCustomerId),
                new StringFilter<>("Nom Client", OrdersModel::getCustomerName),
                new StringFilter<>("Date de livraison", OrdersModel::getDueDate),
                new StringFilter<>("Statut", OrdersModel::getStatusName)
        );
        table.setItems(orders);


    }

    @FXML
    public void showOrder() {
        OrdersModel product = table.getSelectionModel().getSelectedValue();

        if (product == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une commande", DialogType.ERROR);
            return;
        }

        setId(product.getID());

        openPopUp("orders/showPopup.fxml", stackPane.getScene(), "Détails de la commande");

    }

    @FXML
    public void editOrder() {
        OrdersModel product = table.getSelectionModel().getSelectedValue();

        if (product == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une commande", DialogType.ERROR);
            return;
        }

        setId(product.getID());

        openPopUp("orders/editPopup.fxml", stackPane.getScene(), "Modification de la commande");

    }

    @FXML
    public void removeOrder() {
        OrdersModel product = table.getSelectionModel().getSelectedValue();

        if (product == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner une commande", DialogType.ERROR);
            return;
        }

        openDialog(stackPane.getScene(), "Voulez-vous vraiment annuler la commande n° " + orders.getFirst().getID() + " du client " + orders.getFirst().getCustomerName() + " ?", DialogType.CONFIRMATION);

    }
}
