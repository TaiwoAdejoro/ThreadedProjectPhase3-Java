package org.example.travelexpertsphase3desktop.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Package {
    private SimpleIntegerProperty packageId;
    private SimpleStringProperty packageName;
    private SimpleObjectProperty<LocalDate> pkgStartDate;
    private SimpleObjectProperty<LocalDate> pkgEndDate;
    private SimpleStringProperty pkgDesc;
    private SimpleDoubleProperty pkgBasePrice;
    private SimpleDoubleProperty pkgAgencyCommission;
    private SimpleStringProperty productSuppliers;

    // Constructor
    public Package(int packageId, String packageName, LocalDate pkgStartDate, LocalDate pkgEndDate,
                   String pkgDesc, double pkgBasePrice, double pkgAgencyCommission, String productSuppliers) {
        this.packageId = new SimpleIntegerProperty(packageId);
        this.packageName = new SimpleStringProperty(packageName);
        this.pkgStartDate = new SimpleObjectProperty<>(pkgStartDate);
        this.pkgEndDate = new SimpleObjectProperty<>(pkgEndDate);
        this.pkgDesc = new SimpleStringProperty(pkgDesc);
        this.pkgBasePrice = new SimpleDoubleProperty(pkgBasePrice);
        this.pkgAgencyCommission = new SimpleDoubleProperty(pkgAgencyCommission);
        this.productSuppliers = new SimpleStringProperty(productSuppliers);
    }

    // Getters
    public int getPackageId() {
        return packageId.get();
    }

    public String getPackageName() {
        return packageName.get();
    }

    public LocalDate getPkgStartDate() {
        return pkgStartDate.get();
    }

    public LocalDate getPkgEndDate() {
        return pkgEndDate.get();
    }

    public String getPkgDesc() {
        return pkgDesc.get();
    }

    public double getPkgBasePrice() {
        return pkgBasePrice.get();
    }

    public double getPkgAgencyCommission() {
        return pkgAgencyCommission.get();
    }

    public String getProductSuppliers() {
        return productSuppliers.get();
    }

    // Property getters (useful for TableView bindings)
    public SimpleIntegerProperty packageIdProperty() {
        return packageId;
    }

    public SimpleStringProperty packageNameProperty() {
        return packageName;
    }

    public SimpleObjectProperty<LocalDate> pkgStartDateProperty() {
        return pkgStartDate;
    }

    public SimpleObjectProperty<LocalDate> pkgEndDateProperty() {
        return pkgEndDate;
    }

    public SimpleStringProperty pkgDescProperty() {
        return pkgDesc;
    }

    public SimpleDoubleProperty pkgBasePriceProperty() {
        return pkgBasePrice;
    }

    public SimpleDoubleProperty pkgAgencyCommissionProperty() {
        return pkgAgencyCommission;
    }

    public SimpleStringProperty productSuppliersProperty() {
        return productSuppliers;
    }

    // Setters
    public void setPackageId(int packageId) {
        this.packageId.set(packageId);
    }

    public void setPackageName(String packageName) {
        this.packageName.set(packageName);
    }

    public void setPkgStartDate(LocalDate pkgStartDate) {
        this.pkgStartDate.set(pkgStartDate);
    }

    public void setPkgEndDate(LocalDate pkgEndDate) {
        this.pkgEndDate.set(pkgEndDate);
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc.set(pkgDesc);
    }

    public void setPkgBasePrice(double pkgBasePrice) {
        this.pkgBasePrice.set(pkgBasePrice);
    }

    public void setPkgAgencyCommission(double pkgAgencyCommission) {
        this.pkgAgencyCommission.set(pkgAgencyCommission);
    }

    public void setProductSuppliers(String productSuppliers) {
        this.productSuppliers.set(productSuppliers);
    }
}