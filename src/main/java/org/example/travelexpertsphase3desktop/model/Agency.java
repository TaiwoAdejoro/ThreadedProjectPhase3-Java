package org.example.travelexpertsphase3desktop.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agency {
    private SimpleIntegerProperty agencyId;
    private SimpleStringProperty agencyAddress;
    private SimpleStringProperty agencyCity;
    private SimpleStringProperty agencyProvince;
    private SimpleStringProperty agencyPostal;
    private SimpleStringProperty agencyCountry;
    private SimpleStringProperty agencyPhone;
    private SimpleStringProperty agencyFax;

    public Agency(int agencyId,
                  String agencyAddress,
                  String agencyCity,
                  String agencyProvince,
                  String agencyPostal,
                  String agencyCountry,
                  String agencyPhone,
                  String agencyFax) {
        this.agencyId = new SimpleIntegerProperty(agencyId);
        this.agencyAddress = new SimpleStringProperty(agencyAddress);
        this.agencyCity = new SimpleStringProperty(agencyCity);
        this.agencyProvince = new SimpleStringProperty(agencyProvince);
        this.agencyPostal = new SimpleStringProperty(agencyPostal);
        this.agencyCountry = new SimpleStringProperty(agencyCountry);
        this.agencyPhone = new SimpleStringProperty(agencyPhone);
        this.agencyFax = new SimpleStringProperty(agencyFax);
    }

    public int getAgencyId() {
        return agencyId.get();
    }

    public SimpleIntegerProperty agencyIdProperty() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId.set(agencyId);
    }

    public String getAgencyAddress() {
        return agencyAddress.get();
    }

    public SimpleStringProperty agencyAddressProperty() {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress.set(agencyAddress);
    }

    public String getAgencyCity() {
        return agencyCity.get();
    }

    public SimpleStringProperty agencyCityProperty() {
        return agencyCity;
    }

    public void setAgencyCity(String agencyCity) {
        this.agencyCity.set(agencyCity);
    }

    public String getAgencyProvince() {
        return agencyProvince.get();
    }

    public SimpleStringProperty agencyProvinceProperty() {
        return agencyProvince;
    }

    public void setAgencyProvince(String agencyProvince) {
        this.agencyProvince.set(agencyProvince);
    }

    public String getAgencyPostal() {
        return agencyPostal.get();
    }

    public SimpleStringProperty agencyPostalProperty() {
        return agencyPostal;
    }

    public void setAgencyPostal(String agencyPostal) {
        this.agencyPostal.set(agencyPostal);
    }

    public String getAgencyCountry() {
        return agencyCountry.get();
    }

    public SimpleStringProperty agencyCountryProperty() {
        return agencyCountry;
    }

    public void setAgencyCountry(String agencyCountry) {
        this.agencyCountry.set(agencyCountry);
    }

    public String getAgencyPhone() {
        return agencyPhone.get();
    }

    public SimpleStringProperty agencyPhoneProperty() {
        return agencyPhone;
    }

    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone.set(agencyPhone);
    }

    public String getAgencyFax() {
        return agencyFax.get();
    }

    public SimpleStringProperty agencyFaxProperty() {
        return agencyFax;
    }

    public void setAgencyFax(String agencyFax) {
        this.agencyFax.set(agencyFax);
    }
}
