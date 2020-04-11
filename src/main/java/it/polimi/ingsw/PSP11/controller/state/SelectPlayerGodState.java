package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Player;

public class SelectPlayerGodState implements GameState{
    int index;
    String requestingPlayer;
    private Game game;

    public SelectPlayerGodState (Game game){
        this.game = game;
    }

    @Override
    public String selectPlayerGod(int index) {
        game.getCurrentPlayer().setGod(game.selectPlayerGod(index));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());
        return "You choose poorly";
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
    public String execute(Message msg){
        //SPACCHETTAMENTO RAGAZZI 30000 GEMME CLASH ROYALE
        return selectPlayerGod(index);
    }
}
