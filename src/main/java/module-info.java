module org.example.travelexpertsphase3desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;


    opens org.example.travelexpertsphase3desktop to javafx.fxml;
    exports org.example.travelexpertsphase3desktop;
}