package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class HephaestusPowerTurnDecorator extends GodTurn {
    private Point oldBuildPosition;
    private int numberOfTimesAlreadyBuilt = 0;

    @Override
    public void startTurn() {
        getSharedTurn().startTurn();
        numberOfTimesAlreadyBuilt = 0;
    }

    @Override
    public ArrayList<Point> move(Worker worker, Board board) {
        return getSharedTurn().move(worker, board);
    }


    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {
        getSharedTurn().applyMove(worker, board, newPosition);
    }

    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        if (numberOfTimesAlreadyBuilt == 0){
            return getSharedTurn().build(worker, board);
        }
        else{
            ArrayList<Point> oldPositionAsArray = new ArrayList<>();
            getSharedTurn().setBuildAgain(false);
            oldPositionAsArray.add(oldBuildPosition);
            return oldPositionAsArray;
        }
    }


    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition, boolean forceBuildDome) {
        oldBuildPosition = buildPosition;
        getSharedTurn().applyBuild(worker, board, buildPosition, forceBuildDome);
        if(board.getCurrentLevel(oldBuildPosition).ordinal()<3 && numberOfTimesAlreadyBuilt == 0){
            getSharedTurn().setBuildAgain(true);
            numberOfTimesAlreadyBuilt++;
        }
    }

    @Override
    public boolean winCondition(Worker worker, Board board) {
        return getSharedTurn().winCondition(worker, board);
    }

    @Override
    public void endTurn() {
        getSharedTurn().endTurn();
        getSharedTurn().setBuildAgain(false);
        numberOfTimesAlreadyBuilt = 0;
    }


}
