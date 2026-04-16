package edu.utsa.cs3443.HomeHaven.controller;

import edu.utsa.cs3443.HomeHaven.model.Asset;
import edu.utsa.cs3443.HomeHaven.model.DataStore;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AssetsController {

    @FXML private TableView<Asset> tblAssets;
    @FXML private TableColumn<Asset, String> colName;
    @FXML private TableColumn<Asset, String> colCategory;
    @FXML private TableColumn<Asset, String> colRoom;
    @FXML private TableColumn<Asset, String> colStatus;
    @FXML private TableColumn<Asset, String> colPrice;
    @FXML private TableColumn<Asset, String> colWarranty;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colCategory.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategory()));
        colRoom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRoomLocation()));
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus().toString()));
        colPrice.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%.2f", c.getValue().getPurchasePrice())));
        colWarranty.setCellValueFactory(c -> {
            if (c.getValue().getWarrantyExpiry() == null) return new SimpleStringProperty("None");
            return new SimpleStringProperty(c.getValue().getWarrantyExpiry().toString());
        });
        tblAssets.setItems(DataStore.assets);
    }
}
