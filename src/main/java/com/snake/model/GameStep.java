package com.snake.model;

import com.snake.view.GameBoard;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class GameStep {
    private int direction;

    // todo: SnakeSegment instead of Rectangle?
    private List<Rectangle> snakeAsList = new LinkedList<>();
    // Reference to View for Drawing purposes
    private GameBoard gameBoard;

    // todo: Collision Detection
    // todo: FruitSpawn
    // todo: Vertical Movement

    public GameStep(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        // v: X, v1: Y, v2: width, v3: height
        snakeAsList.add(new Rectangle(300, 0, 100, 100));
        snakeAsList.add(new Rectangle(200, 0, 100, 100));
        snakeAsList.add(new Rectangle(100, 0, 100, 100));

        for (Rectangle rectangle : snakeAsList) {
            gameBoard.drawShape(rectangle);
        }
    }

    public boolean nextFrame(){
        snakeAsList.get(snakeAsList.size() - 1).setX(snakeAsList.get(0).getX() + 100 * direction);

        snakeAsList.add(0, snakeAsList.get(snakeAsList.size() - 1));

        snakeAsList.remove(snakeAsList.size() - 1);
        return checkIfOver();
    }
    // todo: Checking if Game should be Over
    public boolean checkIfOver(){
        return false;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }

}
