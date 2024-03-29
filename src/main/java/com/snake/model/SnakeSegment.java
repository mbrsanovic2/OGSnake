package com.snake.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Simplification of the Rectangle, with only functions we need
 */
public class SnakeSegment {
    Rectangle snakeBody = new Rectangle(50, 50, 50, 50);

    /**
     * <pre>
     * default constructor
     * x & y positions are 0, size is 100x100, color is green</pre>
     */
    public SnakeSegment() {
        snakeBody.setFill(Color.GREEN);
    }

    /**
     * default color of the snake-segment is green, default size is 100x100
     *
     * @param xPos The X-Position of the snake-segment
     * @param yPos The Y-Position of the snake-segment
     */
    public SnakeSegment(int xPos, int yPos) {
        snakeBody.setX(xPos);
        snakeBody.setY(yPos);
        snakeBody.setFill(Color.GREEN);
    }

    /**
     * default size is 100x100, use Color.rgb for custom, Color.color just crashes
     *
     * @param xPos       The X-Position of the snake-segment
     * @param yPos       The Y-Position of the snake-segment
     * @param snakeColor The Color of the snake-segment
     */
    public SnakeSegment(int xPos, int yPos, Color snakeColor) {
        snakeBody.setX(xPos);
        snakeBody.setY(yPos);
        snakeBody.setFill(snakeColor);
    }

    /**
     * use Color.rgb for custom,
     * Color.color just crashes
     *
     * @param xPos       The X-Position of the snake-segment
     * @param yPos       The Y-Position of the snake-segment
     * @param Size       The size of the snake-segment, height=width
     * @param snakeColor The Color of the snake-segment
     */
    public SnakeSegment(int xPos, int yPos, int Size, Color snakeColor) {
        snakeBody.setX(xPos);
        snakeBody.setY(yPos);
        snakeBody.setHeight(Size);
        snakeBody.setWidth(Size);
        snakeBody.setFill(snakeColor);
    }

    public void setXPos(double pos) {
        snakeBody.setX(pos);
    }

    public void setYPos(double pos) {
        snakeBody.setY(pos);
    }

    public void setXYPos(double xPos, double yPos) {
        snakeBody.setX(xPos);
        snakeBody.setY(yPos);
    }

    /**
     * Sets the color of the snake-segment, use Color.rgb for custom,
     * Color.color just crashes
     *
     * @param color
     */
    public void setColor(Color color) {
        snakeBody.setFill(color);
    }

    /**
     * Sets the width and height to the size value
     */
    public void setSize(int size) {
        snakeBody.setHeight(size);
        snakeBody.setWidth(size);
    }

    /**
     * @return the x-position of the snake-segment
     */
    public double getXPos() {
        return snakeBody.getX();
    }

    /**
     * @return the y-position of the snake-segment
     */
    public double getYPos() {
        return snakeBody.getY();
    }

    /**
     * @return the color of the snake-segment
     */
    public Color getSnakeColor() {
        return (Color) snakeBody.getFill();
    }

    /**
     * @return the drawShape of the snake-segment
     */
    public Rectangle getRect() {
        return snakeBody;
    }
}
