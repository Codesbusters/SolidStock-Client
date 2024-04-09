package fr.codesbusters.solidstock.controller.users;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.customer.GetCustomerDto;
import fr.codesbusters.solidstock.dto.role.GetRoleDto;
import fr.codesbusters.solidstock.dto.user.GetUserDto;
import fr.codesbusters.solidstock.dto.user.PostUserDto;
import fr.codesbusters.solidstock.listener.CustomerSelectorListener;
import fr.codesbusters.solidstock.model.RoleModel;
import fr.codesbusters.solidstock.service.IntChecker;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class UsersEditController extends DefaultShowController implements Initializable, CustomerSelectorListener {

    @FXML
    public StackPane stackPane;

    @FXML
    public MFXTextField userId;
    @FXML
    public MFXTextField userFirstName;
    @FXML
    public MFXTextField userLastName;
    @FXML
    public MFXTextField userMail;
    @FXML
    public MFXTextField userPassword;
    @FXML
    public MFXTextField userConfirmPassword;
    @FXML
    public Label customerName;
    @FXML
    public MFXTextField userCustomerId;

    @FXML
    private MFXTableView<RoleModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId.setText(String.valueOf(getId()));

        RequestAPI requestAPIRole = new RequestAPI();

        ResponseEntity<String> responseRoleList = requestAPIRole.sendGetRequest("/user/" + getId(), String.class, true, true);
        if (responseRoleList.getStatusCode().is2xxSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            try {

                GetUserDto user = mapper.readValue(responseRoleList.getBody(), new TypeReference<>() {
                });
                userFirstName.setText(user.getFirstName());
                userLastName.setText(user.getLastName());
                userMail.setText(user.getEmail());
                userPassword.setText(user.getPassword());
                userConfirmPassword.setText(user.getPassword());
                if (user.getCustomer() != null) {
                    userCustomerId.setText(String.valueOf(user.getCustomer().getId()));
                    if (user.getCustomer().getCompanyName() != null) {
                        customerName.setText(user.getCustomer().getCompanyName());
                    } else {
                        customerName.setText("");
                    }
                } else {
                    userCustomerId.setText("0");
                    customerName.setText("");
                }
            } catch (Exception e) {
                log.error("Error while parsing user", e);
            }
        }

        setupTable();
    }

    private void setupTable() {
        MFXTableColumn<RoleModel> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(RoleModel::getID));
        MFXTableColumn<RoleModel> nameColumn = new MFXTableColumn<>("Nom", true, Comparator.comparing(RoleModel::getRoleName));

        idColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(RoleModel::getID) {{
            setAlignment(Pos.CENTER_LEFT);
            if (rowCell != null && rowCell.getIsDisabled()) {
                setStyle("-fx-opacity: 0.5;");
            }
        }});
        nameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(RoleModel::getRoleName) {{
            setAlignment(Pos.CENTER_LEFT);
            if (rowCell != null && rowCell.getIsDisabled()) {
                setStyle("-fx-opacity: 0.5;");
            }
        }});

        table.getTableColumns().addAll(idColumn, nameColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("ID", RoleModel::getID),
                new StringFilter<>("Nom", RoleModel::getRoleName)
        );

        reloadRole();
    }

    @FXML
    public void selectCustomer() {
        openCustomerSelector(stackPane.getScene(), this);
    }

    @Override
    public void processCustomerContent(String customerContent) {
        String[] customer = customerContent.split("-");
        userCustomerId.setText(customer[0]);
        customerName.setText(customer[1]);
    }

    @FXML
    public void editUser() throws NumberFormatException, UnsupportedEncodingException {
        String userMailString = userMail.getText();
        String userPasswordString = userPassword.getText();
        String userConfirmPasswordString = userConfirmPassword.getText();
        String lastNameString = userLastName.getText();
        String firstNameString = userFirstName.getText();

        // Vérification du nom
        if (lastNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un nom", DialogType.ERROR, 0);
            return;
        }

        // Vérification du prénom
        if (firstNameString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un prénom", DialogType.ERROR, 0);
            return;
        }

        // Vérification du mail
        if (userMailString.isEmpty()) {
            openDialog(stackPane.getScene(), "Veuillez renseigner un mail", DialogType.ERROR, 0);
            return;
        }

        // Vérification de la confirmation du mot de passe
        if (userPasswordString != null && !userConfirmPasswordString.equals(userPasswordString)) {
            if (!userConfirmPasswordString.isBlank() && !userPasswordString.isBlank()) {
                openDialog(stackPane.getScene(), "Les mots de passe ne correspondent pas", DialogType.ERROR, 0);
                return;
            }
        }


        // Création de l'objet User
        PostUserDto userDto = new PostUserDto();
        userDto.setFirstName(firstNameString);
        userDto.setLastName(lastNameString);
        userDto.setEmail(userMailString);
        userDto.setPassword(userPasswordString);

        // Envoie de la requête
        RequestAPI requestAPI = new RequestAPI();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(userDto);
        } catch (Exception e) {
            log.error("Error while parsing user list", e);
        }
        try {
            ResponseEntity<String> responseEntity = requestAPI.sendPutRequest("/user/" + getId(), userDto, String.class, true);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("User modified successfully : {}", userDto);
                ObjectMapper mapperResponse = new ObjectMapper();
                GetUserDto userResponse = null;
                try {
                    userResponse = mapperResponse.readValue(responseEntity.getBody(), new TypeReference<>() {
                    });
                } catch (Exception e) {
                    log.error("Error while parsing user list", e);
                }
                cancel();
                openDialog(stackPane.getScene(), "Utilisateur " + userDto.getFirstName().toLowerCase() + " " + userDto.getLastName().toUpperCase() + " modifié avec succès", DialogType.INFORMATION, 0);
            } else {
                openDialog(stackPane.getScene(), "Erreur lors de la modification de l'utilisateur " + userDto.getFirstName().toLowerCase() + " " + userDto.getLastName().toUpperCase(), DialogType.ERROR, 0);
            }
        } catch (HttpClientErrorException.BadRequest ex) {
            String responseBody = ex.getResponseBodyAsString();
            if (responseBody.contains("Customer already has a user")) {
                openDialog(stackPane.getScene(), "Le client a déjà un utilisateur associé", DialogType.ERROR, 0);
            } else if (responseBody.contains("Email is already in use")) {
                openDialog(stackPane.getScene(), "L'adresse e-mail est déjà utilisée", DialogType.ERROR, 0);
            } else {
                openDialog(stackPane.getScene(), "Une erreur inattendue est survenue", DialogType.ERROR, 0);
            }
        } catch (Exception ex) {
            openDialog(stackPane.getScene(), "Une erreur inattendue est survenue", DialogType.ERROR, 0);
        }

        cancel();

        openDialog(stackPane.getScene(), "Utilisateur " + userDto.getFirstName().toLowerCase() + " " + userDto.getLastName().toUpperCase() + " modifié avec succès", DialogType.INFORMATION, 0);
    }

    @FXML
    public void onIdCustomerChanged(KeyEvent event) {
        Object source = event.getSource();
        if (source instanceof MFXTextField textField && textField == userCustomerId) {
            String text = textField.getText();
            if (!text.isEmpty() && IntChecker.isValidIntegerInput(text)) {
                int customerId = Integer.parseInt(text);
                String customerName = getCustomerNameById(customerId);
                this.customerName.setText(customerName);
            } else if (!IntChecker.isValidIntegerInput(text)) {
                textField.setText(text.substring(0, text.length() - 1));
            } else {
                this.customerName.setText("");
            }
        }
    }


    private String getCustomerNameById(int customerId) {
        String customer = findCustomerById(customerId);
        this.customerName.setText(Objects.requireNonNullElse(customer, ""));
        return customer;
    }

    private String findCustomerById(int customerId) {
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/customer/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetCustomerDto> customerList = null;
        try {
            customerList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing customer list", e);
        }

        assert customerList != null;
        for (GetCustomerDto customer : customerList) {
            if (customer.getId() == customerId) {
                return customer.getCompanyName();
            }
        }
        return null;
    }

    @FXML
    public void reloadRole() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/user/" + getId(), String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        GetUserDto user = null;
        try {
            user = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing invoice list", e);
        }

        assert user != null;
        if (user.getCustomer() == null) {
            userCustomerId.setText("");
            customerName.setText("Pas lié à un client");
        } else {
            userCustomerId.setText(String.valueOf(user.getCustomer().getId()));
            customerName.setText(user.getCustomer().getCompanyName());
        }
        userMail.setText(user.getEmail());

        ObservableList<RoleModel> roleModels = FXCollections.observableArrayList();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (GetRoleDto roleDto : user.getRoles()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setID(roleDto.getId());
                roleModel.setRoleName(roleDto.getName());

                roleModels.add(roleModel);
            }
        } else {
            for (GetRoleDto ignored : user.getRoles()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setID(0);
                roleModel.setRoleName("Aucun rôle attribué");

                roleModels.add(roleModel);
            }
        }

        table.getItems().addAll(roleModels);
        table.autosizeColumnsOnInitialization();
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addRole(ActionEvent actionEvent) {
        openPopUp("/users/addRolePopup.fxml", stackPane.getScene(), "Ajouter un rôle à l'utilisateur.");
        reloadRole();
    }

    @FXML
    public void removeRole(ActionEvent actionEvent) {
        RoleModel roleModel = table.getSelectionModel().getSelectedValues().getFirst();

        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/user/" + getId() + "/role/" + roleModel.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Role pour l'utilisateur supprimé avec succès.", DialogType.INFORMATION, 0);
            reloadRole();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression du role pour l'utilisateur.", DialogType.ERROR, 0);
        }
    }
}