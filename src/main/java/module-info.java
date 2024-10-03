module com.example.organigramma {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.organigramma to javafx.fxml;
    exports com.example.organigramma;
}