package it.polimi.ingsw.PSP11.controller;

import it.polimi.ingsw.PSP11.controller.state.GameState;
import it.polimi.ingsw.PSP11.controller.state.SelectGameGodsState;
import it.polimi.ingsw.PSP11.messages.ControllerMessage;
import it.polimi.ingsw.PSP11.messages.NotYourTurnMessage;
import it.polimi.ingsw.PSP11.messages.SelectGameGodsRequest;
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
            gameState = gameState.execute(message.getMessage());
            currentPlayers.get(game.getCurrentPlayer().getNickname()).asyncSend(gameState.stateMessage());
            //chiama execute
            //chiama la remote view e non insulta il giocatore
        }
        else{
            requestingView.sendMessage(new NotYourTurnMessage(game.getCurrentPlayer().getNickname()));
        }
    }


    public void start (){
        ClientSocketConnection firstPlayerConnection;
        game.startGame();
        this.gameState = new SelectGameGodsState(game, gameState);
        firstPlayerConnection = currentPlayers.get(game.getCurrentPlayer().getNickname());
        firstPlayerConnection.asyncSend(gameState.stateMessage());
    }

    @Override
    public void update(ControllerMessage message) {
        readMessage(message);
    }
}
