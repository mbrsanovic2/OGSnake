module com.snake.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports com.snake.controller;
    exports com.snake.model;
    exports com.snake.view;
    opens com.snake.controller to javafx.fxml;
}