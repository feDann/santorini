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

        if (numberOfTimesAlreadyBuilt == 0) {
            numberOfTimesAlreadyBuilt++;
            return getSharedTurn().build(worker, board);
        }

        Point workerPosition = worker.getPosition();
        int x = (int) workerPosition.getX();
        int y = (int) workerPosition.getY();
        ArrayList<Point> possiblePosition = new ArrayList<Point>();

        int startX = ((x - 1) < 0)? x : x-1;
        int startY = ((y - 1) < 0)? y : y-1;
        int endX = ((x + 1) > 4)? x : x+1;
        int endY = ((y + 1) > 4)? y : y+1;

        for (int i = startX; i <= endX; i++){
            for (int j = startY; j < endY; j++){

                Point neighbouringPoint = new Point(i,j);

                if(!board.hasDomeOnTop(neighbouringPoint)){
                    if(!board.hasWorkerOnTop(neighbouringPoint)){
                        if(!neighbouringPoint.equals(oldBuildPosition)){
                            possiblePosition.add(neighbouringPoint);
                        }
                    }
                }
            }
        }
        return possiblePosition;

    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition) {
        oldBuildPosition = buildPosition;
        getSharedTurn().applyBuild(worker, board, buildPosition);
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
