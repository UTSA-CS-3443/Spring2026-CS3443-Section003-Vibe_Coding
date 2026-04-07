package edu.utsa.cs3443.app_project;

import java.time.LocalDate;
public class Reminder {
    private String title; // e.g. "Replace HVAC filter"
    private String type; // "Maintenance" | "Inspection" | "Warranty"
    private LocalDate dueDate;
    private boolean done;

    public Reminder(String title, String type, LocalDate dueDate) {
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.done = false;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDueDate() {
        return dueDate;
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
}
