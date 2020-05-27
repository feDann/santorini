package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class ApolloPowerTurnDecorator extends GodTurn {


    @Override
    public void startTurn() {
        getSharedTurn().startTurn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Point> move(Worker worker, Board board) {

        Point workerPosition = worker.getPosition();
        ArrayList<Point> neighbouringPoints = board.getNeighbouringPoints(workerPosition);
        ArrayList<Point> possiblePosition = new ArrayList<>();

        for(Point position : neighbouringPoints){
            if(!(board.hasWorkerOnTop(position) && (worker.getColor().equals(board.getWorker(position).getColor())))) {
                if (!board.hasDomeOnTop(position)) {
                    if (board.getCurrentLevel(position).ordinal() - board.getCurrentLevel(workerPosition).ordinal() <= 1) {
                        if(!(board.getCurrentLevel(position).ordinal() - board.getCurrentLevel(workerPosition).ordinal() == 1 && getSharedTurn().isCantMoveUp())) {
                            possiblePosition.add(position);
                        }
                    }
                }
            }
        }
        return possiblePosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {
        Point oldPosition = worker.getPosition();
        board.removeWorker(oldPosition);
        if(board.getCurrentLevel(newPosition).ordinal() - board.getCurrentLevel(worker.getPosition()).ordinal() == 1){
            getSharedTurn().setMovedUp(true);
        }
        if(board.hasWorkerOnTop(newPosition)){
            Worker opponentWorker = board.getWorker(newPosition);
            //remove the opponent worker from his position
            board.removeWorker(newPosition);
            //switch worker and opponent worker position
            worker.setPosition(newPosition);
            board.placeWorker(newPosition, worker);

            opponentWorker.setPosition(oldPosition);
            board.placeWorker(oldPosition, opponentWorker);

        }
        else {
            worker.setPosition(newPosition);
            board.placeWorker(newPosition, worker);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return getSharedTurn().build(worker , board);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition, boolean forceBuildDome) {
        getSharedTurn().applyBuild(worker, board, buildPosition, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean winCondition(Worker worker, Board board) {
        return getSharedTurn().winCondition(worker, board);
    }


}
