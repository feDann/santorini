package it.polimi.ingsw.PSP11.model;

import java.awt.*;
import java.util.ArrayList;

public abstract class GodTurn implements Turn{

    private Turn sharedTurn;

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




    public void setSharedTurn(Turn sharedTurn) {
        this.sharedTurn = sharedTurn;
    }

}
