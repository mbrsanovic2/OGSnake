package com.snake.controller;

import com.snake.model.GameStep;
import com.snake.view.GameBoard;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    public void switchToMainMenuScene() {
        StackPane stackLayout = new StackPane();
        stackLayout.setBackground(new Background(getBackground()));
        Platform.runLater(() -> {
            stage.setScene(new Scene(stackLayout, 500, 400));
            stage.setResizable(false);
        });
        // Replace current Scene with MainMenuScene

        // Create Button and Text
        Button switchToGameplay = new Button("Play!");
        Text text = new Text("SNAKE");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 60));

        // Change the Style of the Button
        switchToGameplay.setStyle("-fx-background-color: #3498db;");
        switchToGameplay.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        switchToGameplay.setMinSize(120, 60);

        // Add Button functionality -> OnClick execute switchToGameplayScene()
        switchToGameplay.setOnAction(execute -> switchToGameplayScene());

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

    public void switchToGameplayScene() {
        // Create a Canvas to Paint things on
        Pane canvas = new Pane();
        // Create the window with the specified size and canvas (not resizable)
        Scene scene = new Scene(canvas, 900, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        GameBoard gameBoard = new GameBoard(canvas);
        GameStep gameStep = new GameStep(gameBoard);

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) * 0.55;
        double y = bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) * 0.5;
        stage.setX(x);
        stage.setY(y);
        GameThread thread = new GameThread(gameBoard, gameStep, this, 450);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            // assign direction to keyboard and prevent reverse movement
            if (key.getCode() == KeyCode.A && gameStep.getCurrentDirection() != 1) {
                gameStep.setDirection(-1, thread);
            } else if (key.getCode() == KeyCode.D && gameStep.getCurrentDirection() != -1) {
                gameStep.setDirection(1, thread);
            } else if (key.getCode() == KeyCode.W && gameStep.getCurrentDirection() != 2) {
                gameStep.setDirection(-2, thread);
            } else if (key.getCode() == KeyCode.S && gameStep.getCurrentDirection() != -2) {
                gameStep.setDirection(2, thread);
            }
        });
        thread.start();
    }

    //Create the Game Over screen
    public void switchToGameOverScreen() {
        StackPane stackLayout = new StackPane();
        Image textureImage = new Image("wolke_meow.png");

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(textureImage);

        stackLayout.setBackground(new Background(getGameOverScreen()));


        stackLayout.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.BOTTOM_RIGHT);
        imageView.setTranslateX(55);
        imageView.setTranslateY(55);

        Scene scene = new Scene(stackLayout, 900, 600);

        //Nicht auf dem Main Thread laufen
        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.setResizable(false);
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switchToMainMenuScene();
        });

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(10), imageView);

        // Set the end position for the animation
        translateTransition.setToX(-1);
        translateTransition.setToY(-1);

        // Set the cycle count to indefinite for continuous animation
        translateTransition.setCycleCount(1);

        // Play the animation
        translateTransition.play();
    }

    private static BackgroundImage getGameOverScreen() {
        Image backgroundImage = new Image("bluescreen_of_death.jpg");
        BackgroundImage gameOver = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        return gameOver;
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}