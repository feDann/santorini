package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.observer.Observer;

public class Controller implements Observer<Message> {

    private GameState gameState;
    private final Game game;


    public Controller(Game game) {
        this.game = game;
    }

    @Override
    public void update(Message message) {


    }
}
