module org.example.travelexpertsphase3desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;


    opens org.example.travelexpertsphase3desktop to javafx.fxml;
    exports org.example.travelexpertsphase3desktop;
    exports org.example.travelexpertsphase3desktop.model;
    opens org.example.travelexpertsphase3desktop.model to javafx.fxml;
    exports org.example.travelexpertsphase3desktop.controller;
    opens org.example.travelexpertsphase3desktop.controller to javafx.fxml;
    exports org.example.travelexpertsphase3desktop.DAO;
    opens org.example.travelexpertsphase3desktop.DAO to javafx.fxml;
}