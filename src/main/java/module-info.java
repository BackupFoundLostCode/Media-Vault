module com.example.prog2_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens com.example.prog2_project to javafx.fxml, org.hibernate.orm.core;

    exports com.example.prog2_project;
    exports com.example.prog2_project.Medien;
    opens com.example.prog2_project.Medien to javafx.fxml, org.hibernate.orm.core;
    exports com.example.prog2_project.Database;
    opens com.example.prog2_project.Database to javafx.fxml, org.hibernate.orm.core;
}