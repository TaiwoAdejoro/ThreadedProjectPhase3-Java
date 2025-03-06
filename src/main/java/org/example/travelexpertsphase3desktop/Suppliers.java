package org.example.travelexpertsphase3desktop;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Suppliers {
    private IntegerProperty supplierId;
    private StringProperty supplierName;

    public Suppliers(int supplierId, String supplierName) {
        this.supplierId = new SimpleIntegerProperty(supplierId);
        this.supplierName = new SimpleStringProperty(supplierName);
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public IntegerProperty supplierIdProperty() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName.get();
    }

    public void setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
    }

    public StringProperty supplierNameProperty() {
        return supplierName;
    }

    @Override
    public String toString() {
        return "Suppliers{" +
                "supplierId=" + supplierId +
                ", supplierName=" + supplierName +
                '}';
    }
}
