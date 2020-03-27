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
     * @param didWorkerMove true if the worker moved during this player's turn, false otherwise
     */
    public void setMoved (boolean didWorkerMove) {

        this.moved = didWorkerMove;

    }




}