module org.example.travelexpertsphase3desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.travelexpertsphase3desktop to javafx.fxml;
    opens org.example.travelexpertsphase3desktop.model to javafx.base;
    exports org.example.travelexpertsphase3desktop;
    exports org.example.travelexpertsphase3desktop.Utils;
    opens org.example.travelexpertsphase3desktop.Utils to javafx.fxml;
}