package com.smartpantry.manager.model;

public class PantryItem {

    private long id;
    private String name;
    private double quantity;
    private String unit;
    private String expiryDate;

    public PantryItem() {
    }

    public PantryItem(long id, String name, double quantity, String unit, String expiryDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isExpiringSoon() {
        if (expiryDate == null || expiryDate.isEmpty()) {
            return false;
        }
        try {
            java.time.LocalDate expiry = java.time.LocalDate.parse(expiryDate);
            return !expiry.isBefore(java.time.LocalDate.now())
                    && !expiry.isAfter(java.time.LocalDate.now().plusDays(3));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return name + " (" + quantity + " " + unit + ")";
    }
}
