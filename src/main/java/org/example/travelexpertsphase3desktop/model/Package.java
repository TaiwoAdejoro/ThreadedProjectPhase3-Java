package org.example.travelexpertsphase3desktop.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Package {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SimpleStringProperty description;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty commission;

    // Constructor using normal data types
    public Package(int id, String name, String startDateStr, String endDateStr, String description, double price, double commission) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
        this.commission = new SimpleDoubleProperty(commission);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.startDate = LocalDateTime.parse(startDateStr, formatter);
        this.endDate = LocalDateTime.parse(endDateStr, formatter);
    }

    // Getters and setters

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getCommission() {
        return commission.get();
    }

    public SimpleDoubleProperty commissionProperty() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission.set(commission);
    }

    // Additional methods to get formatted date strings
    public String getFormattedStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return startDate.format(formatter);
    }

    public String getFormattedEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return endDate.format(formatter);
    }
}
