package fr.codesbusters.solidstock.controller.products;

import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.materialfx.demo.model.Device;
import io.github.palexdev.materialfx.demo.model.Model;
import io.github.palexdev.materialfx.filter.EnumFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private MFXPaginatedTableView<Device> paginated;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupPaginated();

        paginated.autosizeColumnsOnInitialization();

        When.onChanged(paginated.currentPageProperty())
                .then((oldValue, newValue) -> paginated.autosizeColumns())
                .listen();

        stackPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Calculez le nouveau nombre de lignes par page en fonction de la nouvelle hauteur
                int rowsPerPage = calculateRowsPerPage(newValue.doubleValue());
                log.info("Rows per page: " + rowsPerPage);
                paginated.setRowsPerPage(rowsPerPage);

                // Redimensionnez les colonnes
                paginated.autosizeColumns();
                paginated.setPagesToShow(calculatePageToShow(Model.devices.size(), rowsPerPage));

            }
        });
    }

    private int calculateRowsPerPage(double newHeight) {
        log.info("New height: " + newHeight);
        return (int) (newHeight / 62);
    }

    private int calculatePageToShow(int itemsSize, int rowsPerPage) {
        return (int) Math.ceil((double) itemsSize / rowsPerPage);
    }


    @FXML
    public void addProduct() {
        try {
            // Chargez le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/products/addPopup.fxml"));
            Parent root = loader.load();

            // Créez une nouvelle scène
            Scene scene = new Scene(root);

            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);

            // Créez une nouvelle fenêtre modale pour la pop-up
            Stage popupStage = new Stage();
            popupStage.setTitle("Ajouter un produit - SolidStock");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(popupStage.getOwner());

            // Définissez la scène de la fenêtre pop-up
            popupStage.setScene(scene);

            // Affichez la fenêtre pop-up et attendez qu'elle soit fermée avant de revenir à la fenêtre principale
            popupStage.showAndWait();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private void setupPaginated() {
        MFXTableColumn<Device> idColumn = new MFXTableColumn<>("ID", false, Comparator.comparing(Device::getID));
        MFXTableColumn<Device> nameColumn = new MFXTableColumn<>("Name", false, Comparator.comparing(Device::getName));
        MFXTableColumn<Device> ipColumn = new MFXTableColumn<>("IP", false, Comparator.comparing(Device::getIP));
        MFXTableColumn<Device> ownerColumn = new MFXTableColumn<>("Owner", false, Comparator.comparing(Device::getOwner));
        MFXTableColumn<Device> stateColumn = new MFXTableColumn<>("State", false, Comparator.comparing(Device::getState));

        idColumn.setRowCellFactory(device -> new MFXTableRowCell<>(Device::getID));
        nameColumn.setRowCellFactory(device -> new MFXTableRowCell<>(Device::getName));
        ipColumn.setRowCellFactory(device -> new MFXTableRowCell<>(Device::getIP) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        ownerColumn.setRowCellFactory(device -> new MFXTableRowCell<>(Device::getOwner));
        stateColumn.setRowCellFactory(device -> new MFXTableRowCell<>(Device::getState));
        ipColumn.setAlignment(Pos.CENTER_RIGHT);

        paginated.getTableColumns().addAll(idColumn, nameColumn, ipColumn, ownerColumn, stateColumn);
        paginated.getFilters().addAll(
                new IntegerFilter<>("ID", Device::getID),
                new StringFilter<>("Name", Device::getName),
                new StringFilter<>("IP", Device::getIP),
                new StringFilter<>("Owner", Device::getOwner),
                new EnumFilter<>("State", Device::getState, Device.State.class)
        );
        paginated.setRowsPerPage(19);
        paginated.setItems(Model.devices);

    }

}
