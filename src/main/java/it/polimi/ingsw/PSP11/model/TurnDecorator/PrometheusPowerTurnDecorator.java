package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class PrometheusPowerTurnDecorator extends GodTurn {

    private boolean hasBuilt;

    @Override
    public void startTurn() {
        getSharedTurn().startTurn();
        getSharedTurn().setCanBuildBeforeMove(true);
        this.hasBuilt = false;
    }

    @Override
    public ArrayList<Point> move(Worker worker, Board board) {
        ArrayList<Point> possibleposition;
        if (hasBuilt) {
            getSharedTurn().setCantMoveUp(true);
            possibleposition = getSharedTurn().move(worker, board);
            getSharedTurn().setCantMoveUp(false);
        } else {
            possibleposition = getSharedTurn().move(worker, board);
        }
        return possibleposition;
    }

    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {
        getSharedTurn().applyMove(worker, board, newPosition);
        getSharedTurn().setCanBuildBeforeMove(false);
    }

    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return getSharedTurn().build(worker, board);
    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition, boolean forceBuildDome) {
        getSharedTurn().applyBuild(worker, board, buildPosition, forceBuildDome);
        this.hasBuilt = true;
    }

    @Override
    public boolean winCondition(Worker worker, Board board) {
        return getSharedTurn().winCondition(worker, board);
    }

    @Override
    public void endTurn() {
        getSharedTurn().endTurn();
        this.hasBuilt = false;
    }

    public boolean isHasBuilt() {
        return hasBuilt;
    }
}
