package com.snake.model;

import com.snake.controller.SnakeApplication;
import com.snake.view.GameBoard;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

import java.util.Random;

/**
 * Class that defines the Logic of our Program, for example where to Draw the Snake/Fruit
 */
public class GameStep {
    private int direction = 1;
    private int currentDirection = 1;

    final private Color headColor = Color.DARKGREEN;
    final private Color bodyColor = Color.GREEN;
    private List<SnakeSegment> snakeAsList = new LinkedList<>();

    //Create a food rectangle
    private Rectangle food = new Rectangle(0, 0, 100, 100);

    // Reference to View for Drawing purposes
    private GameBoard gameBoard;

    // todo: Game Over

    /**
     * Constructor which defines our Snake at the Start, and sets our View
     *
     * @param gameBoard Our View (Ding das Zeichnet)
     */
    public GameStep(GameBoard gameBoard) {

        this.gameBoard = gameBoard;
        // initial Rectangle parameters v: X, v1: Y, v2: width, v3: height
        snakeAsList.add((new SnakeSegment(300, 200, headColor)));
        snakeAsList.add((new SnakeSegment(200, 200)));
        snakeAsList.add((new SnakeSegment(100, 200)));

        for (SnakeSegment snake : snakeAsList) {
            gameBoard.drawShape(snake.getRect());
        }
        // spawn the first food and print it
        spawnFood();
        gameBoard.drawShape(food);
    }

    /**
     * What should happen every Frame (ca. 400 ms)? This is defined in this Method
     *
     * @return If the Game-Loop should be stopped
     */
    public boolean nextFrame() {
        // save the position of the tail to add it if the food is eaten
        int snakeTailX = (int) snakeAsList.get(snakeAsList.size() - 1).getXPos();
        int snakeTailY = (int) snakeAsList.get(snakeAsList.size() - 1).getYPos();

        // set new values for last element depending on direction
        if (direction == 1 || direction == -1) {
            snakeAsList.get(snakeAsList.size() - 1).setXPos((int) (snakeAsList.get(0).getXPos() + (100 * direction)));
            snakeAsList.get(snakeAsList.size() - 1).setYPos((int) snakeAsList.get(0).getYPos());
        } else if (direction == 2 || direction == -2) {
            snakeAsList.get(snakeAsList.size() - 1).setXPos((int) snakeAsList.get(0).getXPos());
            snakeAsList.get(snakeAsList.size() - 1).setYPos((int) (snakeAsList.get(0).getYPos() + (100 * direction / 2)));
        }

        // add the last element as new element at the head of snake (first element)
        snakeAsList.add(0, snakeAsList.get(snakeAsList.size() - 1));
        for (SnakeSegment s : snakeAsList)
            s.setColor(bodyColor);
        snakeAsList.get(0).setColor(headColor);

        // remove last element of snake
        snakeAsList.remove(snakeAsList.size() - 1);

        currentDirection = direction;

        // spawn food on another position if food is eaten
        if (food.getX() == snakeAsList.get(0).getXPos() && food.getY() == snakeAsList.get(0).getYPos()) {
            spawnFood();
            // add a new "tail" at the position of the old one
            snakeAsList.add(new SnakeSegment(snakeTailX, snakeTailY));
            // Print the new Snake segment
            gameBoard.drawShape(snakeAsList.get(snakeAsList.size() - 1).getRect());
        }
        //Collision detection (if the snake head is in the same position as another snake segment then end the game)
        for (int i = 1; i < snakeAsList.size() - 1; i++) {
            if (snakeAsList.get(0).getXPos() == snakeAsList.get(i).getXPos() && snakeAsList.get(0).getYPos() == snakeAsList.get(i).getYPos()) {
                gameBoard.drawShape(new Text("Hello Test"));
                return true;
            }
        }

        return checkIfOver();
    }

    // todo: Checking if Game should be Over
    public boolean checkIfOver() {
        return false;
    }

    public void setDirection(int direction) {
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
    }

    // Create a Random object for food position
    public int generateRandomPosition(int bound) {
        Random random = new Random();
        return random.nextInt(bound) * 100;
    }


}
