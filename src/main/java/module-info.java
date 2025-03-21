module org.example.travelexpertsphase3desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.management;


    opens org.example.travelexpertsphase3desktop to javafx.fxml;
    exports org.example.travelexpertsphase3desktop;
    exports org.example.travelexpertsphase3desktop.model;
    opens org.example.travelexpertsphase3desktop.model to javafx.fxml;
    exports org.example.travelexpertsphase3desktop.utils;
    opens org.example.travelexpertsphase3desktop.utils to javafx.fxml;
}