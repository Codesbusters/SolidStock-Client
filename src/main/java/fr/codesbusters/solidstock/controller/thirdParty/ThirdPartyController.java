package fr.codesbusters.solidstock.controller.thirdParty;


import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.model.SolidStockDataIntegration;
import fr.codesbusters.solidstock.model.ThirdPartyModel;
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
public class ThirdPartyController extends DefaultShowController implements Initializable {
    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<ThirdPartyModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }


    @FXML
    public void addThirdParty() {
        openPopUp("thirdParty/addPopup.fxml", stackPane.getScene(), "Ajouter un tiers");
    }


    private void setupTable() {
        MFXTableColumn<ThirdPartyModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(ThirdPartyModel::getID));
        MFXTableColumn<ThirdPartyModel> firstNameColumn = new MFXTableColumn<>("Prénom", true, Comparator.comparing(ThirdPartyModel::getFirstName));
        MFXTableColumn<ThirdPartyModel> lastNameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(ThirdPartyModel::getLastName));
        MFXTableColumn<ThirdPartyModel> cityColumn = new MFXTableColumn<>("Ville", true, Comparator.comparing(ThirdPartyModel::getCity));
        MFXTableColumn<ThirdPartyModel> zipCodeColumn = new MFXTableColumn<>("Code Postal", true, Comparator.comparing(ThirdPartyModel::getZipCode));
        MFXTableColumn<ThirdPartyModel> addressColumn = new MFXTableColumn<>("Adresse", true, Comparator.comparing(ThirdPartyModel::getAddress));
        MFXTableColumn<ThirdPartyModel> streetNumberColumn = new MFXTableColumn<>("Numéro de rue", true, Comparator.comparing(ThirdPartyModel::getStreetNumber));
        MFXTableColumn<ThirdPartyModel> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(ThirdPartyModel::getEmail));
        MFXTableColumn<ThirdPartyModel> mobilePhoneColumn = new MFXTableColumn<>("Numéro de téléphone", true, Comparator.comparing(ThirdPartyModel::getMobilePhone));


        idColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getID));
        firstNameColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getFirstName));
        lastNameColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getLastName));
        addressColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getAddress) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        cityColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getCity) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        zipCodeColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getZipCode) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        streetNumberColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getStreetNumber) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        emailColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getEmail) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        mobilePhoneColumn.setRowCellFactory(thirdParty -> new MFXTableRowCell<>(ThirdPartyModel::getMobilePhone) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});


        table.getTableColumns().addAll(idColumn, lastNameColumn, firstNameColumn, cityColumn, zipCodeColumn, addressColumn, streetNumberColumn, emailColumn, mobilePhoneColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", ThirdPartyModel::getID),
                new StringFilter<>("Libelle", ThirdPartyModel::getLastName),
                new StringFilter<>("Prénom", ThirdPartyModel::getFirstName),
                new StringFilter<>("Nom", ThirdPartyModel::getLastName),
                new StringFilter<>("Ville", ThirdPartyModel::getCity),
                new IntegerFilter<>("Code Postal", ThirdPartyModel::getZipCode),
                new StringFilter<>("Adresse", ThirdPartyModel::getAddress),
                new StringFilter<>("Téléphone", ThirdPartyModel::getMobilePhone)
        );
        table.setItems(SolidStockDataIntegration.thirdParties);
    }

    @FXML
    public void showThirdParty() {
        ThirdPartyModel thirdParties = table.getSelectionModel().getSelectedValue();

        if (thirdParties == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un tiers", DialogType.ERROR, 0);
            return;
        }

        setId(thirdParties.getID());

        openPopUp("thirdParty/showPopup.fxml", stackPane.getScene(), "Détails du tiers");

    }

    @FXML
    public void editThirdParty() {
        ThirdPartyModel thirdParties = table.getSelectionModel().getSelectedValue();

        if (thirdParties == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un tiers", DialogType.ERROR, 0);
            return;
        }

        setId(thirdParties.getID());

        openPopUp("thirdParty/editPopup.fxml", stackPane.getScene(), "Modification du tiers");

    }

    @FXML
    public void removeThirdParty() {
        ThirdPartyModel thirdParties = table.getSelectionModel().getSelectedValue();

        if (thirdParties == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un tiers", DialogType.ERROR, 0);
            return;
        }

        openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer le tiers " + thirdParties.getLastName() + " " + thirdParties.getFirstName() + " ?", DialogType.CONFIRMATION, 0);

    }


    //on double click on a row
    @FXML
    public void showThirdPartyDetails() {
        ThirdPartyModel thirdParties = table.getSelectionModel().getSelectedValue();

        if (thirdParties == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un tiers", DialogType.ERROR, 0);
            return;
        }
        openDialog(stackPane.getScene(), table.getSelectionModel().getSelectedValue().getLastName(), DialogType.CONFIRMATION, 0);
    }


}
