package it.polimi.ingsw.PSP11.model;

import java.awt.*;
import java.util.ArrayList;

public interface Turn {

    public ArrayList<Point> move(Worker worker, Board board);

    public void applyMove(Worker worker, Board board, Point point);

    public ArrayList<Point> build(Worker worker, Board board);

    public void applyBuild(Worker worker, Board board, Point point);

    public boolean winCondition(Worker worker, Board board);

}
