package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.GodTurn;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class ApolloPowerTurnDecorator extends GodTurn {



    @Override
    public void startTurn() {
        super.startTurn();
    }

    @Override
    public ArrayList<Point> move(Worker worker, Board board) {
        return super.move(worker, board);
    }



    @Override
    public void applyMove(Worker worker, Board board, Point newPosition) {
        super.applyMove(worker, board, newPosition);
    }


    @Override
    public ArrayList<Point> build(Worker worker, Board board) {
        return super.build(worker, board);
    }

    @Override
    public void applyBuild(Worker worker, Board board, Point buildPosition) {
        super.applyBuild(worker, board, buildPosition);
    }

    @Override
    public boolean winCondition(Worker worker, Board board) {
        return super.winCondition(worker, board);
    }


}
