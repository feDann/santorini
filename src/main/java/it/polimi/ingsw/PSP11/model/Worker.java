package it.polimi.ingsw.PSP11.model;

import java.awt.*;

/**
 * Class for the players' workers
 */
public class Worker {

    private Point position;
    private boolean moved;
    private Color color;


    /**
     * Class constructor
     * @param chosenColor worker color, has the same value for workers of the same player
     */
    public Worker (Color chosenColor) {
        this.position = null;
        this.moved = false;
        this.color = chosenColor;
    }


    /**
     * Setter method for position attribute
     * @param newPosition the new position of this worker
     */
    public void setPosition (Point newPosition) {
        this.position = newPosition;
    }


    /**
     * Setter method for moved attribute
     * @param didWorkerMove true if the worker moved during the owning player's turn, false otherwise
     */
    public void setMoved (boolean didWorkerMove) {
        this.moved = didWorkerMove;
    }


    /**
     * Getter method for position attribute
     * @return this worker's position as a Point
     */
    public Point getPosition() {
        return position;
    }


    /**
     * Getter method for moved attribute
     * @return true if this worker moved during the owning player's turn
     */
    public boolean isMoved() {
        return moved;
    }


    /**
     * Getter for color attribute
     * @return the color of this worker
     */
    public Color getColor() {
        return color;
    }
}
