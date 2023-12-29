package com.snake.controller;

import com.snake.model.GameStep;
import com.snake.view.GameBoard;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Defines the basic Things we need at the Start of our Application (View, Model, Controller)
 */
public class SnakeApplication extends Application {
    /**
     * Our Pseudo Main Method generates all starter Objects and Sets them
     *
     * @param stage JavaFx iternal thing
     */
    @Override
    public void start(Stage stage) {

        // Create a Canvas to Paint things on
        Pane canvas = new Pane();

        // Create the window with the specified size and canvas
        Scene scene = new Scene(canvas, 900, 600);
        // Cannot resize Window

        // Set up the stage
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
        GameBoard gameBoard = new GameBoard(canvas);
        GameStep gameStep = new GameStep(gameBoard);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            // assign direction to keyboard and prevent reverse movement
            if (key.getCode() == KeyCode.A && gameStep.getDirection() != 1) {
                gameStep.setDirection(-1);
            } else if (key.getCode() == KeyCode.D && gameStep.getDirection() != -1) {
                gameStep.setDirection(1);
            } else if (key.getCode() == KeyCode.W && gameStep.getDirection() != 2) {
                gameStep.setDirection(-2);
            } else if (key.getCode() == KeyCode.S && gameStep.getDirection() != -2) {
                gameStep.setDirection(2);
            }
        });
        GameThread thread = new GameThread(gameBoard, gameStep, 400);
        thread.start();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}