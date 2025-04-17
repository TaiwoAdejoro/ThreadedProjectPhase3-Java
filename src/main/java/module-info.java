module org.example.travelexpertsphase3desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;


    exports org.example.travelexpertsphase3desktop;
    opens org.example.travelexpertsphase3desktop.controllers to javafx.fxml;
    opens org.example.travelexpertsphase3desktop.models to javafx.base;
    exports org.example.travelexpertsphase3desktop.controllers;
    exports org.example.travelexpertsphase3desktop.utils;
    opens org.example.travelexpertsphase3desktop.utils to javafx.fxml;
    exports org.example.travelexpertsphase3desktop.models;
}