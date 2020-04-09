package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class PanPowerTurnDecorator extends GodTurn {

    private boolean movedDownTwoOrMoreLevels;

    @Override
    public void startTurn() {
        getSharedTurn().startTurn();
        movedDownTwoOrMoreLevels = false;
    }

    @Override
    public ArrayList<Point> move(Worker worker, Board board) {
        return getSharedTurn().move(worker, board);
    }

    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {

        if (board.getCurrentLevel(newPosition).ordinal() - board.getCurrentLevel(worker.getPosition()).ordinal() <= -2) {
            movedDownTwoOrMoreLevels = true;
        }

        getSharedTurn().applyMove(worker, board, newPosition);

    }

    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return getSharedTurn().build(worker, board);
    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition, boolean forceBuildDome) {
        getSharedTurn().applyBuild(worker, board, buildPosition, forceBuildDome);
    }

    @Override
    public boolean winCondition(Worker worker, Board board) {
        return getSharedTurn().winCondition(worker, board) || movedDownTwoOrMoreLevels;
    }

    @Override
    public void endTurn() {
        movedDownTwoOrMoreLevels = false;
    }

    public boolean isMovedDownTwoOrMoreLevels() {
        return movedDownTwoOrMoreLevels;
    }

}
