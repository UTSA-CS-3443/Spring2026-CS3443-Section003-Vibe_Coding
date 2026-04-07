package edu.utsa.cs3443.app_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
public class DataStore {
    // 'static' = one shared copy; every controller sees the same data
    public static ObservableList<Asset> assets =
            FXCollections.observableArrayList();
    public static ObservableList<Reminder> reminders =
            FXCollections.observableArrayList();

    // Call once in Main.java before showing the window
    public static void loadSampleData() {
        assets.addAll(
                new Asset("Samsung Washer", "Appliance",
                        LocalDate.of(2022, 3, 15), LocalDate.of(2025, 3, 15),
                        "Good", "Front loader"),
                new Asset("HVAC Unit", "System",
                        LocalDate.of(2019, 6, 1), LocalDate.of(2024, 6, 1),
                        "Needs Maintenance", "Filter due"),
                new Asset("Lawn Mower", "Tool",
                        LocalDate.of(2021, 4, 10), null, "Good", "No warranty")
        );
        reminders.addAll(
                new Reminder("Replace HVAC filter", "Maintenance",
                        LocalDate.now().minusDays(5)), // already overdue!
                new Reminder("Check smoke detectors", "Inspection",
                        LocalDate.now().plusDays(14)), // upcoming
                new Reminder("Washer warranty expiry", "Warranty",
                        LocalDate.of(2025, 3, 15))
        );
    }

    public static int getOverdueCount() {
        return (int) reminders.stream().filter(Reminder::isOverdue).count();
    }

    public static int getExpiredWarrantyCount() {
        return (int) assets.stream().filter(Asset::isWarrantyExpired).count();
    }
}
