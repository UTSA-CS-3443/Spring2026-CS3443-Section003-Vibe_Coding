package edu.utsa.cs3443.homeheaven;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import edu.utsa.cs3443.homeheaven.model.DataStore;
import edu.utsa.cs3443.homeheaven.model.Reminder;
import java.time.LocalDate;

public class RemindersController {

    @FXML
    private ListView<Reminder> reminderList;
    @FXML
    private TextField titleField;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private ChoiceBox<String> typeChoice;
    @FXML
    private Button addButton;

    @FXML
    public void initialize() {
        typeChoice.getItems().addAll("Maintenance", "Inspection", "Warranty");
        reminderList.setItems(DataStore.reminders);

        reminderList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Reminder r, boolean empty) {
                super.updateItem(r, empty);
                if (empty || r == null) {
                    setText(null);
                } else {
                    String status = r.isOverdue() ? " (OVERDUE)" : "";
                    setText(r.getTitle() + " — " + r.getType() + " — " + r.getDueDate() + status);
                }
            }
        });

        addButton.setOnAction(e -> addReminder());
    }

    private void addReminder() {
        String title = titleField.getText();
        LocalDate due = dueDatePicker.getValue();
        String type = typeChoice.getValue();

        if (title == null || title.isEmpty() || due == null || type == null) {
            return;
        }

        DataStore.reminders.add(new Reminder(title, type, due));

        titleField.clear();
        dueDatePicker.setValue(null);
        typeChoice.setValue(null);
    }
}
