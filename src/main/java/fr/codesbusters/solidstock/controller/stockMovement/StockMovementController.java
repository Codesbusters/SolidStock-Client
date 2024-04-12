package fr.codesbusters.solidstock.controller.stockMovement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codesbusters.solidstock.business.DialogType;
import fr.codesbusters.solidstock.controller.DefaultShowController;
import fr.codesbusters.solidstock.dto.stockMovement.GetStockMovementDto;
import fr.codesbusters.solidstock.model.StockMovementModel;
import fr.codesbusters.solidstock.service.RequestAPI;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.*;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@Controller
public class StockMovementController extends DefaultShowController implements Initializable {
    @FXML
    private StackPane stackPane;

    @FXML
    private MFXTableView<StockMovementModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
    }

    private void setupTable() {
        MFXTableColumn<StockMovementModel> icon = new MFXTableColumn<>("", true, Comparator.comparing(StockMovementModel::getInOut));
        MFXTableColumn<StockMovementModel> typeColumn = new MFXTableColumn<>("Type", true, Comparator.comparing(StockMovementModel::getType));
        MFXTableColumn<StockMovementModel> actionDate = new MFXTableColumn<>("Date du mouvement", true, Comparator.comparing(StockMovementModel::getDateAction));
        MFXTableColumn<StockMovementModel> idColumn = new MFXTableColumn<>("Réf.", true, Comparator.comparing(StockMovementModel::getID));
        MFXTableColumn<StockMovementModel> refProductColumn = new MFXTableColumn<>("Réf. Produit", true, Comparator.comparing(StockMovementModel::getRefProfuct));
        MFXTableColumn<StockMovementModel> productNameColumn = new MFXTableColumn<>("Nom Produit", true, Comparator.comparing(StockMovementModel::getProductName));
        MFXTableColumn<StockMovementModel> quantityColumn = new MFXTableColumn<>("Quantité", true, Comparator.comparing(StockMovementModel::getQuantite));
        MFXTableColumn<StockMovementModel> expiredDateColumn = new MFXTableColumn<>("Date de péremption", true, Comparator.comparing(StockMovementModel::getExpiredDate));
        MFXTableColumn<StockMovementModel> batchNumberColumn = new MFXTableColumn<>("Numéro de lot", true, Comparator.comparing(StockMovementModel::getBatchNumber));
        MFXTableColumn<StockMovementModel> noteColumn = new MFXTableColumn<>("Note", true, Comparator.comparing(StockMovementModel::getMotif));

        icon.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getInOut) {{
            MFXFontIcon iconPlus = new MFXFontIcon("fas-plus");
            iconPlus.setColor(Color.rgb(0, 255, 0));
            iconPlus.setSize(20);

            MFXFontIcon iconMinus = new MFXFontIcon("fas-minus");
            iconMinus.setColor(Color.rgb(255, 0, 0));
            iconMinus.setSize(20);

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            setAlignment(Pos.CENTER);
            setGraphic(stockMovementModel.getInOut() ? iconPlus : iconMinus);


        }});

        typeColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getType) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        actionDate.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getDateAction) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        idColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getID) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        refProductColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getRefProfuct) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        productNameColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getProductName) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        quantityColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getQuantite) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        expiredDateColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getExpiredDate) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        batchNumberColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getBatchNumber) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        noteColumn.setRowCellFactory(stockMovementModel -> new MFXTableRowCell<>(StockMovementModel::getMotif) {{
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setAlignment(Pos.CENTER);
        }});

        table.getTableColumns().addAll(icon, typeColumn, actionDate,  refProductColumn, productNameColumn, quantityColumn, expiredDateColumn, batchNumberColumn, noteColumn);
        table.getFilters().addAll(
                new BooleanFilter<>("Entrée", StockMovementModel::getInOut),
                new BooleanFilter<>("Sortie", stockMovementModel -> !stockMovementModel.getInOut()),
                new StringFilter<>("Type", StockMovementModel::getType),
                new StringFilter<>("Date du mouvement", StockMovementModel::getDateAction),
                new IntegerFilter<>("Réf. Produit", StockMovementModel::getRefProfuct),
                new StringFilter<>("Nom Produit", StockMovementModel::getProductName),
                new DoubleFilter<>("Quantité", StockMovementModel::getQuantite),
                new StringFilter<>("Date de péremption", StockMovementModel::getExpiredDate),
                new StringFilter<>("Numéro de lot", StockMovementModel::getBatchNumber),
                new StringFilter<>("Motif", StockMovementModel::getMotif)
        );

        reloadStockMovement();

    }

    @FXML
    public void openConfirmRemove() {

       boolean isOk = openDialog(stackPane.getScene(),"Êtes-vous sûr de vouloir supprimer ce mouvement de stock ?", DialogType.CONFIRMATION,  0);
        if (!isOk) {
            return;
        }
        StockMovementModel stockMovementModel = table.getSelectionModel().getSelectedValue();
        RequestAPI requestAPI = new RequestAPI();
        ResponseEntity<String> responseEntity = requestAPI.sendDeleteRequest("/stock-movement/" + stockMovementModel.getID(), String.class, true);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            openDialog(stackPane.getScene(), "Le mouvement de stock a été supprimé avec succès", DialogType.INFORMATION, 0);
            reloadStockMovement();
        } else {
            openDialog(stackPane.getScene(), "Erreur lors de la suppression du mouvement de stock", DialogType.ERROR, 0);
        }
    }

    @FXML
    public void openDeliveryPopup() {
        openPopUp("stockMovement/deliveryPopup.fxml", stackPane.getScene(), "Nouvelle livraison");
    }

    @FXML
    public void reloadStockMovement() {
        table.getItems().clear();

        RequestAPI requestAPI = new RequestAPI();

        ResponseEntity<String> responseEntity = requestAPI.sendGetRequest("/stock-movement/all", String.class, true, true);
        ObjectMapper mapper = new ObjectMapper();
        List<GetStockMovementDto> stockMovementList = null;
        try {
            stockMovementList = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error while parsing product list", e);
        }
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ObservableList<StockMovementModel> stockMovementModels = FXCollections.observableArrayList();
        assert stockMovementList != null;
        for (GetStockMovementDto stockMovementDto : stockMovementList) {
            StockMovementModel stockMovementModel = new StockMovementModel();
            stockMovementModel.setID(stockMovementDto.getId());
            stockMovementModel.setType(stockMovementDto.getType());
            stockMovementModel.setDateAction(LocalDate.parse(stockMovementDto.getCreatedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")).format(formatters));
            stockMovementModel.setRefProfuct(stockMovementDto.getProduct().getId());
            stockMovementModel.setProductName(stockMovementDto.getProduct().getName());
            stockMovementModel.setQuantite(stockMovementDto.getQuantity());
            stockMovementModel.setMotif(stockMovementDto.getNote() == null ? "" : stockMovementDto.getNote());
            stockMovementModel.setInOut(stockMovementDto.getType().startsWith("IN"));
            stockMovementModel.setExpiredDate(stockMovementDto.getExpiredDate() == null ? "" : LocalDate.parse(stockMovementDto.getExpiredDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(formatters));
            stockMovementModel.setBatchNumber(stockMovementDto.getBatchNumber() == null ? "" : stockMovementDto.getBatchNumber());
            stockMovementModels.add(stockMovementModel);
        }
        stockMovementModels.sort(Comparator.comparingLong(StockMovementModel::getID));
        table.getItems().addAll(stockMovementModels);
        table.autosizeColumnsOnInitialization();
    }
}
