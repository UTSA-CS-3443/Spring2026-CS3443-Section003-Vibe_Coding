package edu.utsa.cs3443.HomeHaven.model;

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
                        899.99, LocalDate.of(2022, 3, 15),
                        LocalDate.of(2025, 3, 15), "Laundry Room",
                        AssetStatus.ACTIVE, "Front loader"),

                new Asset("HVAC Unit", "System",
                        3200.00, LocalDate.of(2019, 6, 1),
                        LocalDate.of(2024, 6, 1), "Utility Room",
                        AssetStatus.NEEDS_MAINTENANCE, "Filter due"),

                new Asset("Lawn Mower", "Tool",
                        450.00, LocalDate.of(2021, 4, 10),
                        null, "Garage",
                        AssetStatus.ACTIVE, "No warranty")
        );

        reminders.addAll(
                new Reminder("Replace HVAC filter",
                        ReminderType.MAINTENANCE,
                        LocalDate.now().minusDays(5), "Overdue!"),

                new Reminder("Check smoke detectors",
                        ReminderType.INSPECTION,
                        LocalDate.now().plusDays(4), "Check all floors"),

                new Reminder("Washer warranty expiry",
                        ReminderType.WARRANTY_EXPIRY,
                        LocalDate.of(2025, 3, 15), "")
        );
    }

    // Add this new helper for the dashboard "due soon" badge
    public static int getDueSoonCount() {
        return (int) reminders.stream().filter(Reminder::isDueSoon).count();
    }

    public static int getOverdueCount() {
        return (int) reminders.stream().filter(Reminder::isOverdue).count();
    }

    public static int getExpiredWarrantyCount() {
        return (int) assets.stream().filter(Asset::isWarrantyExpired).count();
    }
}
