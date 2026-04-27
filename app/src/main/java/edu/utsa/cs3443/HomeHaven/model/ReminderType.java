package edu.utsa.cs3443.HomeHaven.model;

public enum ReminderType {
    MAINTENANCE("Maintenance"),
    WARRANTY_EXPIRY("Warranty Expiry"),
    INSPECTION("Inspection"),
    REPLACEMENT("Replacement");

    private final String label;

    ReminderType(String label) { this.label = label; }

    @Override
    public String toString() { return label; }
}
