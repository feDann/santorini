package it.polimi.ingsw.PSP11.model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public interface Turn extends Serializable {

    /**
     * set the ambient variabales to the standard values
     */
    public void startTurn();

    /**
     *Method that looks for possible moves
     * @param worker the selected worker
     * @param board the game board
     * @return an array list of possible move position
     */
    public ArrayList<Point> move(Worker worker, Board board);

    /**
     *Method that move the worker in the newPosition
     * @param worker the selected worker
     * @param board the game board
     * @param newPosition the new worker position
     */
    public void applyMove(Worker worker, Board board, Point newPosition);

    /**
     *Method that looks for possible build position
     * @param worker the selected worker
     * @param board the game board
     * @return an array list of possible build position
     */
    public ArrayList<Point> build(Worker worker, Board board);

    /**
     *Method that build a block or dome in the buildPosition
     * @param worker the selected worker
     * @param board the game board
     * @param buildPosition the build position
     * @param forceBuildDome true if Atlas power is activated
     */
    public void applyBuild(Worker worker, Board board, Point buildPosition, boolean forceBuildDome);

    /**
     *Method that check if a player's worker won
     * @param worker the selected worker
     * @param board the game board
     * @return true if player won, false otherwise
     */
    public boolean winCondition(Worker worker, Board board);

    public void endTurn();

}
