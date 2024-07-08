module com.example.filamed {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.example.filamed to javafx.fxml;
    exports com.example.filamed;
}