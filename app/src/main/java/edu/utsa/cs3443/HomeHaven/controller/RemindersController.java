package edu.utsa.cs3443.HomeHaven.controller;

import edu.utsa.cs3443.HomeHaven.model.DataStore;
import edu.utsa.cs3443.HomeHaven.model.Reminder;
import edu.utsa.cs3443.HomeHaven.model.ReminderType;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.Optional;

public class RemindersController {

    @FXML private TableView<Reminder> tblReminders;
    @FXML private TableColumn<Reminder, String> colAsset;
    @FXML private TableColumn<Reminder, String> colTitle;
    @FXML private TableColumn<Reminder, String> colType;
    @FXML private TableColumn<Reminder, String> colDueDate;
    @FXML private TableColumn<Reminder, String> colStatus;

    @FXML
    public void initialize() {
        colAsset.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAssetName()));
        colTitle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTitle()));
        colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType().toString()));
        colDueDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDueDate().toString()));

        colStatus.setCellValueFactory(c -> {
            Reminder r = c.getValue();
            if (r.isDone())    return new SimpleStringProperty("Done");
            if (r.isOverdue()) return new SimpleStringProperty("Overdue");
            if (r.isDueSoon()) return new SimpleStringProperty("Due Soon");
            return new SimpleStringProperty("Upcoming");
        });

        colStatus.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) { setText(null); setStyle(""); return; }
                setText(status);
                switch (status) {
                    case "Overdue"  -> setStyle("-fx-text-fill: red;    -fx-font-weight: bold;");
                    case "Due Soon" -> setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                    case "Done"     -> setStyle("-fx-text-fill: green;  -fx-font-weight: bold;");
                    default         -> setStyle("");
                }
            }
        });

        tblReminders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblReminders.setItems(DataStore.reminders);
    }

    // ------------------------------------------------------------------ ADD
    @FXML
    private void handleAdd() {
        showReminderDialog(null).ifPresent(r -> DataStore.reminders.add(r));
    }

    // ----------------------------------------------------------------- EDIT
    @FXML
    private void handleEdit() {
        Reminder selected = tblReminders.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showNoSelectionAlert("edit");
            return;
        }
        showReminderDialog(selected).ifPresent(updated -> {
            int index = DataStore.reminders.indexOf(selected);
            DataStore.reminders.set(index, updated);
        });
    }

    // --------------------------------------------------------------- REMOVE
    @FXML
    private void handleRemove() {
        Reminder selected = tblReminders.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showNoSelectionAlert("remove");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Remove \"" + selected.getTitle() + "\"?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Removal");
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES)
                DataStore.reminders.remove(selected);
        });
    }

    // ------------------------------------------------------- SHARED DIALOG
    /**
     * Opens a dialog pre-populated with {@code existing} data (pass null for Add).
     * Returns an Optional<Reminder> with the new/updated values, or empty if cancelled.
     */
    private Optional<Reminder> showReminderDialog(Reminder existing) {
        boolean isEdit = existing != null;

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setStyle("-fx-padding: 10;");

        ComboBox<String> cbAsset = new ComboBox<>();
        cbAsset.setEditable(true);
        cbAsset.getItems().add(""); // allow no asset
        DataStore.assets.forEach(a -> cbAsset.getItems().add(a.getName()));
        cbAsset.setValue(isEdit ? existing.getAssetName() : "");

        TextField tfTitle = new TextField(isEdit ? existing.getTitle() : "");
        TextField tfNotes = new TextField(isEdit ? existing.getNotes() : "");
        DatePicker dpDueDate = new DatePicker(isEdit ? existing.getDueDate() : LocalDate.now());

        ComboBox<ReminderType> cbType = new ComboBox<>();
        cbType.getItems().addAll(ReminderType.values());
        cbType.setValue(isEdit ? existing.getType() : ReminderType.MAINTENANCE);

        // Only shown when editing — lets the user toggle the done flag
        CheckBox cbDone = new CheckBox("Mark as done");
        cbDone.setSelected(isEdit && existing.isDone());

        grid.addRow(0, new Label("Asset:"),    cbAsset);
        grid.addRow(1, new Label("Title:"),    tfTitle);
        grid.addRow(2, new Label("Type:"),     cbType);
        grid.addRow(3, new Label("Due Date:"), dpDueDate);
        grid.addRow(4, new Label("Notes:"),    tfNotes);
        if (isEdit)
            grid.addRow(5, new Label(""), cbDone);

        Dialog<Reminder> dialog = new Dialog<>();
        dialog.setTitle(isEdit ? "Edit Reminder" : "Add Reminder");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Disable OK until Title is non-empty
        Node okBtn = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setDisable(tfTitle.getText().isBlank());
        tfTitle.textProperty().addListener((obs, o, n) -> okBtn.setDisable(n.isBlank()));

        dialog.setResultConverter(btn -> {
            if (btn != ButtonType.OK) return null;
            try {
                LocalDate dueDate = dpDueDate.getValue();
                if (dueDate == null) {
                    new Alert(Alert.AlertType.ERROR, "Please select a due date.", ButtonType.OK).showAndWait();
                    return null;
                }
                String assetName = cbAsset.getValue() != null ? cbAsset.getValue().trim() : "";
                Reminder result = new Reminder(
                        assetName,
                        tfTitle.getText().trim(),
                        cbType.getValue(),
                        dueDate,
                        tfNotes.getText().trim()
                );
                // Preserve done state — constructor always defaults it to false
                if (isEdit) result.setDone(cbDone.isSelected());
                return result;
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Invalid input: " + e.getMessage(), ButtonType.OK)
                        .showAndWait();
                return null;
            }
        });

        return dialog.showAndWait().filter(r -> r != null);
    }

    // ------------------------------------------------------------ HELPERS
    private void showNoSelectionAlert(String action) {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Please select a reminder to " + action + ".",
                ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
