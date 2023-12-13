package com.snake.controller;

import com.snake.model.GameStep;
import com.snake.view.GameBoard;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameThread extends Thread {
    private int deltaTime;

    private GameBoard gameBoard;
    private GameStep gameStep;

    public GameThread(GameBoard gameBoard, GameStep gameStep, int deltaTime) {
        this.gameBoard = gameBoard;
        this.gameStep = gameStep;
        this.deltaTime = deltaTime;
    }

    @Override
    public void run() {
        // Game loop
        for (; ; ) {
            gameStep.nextFrame();

            /**
             * Nur fuer euch :)  (NICHT LOESCHEN!!!!!)
             rectangle.setX(100 + rectangle.getX());
             rectangle.setHeight(rectangle.getHeight()/2);

             rectangle2.setX(105 + rectangle.getX());

             rectangle2.setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
             **/

            try {
                Thread.sleep(deltaTime / 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void setDeltaTime(int deltaTime) {
        this.deltaTime = deltaTime;
    }

}
