module com.example.bomberman {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens bomberman to javafx.fxml;
    exports bomberman;
}