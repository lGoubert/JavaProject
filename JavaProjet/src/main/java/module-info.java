module com.devops.javaprojet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.mariadb.jdbc;
    requires java.sql;
    requires java.desktop;


    exports com.devops.javaprojet.client;
    opens com.devops.javaprojet.client to javafx.fxml;
}