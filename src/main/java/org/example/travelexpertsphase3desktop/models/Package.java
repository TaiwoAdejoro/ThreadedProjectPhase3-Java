package org.example.travelexpertsphase3desktop.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Package {
    private IntegerProperty packageId;
    private StringProperty packageName;
    private ObjectProperty<LocalDate> pkgStartDate;
    private ObjectProperty<LocalDate> pkgEndDate;
    private StringProperty pkgDesc;
    private DoubleProperty pkgBasePrice;
    private DoubleProperty pkgAgencyCommission;

    public Package(int packageId, String packageName, LocalDate pkgStartDate, LocalDate pkgEndDate, String pkgDesc, Double pkgBasePrice, Double pkgAgencyCommission) {
        this.packageId = new SimpleIntegerProperty(packageId);
        this.packageName = new SimpleStringProperty(packageName);
        this.pkgStartDate = new SimpleObjectProperty<>(pkgStartDate);
        this.pkgEndDate = new SimpleObjectProperty<>(pkgEndDate);
        this.pkgDesc = new SimpleStringProperty(pkgDesc);
        this.pkgBasePrice = new SimpleDoubleProperty(pkgBasePrice);
        this.pkgAgencyCommission = new SimpleDoubleProperty(pkgAgencyCommission);
    }

    public int getPackageId() {
        return packageId.get();
    }

    public void setPackageId(int packageId) {
        this.packageId.set(packageId);
    }

    public IntegerProperty packageIdProperty() {
        return packageId;
    }

    public String getPackageName() {
        return packageName.get();
    }

    public void setPackageName(String packageName) {
        this.packageName.set(packageName);
    }

    public StringProperty packageNameProperty() {
        return packageName;
    }

    public LocalDate getPkgStartDate() {
        return pkgStartDate.get();
    }

    public void setPkgStartDate(LocalDate pkgStartDate) {
        this.pkgStartDate.set(pkgStartDate);
    }

    public ObjectProperty<LocalDate> pkgStartDateProperty() {
        return pkgStartDate;
    }

    public LocalDate getPkgEndDate() {
        return pkgEndDate.get();
    }

    public void setPkgEndDate(LocalDate pkgEndDate) {
        this.pkgEndDate.set(pkgEndDate);
    }

    public ObjectProperty<LocalDate> pkgEndDateProperty() {
        return pkgEndDate;
    }

    public String getPkgDesc() {
        return pkgDesc.get();
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc.set(pkgDesc);
    }

    public StringProperty pkgDescProperty() {
        return pkgDesc;
    }

    public double getPkgBasePrice() {
        return pkgBasePrice.get();
    }

    public void setPkgBasePrice(double pkgBasePrice) {
        this.pkgBasePrice.set(pkgBasePrice);
    }

    public DoubleProperty pkgBasePriceProperty() {
        return pkgBasePrice;
    }

    public double getPkgAgencyCommission() {
        return pkgAgencyCommission.get();
    }

    public void setPkgAgencyCommission(double pkgAgencyCommission) {
        this.pkgAgencyCommission.set(pkgAgencyCommission);
    }

    public DoubleProperty pkgAgencyCommissionProperty() {
        return pkgAgencyCommission;
    }
}
