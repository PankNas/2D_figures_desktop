module com.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires model;
    requires java.desktop;
    requires javafx.swing;
    requires lombok;
    requires org.slf4j;
    requires mongo.java.driver;
    requires com.fasterxml.jackson.databind;

    opens com.gui to javafx.fxml;
    exports com.gui;
}