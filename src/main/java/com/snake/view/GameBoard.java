package com.snake.view;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GameBoard {
    private final Pane canvas;
    public GameBoard(Pane canvas){
        this.canvas = canvas;
    }

    /**
     * Draws a Shape onto our canvas
     * @param shape A shape as defined by javafx (Rectangle, Circle,...)
     */
    public void drawShape(Shape shape){
        Platform.runLater(() -> {
            canvas.getChildren().add(shape);
        });
    }
}
