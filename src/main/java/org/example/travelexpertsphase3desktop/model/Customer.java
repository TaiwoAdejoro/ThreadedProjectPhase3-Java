package org.example.travelexpertsphase3desktop.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty address;
    private SimpleStringProperty city;
    private SimpleStringProperty province;
    private SimpleStringProperty postal;
    private SimpleStringProperty country;
    private SimpleStringProperty homePhone;
    private SimpleStringProperty busPhone;
    private SimpleStringProperty email;
    private SimpleIntegerProperty agentId;

    public Customer(int id,
                    String firstName,
                    String lastName,
                    String address,
                    String city,
                    String province,
                    String postal,
                    String country,
                    String homePhone,
                    String busPhone,
                    String email,
                    int agentId) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.city = new SimpleStringProperty(city);
        this.province = new SimpleStringProperty(province);
        this.postal = new SimpleStringProperty(postal);
        this.country = new SimpleStringProperty(country);
        this.homePhone = new SimpleStringProperty(homePhone);
        this.busPhone = new SimpleStringProperty(busPhone);
        this.email = new SimpleStringProperty(email);
        this.agentId = new SimpleIntegerProperty(agentId);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getProvince() {
        return province.get();
    }

    public SimpleStringProperty provinceProperty() {
        return province;
    }

    public void setProvince(String province) {
        this.province.set(province);
    }

    public String getPostal() {
        return postal.get();
    }

    public SimpleStringProperty postalProperty() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal.set(postal);
    }

    public String getCountry() {
        return country.get();
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getHomePhone() {
        return homePhone.get();
    }

    public SimpleStringProperty homePhoneProperty() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone.set(homePhone);
    }

    public String getBusPhone() {
        return busPhone.get();
    }

    public SimpleStringProperty busPhoneProperty() {
        return busPhone;
    }

    public void setBusPhone(String busPhone) {
        this.busPhone.set(busPhone);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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
