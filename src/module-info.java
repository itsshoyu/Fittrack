module com.fittrack {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive java.sql;
    
    requires com.google.gson;
requires jbcrypt;    

    opens com.fittrack.controller to javafx.fxml;
    opens com.fittrack.model to javafx.fxml, com.google.gson, javafx.base;
    
    exports com.fittrack;
    exports com.fittrack.model;
}
