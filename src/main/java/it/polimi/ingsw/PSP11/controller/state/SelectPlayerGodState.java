package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.SimpleMessage;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Player;

import java.util.ArrayList;

public class SelectPlayerGodState implements GameState{
    int index;
    String requestingPlayer;
    private Game game;

    public SelectPlayerGodState (Game game){
        this.game = game;
    }

    @Override
    public void selectGameGods(ArrayList<Integer> ids) {

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
    public Message stateMessage() {
        return new SimpleMessage("Choose your God\n");
    }

    @Override
    public GameState execute(Message msg){
        return null;
    }
}
