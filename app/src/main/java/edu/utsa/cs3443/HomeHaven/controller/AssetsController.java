package edu.utsa.cs3443.HomeHaven.controller;

import edu.utsa.cs3443.HomeHaven.model.Asset;
import edu.utsa.cs3443.HomeHaven.model.AssetStatus;
import edu.utsa.cs3443.HomeHaven.model.DataStore;
import edu.utsa.cs3443.HomeHaven.model.Reminder;
import edu.utsa.cs3443.HomeHaven.model.ReminderType;
import javafx.scene.layout.GridPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.Optional;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AssetsController {

    @FXML private TableView<Asset> tblAssets;
    @FXML private TableColumn<Asset, String> colName;
    @FXML private TableColumn<Asset, String> colCategory;
    @FXML private TableColumn<Asset, String> colRoom;
    @FXML private TableColumn<Asset, String> colStatus;
    @FXML private TableColumn<Asset, String> colPrice;
    @FXML private TableColumn<Asset, String> colWarranty;
    @FXML private TableColumn<Asset, String> colPurchaseDate;
    @FXML private TableColumn<Asset, String> colNotes;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colCategory.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategory()));
        colRoom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRoomLocation()));
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus().toString()));

        colStatus.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) { setText(null); setStyle(""); return; }
                setText(status);
                switch (status) {
                    case "Active"             -> setStyle("-fx-text-fill: green;  -fx-font-weight: bold;");
                    case "Needs Maintenance"  -> setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                    case "Disposed"           -> setStyle("-fx-text-fill: red;    -fx-font-weight: bold;");
                    default                   -> setStyle("");
                }
            }
        });

        colPrice.setCellValueFactory(c ->
                new SimpleStringProperty(String.format("$%.2f", c.getValue().getPurchasePrice())));

        colWarranty.setCellValueFactory(c -> {
            if (c.getValue().getWarrantyExpiry() == null)
                return new SimpleStringProperty("None");
            return new SimpleStringProperty(c.getValue().getWarrantyExpiry().toString());
        });

        colPurchaseDate.setCellValueFactory(c -> {
            if (c.getValue().getPurchaseDate() == null)
                return new SimpleStringProperty("None");
            return new SimpleStringProperty(c.getValue().getPurchaseDate().toString());
        });

        colNotes.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNotes()));

        tblAssets.setItems(DataStore.assets);
    }

    // ------------------------------------------------------------------ ADD
    @FXML
    private void handleAdd() {
        showAssetDialog(null).ifPresent(asset -> DataStore.assets.add(asset));
    }

    // ----------------------------------------------------------------- EDIT
    @FXML
    private void handleEdit() {
        Asset selected = tblAssets.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showNoSelectionAlert("edit");
            return;
        }
        showAssetDialog(selected).ifPresent(updated -> {
            int index = DataStore.assets.indexOf(selected);
            DataStore.assets.set(index, updated); // triggers table refresh
        });
    }

    // --------------------------------------------------------------- REMOVE
    @FXML
    private void handleRemove() {
        Asset selected = tblAssets.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showNoSelectionAlert("remove");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Remove \"" + selected.getName() + "\"?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Removal");
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES)
                DataStore.assets.remove(selected);
        });
    }

    // ------------------------------------------------------- SHARED DIALOG
    /**
     * Opens an input dialog pre-populated with {@code existing} data (pass null for Add).
     * Returns an Optional<Asset> with the new/updated values, or empty if cancelled.
     */
    private Optional<Asset> showAssetDialog(Asset existing) {
        boolean isEdit = existing != null;

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setStyle("-fx-padding: 10;");

        TextField tfName     = new TextField(isEdit ? existing.getName()         : "");
        TextField tfCategory = new TextField(isEdit ? existing.getCategory()     : "");
        TextField tfRoom     = new TextField(isEdit ? existing.getRoomLocation() : "");
        TextField tfPrice    = new TextField(isEdit ? String.valueOf(existing.getPurchasePrice()) : "");
        TextField tfNotes    = new TextField(isEdit ? existing.getNotes()        : "");

        DatePicker dpPurchaseDate = new DatePicker(isEdit ? existing.getPurchaseDate() : LocalDate.now());
        DatePicker dpWarranty     = new DatePicker(isEdit ? existing.getWarrantyExpiry() : null);

        ComboBox<AssetStatus> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll(AssetStatus.values());
        cbStatus.setValue(isEdit ? existing.getStatus() : AssetStatus.ACTIVE);

        // Warranty reminder checkbox — Add only
        CheckBox cbWarrantyReminder = new CheckBox("Create warranty expiry reminder");
        cbWarrantyReminder.setSelected(false);
        cbWarrantyReminder.setDisable(dpWarranty.getValue() == null);
        dpWarranty.valueProperty().addListener((obs, oldVal, newVal) -> {
            cbWarrantyReminder.setDisable(newVal == null);
            if (newVal == null) cbWarrantyReminder.setSelected(false);
        });

        // --- Pending reminders list (Add only) ---
        // Stores reminders the user has staged before hitting OK
        List<Reminder> pendingReminders = new ArrayList<>();
        ListView<String> reminderPreview = new ListView<>();
        reminderPreview.setPrefHeight(80);
        reminderPreview.setPlaceholder(new Label("No reminders added yet"));

        Button btnAddReminder = new Button("+ Add Reminder");
        btnAddReminder.setOnAction(e -> {
            String assetName = tfName.getText().trim();
            showAttachedReminderDialog(assetName).ifPresent(r -> {
                pendingReminders.add(r);
                reminderPreview.getItems().add(
                        r.getTitle() + "  |  " + r.getType() + "  |  " + r.getDueDate()
                );
            });
        });

        grid.addRow(0, new Label("Name:"),            tfName);
        grid.addRow(1, new Label("Category:"),        tfCategory);
        grid.addRow(2, new Label("Room:"),            tfRoom);
        grid.addRow(3, new Label("Status:"),          cbStatus);
        grid.addRow(4, new Label("Price ($):"),       tfPrice);
        grid.addRow(5, new Label("Purchase Date:"),   dpPurchaseDate);
        grid.addRow(6, new Label("Warranty Expiry:"), dpWarranty);
        grid.addRow(7, new Label("Notes:"),           tfNotes);
        if (!isEdit) {
            grid.addRow(8,  new Label(""),            cbWarrantyReminder);
            grid.addRow(9,  new Label("Reminders:"),  btnAddReminder);
            grid.addRow(10, new Label(""),            reminderPreview);
        }

        Dialog<Asset> dialog = new Dialog<>();
        dialog.setTitle(isEdit ? "Edit Asset" : "Add Asset");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        javafx.scene.Node okBtn = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setDisable(tfName.getText().isBlank());
        tfName.textProperty().addListener((obs, o, n) -> okBtn.setDisable(n.isBlank()));

        dialog.setResultConverter(btn -> {
            if (btn != ButtonType.OK) return null;
            try {
                if (dpPurchaseDate.getValue() == null) {
                    new Alert(Alert.AlertType.ERROR, "Please select a purchase date.", ButtonType.OK).showAndWait();
                    return null;
                }
                double price = tfPrice.getText().isBlank() ? 0.0
                        : Double.parseDouble(tfPrice.getText());

                Asset result = new Asset(
                        tfName.getText().trim(),
                        tfCategory.getText().trim(),
                        price,
                        dpPurchaseDate.getValue(),
                        dpWarranty.getValue(),
                        tfRoom.getText().trim(),
                        cbStatus.getValue(),
                        tfNotes.getText().trim()
                );

                // Auto warranty reminder
                if (!isEdit && cbWarrantyReminder.isSelected() && dpWarranty.getValue() != null) {
                    DataStore.reminders.add(new Reminder(
                            tfName.getText().trim(),
                            tfName.getText().trim() + " — Warranty Expiry",
                            ReminderType.WARRANTY_EXPIRY,
                            dpWarranty.getValue(),
                            "Auto-generated for asset: " + tfName.getText().trim()
                    ));
                }

                // Commit all staged reminders
                DataStore.reminders.addAll(pendingReminders);

                return result;
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Invalid input: " + e.getMessage()).showAndWait();
                return null;
            }
        });

        return dialog.showAndWait().filter(a -> a != null);
    }

    // ---------------------------------------------------------- HELPERS
    private void showNoSelectionAlert(String action) {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Please select an asset to " + action + ".",
                ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // -------------------------------------------------------------- IMPORT
    @FXML
    private void handleImport() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Import Assets from CSV");
        chooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));

        File file = chooser.showOpenDialog(tblAssets.getScene().getWindow());
        if (file == null) return; // user cancelled

        // Confirm discard of existing data
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "This will replace all current assets. Continue?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Import");
        confirm.setHeaderText(null);
        if (confirm.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) return;

        // Parse and load
        List<Asset> imported = parseCSV(file);
        if (imported == null) return; // parse error already shown

        DataStore.assets.setAll(imported);
    }

// --------------------------------------------------------- CSV PARSER
    /**
     * Expected CSV format (header row required):
     * name,category,purchasePrice,purchaseDate,warrantyExpiry,roomLocation,status,notes
     *
     * - Dates must be YYYY-MM-DD, or blank for none.
     * - status must match an AssetStatus enum name exactly (e.g. ACTIVE).
     * - Returns null if a fatal error occurs (bad header); skips and warns on bad rows.
     */
    private List<Asset> parseCSV(File file) {
        List<Asset> results = new ArrayList<>();
        List<String> badRows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                showError("The selected file is empty.");
                return null;
            }

            // Validate header
            String expectedHeader = "name,category,purchasePrice,purchaseDate,warrantyExpiry,roomLocation,status,notes";
            if (!headerLine.trim().equalsIgnoreCase(expectedHeader)) {
                showError("Unexpected CSV header.\n\nExpected:\n" + expectedHeader
                        + "\n\nFound:\n" + headerLine.trim());
                return null;
            }

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;

                String[] parts = line.split(",", -1); // -1 keeps trailing empty fields
                if (parts.length != 8) {
                    badRows.add("Line " + lineNumber + ": expected 8 columns, got " + parts.length);
                    continue;
                }

                try {
                    String name         = parts[0].trim();
                    String category     = parts[1].trim();
                    double price        = parts[2].trim().isEmpty() ? 0.0 : Double.parseDouble(parts[2].trim());
                    LocalDate purchased = parts[3].trim().isEmpty() ? null : LocalDate.parse(parts[3].trim());
                    LocalDate warranty  = parts[4].trim().isEmpty() ? null : LocalDate.parse(parts[4].trim());
                    String room         = parts[5].trim();
                    AssetStatus status  = AssetStatus.valueOf(parts[6].trim().toUpperCase());
                    String notes        = parts[7].trim();

                    results.add(new Asset(name, category, price, purchased, warranty, room, status, notes));
                } catch (Exception e) {
                    badRows.add("Line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            showError("Could not read file:\n" + e.getMessage());
            return null;
        }

        // Warn about any skipped rows but still load what succeeded
        if (!badRows.isEmpty()) {
            String skipped = String.join("\n", badRows);
            Alert warn = new Alert(Alert.AlertType.WARNING,
                    "The following rows were skipped:\n\n" + skipped,
                    ButtonType.OK);
            warn.setTitle("Import Warnings");
            warn.setHeaderText(badRows.size() + " row(s) could not be imported.");
            warn.showAndWait();
        }

        return results;
    }

    // ------------------------------------------------------------ HELPERS
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // ------------------------------------------------- ATTACHED REMINDER DIALOG
    /**
     * A lightweight reminder dialog launched from within the Add Asset dialog.
     * The asset name is pre-filled and locked so the reminder is always linked
     * to the asset currently being created.
     */
    private Optional<Reminder> showAttachedReminderDialog(String assetName) {
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setStyle("-fx-padding: 10;");

        TextField tfTitle = new TextField();
        tfTitle.setPromptText("e.g. Schedule inspection");

        // Asset name is pre-filled and locked to the asset being added
        TextField tfAsset = new TextField(assetName.isBlank() ? "(asset name not entered yet)" : assetName);
        tfAsset.setEditable(false);
        tfAsset.setStyle("-fx-background-color: #f0f0f0;");

        ComboBox<ReminderType> cbType = new ComboBox<>();
        cbType.getItems().addAll(ReminderType.values());
        cbType.setValue(ReminderType.MAINTENANCE);

        DatePicker dpDueDate = new DatePicker();
        TextField tfNotes    = new TextField();

        grid.addRow(0, new Label("Reminder Title:"), tfTitle);
        grid.addRow(1, new Label("Linked Asset:"),   tfAsset);
        grid.addRow(2, new Label("Type:"),           cbType);
        grid.addRow(3, new Label("Due Date:"),       dpDueDate);
        grid.addRow(4, new Label("Notes:"),          tfNotes);

        Dialog<Reminder> dialog = new Dialog<>();
        dialog.setTitle("Add Reminder for Asset");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // OK disabled until both title and due date are provided
        javafx.scene.Node okBtn = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setDisable(true);
        Runnable validate = () -> okBtn.setDisable(
                tfTitle.getText().isBlank() || dpDueDate.getValue() == null);
        tfTitle.textProperty().addListener((obs, o, n) -> validate.run());
        dpDueDate.valueProperty().addListener((obs, o, n) -> validate.run());

        dialog.setResultConverter(btn -> {
            if (btn != ButtonType.OK) return null;
            String linkedName = assetName.isBlank() ? "Unknown Asset" : assetName;
            return new Reminder(
                    tfTitle.getText().trim(),
                    linkedName,
                    cbType.getValue(),
                    dpDueDate.getValue(),
                    tfNotes.getText().trim()
            );
        });

        return dialog.showAndWait().filter(r -> r != null);
    }

}
