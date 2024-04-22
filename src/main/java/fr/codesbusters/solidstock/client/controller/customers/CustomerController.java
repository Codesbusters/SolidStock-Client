package fr.codesbusters.solidstock.client.controller.customers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.client.business.DialogType;
import fr.codesbusters.solidstock.client.controller.DefaultShowController;
import fr.codesbusters.solidstock.client.dto.customer.GetCustomerDto;
import fr.codesbusters.solidstock.client.model.CustomerModel;
import fr.codesbusters.solidstock.client.service.RequestAPI;
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
public class CustomerController extends DefaultShowController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<CustomerModel> table;

    @FXML
    private MFXButton modifyButton;

    @FXML
    private MFXButton deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        table.autosizeColumnsOnInitialization();

        table.setOnMouseClicked(event -> {
            CustomerModel customer = table.getSelectionModel().getSelectedValue();

            if (customer != null) {
                if (customer.getIsDisabled()) {
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
    public void addCustomer() {
        openPopUp("customers/addPopup.fxml", stackPane.getScene(), "Ajouter un client");
        reloadCustomer();
    }


    private void setupTable() {
        MFXTableColumn<CustomerModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(CustomerModel::getID));
        MFXTableColumn<CustomerModel> nameColumn = new MFXTableColumn<>("Raison sociale", true, Comparator.comparing(CustomerModel::getName));
        MFXTableColumn<CustomerModel> countryColumn = new MFXTableColumn<>("Pays", true, Comparator.comparing(CustomerModel::getCountry));
        MFXTableColumn<CustomerModel> cityCodeColumn = new MFXTableColumn<>("Ville/CP", true, Comparator.comparing(CustomerModel::getCityCode));
        MFXTableColumn<CustomerModel> phoneColumn = new MFXTableColumn<>("Téléphone", true, Comparator.comparing(CustomerModel::getPhone));
        MFXTableColumn<CustomerModel> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(CustomerModel::getEmail));

        idColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(CustomerModel::getID));

        nameColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(CustomerModel::getName) {
            {
                setAlignment(Pos.CENTER_LEFT);
                if (rowCell != null && rowCell.getIsDisabled()) {
                    setStyle("-fx-text-fill: grey;");
                }
            }
        });

        countryColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(CustomerModel::getCountry) {{
            setAlignment(Pos.CENTER_LEFT);
            if (rowCell != null && rowCell.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
        }});

        cityCodeColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(CustomerModel::getCityCode) {{
            setAlignment(Pos.CENTER_LEFT);
            if (rowCell != null && rowCell.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
        }});

        phoneColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(CustomerModel::getPhone) {{
            setAlignment(Pos.CENTER_LEFT);
            if (rowCell != null && rowCell.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
            setGraphicTextGap(5);
        }});

        emailColumn.setRowCellFactory(rowCell -> new MFXTableRowCell<>(CustomerModel::getEmail) {{
            setAlignment(Pos.CENTER_LEFT);
            if (rowCell != null && rowCell.getIsDisabled()) {
                setStyle("-fx-text-fill: grey;");
            }
            setGraphicTextGap(5);
        }});

        table.getTableColumns().addAll(idColumn, nameColumn, countryColumn, cityCodeColumn, phoneColumn, emailColumn);

        table.getFilters().addAll(
                new IntegerFilter<>("Réf.", CustomerModel::getID),
                new StringFilter<>("Raison sociale", CustomerModel::getName),
                new StringFilter<>("Pays", CustomerModel::getCountry),
                new StringFilter<>("Ville/CP", CustomerModel::getCityCode),
                new StringFilter<>("Téléphone", CustomerModel::getPhone),
                new StringFilter<>("Email", CustomerModel::getEmail)
        );

        reloadCustomer();
    }

    @FXML
    public void showCustomer() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR, 0);
            return;
        }

        setId(customer.getID());

        openPopUp("customers/showPopup.fxml", stackPane.getScene(), "Détails du client");
        reloadCustomer();
    }

    @FXML
    public void editCustomer() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR, 0);
            return;
        }

        setId(customer.getID());

        openPopUp("customers/editPopup.fxml", stackPane.getScene(), "Modification du client");
        reloadCustomer();

    }

    @FXML
    public void removeCustomer() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR, 0);
            return;
        }
        boolean result = openDialog(stackPane.getScene(), "Voulez-vous vraiment supprimer le client " + customer.getName(), DialogType.CONFIRMATION, 0);
        if (!result) {
            return;
        }
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/customer/" + customer.getID(), String.class, true);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Customer removed successfully : {}", customer);
            openDialog(stackPane.getScene(), "Client " + customer.getName() + " supprimé avec succès", DialogType.INFORMATION, 0);
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression du client", DialogType.ERROR, 0);
        }
        reloadCustomer();
    }


    //on double click on a row
    @FXML
    public void showCustomerDetails() {
        CustomerModel customer = table.getSelectionModel().getSelectedValue();

        if (customer == null) {
            openDialog(stackPane.getScene(), "Veuillez sélectionner un client", DialogType.ERROR, 0);
            return;
        }
        openDialog(stackPane.getScene(), table.getSelectionModel().getSelectedValue().getName(), DialogType.CONFIRMATION, 0);
    }

    @FXML
    public void reloadCustomer() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/customer/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetCustomerDto> customerList = null;
        try {
            customerList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing customers list", e);
        }

        ObservableList<CustomerModel> customerModels = FXCollections.observableArrayList();
        assert customerList != null;
        for (GetCustomerDto customer : customerList) {
            CustomerModel customerModel = new CustomerModel();
            customerModel.setID(customer.getId());

            if (customer.getCompanyName() != null && !customer.getCompanyName().isEmpty()) {
                customerModel.setName(customer.getCompanyName());
            } else {
                customerModel.setName(customer.getFirstName() + " " + customer.getLastName());
            }

            if (customer.getCountry() == null || customer.getCountry().isEmpty()) {
                customerModel.setCountry("");
            } else {
                customerModel.setCountry(customer.getCountry());
            }

            if ((customer.getCity() == null || customer.getCity().isEmpty()) && (customer.getZipCode() == null || customer.getZipCode().isEmpty())) {
                customerModel.setCityCode("");
            } else {
                customerModel.setCityCode(customer.getCity() + " " + customer.getZipCode());
            }

            if (customer.getMobilePhone() != null && !customer.getMobilePhone().isEmpty()) {
                customerModel.setPhone(customer.getMobilePhone());
            } else {
                if (customer.getWorkPhone() != null && !customer.getWorkPhone().isEmpty()) {
                    customerModel.setPhone(customer.getWorkPhone());
                } else {
                    if (customer.getHomePhone() != null && !customer.getHomePhone().isEmpty()) {
                        customerModel.setPhone(customer.getHomePhone());
                    } else {
                        customerModel.setPhone("");
                    }
                }
            }

            if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
                customerModel.setEmail("");
            } else {
                customerModel.setEmail(customer.getEmail());
            }
            customerModel.setIsDisabled(customer.isDisabled());
            customerModels.add(customerModel);
        }
        customerModels.sort(Comparator.comparingInt(CustomerModel::getID));
        table.getItems().addAll(customerModels);
    }
}
