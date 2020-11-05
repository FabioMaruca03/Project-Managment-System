module Project.Managment.System {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires java.logging;

    opens com.marufeb.fiverr.java to javafx.fxml, javafx.controls;
    opens com.marufeb.fiverr.java.controllers to javafx.fxml, javafx.controls;
    opens com.marufeb.fiverr.kotlin.model to kotlin.stdlib;

    exports com.marufeb.fiverr.java;
    exports com.marufeb.fiverr.kotlin.model;
    exports com.marufeb.fiverr.kotlin.data;
}