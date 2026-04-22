package edu.utsa.cs3443.HomeHaven.controller;

import edu.utsa.cs3443.HomeHaven.model.DataStore;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class DashboardController {

    @FXML private Label lblAssetCount;
    @FXML private Label lblDueSoon;
    @FXML private Label lblOverdue;
    @FXML private Label lblExpiredWarranties;
    @FXML private ListView<String> recentList;

    @FXML
    public void initialize() {
        refresh(); // load data on startup
    }

    // 🔥 This gets called whenever dashboard is opened
    public void refresh() {

        int total = DataStore.assets.size();
        int dueSoon = DataStore.getDueSoonCount();
        int overdue = DataStore.getOverdueCount();
        int expired = DataStore.getExpiredWarrantyCount();

        lblAssetCount.setText(String.valueOf(total));
        lblDueSoon.setText(String.valueOf(dueSoon));
        lblOverdue.setText(String.valueOf(overdue));
        lblExpiredWarranties.setText(String.valueOf(expired));

        // 🔥 Dynamic colors
        lblOverdue.setStyle(
                overdue > 0
                        ? "-fx-text-fill: red; -fx-font-size: 22; -fx-font-weight: bold;"
                        : "-fx-font-size: 22; -fx-font-weight: bold;"
        );

        lblDueSoon.setStyle(
                dueSoon > 0
                        ? "-fx-text-fill: orange; -fx-font-size: 22; -fx-font-weight: bold;"
                        : "-fx-font-size: 22; -fx-font-weight: bold;"
        );

        // 🔥 Refresh list (no duplicates)
        recentList.getItems().clear();
        DataStore.assets.forEach(asset ->
                recentList.getItems().add(asset.getName())
        );
    }
}