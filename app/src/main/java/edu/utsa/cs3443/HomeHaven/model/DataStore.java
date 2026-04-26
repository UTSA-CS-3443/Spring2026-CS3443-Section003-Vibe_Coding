package edu.utsa.cs3443.HomeHaven.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
public class DataStore {
    public static ObservableList<Asset> assets = //shared list of all assets in the app
            FXCollections.observableArrayList();
   
    public static ObservableList<Reminder> reminders = //shared list of all reminders in the app
            FXCollections.observableArrayList();
  
    public static void loadSampleData() { //loads sample data
        if (!assets.isEmpty()) return;
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
                new Reminder("Replace HVAC filter", //overdue reminder
                        ReminderType.MAINTENANCE,
                        LocalDate.now().minusDays(5), "Overdue!"),

                new Reminder("Check smoke detectors", //upcoming reminder
                        ReminderType.INSPECTION,
                        LocalDate.now().plusDays(4), "Check all floors"),

                new Reminder("Washer warranty expiry", //reminder for warranty
                        ReminderType.WARRANTY_EXPIRY,
                        LocalDate.of(2025, 3, 15), "")
        );
    }
    public static int getDueSoonCount() { //counts reminders due within 7 days
        return (int) reminders.stream().filter(Reminder::isDueSoon).count();
    }
    public static int getOverdueCount() { //counts reminders that are overdue
        return (int) reminders.stream().filter(Reminder::isOverdue).count();
    }
    public static int getExpiredWarrantyCount() { //counts assets with expired warranties
        return (int) assets.stream().filter(Asset::isWarrantyExpired).count();
    }

}
