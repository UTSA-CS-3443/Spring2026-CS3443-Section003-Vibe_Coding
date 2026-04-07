package edu.utsa.cs3443.app_project.model;

import java.time.LocalDate;
public class Asset {
    private String name; // e.g. "Samsung Washing Machine"
    private String category; // e.g. "Appliance", "Tool"
    private LocalDate purchaseDate;
    private LocalDate warrantyExpiry; // can be null
    private String status; // "Good" | "Needs Maintenance" | "Overdue"
    private String notes;

    public Asset(String name, String category, LocalDate purchaseDate,
                 LocalDate warrantyExpiry, String status, String notes) {
        this.name = name;
        this.category = category;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public LocalDate getWarrantyExpiry() {
        return warrantyExpiry;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setCategory(String c) {
        this.category = c;
    }

    public void setPurchaseDate(LocalDate d) {
        this.purchaseDate = d;
    }

    public void setWarrantyExpiry(LocalDate d) {
        this.warrantyExpiry = d;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    public void setNotes(String n) {
        this.notes = n;
    }

    public boolean isWarrantyExpired() {
        if (warrantyExpiry == null) return false;
        return warrantyExpiry.isBefore(LocalDate.now());
    }
}

