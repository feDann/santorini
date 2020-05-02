package it.polimi.ingsw.PSP11.model;

import java.awt.*;
import java.util.ArrayList;

public abstract class GodTurn implements Turn {

    private StandardTurn sharedTurn;

    /**
     * return all the possible moves
     * @param worker the selected worker
     * @param board the game board
     * @return an ArrayList of points containing all the possible moves
     */
    @Override
    public ArrayList<Point> move(Worker worker, Board board) {
        return null;
    }

    /**
     * return all the possible build position
     * @param worker the selected worker
     * @param board the game board
     * @return an ArrayList of points containing all the possible build positions
     */
    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return null;
    }

    /**
     * check if the player has won the game
     * @param worker the selected worker
     * @param board the game board
     * @return true if the player has won
     */
    @Override
    public boolean winCondition(Worker worker, Board board) {
        return false;
    }

    @Override
    public void endTurn() {

    }

    /**
     * reset some attributes
     */
    @Override
    public void startTurn() {

    }

    /**
     * move the selected worker to the desired point
     * @param worker the selected worker
     * @param board the game board
     * @param newPosition the new worker position
     */
    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {

    }

    /**
     * build on the desired point
     * @param worker the selected worker
     * @param board the game board
     * @param buildPosition the build position
     * @param forceBuildDome true if Atlas power is activated
     */
    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition,boolean forceBuildDome) {

    }

    public void setSharedTurn(StandardTurn sharedTurn) {
        this.sharedTurn = sharedTurn;
    }

    public StandardTurn getSharedTurn() {
        return sharedTurn;
    }

}
