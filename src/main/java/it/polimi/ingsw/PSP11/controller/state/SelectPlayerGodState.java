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
    ArrayList<Card> chosenCards;

    public SelectPlayerGodState (Game game){
        this.game = game;
        this.chosenCards = game.getChosenCards();
    }

    @Override
    public void selectGameGods(ArrayList<Integer> ids) {

    }

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
        System.out.println("Dio scelto: " + game.getCurrentPlayer().getGod().getName());
        chosenCards.remove(index);

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
    public void applyMove(Point point) {

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
        return new SelectPlayerGodRequest(chosenCards);
    }

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
