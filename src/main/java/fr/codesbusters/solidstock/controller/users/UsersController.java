package fr.codesbusters.solidstock.controller.users;

import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.model.SolidStockModel;
import fr.codesbusters.solidstock.model.UsersModel;
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

@Slf4j
@Controller
public class UsersController extends DefaultShowController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<UsersModel> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    @FXML
    public void addUser() {
        openPopUp("users/addPopup.fxml", stackPane.getScene(), "Ajouter un utilisateur");
    }

    @FXML
    public void editUser() {
        UsersModel user = table.getSelectionModel().getSelectedValue();

        if (user == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un utilisateur", DialogType.ERROR);
            return;
        }

        setId(user.getID());

        openPopUp("users/editPopup.fxml", stackPane.getScene(), "Modification de l'utilisateur");

    }

    @FXML
    public void showUser() {
        UsersModel user = table.getSelectionModel().getSelectedValue();

        if (user == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un utilisateur", DialogType.ERROR);
            return;
        }

        setId(user.getID());

        openPopUp("users/showPopup.fxml", stackPane.getScene(), "Détails de l'utilisateur");

    }

    @FXML
    public void removeUser() {
        UsersModel user = table.getSelectionModel().getSelectedValue();

        if (user == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un utilisateur", DialogType.ERROR);
            return;
        }

        openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer l'utilisateur " + user.getUserLoginName() + " ?", DialogType.CONFIRMATION);

    }

    private void setupTable() {
        MFXTableColumn<UsersModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(UsersModel::getID));
        MFXTableColumn<UsersModel> surNameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(UsersModel::getSurName));
        MFXTableColumn<UsersModel> firstNameColumn = new MFXTableColumn<>("Prénom", true, Comparator.comparing(UsersModel::getFirstName));
        MFXTableColumn<UsersModel> thirdPartyIdColumn = new MFXTableColumn<>("Id tiers", true, Comparator.comparing(UsersModel::getThirdPartyId));
        MFXTableColumn<UsersModel> passwordColumn = new MFXTableColumn<>("Mot de passe", true, Comparator.comparing(UsersModel::getPassword));
        MFXTableColumn<UsersModel> roleIdColumn = new MFXTableColumn<>("Id Rôle", true, Comparator.comparing(UsersModel::getRoleId));
        MFXTableColumn<UsersModel> roleNameColumn = new MFXTableColumn<>("Rôle", true, Comparator.comparing(UsersModel::getRoleName));
        MFXTableColumn<UsersModel> roleDescriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(UsersModel::getRoleDescription));
        MFXTableColumn<UsersModel> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(UsersModel::getEmail));
        MFXTableColumn<UsersModel> phoneNumberColumn = new MFXTableColumn<>("Numéro téléphone", true, Comparator.comparing(UsersModel::getPhoneNumber));
        MFXTableColumn<UsersModel> userLoginNameColumn = new MFXTableColumn<>("Identifiant", true, Comparator.comparing(UsersModel::getUserLoginName));

        idColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getID));
        surNameColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getSurName));
        firstNameColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getFirstName));
        thirdPartyIdColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getThirdPartyId));
        passwordColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getPassword));
        roleIdColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getRoleId));
        roleNameColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getRoleName));
        roleDescriptionColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getRoleDescription));
        emailColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getEmail));
        phoneNumberColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getPhoneNumber));
        userLoginNameColumn.setRowCellFactory(usersModel -> new MFXTableRowCell<>(UsersModel::getUserLoginName));

        table.getTableColumns().addAll(idColumn, surNameColumn, firstNameColumn, emailColumn, phoneNumberColumn, userLoginNameColumn, roleNameColumn);
        table.getFilters().addAll(
                new StringFilter<>("Nom", UsersModel::getSurName),
                new StringFilter<>("Prénom", UsersModel::getFirstName),
                new StringFilter<>("Email", UsersModel::getEmail),
                new IntegerFilter<>("Id Tiers", UsersModel::getThirdPartyId),
                new StringFilter<>("Rôle", UsersModel::getRoleName)
        );
        table.setItems(SolidStockModel.users);
    }
}
