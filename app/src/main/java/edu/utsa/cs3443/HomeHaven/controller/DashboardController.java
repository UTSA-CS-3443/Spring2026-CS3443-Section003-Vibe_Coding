package edu.utsa.cs3443.HomeHaven.controller;

import edu.utsa.cs3443.HomeHaven.model.DataStore;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML private Label lblAssetCount;
    @FXML private Label lblDueSoon;
    @FXML private Label lblOverdue;
    @FXML private Label lblExpiredWarranties;

    @FXML
    public void initialize() {
        lblAssetCount.setText(String.valueOf(DataStore.assets.size()));
        lblDueSoon.setText(String.valueOf(DataStore.getDueSoonCount()));
        lblOverdue.setText(String.valueOf(DataStore.getOverdueCount()));
        lblExpiredWarranties.setText(String.valueOf(DataStore.getExpiredWarrantyCount()));
    }
}
