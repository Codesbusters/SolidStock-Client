package fr.codesbusters.solidstock.client.controller.selectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.role.GetRoleDto;
import fr.codesbusters.solidstock.client.listener.RoleSelectorListener;
import fr.codesbusters.solidstock.client.model.RoleModel;
import fr.codesbusters.solidstock.client.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class RoleSelectorController extends DefaultShowController implements Initializable {

    @FXML
    public AnchorPane stackPane;

    @FXML
    public MFXTableView<RoleModel> table;

    public Stage parentStage;

    public RoleSelectorListener listener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();
    }

    public void setStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setListener(RoleSelectorListener listener) {
        this.listener = listener;
    }

    @FXML
    public void addRole() {
        openPopUp("users/role/addPopup.fxml", stackPane.getScene(), "Ajouter un rôle");
        reloadRole();
    }

    public void setupTable() {
        MFXTableColumn<RoleModel> idColumn = new MFXTableColumn<>("Réf.",true, Comparator.comparing(RoleModel::getID));
        MFXTableColumn<RoleModel> nameColumn = new MFXTableColumn<>("Nom",true, Comparator.comparing(RoleModel::getRoleName));

        idColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(RoleModel::getID) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });

        nameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(RoleModel::getRoleName) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });

        table.getTableColumns().addAll(idColumn, nameColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", RoleModel::getID),
                new StringFilter<>("Nom rôle", RoleModel::getRoleName)
        );
        reloadRole();
    }

    @FXML
    public void showRole() {
        RoleModel role = table.getSelectionModel().getSelectedValue();

        if (role == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un rôle", DialogType.ERROR, 0);
            return;
        }

        setId(role.getID());
        openPopUp("users/role/showPopup.fxml", stackPane.getScene(),"Détail du rôle");
        reloadRole();
    }

    @FXML
    public void editRole() {
        RoleModel role = table.getSelectionModel().getSelectedValue();

        if (role == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un rôle", DialogType.ERROR, 0);
            return;
        }

        setId(role.getID());

        openPopUp("users/role/editPopup.fxml", stackPane.getScene(), "Modification du rôle");
        reloadRole();
    }

    @FXML
    public void removeRole() {
        RoleModel role = table.getSelectionModel().getSelectedValue();

        if (role == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un rôle", DialogType.ERROR, 0);
            return;
        }
        boolean result = openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer le rôle " + role.getRoleName() + " ?", DialogType.CONFIRMATION, 0);
        if (!result) {
            return;
        }
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/role/" + role.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Role removed successfully : {}", role);
            openDialog(stackPane.getScene(), "Role " + role.getRoleName() + " supprimé avec succès", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression du Rôle", DialogType.ERROR, 0);
        }
        reloadRole();
    }

    @FXML
    public void reloadRole() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/role/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetRoleDto> roleList = null;
        try {
            roleList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing role list", e);
        }

        ObservableList<RoleModel> roleModels = FXCollections.observableArrayList();
        assert roleList != null;
        for (GetRoleDto role : roleList) {
            RoleModel roleModel = new RoleModel();
            roleModel.setID(role.getId());

            if (role.getName() == null || role.getName().isEmpty()) {
                roleModel.setRoleName("");
            } else
            {
                roleModel.setRoleName(role.getName());
            }
            roleModel.setIsDisabled(role.isDisabled());
            roleModels.add(roleModel);
        }
        roleModels.sort(Comparator.comparingInt(RoleModel::getID));
        table.getItems().addAll(roleModels);
    }

    @FXML
    private void submitAction(ActionEvent event) {

        // Obtenez l'ID sélectionné de la table
        RoleModel selectedValue = table.getSelectionModel().getSelectedValue();

        // Vérifiez si l'ID est null
        if (selectedValue != null) {
            String roleId = String.valueOf(selectedValue.getID());
            String roleName = selectedValue.getRoleName();
            if (listener != null) {
                listener.processRoleContent(roleId, roleName);
            } else {
                openDialog(table.getScene(), "Une erreur est survenue, veuillez réessayer.", DialogType.ERROR, 0);
            }

            // Fermez la fenêtre pop-up
            parentStage.close();
        } else {
            openDialog(table.getScene(), "Veuillez séléctionner un rôle", DialogType.ERROR, 0);
        }
    }

    @FXML
    private void cancel() {
        parentStage.close();
    }
}
