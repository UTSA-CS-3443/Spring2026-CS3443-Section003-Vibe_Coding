package edu.utsa.cs3443.HomeHaven.controller;

import edu.utsa.cs3443.HomeHaven.model.Asset;
import edu.utsa.cs3443.HomeHaven.model.DataStore;
import edu.utsa.cs3443.HomeHaven.model.Reminder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

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

    @FXML
    private void handleDownloadCsv() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Assets CSV");
        fileChooser.setInitialFileName("assets.csv");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showSaveDialog(recentList.getScene().getWindow());
        if (file == null) return;

        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            // --- Assets table ---
            writer.write("ASSETS\n");
            writer.write("Name,Category,Room,Status,Purchase Price,Purchase Date,Warranty Expiry,Notes\n");
            for (Asset asset : DataStore.assets) {
                writer.write(String.format("%s,%s,%s,%s,%.2f,%s,%s,%s\n",
                        escapeCsv(asset.getName()),
                        escapeCsv(asset.getCategory()),
                        escapeCsv(asset.getRoomLocation()),
                        escapeCsv(asset.getStatus().toString()),
                        asset.getPurchasePrice(),
                        asset.getPurchaseDate() != null ? asset.getPurchaseDate().toString() : "",
                        asset.getWarrantyExpiry() != null ? asset.getWarrantyExpiry().toString() : "",
                        escapeCsv(asset.getNotes())
                ));
            }
            // --- Reminders table ---
            writer.write("\n");
            writer.write("REMINDERS\n");
            writer.write("Asset,Title,Type,Due Date,Notes,Status\n");
            for (Reminder reminder : DataStore.reminders) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                        escapeCsv(reminder.getAssetName()),
                        escapeCsv(reminder.getTitle()),
                        escapeCsv(reminder.getType().toString()),
                        reminder.getDueDate() != null ? reminder.getDueDate().toString() : "",
                        escapeCsv(reminder.getNotes()),
                        reminder.isDone() ? "Done" : reminder.isOverdue() ? "Overdue" : reminder.isDueSoon() ? "Due Soon" : "Upcoming"
                ));
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Successful");
            alert.setHeaderText(null);
            alert.setContentText("Assets exported to:\n" + file.getAbsolutePath());
            alert.showAndWait();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export Failed");
            alert.setHeaderText(null);
            alert.setContentText("Could not write file: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}