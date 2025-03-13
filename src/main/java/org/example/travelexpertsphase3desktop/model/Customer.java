package org.example.travelexpertsphase3desktop.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty customerId;
    private SimpleStringProperty customerFirstName;
    private SimpleStringProperty customerLastName;
    private SimpleStringProperty customerAddress;
    private SimpleStringProperty customerCity;
    private SimpleStringProperty customerProvince;
    private SimpleStringProperty customerPostCode;
    private SimpleStringProperty customerCountry;
    private SimpleStringProperty customerHomePhone;
    private SimpleStringProperty customerBusPhone;
    private SimpleStringProperty customerEmail;
    private SimpleIntegerProperty agentId;

    public Customer(int customerId, String customerFirstName, String customerLastName, String customerAddress,String customerCity,String customerProvince, String customerPostCode,String customerCountry, String customerHomePhone, String customerBusPhone, String customerEmail, int agentId) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.customerFirstName = new SimpleStringProperty(customerFirstName);
        this.customerLastName = new SimpleStringProperty(customerLastName);
        this.customerAddress = new SimpleStringProperty(customerAddress);
        this.customerCity = new SimpleStringProperty(customerCity);
        this.customerProvince = new SimpleStringProperty(customerProvince);
        this.customerPostCode = new SimpleStringProperty(customerPostCode);
        this.customerCountry = new SimpleStringProperty(customerCountry);
        this.customerHomePhone = new SimpleStringProperty(customerHomePhone);
        this.customerBusPhone = new SimpleStringProperty(customerBusPhone);
        this.customerEmail = new SimpleStringProperty(customerEmail);
        this.agentId = new SimpleIntegerProperty(agentId);
    }

    public String getCustomerCity() {
        return customerCity.get();
    }

    public SimpleStringProperty customerCityProperty() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity.set(customerCity);
    }

    public String getCustomerProvince() {
        return customerProvince.get();
    }

    public SimpleStringProperty customerProvinceProperty() {
        return customerProvince;
    }

    public void setCustomerProvince(String customerProvince) {
        this.customerProvince.set(customerProvince);
    }

    public String getCustomerCountry() {
        return customerCountry.get();
    }

    public SimpleStringProperty customerCountryProperty() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry.set(customerCountry);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getCustomerFirstName() {
        return customerFirstName.get();
    }

    public SimpleStringProperty customerFirstNameProperty() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName.set(customerFirstName);
    }

    public String getCustomerLastName() {
        return customerLastName.get();
    }

    public SimpleStringProperty customerLastNameProperty() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName.set(customerLastName);
    }

    public String getCustomerAddress() {
        return customerAddress.get();
    }

    public SimpleStringProperty customerAddressProperty() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress.set(customerAddress);
    }

    public String getCustomerPostCode() {
        return customerPostCode.get();
    }

    public SimpleStringProperty customerPostCodeProperty() {
        return customerPostCode;
    }

    public void setCustomerPostCode(String customerPostCode) {
        this.customerPostCode.set(customerPostCode);
    }

    public String getCustomerHomePhone() {
        return customerHomePhone.get();
    }

    public SimpleStringProperty customerHomePhoneProperty() {
        return customerHomePhone;
    }

    public void setCustomerHomePhone(String customerHomePhone) {
        this.customerHomePhone.set(customerHomePhone);
    }

    public String getCustomerBusPhone() {
        return customerBusPhone.get();
    }

    public SimpleStringProperty customerBusPhoneProperty() {
        return customerBusPhone;
    }

    public void setCustomerBusPhone(String customerBusPhone) {
        this.customerBusPhone.set(customerBusPhone);
    }

    public String getCustomerEmail() {
        return customerEmail.get();
    }

    public SimpleStringProperty customerEmailProperty() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail.set(customerEmail);
    }

    public int getAgentId() {
        return agentId.get();
    }

    public SimpleIntegerProperty agentIdProperty() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId.set(agentId);
    }
}
