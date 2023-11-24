package com.example.demo;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class GameThread extends Thread{
    private int deltaTime = 50;
    private Rectangle output;

    private Pane canvas;
    @Override
    public void run() {
        Random rng = new Random();
        for (;;) {
            // Create new Rectangle 2. Layer
            Rectangle snakeBlock = new Rectangle(rng.nextInt(400),rng.nextInt(400),rng.nextInt(600),rng.nextInt(600));
            snakeBlock.setFill(Color.rgb(rng.nextInt(222), rng.nextInt(222), rng.nextInt(222)));
            Circle c = new Circle(rng.nextInt(400),rng.nextInt(400),rng.nextInt(600));
            c.setFill(Color.rgb(rng.nextInt(222), rng.nextInt(222), rng.nextInt(222)));
            Platform.runLater(() -> {
                canvas.getChildren().add(c);
                canvas.getChildren().add(snakeBlock);
            });

            try {
                Thread.sleep(deltaTime/10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public void setDeltaTime(int deltaTime) {
        this.deltaTime = deltaTime;
    }

    public void setPane(Pane pane) {
        this.canvas = pane;
    }
}
