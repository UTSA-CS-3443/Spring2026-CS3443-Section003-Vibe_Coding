package edu.utsa.cs3443.HomeHaven.controller;

import edu.utsa.cs3443.HomeHaven.model.DataStore;
import edu.utsa.cs3443.HomeHaven.model.Reminder;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;

public class RemindersController {

    @FXML private TableView<Reminder> tblReminders;
    @FXML private TableColumn<Reminder, String> colTitle;
    @FXML private TableColumn<Reminder, String> colType;
    @FXML private TableColumn<Reminder, String> colDueDate;
    @FXML private TableColumn<Reminder, String> colStatus;

    @FXML
    public void initialize() {

        colTitle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTitle()));
        colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType().toString()));
        colDueDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDueDate().toString()));

        colStatus.setCellValueFactory(c -> {
            Reminder r = c.getValue();
            if (r.isDone()) return new SimpleStringProperty("Done");
            if (r.isOverdue()) return new SimpleStringProperty("Overdue");
            if (r.isDueSoon()) return new SimpleStringProperty("Due Soon");
            return new SimpleStringProperty("Upcoming");
        });

        // 🔥 COLOR STATUS
        colStatus.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);

                    switch (status) {
                        case "Overdue":
                            setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                            break;
                        case "Due Soon":
                            setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                            break;
                        case "Done":
                            setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("");
                    }
                }
            }
        });

        tblReminders.setItems(DataStore.reminders);
    }
}