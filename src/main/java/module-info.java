module comp3111.examsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires javafx.base;
    requires org.jooq;
    requires java.sql;

    opens comp3111.examsystem to javafx.fxml;
    opens comp3111.examsystem.entity to javafx.fxml, javafx.base, java.sql, org.jooq;
    exports comp3111.examsystem;
    opens comp3111.examsystem.controller to javafx.fxml;
    exports comp3111.examsystem.controller;
    exports com.examsystem.jooq.generated.tables.records to org.jooq;
    opens com.examsystem.jooq.generated.tables to org.jooq;
}

