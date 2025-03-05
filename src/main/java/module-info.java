module org.example.travelexpertsphase3desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example.travelexpertsphase3desktop to javafx.fxml;
    exports org.example.travelexpertsphase3desktop;
    exports org.example.travelexpertsphase3desktop.Models;
    opens org.example.travelexpertsphase3desktop.Models to javafx.fxml;
    exports org.example.travelexpertsphase3desktop.Controllers;
    opens org.example.travelexpertsphase3desktop.Controllers to javafx.fxml;
}