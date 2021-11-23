module com.example.bomberman {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens bomberman to javafx.fxml;
    exports bomberman;
    exports bomberman.GlobalVariable;
    opens bomberman.GlobalVariable to javafx.fxml;
    exports bomberman.Object;
    opens bomberman.Object to javafx.fxml;
    exports bomberman.Object.NonMovingObject;
    opens bomberman.Object.NonMovingObject to javafx.fxml;
    exports bomberman.Object.MovingObject;
    opens bomberman.Object.MovingObject to javafx.fxml;
    exports bomberman.Object.MovingObject.Threats;
    opens bomberman.Object.MovingObject.Threats to javafx.fxml;
    exports bomberman.Object.Map;
    opens bomberman.Object.Map to javafx.fxml;
    exports bomberman.Object.MovingObject.Bomber;
    opens bomberman.Object.MovingObject.Bomber to javafx.fxml;
}