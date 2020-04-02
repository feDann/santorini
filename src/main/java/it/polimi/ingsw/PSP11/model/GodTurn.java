package it.polimi.ingsw.PSP11.model;

import java.awt.*;
import java.util.ArrayList;

public abstract class GodTurn implements Turn{

    private StandardTurn sharedTurn;

    @Override
    public ArrayList<Point> move(Worker worker, Board board) {
        return null;
    }

    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return null;
    }

    @Override
    public boolean winCondition(Worker worker, Board board) {
        return false;
    }

    @Override
    public void endTurn() {

    }

    @Override
    public void startTurn() {

    }

    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {

    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition) {

    }

    public void setSharedTurn(StandardTurn sharedTurn) {
        this.sharedTurn = sharedTurn;
    }

    public StandardTurn getSharedTurn() {
        return sharedTurn;
    }

}
