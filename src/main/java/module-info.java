module com.di.gbl.empleados {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.di.gbl to javafx.fxml;
    exports com.di.gbl;
    exports com.di.gbl.controllers;
    opens com.di.gbl.controllers to javafx.fxml;
    opens com.di.gbl.model;
}