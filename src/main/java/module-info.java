module comp3111.examsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires javafx.base;
    requires org.jooq;


    opens comp3111.examsystem to javafx.fxml;
    opens comp3111.examsystem.entity to javafx.fxml, javafx.base;
    exports comp3111.examsystem;
    opens comp3111.examsystem.controller to javafx.fxml;
    exports comp3111.examsystem.controller;
}