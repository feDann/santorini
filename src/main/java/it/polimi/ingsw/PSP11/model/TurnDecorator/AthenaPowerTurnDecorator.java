package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class AthenaPowerTurnDecorator extends GodTurn {

    @Override
    public ArrayList<Point> move(Worker worker, Board board) {
        return getSharedTurn().move(worker, board);
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
    public void endTurn() {
        getSharedTurn().endTurn();
    }

    @Override
    public void startTurn() {
        getSharedTurn().setCantMoveUp(false);
        getSharedTurn().startTurn();
    }

    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {
        if(board.getCurrentLevel(newPosition).ordinal() - board.getCurrentLevel(worker.getPosition()).ordinal() == 1){
            getSharedTurn().setCantMoveUp(true);
        }
        getSharedTurn().applyMove(worker, board, newPosition);

    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition) {
        getSharedTurn().applyBuild(worker, board, buildPosition);
    }
}
