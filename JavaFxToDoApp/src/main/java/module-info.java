module com.javafxtodoapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.net.http;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports com.javafxtodoapp;
    opens com.javafxtodoapp to javafx.fxml, com.fasterxml.jackson.databind;
}