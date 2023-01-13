module com.devops.javaprojet {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.devops.javaprojet.client;
    opens com.devops.javaprojet.client to javafx.fxml;
}