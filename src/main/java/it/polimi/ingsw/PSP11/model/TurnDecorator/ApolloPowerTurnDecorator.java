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

    @Override
    public ArrayList<Point> move(Worker worker, Board board) {
        ArrayList<Point> possiblePosition = new ArrayList<>();
        possiblePosition = getSharedTurn().move(worker,board);
        Point workerPosition = new Point(worker.getPosition());
        int x = (int) workerPosition.getX();
        int y = (int) workerPosition.getY();

        int startX = ((x - 1) < 0)? x : x-1;
        int startY = ((y - 1) < 0)? y : y-1;
        int endX = ((x + 1) > 4)? x : x+1;
        int endY = ((y + 1) > 4)? y : y+1;

        for (int i = startX; i <= endX; i++){
            for (int j = startY; j < endY; j++){
                Point neighbouringPoint = new Point(i,j);
                if(!board.hasDomeOnTop(neighbouringPoint)){
                    if (board.getCurrentLevel(neighbouringPoint).ordinal() - board.getCurrentLevel(workerPosition).ordinal() <= 1) {
                        possiblePosition.add(neighbouringPoint);
                    }
                }
            }
        }
        return possiblePosition;
    }



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
            board.removeWorker(opponentWorker.getPosition());
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


    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return getSharedTurn().build(worker , board);
    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition) {
        getSharedTurn().applyBuild(worker, board, buildPosition);
    }

    @Override
    public boolean winCondition(Worker worker, Board board) {
        return getSharedTurn().winCondition(worker, board);
    }


}
