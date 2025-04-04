module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.httpserver;
    requires com.google.gson;
    requires java.sql;
    requires java.net.http;


    opens com.project to javafx.fxml;
    opens com.project.apiServer to com.google.gson;
    exports com.project;
    opens com.project.models to com.google.gson;
}