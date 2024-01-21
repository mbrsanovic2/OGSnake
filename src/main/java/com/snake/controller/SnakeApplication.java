package com.snake.controller;

import com.snake.model.GameStep;
import com.snake.view.GameBoard;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;

/**
 * Defines the basic Things we need at the Start of our Application (View, Model, Controller)
 */
public class SnakeApplication extends Application {
    private Stage stage;
    private int screenWidth = 900;
    private int screenHeight = 600;
    public static final int RIGHT = 1, LEFT = -1, UP = -2, DOWN = 2;
    private GameStep.snakeColor_E startColor= GameStep.snakeColor_E.green;
    private static final String PATH_SOUND_NEW_HIGHSCORE = "src/main/resources/sound_new_highscore.mp3";
    private static final String PATH_SOUND_GAME_OVER = "src/main/resources/sound_game_over.mp3";

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
        Scene scene = new Scene(stackLayout, screenWidth, screenHeight);

        // Set the position of all the Windows to the center
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) * 0.5;
        double y = bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) * 0.5;
        stage.setX(x);
        stage.setY(y);

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

        // Create Button for Color-Options, change style & add function
        Button switchToOptions = new Button("Change Colors");
        switchToOptions.setStyle("-fx-background-color: #3498db");
        switchToOptions.setFont(Font.font("Arial",FontWeight.BOLD,18));
        switchToOptions.setMinSize(120, 60);
        switchToOptions.setOnAction(execute -> switchToOptionsScene());

        // Add to Pane so that it is now in our Scene
        stackLayout.getChildren().addAll(text, switchToGameplay, switchToOptions);
        StackPane.setAlignment(text, Pos.TOP_CENTER);
        StackPane.setAlignment(switchToGameplay, Pos.TOP_CENTER);
        StackPane.setAlignment(switchToOptions, Pos.TOP_CENTER);
        StackPane.setMargin(text, new Insets(22));
        StackPane.setMargin(switchToGameplay, new Insets(90));
        StackPane.setMargin(switchToOptions, new Insets(160));

        // Set the created scene to the stage
        stage.setScene(scene);
        stage.setResizable(false);
    }

    // Creates 6 ColorOption-Buttons to switch the color of the snake
    public void switchToOptionsScene(){
        StackPane stackLayout = new StackPane();
        stackLayout.setBackground(new Background(getBackground()));
        Scene scene = new Scene(stackLayout, screenWidth, screenHeight);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #3498db;");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        backButton.setMinSize(120, 60);
        backButton.setOnAction(execute->switchToMainMenuScene());
        StackPane.setAlignment(backButton,Pos.BOTTOM_CENTER);
        StackPane.setMargin(backButton,new Insets(30));

        Button selectGreen = new Button("Green");
        Button selectBlue = new Button("Blue");
        Button selectBlack = new Button("Black");
        Button selectRed = new Button("Red");
        Button selectGrey = new Button("Grey");
        Button selectYellow = new Button("Yellow");

        selectGreen.setStyle("-fx-background-color: green;-fx-text-fill: black;");
        selectGreen.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        selectGreen.setMinSize(190, 90);
        StackPane.setAlignment(selectGreen,Pos.TOP_LEFT);
        StackPane.setMargin(selectGreen,new Insets(150));
        selectGreen.setOnAction(execute -> {
            startColor= GameStep.snakeColor_E.green;
            switchToMainMenuScene();
        });

        selectBlue.setStyle("-fx-background-color: blue;-fx-text-fill: black;");
        selectBlue.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        selectBlue.setMinSize(190, 90);
        StackPane.setAlignment(selectBlue,Pos.TOP_CENTER);
        StackPane.setMargin(selectBlue,new Insets(150));
        selectBlue.setOnAction(execute-> {
            startColor = GameStep.snakeColor_E.blue;
            switchToMainMenuScene();
        });

        selectRed.setStyle("-fx-background-color: red;-fx-text-fill: black;");
        selectRed.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        selectRed.setMinSize(190, 90);
        StackPane.setAlignment(selectRed,Pos.TOP_RIGHT);
        StackPane.setMargin(selectRed,new Insets(150));
        selectRed.setOnAction(execute-> {
            startColor = GameStep.snakeColor_E.red;
            switchToMainMenuScene();
        });

        selectBlack.setStyle("-fx-background-color: black;-fx-text-fill: white;");
        selectBlack.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        selectBlack.setMinSize(190, 90);
        StackPane.setAlignment(selectBlack,Pos.CENTER_LEFT);
        StackPane.setMargin(selectBlack,new Insets(150));
        selectBlack.setOnAction(execute-> {
            startColor = GameStep.snakeColor_E.black;
            switchToMainMenuScene();
        });

        selectGrey.setStyle("-fx-background-color: grey;-fx-text-fill: white;");
        selectGrey.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        selectGrey.setMinSize(190, 90);
        StackPane.setAlignment(selectGrey,Pos.CENTER);
        StackPane.setMargin(selectGrey,new Insets(150));
        selectGrey.setOnAction(execute-> {
            startColor = GameStep.snakeColor_E.grey;
            switchToMainMenuScene();
        });

        selectYellow.setStyle("-fx-background-color: yellow;-fx-text-fill: black;");
        selectYellow.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        selectYellow.setMinSize(190, 90);
        StackPane.setAlignment(selectYellow,Pos.CENTER_RIGHT);
        StackPane.setMargin(selectYellow,new Insets(150));
        selectYellow.setOnAction(execute-> {
            startColor = GameStep.snakeColor_E.yellow;
            switchToMainMenuScene();
        });

        stackLayout.getChildren().addAll(backButton,selectGreen,selectBlack,selectBlue,selectGrey,selectRed,selectYellow);

        stage.setScene(scene);
        stage.setResizable(false);
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
        GameStep gameStep = new GameStep(gameBoard, screenWidth, screenHeight, startColor);

        GameThread thread = new GameThread(gameBoard, gameStep, this, 400);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            // Assign direction to keyboard (W,A,S,D keys) and prevent reverse movement
            if (key.getCode() == KeyCode.A && gameStep.getCurrentDirection() != RIGHT) {
                gameStep.setDirection(LEFT, thread);
            } else if (key.getCode() == KeyCode.D && gameStep.getCurrentDirection() != LEFT) {
                gameStep.setDirection(RIGHT, thread);
            } else if (key.getCode() == KeyCode.W && gameStep.getCurrentDirection() != DOWN) {
                gameStep.setDirection(UP, thread);
            } else if (key.getCode() == KeyCode.S && gameStep.getCurrentDirection() != UP) {
                gameStep.setDirection(DOWN, thread);
            }
        });
        thread.start();
    }

    // Create the Game Over screen
    public void switchToGameOverScreen(int score, int highscore) {
        if (score > highscore)
            playNewHighscoreSound();
        else
            playGameOverSound();

        StackPane stackLayout = new StackPane();
        Image textureImage = new Image("wolke_meow.png");

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(textureImage);

        // Set the Background
        stackLayout.setBackground(new Background(getGameOverScreen()));

        // Create a text to display the highscore
        Text highscoreText = new Text("Highscore: " + highscore);
        highscoreText.setFont(Font.font("Arial", 20));
        highscoreText.setFill(Color.WHITE);
        highscoreText.setTextAlignment(TextAlignment.CENTER);

        stackLayout.getChildren().add(highscoreText);
        highscoreText.setTranslateY(200);

        // Create a text to display the score
        Text scoreText = new Text("Your score: " + score);
        scoreText.setFont(Font.font("Arial", 20));
        scoreText.setFill(Color.WHITE);
        scoreText.setTextAlignment(TextAlignment.CENTER);

        stackLayout.getChildren().add(scoreText);
        scoreText.setTranslateY(250);

        stackLayout.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.BOTTOM_RIGHT);
        imageView.setTranslateX(55);
        imageView.setTranslateY(55);

        Scene scene = new Scene(stackLayout, 900, 600);

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.setResizable(false);
        });

        // Switch to the main menu when any button is pressed
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
    public void stop() {System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }

    public void playGameOverSound()
    {
        Media sound = new Media(new File(PATH_SOUND_GAME_OVER).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        mediaPlayer.setOnEndOfMedia(mediaPlayer::stop);

        mediaPlayer.play();
    }

    public void playNewHighscoreSound()
    {
        Media sound = new Media(new File(PATH_SOUND_NEW_HIGHSCORE).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        mediaPlayer.setOnEndOfMedia(mediaPlayer::stop);

        mediaPlayer.play();
    }


}