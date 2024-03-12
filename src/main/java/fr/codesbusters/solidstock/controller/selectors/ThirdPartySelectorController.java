package fr.codesbusters.solidstock.controller.selectors;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.listener.ThirdPartySelectorListener;
import fr.codesbusters.solidstock.model.SolidStockModel;
import fr.codesbusters.solidstock.model.ThirdPartyModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.Comparator;

@Slf4j
@Controller
public class ThirdPartySelectorController extends DefaultShowController implements Initializable {

    @FXML
    AnchorPane anchorPane;

    @FXML
    MFXTableView<ThirdPartyModel> table;

    private Stage parentStage;

    private ThirdPartySelectorListener listener;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    public void setStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setListener(ThirdPartySelectorListener listener) {
        this.listener = listener;
    }

    private void setupTable() {
        MFXTableColumn<ThirdPartyModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(ThirdPartyModel::getID));
        MFXTableColumn<ThirdPartyModel> firstNameColumn = new MFXTableColumn<>("Prénom", true, Comparator.comparing(ThirdPartyModel::getFirstName));
        MFXTableColumn<ThirdPartyModel> lastNameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(ThirdPartyModel::getLastName));
        MFXTableColumn<ThirdPartyModel> cityColumn = new MFXTableColumn<>("Ville", true, Comparator.comparing(ThirdPartyModel::getCity));
        MFXTableColumn<ThirdPartyModel> zipCodeColumn = new MFXTableColumn<>("Code postal", true, Comparator.comparing(ThirdPartyModel::getZipCode));
        MFXTableColumn<ThirdPartyModel> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(ThirdPartyModel::getEmail));
        MFXTableColumn<ThirdPartyModel> mobilePhoneColumn = new MFXTableColumn<>("Téléphone", true, Comparator.comparing(ThirdPartyModel::getMobilePhone));

        idColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getID));
        firstNameColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getFirstName));
        lastNameColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getLastName));
        cityColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getCity));
        zipCodeColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getZipCode));
        emailColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getEmail));
        mobilePhoneColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getMobilePhone));

        table.getTableColumns().addAll(idColumn, firstNameColumn, lastNameColumn, cityColumn, zipCodeColumn, emailColumn, mobilePhoneColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", ThirdPartyModel::getID),
                new StringFilter<>("Prénom", ThirdPartyModel::getFirstName),
                new StringFilter<>("Nom", ThirdPartyModel::getLastName),
                new StringFilter<>("Ville", ThirdPartyModel::getCity),
                new IntegerFilter<>("CP", ThirdPartyModel::getZipCode),
                new StringFilter<>("Email", ThirdPartyModel::getEmail),
                new StringFilter<>("Téléphone", ThirdPartyModel::getMobilePhone)
        );
        table.setItems(SolidStockModel.thirdParties);

    }

    @FXML
    private void submitAction(ActionEvent event) {

        // Obtenez l'ID sélectionné de la table
        ThirdPartyModel selectedValue = table.getSelectionModel().getSelectedValue();

        // Vérifiez si l'ID est null
        if (selectedValue != null) {
            String thirdPartyId = String.valueOf(selectedValue.getID());
            if (listener != null) {
                listener.processThirdPartyContent(thirdPartyId);
            } else {
                openDialog(table.getScene(), "Une erreur est survenue, veuillez réessayer.", DialogType.ERROR);
            }

            // Fermez la fenêtre pop-up
            parentStage.close();
        } else {
            openDialog(table.getScene(), "Veuillez séléctionner un tier", DialogType.ERROR);
        }
    }

    @FXML
    public void addThirdParty() {
        openPopUp("thirdParty/addPopup.fxml", anchorPane.getScene(), "Ajouter un tiers");
    }

    @FXML
    private void cancel() {
        parentStage.close();
    }

    @FXML
    public void showThirdParty() {
        ThirdPartyModel thirdParties = table.getSelectionModel().getSelectedValue();

        if (thirdParties == null) {
            openDialog(anchorPane.getScene(), "Veuillez sélectionner un tiers", DialogType.ERROR);
            return;
        }

        setId(thirdParties.getID());

        openPopUp("thirdParty/showPopup.fxml", anchorPane.getScene(), "Détails du tiers");

    }

    @FXML
    public void editThirdParty() {
        ThirdPartyModel thirdParties = table.getSelectionModel().getSelectedValue();

        if (thirdParties == null) {
            openDialog(anchorPane.getScene(), "Veuillez sélectionner un tiers", DialogType.ERROR);
            return;
        }

        setId(thirdParties.getID());

        openPopUp("thirdParty/editPopup.fxml", anchorPane.getScene(), "Modification du tiers");

    }

    @FXML
    public void removeThirdParty() {
        ThirdPartyModel thirdParties = table.getSelectionModel().getSelectedValue();

        if (thirdParties == null) {
            openDialog(anchorPane.getScene(), "Veuillez sélectionner un tiers", DialogType.ERROR);
            return;
        }

        openDialog(anchorPane.getScene(), "Voulez-vous vraiment supprimer le tiers " + thirdParties.getLastName() + " " + thirdParties.getFirstName() + " ?", DialogType.CONFIRMATION);

    }
}
