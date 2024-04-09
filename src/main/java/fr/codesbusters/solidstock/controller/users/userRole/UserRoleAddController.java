package fr.codesbusters.solidstock.controller.users.userRole;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.role.GetRoleDto;
import fr.codesbusters.solidstock.dto.user.GetUserDto;
import fr.codesbusters.solidstock.dto.user.PostUserDto;
import fr.codesbusters.solidstock.model.RoleModel;
import fr.codesbusters.solidstock.service.RequestAPI;
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
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class UserRoleAddController extends DefaultShowController implements Initializable {

    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTableView<RoleModel> table;

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addNewRole() throws NumberFormatException, UnsupportedEncodingException {
        RoleModel selectedValue = table.getSelectionModel().getSelectedValue();
        if (selectedValue == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un rôle", DialogType.ERROR, 0);
            return;
        }


        int roleId = selectedValue.getID();

        // Récupérer l'utilisateur existant
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntityUser = requestAPI.sendGetRequest("/user/" + getId(), String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetUserDto user = null;
        try {
            user = mapper.readValue(responseEntityUser.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing user list", e);
            openDialog(stackPane.getScene(), "Erreur lors de la récupération de l'utilisateur", DialogType.ERROR, 0);
            return;
        }
        if (user != null) {
            GetRoleDto newRole = new GetRoleDto();
            newRole.setId(roleId);
            PostUserDto updateUser = new PostUserDto();
            updateUser.setRole(newRole);

            String json = null;
            try {
                json = mapper.writeValueAsString(updateUser);
            } catch (Exception e) {
                log.error("Error while converting object to json", e);
                openDialog(stackPane.getScene(), "Erreur lors de la conversion de l'objet en JSON", DialogType.ERROR, 0);
                return;
            }

            ResponseEntity<String> responseEntity = requestAPI.sendPostRequest("/user/" + user.getId() + "/role/" + roleId, json, String.class, true, true);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                cancel();
            } else {
                openDialog(stackPane.getScene(), "Erreur lors de l'ajout d'un rôle à l'utilisateur", DialogType.ERROR, 0);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.autosizeColumnsOnInitialization();
        MFXTableColumn<RoleModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(RoleModel::getID));
        MFXTableColumn<RoleModel> nameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(RoleModel::getRoleName));

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
            } else {
                roleModel.setRoleName(role.getName());
            }
            roleModel.setIsDisabled(role.isDisabled());
            roleModels.add(roleModel);
        }
        roleModels.sort(Comparator.comparingInt(RoleModel::getID));
        table.getItems().addAll(roleModels);
    }
}