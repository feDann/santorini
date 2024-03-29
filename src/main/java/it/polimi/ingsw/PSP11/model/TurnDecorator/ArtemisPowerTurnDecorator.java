package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class ArtemisPowerTurnDecorator extends GodTurn {

    public Point oldPosition;

    public int getNumberOfTimesAlreadyMoved() {
        return numberOfTimesAlreadyMoved;
    }

    private int numberOfTimesAlreadyMoved = 0;

    @Override
    public void startTurn() {
        getSharedTurn().startTurn();
        numberOfTimesAlreadyMoved = 0;
        getSharedTurn().setMoveAgain(true);
    }


    @Override
    public ArrayList<Point> move(Worker worker, Board board) {
        //the first move invokes the standard move method
        if(numberOfTimesAlreadyMoved == 0){
            return getSharedTurn().move(worker, board);
        }
        //the second move also removes the oldPosition from possibleMoves
        else{
            ArrayList<Point> possibleMoves = getSharedTurn().move(worker,board);
            possibleMoves.remove(oldPosition);
            getSharedTurn().setMoveAgain(false);
            return possibleMoves;
        }
    }
    

    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return getSharedTurn().build(worker, board);
    }

    @Override
    public boolean winCondition(Worker worker, Board board) {
        return getSharedTurn().winCondition(worker, board);
    }


    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {
        oldPosition = worker.getPosition();
        numberOfTimesAlreadyMoved++;
        getSharedTurn().applyMove(worker, board, newPosition);
    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition, boolean forceBuildDome) {
        getSharedTurn().applyBuild(worker, board, buildPosition, false);
    }

    @Override
    public void endTurn() {
        numberOfTimesAlreadyMoved = 0;
    }
}
