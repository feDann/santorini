package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class AtlasPowerTurnDecorator extends GodTurn {

    @Override
    public void startTurn() {
        getSharedTurn().startTurn();
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
        return getSharedTurn().build(worker, board);
    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition, boolean buildDome) {
        if(buildDome){
            board.addDome(buildPosition);
            return;
        }
        else {
            getSharedTurn().applyBuild(worker, board, buildPosition, false);
            return;
        }
    }

    @Override
    public boolean winCondition(Worker worker, Board board) {
        return getSharedTurn().winCondition(worker, board);
    }

    @Override
    public void endTurn() {
        getSharedTurn().endTurn();
    }


}
