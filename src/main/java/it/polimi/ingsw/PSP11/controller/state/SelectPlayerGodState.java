package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.model.Game;

public class SelectPlayerGodState implements GameState{

    private Game game;

    public SelectPlayerGodState (Game game){
        this.game = game;
    }

    @Override
    public void selectPlayerGod(int index) {

    }

    @Override
    public void placeWorker() {

    }

    @Override
    public void startTurn() {

    }

    @Override
    public void selectWorker() {

    }

    @Override
    public void moveWorker() {

    }

    @Override
    public void checkLose() {

    }

    @Override
    public void applyMove() {

    }

    @Override
    public void checkWin() {

    }

    @Override
    public void workerBuild() {

    }

    @Override
    public void applyBuild() {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public void execute() {

    }
}
