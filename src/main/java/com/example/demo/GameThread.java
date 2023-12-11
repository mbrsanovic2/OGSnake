package com.example.demo;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameThread extends Thread{
    private int deltaTime = 50;

    private int direction;

    public void setDirection(int direction) {
        this.direction = direction;
    }

    private Pane canvas;
    @Override
    public void run() {
        List<Rectangle> rectangles = new ArrayList<>();
        // v: X, v1: Y, v2: width, v3: height
        rectangles.add(new Rectangle(300, 0, 100, 100));
        rectangles.add(new Rectangle(200, 0, 100, 100));
        rectangles.add(new Rectangle(100, 0, 100, 100));

        Platform.runLater(() -> {
            for (Rectangle rectangle: rectangles) {
                canvas.getChildren().add(rectangle);
            }
        });
        // Game loop
        for (;;) {
            rectangles.getLast().setX(rectangles.getFirst().getX() + 100 * direction);

            rectangles.addFirst(rectangles.getLast());

            rectangles.removeLast();
            /**
             * Nur fuer euch :)  (NICHT LOESCHEN!!!!!)
            rectangle.setX(100 + rectangle.getX());
            rectangle.setHeight(rectangle.getHeight()/2);

            rectangle2.setX(105 + rectangle.getX());

            rectangle2.setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
             **/

            // Create new Rectangle 2. Layer

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
