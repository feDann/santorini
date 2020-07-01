package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.EndTurnMessage;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.SelectPlayerGodRequest;
import it.polimi.ingsw.PSP11.messages.SelectPlayerGodResponse;
import it.polimi.ingsw.PSP11.model.Card;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class SelectPlayerGodState implements GameState{

    int index;
    String requestingPlayer;
    private Game game;
    ArrayList<Card> chosenCards = new ArrayList<>();

    public SelectPlayerGodState (Game game){
        this.game = game;
        for(Card card : game.getChosenCards()){
            this.chosenCards.add(card.cardClone());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectGameGods(ArrayList<Integer> ids) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectPlayerGod(int index) {
        int id = 0;
        int i = 0;

        for(Card card : game.getChosenCards()){
            if(card.getName().equals(chosenCards.get(index).getName())) id = i;
            i++;
        }
        game.getCurrentPlayer().setGod(game.selectPlayerGod(id));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());
        chosenCards.remove(index);

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
        return new SelectPlayerGodRequest(chosenCards);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState execute(Message msg, VirtualView virtualView){
        selectPlayerGod(((SelectPlayerGodResponse) msg).getId());
        virtualView.sendMessage(new EndTurnMessage());
        game.nextPlayer();
        if (chosenCards.size()>0){
            return this;
        }

        return new PlaceWorkerState(game);
    }
}
