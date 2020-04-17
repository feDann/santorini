package it.polimi.ingsw.PSP11.controller;

import it.polimi.ingsw.PSP11.client.Client;
import it.polimi.ingsw.PSP11.controller.state.GameState;
import it.polimi.ingsw.PSP11.messages.ControllerMessage;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.NotYourTurnMessage;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.observer.Observer;
import it.polimi.ingsw.PSP11.server.ClientSocketConnection;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.util.HashMap;
import java.util.Map;

public class Controller implements Observer<ControllerMessage> {

    private GameState gameState;
    private final Game game;
    private String requestingPlayer;
    private Map<String, ClientSocketConnection> currentPlayers = new HashMap<>();
    private VirtualView requestingView;

    public Controller(Game game, Map<String, ClientSocketConnection> currentPlayers) {
        this.game = game;
        this.currentPlayers = currentPlayers;
    }


    public synchronized void readMessage(ControllerMessage message){
        requestingView = message.getVirtualView();
        requestingPlayer = requestingView.getPlayer();
        if (requestingPlayer.equals(game.getCurrentPlayer().getNickname())){
            //fa le istance of
            //chiama execute
            //chiama la remote view e non insulta il giocatore
        }
        else{
            requestingView.sendMessage(new NotYourTurnMessage(game.getCurrentPlayer().getNickname()));
        }
    }

    public void start (){
        game.startGame();
        //this.gameState = new PickGodState();

    }

    @Override
    public void update(ControllerMessage message) {
        readMessage(message);
    }
}
