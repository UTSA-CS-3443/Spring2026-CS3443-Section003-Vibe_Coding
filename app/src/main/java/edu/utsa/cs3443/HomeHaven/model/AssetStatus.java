package edu.utsa.cs3443.HomeHaven.model;

// Enum prevents typos — can't accidentally type "ACTVE" or "active"
// Use this everywhere instead of a plain String for status
public enum AssetStatus {
    ACTIVE,
    NEEDS_MAINTENANCE,
    IN_STORAGE,
    DISPOSED
}