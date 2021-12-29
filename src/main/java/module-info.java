module io.github.xf8b.atsjobbook {
    requires kotlin.stdlib; // kotlin
    requires kotlin.reflect; // kotlin reflection
    requires javafx.controls; // javafx
    requires javafx.fxml; // javafx fxml
    requires org.slf4j; // logging
    requires com.google.gson; // json

    opens io.github.xf8b.atsjobbook.view to javafx.fxml; // for fxml
    opens io.github.xf8b.atsjobbook.model to com.google.gson; // for gson serialization

    exports io.github.xf8b.atsjobbook; // allow main class to be used
}