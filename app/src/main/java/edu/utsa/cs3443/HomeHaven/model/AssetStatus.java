package edu.utsa.cs3443.HomeHaven.model;

// Enum prevents typos — can't accidentally type "ACTVE" or "active"
// Use this everywhere instead of a plain String for status
public enum AssetStatus {
    ACTIVE("Active"),
    NEEDS_MAINTENANCE("Needs Maintenance"),
    IN_STORAGE("In Storage"),
    DISPOSED("Disposed");

    private final String label;

    AssetStatus(String label) { this.label = label; }

    @Override
    public String toString() { return label; }
}
