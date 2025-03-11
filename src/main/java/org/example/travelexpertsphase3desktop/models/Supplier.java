package org.example.travelexpertsphase3desktop.models;

public class Supplier {
    private int supplierId;
    private String supplierName;

    public Supplier(int supplierId, String supplierName) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
    }

    public int getSupplierId() { return supplierId; }
    public String getSupplierName() { return supplierName; }

    @Override
    public String toString() {
        return supplierName; // This ensures the ComboBox displays supplier names
    }
}

