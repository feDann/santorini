package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class DemeterPowerTurnDecorator extends GodTurn {

    private Point oldBuildPosition;
    private int numberOfTimesAlreadyBuilt = 0;

    @Override
    public void startTurn() {
        getSharedTurn().startTurn();
        getSharedTurn().setBuildAgain(true);
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

        //the first build invokes the standard build method
        if (numberOfTimesAlreadyBuilt == 0) {
            numberOfTimesAlreadyBuilt++;
            return getSharedTurn().build(worker, board);
        }
        else {
            //the second build also removes the oldBuild position from possibleBuildPoints
            ArrayList<Point> possibleBuildPoints = getSharedTurn().build(worker, board);
            possibleBuildPoints.remove(oldBuildPosition);
            getSharedTurn().setBuildAgain(false);
            return possibleBuildPoints;
        }
    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition, boolean forceBuildDome) {
        oldBuildPosition = buildPosition;
        getSharedTurn().applyBuild(worker, board, buildPosition, false);
    }


    @Override
    public boolean winCondition(Worker worker, Board board) {
        return getSharedTurn().winCondition(worker, board);
    }

    @Override
    public void endTurn() {
        numberOfTimesAlreadyBuilt = 0;
    }

}
