package fr.codesbusters.solidstock.controller.users;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.user.GetUserDto;
import fr.codesbusters.solidstock.model.UsersModel;
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
public class UsersController extends DefaultShowController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<UsersModel> table;

    @FXML
    private MFXButton modifyButton;

    @FXML
    private MFXButton deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        table.autosizeColumnsOnInitialization();
        table.setOnMouseClicked(event -> {
            UsersModel user = table.getSelectionModel().getSelectedValue();

            if (user != null) {
                if (user.getIsDisabled()) {
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
    public void addUser() {
        openPopUp("users/addPopup.fxml", stackPane.getScene(), "Ajouter un utilisateur");
        reloadUser();
    }

    @FXML
    public void editUser() {
        UsersModel user = table.getSelectionModel().getSelectedValue();

        if (user == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un utilisateur", DialogType.ERROR, 0);
            return;
        }

        setId(user.getID());

        openPopUp("users/editPopup.fxml", stackPane.getScene(), "Modification de l'utilisateur");
        reloadUser();

    }

    @FXML
    public void showUser() {
        UsersModel user = table.getSelectionModel().getSelectedValue();

        if (user == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un utilisateur", DialogType.ERROR, 0);
            return;
        }

        setId(user.getID());

        openPopUp("users/showPopup.fxml", stackPane.getScene(), "Détails de l'utilisateur");
        reloadUser();
    }

    @FXML
    public void removeUser() {
        UsersModel user = table.getSelectionModel().getSelectedValue();

        if (user == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un utilisateur", DialogType.ERROR, 0);
            return;
        }

        openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer l'utilisateur " + user.getFirstName() + " " + user.getLastName() + " ?", DialogType.CONFIRMATION, 0);
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/user/" + user.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Utilisateur " + user.getFirstName() + " " + user.getLastName().toUpperCase() + " supprimé avec succès", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression de l'utilisateur", DialogType.ERROR, 0);
        }
        reloadUser();
    }

    private void setupTable() {
        MFXTableColumn<UsersModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(UsersModel::getID));
        MFXTableColumn<UsersModel> nameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(UsersModel::getLastName));
        MFXTableColumn<UsersModel> firstNameColumn = new MFXTableColumn<>("Prénom", true, Comparator.comparing(UsersModel::getFirstName));
        MFXTableColumn<UsersModel> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(UsersModel::getEmail));
        MFXTableColumn<UsersModel> customerIdColumn = new MFXTableColumn<>("Id client", true, Comparator.comparing(UsersModel::getCustomerId));

        idColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(UsersModel::getID) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });
        nameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(UsersModel::getLastName) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });
        firstNameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(UsersModel::getFirstName) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });
        emailColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(UsersModel::getEmail) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });
        customerIdColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(UsersModel::getCustomerId) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-opacity: 0.5;");
                }
            }
        });

        table.getTableColumns().addAll(idColumn, nameColumn, firstNameColumn, emailColumn, customerIdColumn);
        table.getFilters().addAll(
                new StringFilter<>("Nom", UsersModel::getLastName),
                new StringFilter<>("Prénom", UsersModel::getFirstName),
                new StringFilter<>("Email", UsersModel::getEmail),
                new IntegerFilter<>("Id Client", UsersModel::getCustomerId)
        );
        reloadUser();
    }

    @FXML
    public void reloadUser() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/user/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetUserDto> userList = null;
        try {
            userList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing user list", e);
        }

        ObservableList<UsersModel> usersModels = FXCollections.observableArrayList();
        assert userList != null;
        for (GetUserDto user : userList) {
            UsersModel usersModel = new UsersModel();
            usersModel.setID(user.getId());
            usersModel.setEmail(user.getEmail() != null && !user.getEmail().isEmpty() ? user.getEmail() : "");
            usersModel.setCustomerId(user.getCustomer() != null ? user.getCustomer().getId() : 0);
            usersModel.setLastName(user.getLastName() != null ? user.getLastName() : "");
            usersModel.setFirstName(user.getFirstName() != null ? user.getFirstName() : "");
            usersModel.setIsDisabled(user.isDeleted());
            usersModels.add(usersModel);
        }
        usersModels.sort(Comparator.comparingInt(UsersModel::getID));
        table.getItems().addAll(usersModels);
    }
}