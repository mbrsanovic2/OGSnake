package com.snake.controller;

import com.snake.model.GameStep;
import com.snake.view.GameBoard;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Is responsible for alle the Game-Logic, implements some sort of Time Control
 */
public class GameThread extends Thread {
    private int deltaTime;

    private GameBoard gameBoard;
    private SnakeApplication snakeApplication;
    private GameStep gameStep;

    /**
     * Constructor For our Game Thread
     *
     * @param gameBoard Our View (Draws Things on the Canvas)
     * @param gameStep  Our Logic (Defines what to Draw)
     * @param deltaTime How long for every Frame
     */
    public GameThread(GameBoard gameBoard, GameStep gameStep, SnakeApplication snakeApplication, int deltaTime) {
        this.gameBoard = gameBoard;
        this.gameStep = gameStep;
        this.deltaTime = deltaTime;
        this.snakeApplication = snakeApplication;
    }

    /**
     * Defines what happens in the Game Loop, implements things like time control(every Deltatime in Milliseconds)
     */
    @Override
    public void run() {
        // Game loop
        for (; ; ) {
            long startTime = System.currentTimeMillis();
            // Let each thread wait for specific deltaTime (in millisec) before the next starts
            try {
                Thread.sleep(deltaTime);
            } catch (InterruptedException ignored) {
                // If the thread is interrupted while sleeping, put it back to sleep
                long remainingTime = deltaTime - (System.currentTimeMillis() - startTime);
                //if (remainingTime > deltaTime * 0.85) continue;
                if (remainingTime > 0) {
                    try {
                        Thread.sleep(remainingTime / 2);
                    } catch (InterruptedException interrupt) {
                        continue;
                    }
                }
            }
            //Set the maximum speed of the snake
            if (deltaTime > 100) {
                //Increase the speed of the snake with every food that is eaten
                deltaTime = deltaTime - gameStep.getGameSpeed();
            }
            if (gameStep.nextFrame()) break;

            /**
             * Nur fuer euch :)  (NICHT LOESCHEN!!!!!)
             rectangle.setX(100 + rectangle.getX());
             rectangle.setHeight(rectangle.getHeight()/2);

             rectangle2.setX(105 + rectangle.getX());

             rectangle2.setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
             **/
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        //If game over switch to game over screen
        snakeApplication.switchToGameOverScreen();
    }


    public void setDeltaTime(int deltaTime) {
        this.deltaTime = deltaTime;
    }

}
