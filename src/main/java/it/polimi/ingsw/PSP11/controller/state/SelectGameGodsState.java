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

    public SelectGameGodsState(Game game) {
        this.game = game;
    }

    @Override
    public void selectGameGods(ArrayList<Integer> ids)  {
        for (int i : ids){
            game.selectGod(i);
        }
        game.nextPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectPlayerGod(int index) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeWorker(Point point) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startTurn() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectWorker() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveWorker() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkLose() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyMove(Point point) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkWin() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void workerBuild() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyBuild(Point point, boolean forceBuildDome) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message stateMessage() {
        return new SelectGameGodsRequest(game.getDeck().deckClone(),game.getNumOfPlayers());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        selectGameGods(((SelectGameGodResponse)message).getIdOfChosenGods());
        virtualView.sendMessage(new EndTurnMessage());
        return new SelectPlayerGodState(game);
    }
}
