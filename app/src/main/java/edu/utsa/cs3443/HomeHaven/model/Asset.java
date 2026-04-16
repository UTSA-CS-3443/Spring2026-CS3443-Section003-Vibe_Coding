package edu.utsa.cs3443.HomeHaven.model;

import java.time.LocalDate;

public class Asset {

    private String name;
    private String category;
    private double purchasePrice;   // NEW from UML
    private LocalDate purchaseDate;
    private LocalDate warrantyExpiry;
    private String roomLocation;    // NEW from UML e.g. "Kitchen", "Garage"
    private AssetStatus status;          // CHANGED: was String, now enum
    private String notes;

    public Asset(String name, String category,
                 double purchasePrice, LocalDate purchaseDate,
                 LocalDate warrantyExpiry, String roomLocation,
                 AssetStatus status, String notes) {
        this.name = name;
        this.category = category;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.roomLocation = roomLocation;
        this.status = status;
        this.notes = notes;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public LocalDate getWarrantyExpiry() {
        return warrantyExpiry;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    // Setters
    public void setName(String n) {
        this.name = n;
    }

    public void setCategory(String c) {
        this.category = c;
    }

    public void setPurchasePrice(double p) {
        this.purchasePrice = p;
    }

    public void setPurchaseDate(LocalDate d) {
        this.purchaseDate = d;
    }

    public void setWarrantyExpiry(LocalDate d) {
        this.warrantyExpiry = d;
    }

    public void setRoomLocation(String r) {
        this.roomLocation = r;
    }

    public void setStatus(AssetStatus s) {
        this.status = s;
    }

    public void setNotes(String n) {
        this.notes = n;
    }

    // Helper methods from UML
    public boolean isWarrantyExpired() {
        if (warrantyExpiry == null) return false;
        return warrantyExpiry.isBefore(LocalDate.now());
    }

    public boolean needsMaintenance() {
        return status == AssetStatus.NEEDS_MAINTENANCE;
    }
}