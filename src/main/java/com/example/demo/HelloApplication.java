package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
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


        GameThread thread = new GameThread();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.A) {
                thread.setDirection(-1);
            }else if(key.getCode()== KeyCode.D){
                thread.setDirection(1);
            }
        });
        thread.setPane(canvas);
        thread.setDeltaTime(5000);
        thread.start();
    }

    public static void main(String[] args) {
        launch();
    }
}