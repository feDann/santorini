package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.EndTurnMessage;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.SelectGameGodResponse;
import it.polimi.ingsw.PSP11.messages.SelectGameGodsRequest;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class SelectGameGodsState implements GameState{

    private Game game;
    private GameState gameState;

    public SelectGameGodsState(Game game, GameState gameState) {
        this.game = game;
        this.gameState = gameState;
    }

    @Override
    public void selectGameGods(ArrayList<Integer> ids)  {
        for (int i : ids){
            game.selectGod(i);
        }
        game.nextPlayer();
    }

    @Override
    public void selectPlayerGod(int index) {
    }

    @Override
    public void placeWorker(Point point) {

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
        return new SelectGameGodsRequest(game.getDeck().deckClone(),game.getNumOfPlayers());
    }

    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        selectGameGods(((SelectGameGodResponse)message).getIdOfChosenGods());
        virtualView.sendMessage(new EndTurnMessage());
        return new SelectPlayerGodState(game);
    }
}
