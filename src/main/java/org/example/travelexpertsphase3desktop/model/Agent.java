package org.example.travelexpertsphase3desktop.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agent {

    //use SimpleProperty datatypes instead of primitive datatypes.
    //these are from JavaFX and used because of their observable properties
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstname;
    private SimpleStringProperty middleInitial;
    private SimpleStringProperty lastname;
    private SimpleStringProperty busPhone;
    private SimpleStringProperty email;
    private SimpleStringProperty position;
    private SimpleIntegerProperty agencyId;

    public Agent(int id,
                 String firstname,
                 String middleInitial,
                 String lastname,
                 String busPhone,
                 String email,
                 String position,
                 int agencyId) {
        this.id = new SimpleIntegerProperty(id);
        this.firstname = new SimpleStringProperty(firstname);
        this.middleInitial = new SimpleStringProperty(middleInitial);
        this.lastname = new SimpleStringProperty(lastname);
        this.busPhone = new SimpleStringProperty(busPhone);
        this.email = new SimpleStringProperty(email);
        this.position = new SimpleStringProperty(position);
        this.agencyId = new SimpleIntegerProperty(agencyId);
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

    public String getFirstname() {
        return firstname.get();
    }

    public SimpleStringProperty firstnameProperty() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getMiddleInitial() {
        return middleInitial.get();
    }

    public SimpleStringProperty middleInitialProperty() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial.set(middleInitial);
    }

    public String getLastname() {
        return lastname.get();
    }

    public SimpleStringProperty lastnameProperty() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
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

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
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
}
