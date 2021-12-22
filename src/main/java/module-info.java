module io.github.xf8b.atsjobbook {
    requires kotlin.stdlib; // kotlin
    requires javafx.controls; // javafx
    requires javafx.fxml; // javafx fxml
    requires org.slf4j; // logging

    opens io.github.xf8b.atsjobbook to javafx.fxml; // required for fxml reflection

    exports io.github.xf8b.atsjobbook; // idk lol
}