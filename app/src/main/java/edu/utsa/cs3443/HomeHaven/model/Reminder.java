package edu.utsa.cs3443.HomeHaven.model;

import java.time.LocalDate;

public class Reminder {

    private String title;
    private ReminderType type;    // CHANGED: was String, now enum
    private LocalDate dueDate;
    private String notes;   // NEW from UML
    private boolean done;

    public Reminder(String title, ReminderType type,
                    LocalDate dueDate, String notes) {
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.notes = notes;
        this.done = false;
    }

    public String getTitle() {
        return title;
    }

    public ReminderType getType() {
        return type;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isOverdue() {
        return !done && dueDate.isBefore(LocalDate.now());
    }

    // NEW: due within the next 7 days (useful for dashboard warning)
    public boolean isDueSoon() {
        if (done) return false;
        LocalDate today = LocalDate.now();
        return !dueDate.isBefore(today) &&
                dueDate.isBefore(today.plusDays(7));
    }
}