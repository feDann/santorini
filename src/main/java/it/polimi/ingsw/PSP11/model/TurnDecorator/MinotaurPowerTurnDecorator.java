package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class MinotaurPowerTurnDecorator extends GodTurn {

    private boolean canBePushed(Worker worker, Point originalPosition, Board board){
        Point villainWorkerPosition = worker.getPosition();
        int differenceX = villainWorkerPosition.x - originalPosition.x;
        int differenceY = villainWorkerPosition.y - originalPosition.y;
        int newPositionX = villainWorkerPosition.x + differenceX;
        int newPositionY = villainWorkerPosition.y + differenceY;

        if(newPositionX >= 0 && newPositionX <5 && newPositionY >= 0 && newPositionY <5 ){
            Point newPosition = new Point(newPositionX, newPositionY);
            if(!board.hasDomeOnTop(newPosition) && !board.hasWorkerOnTop(newPosition)){
                //return board.getCurrentLevel(newPosition).ordinal() - board.getCurrentLevel(villainWorkerPosition).ordinal() <= 1;
                return true;
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
        ArrayList<Point> neighbouringPoints = board.getNeighbouringPoints(workerPosition);
        ArrayList<Point> possiblePosition = new ArrayList<>();


            for (Point position : neighbouringPoints) {


                if (!board.hasDomeOnTop(position)) {
                    if (board.hasWorkerOnTop(position)) {
                        //move
                        if (!board.getWorker(workerPosition).getColor().equals(board.getWorker(position).getColor()) && canBePushed(board.getWorker(position), workerPosition, board)) {
                            if (board.getCurrentLevel(position).ordinal() - board.getCurrentLevel(workerPosition).ordinal() <= 1) {
                                //check for Athena power
                                if (!(board.getCurrentLevel(position).ordinal() - board.getCurrentLevel(workerPosition).ordinal() == 1 && getSharedTurn().isCantMoveUp())) {
                                    possiblePosition.add(position);
                                }
                            }

                        }
                    } else {
                        if (board.getCurrentLevel(position).ordinal() - board.getCurrentLevel(workerPosition).ordinal() <= 1) {
                            //check for Athena power
                            if (!(board.getCurrentLevel(position).ordinal() - board.getCurrentLevel(workerPosition).ordinal() == 1 && getSharedTurn().isCantMoveUp())) {
                                possiblePosition.add(position);
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
            if(board.getCurrentLevel(newPosition).ordinal() - board.getCurrentLevel(worker.getPosition()).ordinal() == 1){
                getSharedTurn().setMovedUp(true);
            }
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
