module com.example.organigramma {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.organigramma to javafx.fxml;
    exports com.example.organigramma.UserInterface;
    opens com.example.organigramma.UserInterface to javafx.fxml;
}