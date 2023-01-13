module com.devops.javaprojet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.mariadb.jdbc;
    requires java.sql;


    exports com.devops.javaprojet.client;
    opens com.devops.javaprojet.client to javafx.fxml;
}