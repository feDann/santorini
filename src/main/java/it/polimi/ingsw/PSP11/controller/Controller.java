package it.polimi.ingsw.PSP11.controller;

import it.polimi.ingsw.PSP11.controller.state.GameState;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.observer.Observer;

public class Controller implements Observer<Message> {

    private GameState gameState;
    private final Game game;
    private String requestingPlayer;


    public Controller(Game game) {
        this.game = game;
    }

    public synchronized void readMessage(Message message){
        if (requestingPlayer.equals(game.getCurrentPlayer().getNickname())){
            //fa le istance of
            //chiama execute
            //chiama la remote view e non insulta il giocatore
        }
        else{
            //chiama la remote view e insulta il giocatore
        }


    }

    @Override
    public void update(Message message) {
        readMessage(message);
    }

}
