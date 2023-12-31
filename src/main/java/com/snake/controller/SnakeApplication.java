package com.snake.controller;

import com.snake.model.GameStep;
import com.snake.view.GameBoard;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Defines the basic Things we need at the Start of our Application (View, Model, Controller)
 */
public class SnakeApplication extends Application {
    private Stage stage;
    /**
     * Our Pseudo Main Method generates all starter Objects and Sets them
     *
     * @param stage JavaFx iternal thing
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;

        // Set up the stage
        stage.setTitle("Snake");
        switchToMainMenuScene();
        stage.show();
    }

    private void switchToMainMenuScene() {
        StackPane stackLayout = new StackPane();
        stackLayout.setBackground(new Background(getBackground()));

        // Replace current Scene with MainMenuScene
        stage.setScene(new Scene(stackLayout, 500, 400));

        // Create Button and Text
        Button switchToGameplay = new Button("Play!");
        Text text = new Text("SNAKE");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 60));

        // Change the Style of the Button
        switchToGameplay.setStyle("-fx-background-color: #3498db;");
        switchToGameplay.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        switchToGameplay.setMinSize(120, 60);

        // Add Button functionality -> OnClick execute switchToGameplayScene()
        switchToGameplay.setOnAction(e -> switchToGameplayScene());

        // Add to Pane so that it is now in our Scene
        stackLayout.getChildren().addAll(text, switchToGameplay);
        StackPane.setAlignment(text, Pos.TOP_CENTER);
        StackPane.setAlignment(switchToGameplay, Pos.TOP_CENTER);
        StackPane.setMargin(text, new Insets(22));
        StackPane.setMargin(switchToGameplay, new Insets(130));
    }

    private static BackgroundImage getBackground() {
        Image backgroundImage = new Image("snake.png");
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        return background;
    }

    private void switchToGameplayScene() {
        // Create a Canvas to Paint things on
        Pane canvas = new Pane();
        // Create the window with the specified size and canvas
        Scene scene = new Scene(canvas, 900, 600);
        stage.setScene(scene);
        GameBoard gameBoard = new GameBoard(canvas);
        GameStep gameStep = new GameStep(gameBoard);

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) * 0.55;
        double y = bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) * 0.5;
        stage.setX(x);
        stage.setY(y);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            // assign direction to keyboard and prevent reverse movement
            if (key.getCode() == KeyCode.A && gameStep.getCurrentDirection() != 1) {
                gameStep.setDirection(-1);
            } else if (key.getCode() == KeyCode.D && gameStep.getCurrentDirection() != -1) {
                gameStep.setDirection(1);
            } else if (key.getCode() == KeyCode.W && gameStep.getCurrentDirection() != 2) {
                gameStep.setDirection(-2);
            } else if (key.getCode() == KeyCode.S && gameStep.getCurrentDirection() != -2) {
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