package it.polimi.ingsw.PSP11.model;

import java.awt.*;
import java.util.ArrayList;

public class StandardTurn implements Turn{

    private boolean movedUp;
    private boolean movedDown;
    private boolean movedAgain;
    private boolean buildAgain;



    private ArrayList<Point> getLegalPosition (Worker worker, Board board, int whatToDo){

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
                        //move
                        if( whatToDo == 0 ) {
                            if (board.getCurrentLevel(neighbouringPoint).ordinal() - board.getCurrentLevel(workerPosition).ordinal() <= 1) {
                                possiblePosition.add(neighbouringPoint);
                            }
                        }
                        //build
                        if( whatToDo == 1){
                            possiblePosition.add(neighbouringPoint);
                        }
                    }
                }
            }
        }
        return possiblePosition;
    }

    @Override
    public ArrayList<Point> move(Worker worker, Board board){
        return getLegalPosition(worker,board,0);
    }

    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {
        board.removeWorker(worker.getPosition());
        if(board.getCurrentLevel(newPosition).ordinal() - board.getCurrentLevel(worker.getPosition()).ordinal() == 1){
            movedUp = true;
        }
        worker.setPosition(newPosition);
        board.placeWorker(newPosition);
        //worker.setMoved(true);
    }

    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return getLegalPosition(worker,board,1);
    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition) {
        if(board.getCurrentLevel(buildPosition).equals(Block.TOP)){
            board.addDome(buildPosition);
            return;
        }
        board.addBlock(buildPosition);
        return;
    }

    @Override
    public boolean winCondition(Worker worker, Board board) {
        if(movedUp && board.getCurrentLevel(worker.getPosition()).equals(Block.TOP)){
            return true;
        }
        return false;
    }

}