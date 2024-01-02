package com.snake.model;

import com.snake.controller.SnakeApplication;
import com.snake.view.GameBoard;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;
import java.util.List;

import java.util.Random;

/**
 * Class that defines the Logic of our Program, for example where to Draw the Snake/Fruit
 */
public class GameStep {
    private int direction;
    private int currentDirection;
    private List<SnakeSegment> snakeAsList = new LinkedList<>();

    //Create a food rectangle
    private Rectangle food = new Rectangle(0, 0, 100, 100);

    // Reference to View for Drawing purposes
    private GameBoard gameBoard;

    // todo: Collision Detection
    // todo: FruitSpawn

    /**
     * Constructor which defines our Snake at the Start, and sets our View
     * @param gameBoard Our View (Ding das Zeichnet)
     */
    public GameStep(GameBoard gameBoard){

        this.gameBoard = gameBoard;
        // initial Rectangle parameters v: X, v1: Y, v2: width, v3: height
        snakeAsList.add((new SnakeSegment(300,200)));
        snakeAsList.add((new SnakeSegment(200,200)));
        snakeAsList.add((new SnakeSegment(100,200)));

        for (SnakeSegment snake : snakeAsList) {
            gameBoard.drawShape(snake.getRect());
        }
        spawnFood();
    }

    /**
     * What should happen every Frame (ca. 400 ms)? This is defined in this Method
     * @return If the Game-Loop should be stopped
     */
    public boolean nextFrame(){
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

        // todo: insert second case if fruit is eaten and snake gets longer

        // remove last element of snake
        snakeAsList.remove(snakeAsList.size() - 1);

        currentDirection = direction;

        // spawn food on another position if fruit is eaten
        if (food.getX() == snakeAsList.get(0).getXPos() && food.getY() == snakeAsList.get(0).getYPos()){
            spawnFood();
        }

        return checkIfOver();
    }
    // todo: Checking if Game should be Over
    public boolean checkIfOver(){
        return false;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public int getCurrentDirection() {
        return currentDirection;
    }

    public void spawnFood (){
        //spawn food at a random position and print it
        food.setX(generateRandomPosition(9));
        food.setY(generateRandomPosition(6));
        gameBoard.drawShape(food);
    }

    // Create a Random object for food position (9 and 6 for max size of the scene)
    public int generateRandomPosition (int bound){
        Random random = new Random();
        return random.nextInt(bound) * 100;
    }


}
