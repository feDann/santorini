package it.polimi.ingsw.PSP11.controller;

import it.polimi.ingsw.PSP11.controller.state.GameState;
import it.polimi.ingsw.PSP11.controller.state.SelectGameGodsState;
import it.polimi.ingsw.PSP11.messages.ControllerMessage;
import it.polimi.ingsw.PSP11.messages.NotYourTurnMessage;
import it.polimi.ingsw.PSP11.messages.WinMessage;
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

    private void endGame(){
        String nickname = game.getCurrentPlayer().getNickname();
        currentPlayers.get(nickname).killGame(nickname);
        currentPlayers.clear();
    }

    private void looseConditionHandler() {
        if (game.getNumOfPlayers() == 2){
            game.nextPlayer();
            currentPlayers.get(game.getCurrentPlayer().getNickname()).asyncSend(new WinMessage());
            endGame();
        }
        if (game.getNumOfPlayers() == 3){

        }
    }



    public synchronized void readMessage(ControllerMessage message){
        requestingView = message.getVirtualView();
        requestingPlayer = requestingView.getPlayer().getName();
        if (requestingPlayer.equals(game.getCurrentPlayer().getNickname())){
            gameState = gameState.execute(message.getMessage(),requestingView);
            currentPlayers.get(game.getCurrentPlayer().getNickname()).asyncSend(gameState.stateMessage());
            //TODO
            if (game.isThereIsALooser()){
                looseConditionHandler();
            }
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
