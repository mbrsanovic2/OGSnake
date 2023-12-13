module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.snake.controller;
    exports com.snake.model;
    exports com.snake.view;
    opens com.snake.controller to javafx.fxml;
}