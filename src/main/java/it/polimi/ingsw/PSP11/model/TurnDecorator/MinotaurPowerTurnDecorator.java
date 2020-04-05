package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class MinotaurPowerTurnDecorator extends GodTurn {

    private boolean canBePushed(Worker worker, Point originalPosition, Board board){
        Point villainWorkerPosition = worker.getPosition();
        int differenceX = (int)(villainWorkerPosition.getX() - originalPosition.getX());
        int differenceY = (int)(villainWorkerPosition.getY() - originalPosition.getY());
        int newPositionX = (int)villainWorkerPosition.getX() + differenceX;
        int newPositionY = (int)villainWorkerPosition.getY() + differenceY;

        if(newPositionX > 0 && newPositionX <5 && newPositionY > 0 && newPositionY <5 ){
            Point newPosition = new Point(newPositionX, newPositionY);
            if(!board.hasDomeOnTop(newPosition) && !board.hasWorkerOnTop(newPosition)){
                if(board.getCurrentLevel(newPosition).ordinal()- board.getCurrentLevel(villainWorkerPosition).ordinal() <=1){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void startTurn() {
        getSharedTurn().startTurn();
    }

    @Override
    public ArrayList<Point> move(Worker worker, Board board) {

        Point workerPosition = worker.getPosition();
        int x = (int) workerPosition.getX();
        int y = (int) workerPosition.getY();
        ArrayList<Point> possiblePosition = new ArrayList<Point>();

        int startX = ((x - 1) < 0) ? x : x - 1;
        int startY = ((y - 1) < 0) ? y : y - 1;
        int endX = ((x + 1) > 4) ? x : x + 1;
        int endY = ((y + 1) > 4) ? y : y + 1;

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {

                Point neighbouringPoint = new Point(i, j);

                if (!board.hasDomeOnTop(neighbouringPoint)) {
                    if (board.hasWorkerOnTop(neighbouringPoint)) {
                        //move
                        if (!board.getWorker(workerPosition).getColor().equals(board.getWorker(neighbouringPoint).getColor()) && canBePushed(board.getWorker(neighbouringPoint), workerPosition, board)) {
                            if (board.getCurrentLevel(neighbouringPoint).ordinal() - board.getCurrentLevel(workerPosition).ordinal() <= 1) {
                                //check for Athena power
                                if (!(board.getCurrentLevel(neighbouringPoint).ordinal() - board.getCurrentLevel(workerPosition).ordinal() == 1 && getSharedTurn().isCantMoveUp())) {
                                    possiblePosition.add(neighbouringPoint);
                                }
                            }

                        }
                    } else {
                        if (board.getCurrentLevel(neighbouringPoint).ordinal() - board.getCurrentLevel(workerPosition).ordinal() <= 1) {
                            //check for Athena power
                            if (!(board.getCurrentLevel(neighbouringPoint).ordinal() - board.getCurrentLevel(workerPosition).ordinal() == 1 && getSharedTurn().isCantMoveUp())) {
                                possiblePosition.add(neighbouringPoint);
                            }
                        }
                    }
                }
            }

        }
        possiblePosition.remove(workerPosition);
        return possiblePosition;
    }

    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {
        if(!board.hasWorkerOnTop(newPosition)){
            getSharedTurn().applyMove(worker, board, newPosition);
        }
        else{
            Worker opponentWorker = board.getWorker(newPosition);
            Point oldPosition = worker.getPosition();
            Point opponentWorkerNewPosition = new Point(newPosition);
            opponentWorkerNewPosition.translate((int)(newPosition.getX()-oldPosition.getX()),(int)(newPosition.getY()-oldPosition.getY()));

            //remove the opponent worker && set in the new position
            board.removeWorker(newPosition);
            opponentWorker.setPosition(opponentWorkerNewPosition);
            board.placeWorker(opponentWorkerNewPosition,opponentWorker);

            //change the worker position
            board.removeWorker(oldPosition);
            worker.setPosition(newPosition);
            board.placeWorker(newPosition, worker);
        }
    }


    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return getSharedTurn().build(worker, board);
    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition, boolean forceBuildDome) {
        getSharedTurn().applyBuild(worker,board, buildPosition, false );
    }

    @Override
    public boolean winCondition(Worker worker, Board board) {
        return getSharedTurn().winCondition(worker,board);
    }

    @Override
    public void endTurn() {
        getSharedTurn().endTurn();
    }


}
