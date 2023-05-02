module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires java.net.http;
    requires gson;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires transitive com.fasterxml.jackson.core;
    requires transitive javafx.graphics;
    opens com.example to javafx.fxml;
    exports com.example;
}
