module com.devops.javaprojet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.devops.javaprojet to javafx.fxml;
    exports com.devops.javaprojet;
    exports com.devops.javaprojet.client;
    opens com.devops.javaprojet.client to javafx.fxml;
}