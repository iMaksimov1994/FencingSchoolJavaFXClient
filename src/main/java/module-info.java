module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.prefs;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens org.example to javafx.fxml;
    exports org.example;
    exports org.model to com.fasterxml.jackson.databind;
    exports org.reauthorization;
    opens org.reauthorization to javafx.fxml;
    opens org.model to javafx.base;

}