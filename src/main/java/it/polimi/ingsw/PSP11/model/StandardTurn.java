package it.polimi.ingsw.PSP11.model;

import java.awt.*;
import java.util.ArrayList;

public class StandardTurn implements Turn{

    private boolean movedUp;
    private boolean movedDown;
    private boolean moveAgain;
    private boolean buildAgain;
    private boolean cantMoveUp = false;
    private boolean canBuildDomeAnyLevel = false;

    public boolean isCanBuildDomeAnyLevel() {
        return canBuildDomeAnyLevel;
    }

    public void setCanBuildDomeAnyLevel(boolean canBuildDomeAnyLevel) {
        this.canBuildDomeAnyLevel = canBuildDomeAnyLevel;
    }

    public boolean isCantMoveUp() {
        return cantMoveUp;
    }

    public void setCantMoveUp(boolean cantMoveUp) {
        this.cantMoveUp = cantMoveUp;
    }

    public boolean isMovedUp() {
        return movedUp;
    }

    public void setMovedUp(boolean movedUp) {
        this.movedUp = movedUp;
    }

    public boolean isMovedDown() {
        return movedDown;
    }

    public void setMovedDown(boolean movedDown) {
        this.movedDown = movedDown;
    }

    public boolean isMoveAgain() {
        return moveAgain;
    }

    public void setMoveAgain(boolean moveAgain) {
        this.moveAgain = moveAgain;
    }

    public boolean isBuildAgain() {
        return buildAgain;
    }

    public void setBuildAgain(boolean buildAgain) {
        this.buildAgain = buildAgain;
    }

    /**
     * method to get the legal position for a build or a move
     * @param worker that wants to build or move
     * @param board the game board
     * @param whatToDo 0 if move and 1 if build
     * @return arraylist of points representing the legal positions
     */
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
            for (int j = startY; j <= endY; j++){

                Point neighbouringPoint = new Point(i,j);

                if(!board.hasDomeOnTop(neighbouringPoint)){
                    if(!board.hasWorkerOnTop(neighbouringPoint)){
                        //move
                        if( whatToDo == 0 ) {
                            if (board.getCurrentLevel(neighbouringPoint).ordinal() - board.getCurrentLevel(workerPosition).ordinal() <= 1) {
                                //check for Athena power
                                if(!(board.getCurrentLevel(neighbouringPoint).ordinal() - board.getCurrentLevel(workerPosition).ordinal() == 1 && cantMoveUp == true)) {
                                    possiblePosition.add(neighbouringPoint);
                                }
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
        possiblePosition.remove(workerPosition);
        return possiblePosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startTurn() {
        movedDown= false;
        movedUp = false;
        moveAgain = false;
        buildAgain = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Point> move(Worker worker, Board board){
        return getLegalPosition(worker,board,0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {
        board.removeWorker(worker.getPosition());
        if(board.getCurrentLevel(newPosition).ordinal() - board.getCurrentLevel(worker.getPosition()).ordinal() == 1){
            movedUp = true;
        }
        worker.setPosition(newPosition);
        board.placeWorker(newPosition , worker);
        //worker.setMoved(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return getLegalPosition(worker,board,1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition, boolean forceBuildDome) {
        if(board.getCurrentLevel(buildPosition).equals(Block.TOP)){
            board.addDome(buildPosition);
            return;
        }
        board.addBlock(buildPosition);
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean winCondition(Worker worker, Board board) {
        if(movedUp && board.getCurrentLevel(worker.getPosition()).equals(Block.TOP)){
            return true;
        }
        return false;
    }

    @Override
    public void endTurn() {

    }

}