package com.snake.model;

import com.snake.controller.GameThread;
import com.snake.controller.SnakeApplication;
import com.snake.view.GameBoard;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import java.util.Random;

/**
 * Class that defines the Logic of our Program, for example where to Draw the Snake/Fruit
 */
public class GameStep {
    private int direction = 1;
    private int currentDirection = 1;
    private int gameSpeed = 0;
    private int score = 0;

    //enum for the color pattern & start color
    private snakeColor_E color_e;
    private Color bodyColor;

    private List<SnakeSegment> snakeAsList = new LinkedList<>();

    //Create a food rectangle
    private Rectangle food = new Rectangle(0, 0, 50, 50);

    // Reference to View for Drawing purposes
    private GameBoard gameBoard;

    private static final String PATH_SOUND_EAT_FOOD = "src/main/resources/sound_eat_food.mp3";
    MediaPlayer mediaPlayer;

    // todo: Game Over

    /**
     * Constructor which defines our Snake at the Start, and sets our View
     *
     * @param gameBoard Our View (Ding das Zeichnet)
     */
    public GameStep(GameBoard gameBoard, snakeColor_E color_e) {

        this.color_e=color_e;
        bodyColor=getColor(color_e);
        this.gameBoard = gameBoard;
        // Create 3 snake objects for basic snake at the beginning
        snakeAsList.add((new SnakeSegment(150, 200, bodyColor.darker())));
        snakeAsList.add((new SnakeSegment(100, 200, bodyColor)));
        snakeAsList.add((new SnakeSegment(50, 200, bodyColor)));

        for (SnakeSegment snake : snakeAsList) {
            gameBoard.drawShape(snake.getRect());
        }
        // spawn the first food and print it
        spawnFood();
        gameBoard.drawShape(food);


        // media player for eat food sound
        Media sound = new Media(new File(PATH_SOUND_EAT_FOOD).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());
    }

    /**
     * What should happen every Frame (ca. 400 ms)? This is defined in this Method
     *
     * @return If the Game-Loop should be stopped
     */
    public boolean nextFrame() {
        //set the game speed value back to 0 so that the snake doesn´t speed up in every frame but only when a meal is eaten
        gameSpeed = 0;
        // save the position of the tail to add it if the food is eaten
        int snakeTailX = (int) snakeAsList.get(snakeAsList.size() - 1).getXPos();
        int snakeTailY = (int) snakeAsList.get(snakeAsList.size() - 1).getYPos();

        // set new values for last element depending on direction
        if (direction == 1 || direction == -1) {
            snakeAsList.get(snakeAsList.size() - 1).setXPos((int) (snakeAsList.get(0).getXPos() + (50 * direction)));
            snakeAsList.get(snakeAsList.size() - 1).setYPos((int) snakeAsList.get(0).getYPos());
        } else if (direction == 2 || direction == -2) {
            snakeAsList.get(snakeAsList.size() - 1).setXPos((int) snakeAsList.get(0).getXPos());
            snakeAsList.get(snakeAsList.size() - 1).setYPos((int) (snakeAsList.get(0).getYPos() + (50 * direction / 2)));
        }

        // add the last element as new element at the head of snake (first element)
        snakeAsList.add(0, snakeAsList.get(snakeAsList.size() - 1));

        //set color of snake, needed to make headColor correct
        changeColor(getColor(color_e));

        // remove last element of snake
        snakeAsList.remove(snakeAsList.size() - 1);

        currentDirection = direction;

        // spawn food on another position if food is eaten
        if (food.getX() == snakeAsList.get(0).getXPos() && food.getY() == snakeAsList.get(0).getYPos()) {
            // Play eat food sound and spawn new food
            playSoundEatFood();
            spawnFood();

            // add a new "tail" at the position of the old one
            snakeAsList.add(new SnakeSegment(snakeTailX, snakeTailY, bodyColor));
            // Print the new Snake segment
            gameBoard.drawShape(snakeAsList.get(snakeAsList.size() - 1).getRect());
            // Increment score by 1
            incrementScore();
        }

        return checkIfOver();
    }

    //Returns Color based on Enum
    private Color getColor(snakeColor_E snakeColor_e){
        switch (color_e){
            case green -> {
                return Color.GREEN;
            }
            case red -> {
                return Color.RED;
            }
            case blue -> {
                return (Color.BLUE);
            }
            case black -> {
                return (Color.BLACK);
            }
            case grey -> {
                return (Color.GRAY);
            }
            case yellow -> {
                return (Color.YELLOW);
            }
            default -> {
                return Color.GREEN;
            }
        }
    }

    //changes the color of the entire snake and makes the head darker
    private void changeColor(Color color){
        for (SnakeSegment s : snakeAsList)
            s.setColor(color);
        snakeAsList.get(0).setColor(color.darker());
    }

    // todo: Checking if Game should be Over
    public boolean checkIfOver() {
        // Collision detection: Snake beyond borders
        // If the direction is not changed at the borders and snake's head is outside border then end the game
        if (snakeAsList.get(0).getXPos() >= 900 | snakeAsList.get(0).getXPos() < 0) return true;
        else if (snakeAsList.get(0).getYPos() >= 600 | snakeAsList.get(0).getYPos() < 0) return true;

        // Collision detection: Snake bites itself
        // If the snake head is in the same position as another snake segment then end the game
        for (int i = 1; i < snakeAsList.size(); i++) {
            if (snakeAsList.get(0).getXPos() == snakeAsList.get(i).getXPos() && snakeAsList.get(0).getYPos() == snakeAsList.get(i).getYPos()) {
                gameBoard.drawShape(new Text("Hello Test"));
                return true;
            }
        }
        return false;
    }

    public void setDirection(int direction, Thread thread) {
        if (this.direction != direction) thread.interrupt();
        this.direction = direction;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    //spawn food at a random position and print it (9 and 6 for max size of the scene)
    public void spawnFood() {
        food.setX(generateRandomPosition(9));
        food.setY(generateRandomPosition(6));
        //spawn food again if it is on the same position as a snake segment
        for (SnakeSegment snake : snakeAsList) {
            if (food.getX() == snake.getXPos() && food.getY() == snake.getYPos()) {
                spawnFood();
            }
        }
        //increase the snake speed (frame rate) with every meal eaten
        gameSpeed += 5;
    }

    // Create a Random object for food position
    public int generateRandomPosition(int bound) {
        Random random = new Random();
        return random.nextInt(bound) * 100;
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    //Enum for Color-pattern
    public enum snakeColor_E{
        green,
        red,
        blue,
        rainbow,
        rainbowSegments,
        yellow,
        black,
        grey
    }

    public void incrementScore()
    {
        score++;
    }

    public int getScore(){
        return score;
    }

    public void playSoundEatFood()
    {
        mediaPlayer.play();
    }
}
