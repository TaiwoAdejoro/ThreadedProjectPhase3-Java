module org.example.travelexpertsphase3desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.travelexpertsphase3desktop to javafx.fxml;
    exports org.example.travelexpertsphase3desktop;
}