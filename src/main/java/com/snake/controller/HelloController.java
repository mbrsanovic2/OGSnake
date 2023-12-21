package com.snake.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    // todo: Adding a MainMenu Scene for Mode Selection
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}