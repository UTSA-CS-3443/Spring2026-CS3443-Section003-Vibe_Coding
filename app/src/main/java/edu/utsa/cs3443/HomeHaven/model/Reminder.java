package edu.utsa.cs3443.HomeHaven.model;

import java.time.LocalDate;

public class Reminder {

    private String assetName;
    private String title;
    private ReminderType type;
    private LocalDate dueDate;
    private String notes;
    private boolean done;

    public Reminder(String assetName, String title, ReminderType type, //creates a new reminder
                    LocalDate dueDate, String notes) {
        this.assetName = assetName;
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.notes = notes;
        this.done = false;
    }
    public String getAssetName() { //returns the associated asset name
        return assetName != null ? assetName : "";
    }
    public void setAssetName(String assetName) { //sets the associated asset name
        this.assetName = assetName;
    }
    public String getTitle() { //returns title
        return title;
    }
    public ReminderType getType() { //returns reminder type
        return type;
    }
    public LocalDate getDueDate() { //return due date
        return dueDate;
    }
    public String getNotes() { //returns any notes
        return notes != null ? notes : "";
    }
    public boolean isDone() { //returns true if reminder is completed
        return done;
    }
    public void setDone(boolean done) { //marks reminder as done or not done
        this.done = done;
    }
    public boolean isOverdue() { //returns true if reminder is past due date n not done
        return !done && dueDate.isBefore(LocalDate.now());
    }
    public boolean isDueSoon() { //reteuns true if reminder is due
        if (done) return false;
        LocalDate today = LocalDate.now();
        return !dueDate.isBefore(today) &&
                dueDate.isBefore(today.plusDays(7));
    }
}
