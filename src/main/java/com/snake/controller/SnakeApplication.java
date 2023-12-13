package com.snake.controller;

import com.snake.model.GameStep;
import com.snake.view.GameBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SnakeApplication extends Application {
    @Override
    public void start(Stage stage) {

        // Create a Canvas to Paint things on
        Pane canvas = new Pane();

        // Create the window with the specified size and canvas
        Scene scene = new Scene(canvas, 900, 600);
        // Cannot resize Window

        // Set up the stage
        stage.setTitle("Rectangle Drawing App");
        stage.setScene(scene);
        stage.show();
        GameBoard gameBoard = new GameBoard(canvas);
        GameStep gameStep = new GameStep(gameBoard);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.A) {
                gameStep.setDirection(-1);
            }else if(key.getCode()== KeyCode.D){
                gameStep.setDirection(1);
            }
            //todo: Add other keys
        });
        GameThread thread = new GameThread(gameBoard, gameStep, 4000);

        thread.start();
    }

    public static void main(String[] args) {
        launch();
    }
}