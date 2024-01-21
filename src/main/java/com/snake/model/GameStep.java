package com.snake.model;

import com.snake.controller.SnakeApplication;
import com.snake.view.GameBoard;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private int screenWidth;
    private int screenHeight;
    private SnakeSegment head, tail;
    private int score = 0;

    //enum for the color pattern & start color
    private snakeColor_E color_e;
    private Color bodyColor;

    private List<SnakeSegment> snakeAsList = new LinkedList<>();

    // Create a food rectangle
    private Rectangle food = new Rectangle(0, 0, 50, 50);

    // Reference to View for Drawing purposes
    private GameBoard gameBoard;

    private static final String PATH_SOUND_EAT_FOOD = "src/main/resources/sound_eat_food.mp3";
    MediaPlayer mediaPlayer;

    /**
     * Constructor which defines our Snake at the Start, and sets our View
     *
     * @param gameBoard Our View (Ding das Zeichnet)
     */
    public GameStep(GameBoard gameBoard, int screenWidth, int screenHeight, snakeColor_E color_e) {

        this.color_e=color_e;
        bodyColor=getColor(color_e);
        this.gameBoard = gameBoard;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        // Create 3 snake objects for basic snake at the beginning
        snakeAsList.add((new SnakeSegment(150, 200, bodyColor.darker())));
        snakeAsList.add((new SnakeSegment(100, 200, bodyColor)));
        snakeAsList.add((new SnakeSegment(50, 200, bodyColor)));

        for (SnakeSegment snake : snakeAsList) {
            gameBoard.drawShape(snake.getRect());
        }
        // Spawn the first food and print it
        spawnFood();
        gameBoard.drawShape(food);

        // Media player for eat food sound
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
        // Save values for head and tail of snake
        savePosition();
        // Set the game speed value back to 0 to only speed up if food is eaten
        gameSpeed = 0;
        // Save the position of the tail to add it if the food is eaten
        int snakeTailX = (int) tail.getXPos();
        int snakeTailY = (int) tail.getYPos();

        movementUpdate();

        // Save new values for head and tail of snake after movement
        savePosition();

        // Spawn food on another position if food is eaten and make snake larger
        if (food.getX() == head.getXPos() && food.getY() == head.getYPos()) {
            // Play eat food sound and spawn new food
            playSoundEatFood();
            spawnFood();
            // Add a new tail at the position of the old one
            snakeAsList.add(new SnakeSegment(snakeTailX, snakeTailY, bodyColor));
            // Print the new Snake segment
            gameBoard.drawShape(snakeAsList.get(snakeAsList.size() - 1).getRect());
            // Increment score by 1
            incrementScore();
        }

        // Save new values for head and tail of snake after eating
        savePosition();

        return checkIfOver();
    }

    // Returns Color based on Enum
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

    // Changes the color of the entire snake and makes the head darker
    private void changeColor(Color color){
        for (SnakeSegment s : snakeAsList)
            s.setColor(color);
        snakeAsList.get(0).setColor(color.darker());
    }

    public boolean checkIfOver() {
        // Collision detection: Snake beyond borders
        // If the direction is not changed at the borders and snake's head is outside border then end the game
        if (head.getXPos() >= screenWidth | head.getXPos() < 0) return true;
        else if (head.getYPos() >= screenHeight | head.getYPos() < 0) return true;

        // Collision detection: Snake bites itself
        // If the snake head is in the same position as another snake segment then end the game
        for (int i = 1; i < snakeAsList.size(); i++) {
            if (head.getXPos() == snakeAsList.get(i).getXPos() && head.getYPos() == snakeAsList.get(i).getYPos()) {
                return true;
            }
        }
        return false;
    }

    public void savePosition() {
        head = snakeAsList.get(0);
        tail = snakeAsList.get(snakeAsList.size() - 1);
    }

    public void movementUpdate() {
        // Set new values for last element depending on direction
        if (direction == SnakeApplication.RIGHT || direction == SnakeApplication.LEFT) {
            tail.setXPos(head.getXPos() + (50 * direction));
            tail.setYPos(head.getYPos());
        } else if (direction == SnakeApplication.DOWN || direction == SnakeApplication.UP) {
            tail.setXPos(head.getXPos());
            tail.setYPos(head.getYPos() + ((double) (50 * direction) / 2));
        }

        // Add the last element as new element at the head of snake (first element)
        snakeAsList.add(0, tail);

        // Set color of snake, needed to make headColor correct
        changeColor(getColor(color_e));

        // Remove last element of snake
        snakeAsList.remove(snakeAsList.size() - 1);

        currentDirection = direction;
    }

    public void setDirection(int direction, Thread thread) {
        if (this.direction != direction) thread.interrupt();
        this.direction = direction;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    // Spawn food at a random position and print it
    public void spawnFood() {
        food.setX(generateRandomPosition(screenWidth / 100));
        food.setY(generateRandomPosition(screenHeight / 100));
        // Spawn food again if it is on the same position as a snake segment
        for (SnakeSegment snake : snakeAsList) {
            if (food.getX() == snake.getXPos() && food.getY() == snake.getYPos()) {
                spawnFood();
            }
        }
        // Increase the snake speed (frame rate) with every meal eaten
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

    // Enum for Color-pattern
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
